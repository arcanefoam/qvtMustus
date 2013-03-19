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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.pivot.Element;
import org.eclipse.ocl.examples.pivot.Environment;
import org.eclipse.ocl.examples.pivot.Variable;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.qvtd.pivot.qvtbase.Domain;
import org.eclipse.qvtd.pivot.qvtcorebase.Area;
import org.eclipse.qvtd.pivot.qvtcorebase.Assignment;
import org.eclipse.qvtd.pivot.qvtcorebase.BottomPattern;
import org.eclipse.qvtd.pivot.qvtcorebase.CoreDomain;
import org.eclipse.qvtd.pivot.qvtcorebase.EnforcementOperation;
import org.eclipse.qvtd.pivot.qvtcorebase.RealizedVariable;
import org.eclipse.qvtd.pivot.qvtimperative.Mapping;
import org.eclipse.qvtd.pivot.qvtimperative.MappingCall;
import org.eclipse.qvtd.pivot.qvtimperative.util.QVTimperativeVisitor;

// TODO: Auto-generated Javadoc
/**
 * QVTimperativeLMEvaluationVisitor is the class for ...
 */
public class QVTimperativeLMEvaluationVisitor extends QVTimperativeEvaluationVisitorImpl 
        implements QVTimperativeVisitor<Object> {

    /**
     * Instantiates a new QVTcore LM Evaluation Visitor.
     *
     * @param env the environment
     * @param evalEnv the evaluation environment
     * @param modelManager the model manager
     */
    public QVTimperativeLMEvaluationVisitor(@NonNull Environment env,
            @NonNull EvaluationEnvironment evalEnv,
            @NonNull DomainModelManager modelManager) {
        super(env, evalEnv, modelManager);
    }
    

    /* (non-Javadoc)
     * @see uk.ac.york.qvtd.pivot.qvtcorebase.evaluation.QVTcoreBaseEvaluationVisitorImpl#visitBottomPattern(org.eclipse.qvtd.pivot.qvtcorebase.BottomPattern)
     */
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
        
        Map<Variable, List<Object>>  guardBindings =  new HashMap<>();
        guardBindings.putAll((Map<Variable, List<Object>>) coreDomain.getGuardPattern().accept(this));
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
    
    
    /* (non-Javadoc)
     * @see uk.ac.york.qvtd.pivot.qvtimperative.evaluation.QVTimperativeAbstractEvaluationVisitorImpl#visitMapping(org.eclipse.qvtd.pivot.qvtimperative.Mapping)
     */
    @Override
    public Object visitMapping(@NonNull Mapping mapping) {
        
        assert mapping.getDomain().size() == 1 : "Unsupported "
                + mapping.eClass().getName() + ". Max supported number of domains is 1.";
        Map<Variable, List<Object>>  mappingBindings = new HashMap<>();
        for (Domain domain : mapping.getDomain()) {
            mappingBindings.putAll((Map<Variable, List<Object>>)domain.accept(this));
            assert mappingBindings.size() == 1 : "Unsupported " 
                    + mapping.eClass().getName() + ". Nested domains provided more than 1 variable binding.";
        }
        /* Do we have guard patterns in LM mappings?
        for (Map.Entry<Variable, Set<Object>> domainEntry : mappingBindings.entrySet()) {
            Variable domainVar = domainEntry.getKey();
            for (Object binding : domainEntry.getValue()) {
                getEvaluationEnvironment().replace(domainVar, binding);
                mappingBindings.putAll((Map<Variable, Set<Object>>) mapping.getGuardPattern().accept(this));
            }
        }
        */
        for (Map.Entry<Variable, List<Object>> mappingBindingEntry : mappingBindings.entrySet()) {
            Variable var = mappingBindingEntry.getKey();
            for (Object binding : mappingBindingEntry.getValue()) {
                getEvaluationEnvironment().replace(var, binding);
                finishMappingVisit(mapping);
            }
            
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see uk.ac.york.qvtd.pivot.qvtimperative.evaluation.QVTimperativeAbstractEvaluationVisitorImpl#visitMappingCall(org.eclipse.qvtd.pivot.qvtimperative.MappingCall)
     */
    @Override
    @Nullable
    public Object visitMappingCall(@NonNull MappingCall mappingCall) {
        
        if (isLtoMMapping(mappingCall.getReferredMapping())) {
            // Verify that the invoked mapping variables (in domain and mapping guards) are defined in the
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
            for (Variable variable : mappingCall.getReferredMapping().getGuardPattern().getVariable()) {
                Object value = getEvaluationEnvironment().getValueOf(variable);
                if (value == null) {
                    throw new IllegalArgumentException("Unsupported " + mappingCall.eClass().getName()
                            + " specification. Referenced mapping variables not bound.");
                }
            }
            finishMappingVisit(mappingCall.getReferredMapping());
        } else if (isMtoRMapping(mappingCall.getReferredMapping())) {
            QVTimperativeMREvaluationVisitor MRVisitor = new QVTimperativeMREvaluationVisitor(
                    getEnvironment(), getEvaluationEnvironment(), modelManager);
            mappingCall.accept(MRVisitor);
        }
        return null;
    }
    
    /**
     * Lto m mapping error.
     *
     * @param node the node
     * @param cause the cause
     */
    private void LtoMMappingError(Element node, String cause) {
        throw new IllegalArgumentException("Unsupported " + node.eClass().getName()
                + " specification in Left to Middle mapping. " + cause);
    }

}
