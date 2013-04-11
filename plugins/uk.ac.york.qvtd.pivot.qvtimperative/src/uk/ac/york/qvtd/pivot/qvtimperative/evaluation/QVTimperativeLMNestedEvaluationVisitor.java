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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.pivot.Element;
import org.eclipse.ocl.examples.pivot.Environment;
import org.eclipse.ocl.examples.pivot.EnvironmentFactory;
import org.eclipse.ocl.examples.pivot.Variable;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitor;
import org.eclipse.qvtd.pivot.qvtbase.Domain;
import org.eclipse.qvtd.pivot.qvtbase.Predicate;
import org.eclipse.qvtd.pivot.qvtbase.TypedModel;
import org.eclipse.qvtd.pivot.qvtcorebase.Area;
import org.eclipse.qvtd.pivot.qvtcorebase.Assignment;
import org.eclipse.qvtd.pivot.qvtcorebase.BottomPattern;
import org.eclipse.qvtd.pivot.qvtcorebase.CoreDomain;
import org.eclipse.qvtd.pivot.qvtcorebase.EnforcementOperation;
import org.eclipse.qvtd.pivot.qvtcorebase.GuardPattern;
import org.eclipse.qvtd.pivot.qvtcorebase.RealizedVariable;
import org.eclipse.qvtd.pivot.qvtimperative.Mapping;
import org.eclipse.qvtd.pivot.qvtimperative.MappingCall;
import org.eclipse.qvtd.pivot.qvtimperative.util.QVTimperativeVisitor;

import uk.ac.york.qvtd.library.executor.QVTcDomainManager;

// TODO: Auto-generated Javadoc
/**
 * QVTimperativeLMEvaluationVisitor is the class for ...
 */
public class QVTimperativeLMNestedEvaluationVisitor extends QVTimperativeAbstractEvaluationVisitor 
        implements QVTimperativeVisitor<Object> {

    /**
     * Instantiates a new QVTcore LM Evaluation Visitor.
     *
     * @param env the environment
     * @param evalEnv the evaluation environment
     * @param modelManager the model manager
     */
    public QVTimperativeLMNestedEvaluationVisitor(@NonNull Environment env,
            @NonNull EvaluationEnvironment evalEnv,
            @NonNull DomainModelManager modelManager) {
        super(env, evalEnv, modelManager);
    }

    @Override
    public @NonNull EvaluationVisitor createNestedEvaluator() {
        Environment environment = getEnvironment();
        EnvironmentFactory factory = environment.getFactory();
        EvaluationEnvironment nestedEvalEnv = factory.createEvaluationEnvironment(getEvaluationEnvironment());
        QVTimperativeLMNestedEvaluationVisitor ne = new QVTimperativeLMNestedEvaluationVisitor(environment, nestedEvalEnv, getModelManager());
        return ne;
    }

    /* (non-Javadoc)
     * @see uk.ac.york.qvtd.pivot.qvtcorebase.evaluation.QVTcoreBaseEvaluationVisitorImpl#visitBottomPattern(org.eclipse.qvtd.pivot.qvtcorebase.BottomPattern)
     */
    @Override
    public @Nullable Object visitBottomPattern(@NonNull BottomPattern bottomPattern) {
        
        Area area = bottomPattern.getArea();
        /* L Core Domains should not have anything to visit
        if (area instanceof CoreDomain) {
            // The bottom pattern of an L CoreDomain should not have any variables or constraints
            assert bottomPattern.getVariable().size() == 0 : "Error: BottomPattern of L Coredomain has variables.";
            assert bottomPattern.getPredicate().size() == 0 : "Error: BottomPattern of L CoreDomain has constraints.";
        }
        // LtoM Mapping. The bottomPattern belongs to a Mapping and it is visited once per
        // binding of the L domain. The bottom pattern should have the realized variables of the
        // middle model. Use the assignments to set values to their properties
        else*/
        if (area instanceof Mapping) {
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
    public @Nullable Object visitCoreDomain(@NonNull CoreDomain coreDomain) {
        
        /* Bindings are set by the caller, just test the predicates */
    	return coreDomain.getGuardPattern().accept(this);
        /* THERE SHOULD BE NO VARIABLES OR PREDICATES IN THE BottomPattern
        for (Map.Entry<Variable, Set<Object>> entry : guardBindings.entrySet()) {
            Variable var = entry.getKey();
            for (Object e : entry.getValue()) {
                getEvaluationEnvironment().replace(var, e);
                coreDomain.getBottomPattern().accept(this); 
            }
        }*/
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitGuardPattern(
     * org.eclipse.qvtd.pivot.qvtcore.GuardPattern)
     */
    @Override
    public @Nullable Object visitGuardPattern(@NonNull GuardPattern guardPattern) {
        
        // The bindings are already defined, test the constraints
        boolean result = true;
        for (Predicate predicate : guardPattern.getPredicate()) {
            // If the predicate is not true, the binding is not valid
            result = (Boolean) predicate.accept(this);
            if (!result) {
            	break;
            }
        }
        return result;        
    }
    
    
    /* (non-Javadoc)
     * @see uk.ac.york.qvtd.pivot.qvtimperative.evaluation.QVTimperativeAbstractEvaluationVisitorImpl#visitMapping(org.eclipse.qvtd.pivot.qvtimperative.Mapping)
     */
    public @Nullable Object visitMapping(@NonNull Mapping mapping) {
        
    	if (mapping.getDomain().size() > 1) {
    		LtoMNestedMappingError(mapping, "Max supported number of domains is 1.");
        }
    	boolean result = false;
        for (Domain domain : mapping.getDomain()) {
            result = (Boolean) domain.accept(this);
        }
        if (result) {
        	result = (Boolean) mapping.getGuardPattern().accept(this);
            if (result) {
            	mapping.getBottomPattern().accept(this);
            	for (MappingCall mappingCall : mapping.getMappingCall()) {
                	mappingCall.accept(this);
                }
            }
        }
        return null;
    }
    
    /**
     * Lto m mapping error.
     *
     * @param node the node
     * @param cause the cause
     */
    private void LtoMNestedMappingError(Element node, String cause) {
        throw new IllegalArgumentException("Unsupported " + node.eClass().getName()
                + " specification in Left to Middle nested mapping. " + cause);
    }

}
