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
package uk.ac.york.qvtd.pivot.qvtimperative.evaluation;

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
import org.eclipse.qvtd.pivot.qvtbase.Predicate;
import org.eclipse.qvtd.pivot.qvtcorebase.Area;
import org.eclipse.qvtd.pivot.qvtcorebase.BottomPattern;
import org.eclipse.qvtd.pivot.qvtcorebase.CoreDomain;
import org.eclipse.qvtd.pivot.qvtcorebase.PropertyAssignment;
import org.eclipse.qvtd.pivot.qvtcorebase.RealizedVariable;
import org.eclipse.qvtd.pivot.qvtimperative.Mapping;
import org.eclipse.qvtd.pivot.qvtimperative.MappingCall;
import org.eclipse.qvtd.pivot.qvtimperative.MappingCallBinding;
import org.eclipse.qvtd.pivot.qvtimperative.util.QVTimperativeVisitor;

import uk.ac.york.qvtd.library.executor.QVTcDomainManager;

// TODO: Auto-generated Javadoc
/**
 * QVTcoreMREvaluationVisitor is the class for ...
 */
public class QVTimperativeMREvaluationVisitor extends QVTimperativeAbstractEvaluationVisitorImpl
        implements QVTimperativeVisitor<Object> {

    /**
     * Instantiates a new qV tcore mr evaluation visitor.
     *
     * @param env the environment
     * @param evalEnv the evaluation environment
     * @param modelManager the model manager
     */
    public QVTimperativeMREvaluationVisitor(@NonNull Environment env,
            @NonNull EvaluationEnvironment evalEnv,
            @NonNull DomainModelManager modelManager) {
        super(env, evalEnv, modelManager);
        // TODO Auto-generated constructor stub
    }
    
    
    /* (non-Javadoc)
     * @see org.eclipse.qvtd.pivot.qvtcore.evaluation.QVTcoreAbstractEvaluationVisitorImpl#visitBottomPattern(org.eclipse.qvtd.pivot.qvtcore.BottomPattern)
     */
    @Override
    public Object visitBottomPattern(@NonNull BottomPattern bottomPattern) {
        
        Map<Variable, Set<Object>> patternValidBindings = new HashMap<>();
        Area area = bottomPattern.getArea();
        if (area instanceof CoreDomain) {
            for (RealizedVariable rVar : bottomPattern.getRealizedVariable()) {
                rVar.accept(this);
            }
            /*// There should be no assignments
            for (Assignment assigment : bottomPattern.getAssignment()) {
                assigment.accept(this);
            }*/
            /*// There should be no predicates
            for (Assignment assigment : bottomPattern.getAssignment()) {
                assigment.accept(this);
            }*/
            /* // Probably enforcement operations must be called too
            for (EnforcementOperation enforceOp : bottomPattern
                    .getEnforcementOperation()) {
                enforceOp.accept(this);
            }*/
        }
        else if (area instanceof Mapping) {
            assert bottomPattern.getRealizedVariable().size() == 0 : "Unsupported " + bottomPattern.eClass().getName() + " defines 1 or more realized variables.";
            for (Variable var : bottomPattern.getVariable()) {
                // Values of variables exist in the middle model
                Set<Object> bindingValues = ((QVTcDomainManager) modelManager).getElementsByType(
                        QVTcDomainManager.MIDDLE_MODEL, var.getType());
                patternValidBindings.put(var, bindingValues);
            }
            // For each binding visit the predicates to leave only the valid bindings
            Iterator<Entry<Variable, Set<Object>>> it = patternValidBindings.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Variable, Set<Object>> pairs = (Map.Entry<Variable, Set<Object>>)it.next();
                Iterator<Object> bindingIt = pairs.getValue().iterator();
                while (bindingIt.hasNext()) {
                    Variable var = pairs.getKey();
                    if (var != null) {
                        getEvaluationEnvironment().replace(var, bindingIt.next());
                        for (Predicate predicate : bottomPattern.getPredicate()) {
                            // If the predicate is not true, the binding is not valid
                            Boolean result = (Boolean) predicate.accept(this);
                            if (result != null && !result) {
                                it.remove();
                            }
                        }
                    }
                }
            }
        }
        return patternValidBindings;
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
        
        /*// THERE SHULD BE NO GUARD PATTERN IN THE R CoreDomain
        coreDomain.getGuardPattern().accept(this);
        */
        return coreDomain.getBottomPattern().accept(this);
    }
    
    
    /* (non-Javadoc)
     * @see org.eclipse.qvtd.pivot.qvtcore.evaluation.QVTcoreAbstractEvaluationVisitorImpl#visitMapping(org.eclipse.qvtd.pivot.qvtcore.Mapping)
     */
    @Override
    public Object visitMapping(@NonNull Mapping mapping) {
        
        if (mapping.getDomain().size() != 1) {
            MtoRMappingError(mapping, "Max supported number of domains is 1.");
        }
        if (mapping.getGuardPattern().getVariable().size() == 0) {
            mapping.getBottomPattern().accept(this);
            for (Domain domain : mapping.getDomain()) {
                domain.accept(this);
            }
        } else {
            Map<Variable, Set<Object>> guardBindings = (Map<Variable, Set<Object>>) mapping.getGuardPattern().accept(this);
            assert guardBindings.size() <= 1 : "Unsupported " 
                    + mapping.eClass().getName() + ". BottomGuardPattern provided more than 1 variable binding.";
            
            for (Map.Entry<Variable, Set<Object>> entry : guardBindings.entrySet()) {
                Variable var = entry.getKey();
                for (Object e : entry.getValue()) {
                    getEvaluationEnvironment().replace(var, e);
                    mapping.getBottomPattern().accept(this);
                    for (MappingCall mappingCall : mapping.getMappingCall()) {
                        List<List<Map<Variable, Object>>> bindingCartesian = new ArrayList<>();
                        for (MappingCallBinding binding : ((MappingCall)mappingCall).getBinding()) {
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
                            mappingCall.accept(this);
                        }
                    }
                }
            }
        }
        
        return true;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitPropertyAssignment
     * (org.eclipse.qvtd.pivot.qvtcore.PropertyAssignment)
     */
    @Override
    @Nullable
    public Object visitPropertyAssignment(@NonNull PropertyAssignment propertyAssignment) {
        
        /*
         * MtoR Mapping. Property assignments are in the mapping's bottom pattern
         * and define values for middle model element's attributes. The property
         * assignments are being visited for each binding of variable in the mapping. 
         */
        // So far this case never happens
        return super.visitPropertyAssignment(propertyAssignment);
    }

    
    private void MtoRMappingError(Element node, String cause) {
        throw new IllegalArgumentException("Unsupported " + node.eClass().getName()
                + " specification in Middle to Right mapping. " + cause);
    }
}
