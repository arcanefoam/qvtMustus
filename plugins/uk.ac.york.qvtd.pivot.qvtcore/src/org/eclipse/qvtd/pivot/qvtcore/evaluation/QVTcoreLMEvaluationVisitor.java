/*******************************************************************************
 * Copyright (c) 2013 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     hhoyos - initial API and implementation
 ******************************************************************************/
package org.eclipse.qvtd.pivot.qvtcore.evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.domain.values.CollectionValue;
import org.eclipse.ocl.examples.pivot.Element;
import org.eclipse.ocl.examples.pivot.Environment;
import org.eclipse.ocl.examples.pivot.OCLExpression;
import org.eclipse.ocl.examples.pivot.Variable;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.qvtd.pivot.qvtbase.Domain;
import org.eclipse.qvtd.pivot.qvtcore.Area;
import org.eclipse.qvtd.pivot.qvtcore.Assignment;
import org.eclipse.qvtd.pivot.qvtcore.BottomPattern;
import org.eclipse.qvtd.pivot.qvtcore.CoreDomain;
import org.eclipse.qvtd.pivot.qvtcore.EnforcementOperation;
import org.eclipse.qvtd.pivot.qvtcore.Mapping;
import org.eclipse.qvtd.pivot.qvtcore.MappingCall;
import org.eclipse.qvtd.pivot.qvtcore.MappingCallBinding;
import org.eclipse.qvtd.pivot.qvtcore.NestedMapping;
import org.eclipse.qvtd.pivot.qvtcore.RealizedVariable;
import org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor;

public class QVTcoreLMEvaluationVisitor extends QVTcoreAbstractEvaluationVisitorImpl 
        implements QVTcoreVisitor<Object> {

    /**
     * Instantiates a new QVTcore LM Evaluation Visitor.
     *
     * @param env the environment
     * @param evalEnv the evaluation environment
     * @param modelManager the model manager
     */
    public QVTcoreLMEvaluationVisitor(@NonNull Environment env,
            @NonNull EvaluationEnvironment evalEnv,
            @NonNull DomainModelManager modelManager) {
        super(env, evalEnv, modelManager);
    }
    

    @Override
    public Object visitBottomPattern(@NonNull BottomPattern bottomPattern) {
        
        Area area = bottomPattern.getArea();
        if (area instanceof CoreDomain) {
            // The bottom pattern of an L CoreDomain should not have any variables or constraints
            assert bottomPattern.getVariable().size() == 0 : "Error: BottomPattern of L Coredomain has variables.";
            assert bottomPattern.getPredicate().size() == 0 : "Error: BottomPattern of L CoreDomain has constraints.";
        }
        // LtoM Mapping. The bottomPattern belongs to a Mapping and it is visited once per
        // binding of the L domain. The bottom pattern should have the realized variables of the
        // middle model. Use the assignments to set values to their properties
        else if (area instanceof Mapping) {
            for (RealizedVariable rVar : bottomPattern.getRealizedVariable()) {
                rVar.accept(this);
            }
            for (Assignment assigment : bottomPattern.getAssignment()) {
                assigment.accept(this);
            }
            for (EnforcementOperation enforceOp : bottomPattern
                    .getEnforcementOperation()) {
                enforceOp.accept(this);
            }
        }
        return null;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitCoreDomain(org
     * .eclipse.qvtd.pivot.qvtcore.CoreDomain)
     */
    @Nullable
    public Object visitCoreDomain(@NonNull CoreDomain coreDomain) {
        
        Map<Variable, Set<Object>>  guardBindings =  new HashMap<>();
        guardBindings.putAll((Map<Variable, Set<Object>>) coreDomain.getGuardPattern().accept(this));
        /* THERE SHOULD BE NO VARIABLES OR PREDICATES IN THE BottomPattern
        for (Map.Entry<Variable, Set<Object>> entry : guardBindings.entrySet()) {
            Variable var = entry.getKey();
            for (Object e : entry.getValue()) {
                getEvaluationEnvironment().replace(var, e);
                coreDomain.getBottomPattern().accept(this); 
            }
        }*/
        return guardBindings;
    }
    
    
    @Override
    public Object visitMapping(@NonNull Mapping mapping) {
        
        assert mapping.getDomain().size() == 1 : "Unsupported "
                + mapping.eClass().getName() + ". Max supported number of domains is 1.";
        Map<Variable, Set<Object>>  domainBindings = new HashMap<>();
        for (Domain domain : mapping.getDomain()) {
            domainBindings.putAll((Map<Variable, Set<Object>>)domain.accept(this));
            assert domainBindings.size() == 1 : "Unsupported " 
                    + mapping.eClass().getName() + ". Nested domains provided more than 1 variable binding.";
        }
        for (Map.Entry<Variable, Set<Object>> domainEntry : domainBindings.entrySet()) {
            Variable domainVar = domainEntry.getKey();
            Map<Variable, Set<Object>>  guardBindings = new HashMap<>();
            for (Object binding : domainEntry.getValue()) {
                getEvaluationEnvironment().replace(domainVar, binding);
                guardBindings.putAll((Map<Variable, Set<Object>>) mapping.getGuardPattern().accept(this));
            }
            for (Map.Entry<Variable, Set<Object>> mappingEntry : guardBindings.entrySet()) {
                Variable var = mappingEntry.getKey();
                for (Object binding : mappingEntry.getValue()) {
                    getEvaluationEnvironment().replace(var, binding);
                    mapping.getBottomPattern().accept(this);
                }
                for (NestedMapping localMapping : mapping.getLocal()) {
                    if (localMapping.getClass().isInstance(MappingCall.class)) {
                        List<List<Map<Variable, Object>>> bindingCartesian = new ArrayList<>();
                        for (MappingCallBinding binding : ((MappingCall)localMapping).getBinding()) {
                            OCLExpression value = binding.getValue();
                            Object result = safeVisit(value);
                            List<Map<Variable, Object>> bindingValues = new ArrayList<>();
                            if (result.getClass().isInstance(CollectionValue.class)) {
                                // Create a binding for each of the elements in the collection
                                for (Object resValue : ((CollectionValue)result).asCollection()) {
                                    Map<Variable, Object> varValue = new HashMap<>();
                                    varValue.put(var, resValue);
                                    bindingValues.add(varValue);
                               }
                            } else {
                                Map<Variable, Object> varValue = new HashMap<>();
                                varValue.put(var, result);
                                bindingValues.add(varValue);
                            }
                            bindingCartesian.add(bindingValues);
                        }
                        // Calculate the Cartesian list of bindings
                        List<List<Map<Variable, Object>>> cartesian = cartesianBindings(bindingCartesian);
                        for (List<Map<Variable, Object>> bindings : cartesian) {
                            for (Map<Variable, Object> binding : bindings) {
                                Iterator<Entry<Variable, Object>> it = binding.entrySet().iterator();
                                while (it.hasNext()) {
                                    Map.Entry<Variable, Object> pairs = (Map.Entry<Variable, Object>)it.next();
                                    getEvaluationEnvironment().replace(pairs.getKey(), pairs.getValue());
                                }
                            }
                            localMapping.accept(this);
                        }
                    } else if (localMapping.getClass().isInstance(Mapping.class)) {
                        localMapping.accept(this);
                    }
                }
            }
        }
        return true;
    }
    
    @Override
    @Nullable
    public Object visitMappingCall(@NonNull MappingCall mappingCall) {
        
        // Verify that the invoked mapping variables are defined in the
        // environment
        for (Domain domain : mappingCall.getReferredMapping().getDomain()) {
            for(Variable variable : ((CoreDomain)domain).getGuardPattern().getVariable()) {
                Object value = getEvaluationEnvironment().getValueOf(variable);
                if (value == null) {
                    throw new IllegalArgumentException("Unsupported " + mappingCall.eClass().getName()
                            + " specification. Referenced mapping variables not bound.");
                }
            }
        }
        for (Variable variable : mappingCall.getReferredMapping().getBottomPattern().getVariable()) {
            Object value = getEvaluationEnvironment().getValueOf(variable);
            if (value == null) {
                throw new IllegalArgumentException("Unsupported " + mappingCall.eClass().getName()
                        + " specification. Referenced mapping variables not bound.");
            }
        }
        
        
        return null;
    }
    
    private void LtoMMappingError(Element node, String cause) {
        throw new IllegalArgumentException("Unsupported " + node.eClass().getName()
                + " specification in Left to Middle mapping. " + cause);
    }

}
