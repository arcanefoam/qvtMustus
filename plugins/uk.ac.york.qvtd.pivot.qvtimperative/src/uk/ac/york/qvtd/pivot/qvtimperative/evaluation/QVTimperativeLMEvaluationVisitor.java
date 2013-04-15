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
import org.eclipse.ocl.examples.pivot.EnvironmentFactory;
import org.eclipse.ocl.examples.pivot.Variable;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitor;
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
public class QVTimperativeLMEvaluationVisitor extends QVTimperativeAbstractEvaluationVisitor 
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

    @Override
    public @NonNull EvaluationVisitor createNestedEvaluator() {
        Environment environment = getEnvironment();
        EnvironmentFactory factory = environment.getFactory();
        EvaluationEnvironment nestedEvalEnv = factory.createEvaluationEnvironment(getEvaluationEnvironment());
        QVTimperativeLMEvaluationVisitor ne = new QVTimperativeLMEvaluationVisitor(environment, nestedEvalEnv, getModelManager());
        return ne;
    }

    /* (non-Javadoc)
     * @see uk.ac.york.qvtd.pivot.qvtcorebase.evaluation.QVTcoreBaseEvaluationVisitorImpl#visitBottomPattern(org.eclipse.qvtd.pivot.qvtcorebase.BottomPattern)
     */
    @Override
    public @Nullable Object visitBottomPattern(@NonNull BottomPattern bottomPattern) {
        
        Area area = bottomPattern.getArea();
        /* CoreDomain BottomPatterns should never be visited
        if (area instanceof CoreDomain) {
            // The bottom pattern of an L CoreDomain should not have any variables or constraints
        	if (bottomPattern.getVariable().size() != 0) {
        		LtoMMappingError(bottomPattern, "BottomPattern of L CoreDomain defined 1 or more variables.");
        	}
        	if (bottomPattern.getPredicate().size() != 0) {
        		LtoMMappingError(bottomPattern, "BottomPattern of L CoreDomain defined 1 or more predicates.");
        	}
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
    
    
    /* (non-Javadoc)
     * @see uk.ac.york.qvtd.pivot.qvtimperative.evaluation.QVTimperativeAbstractEvaluationVisitorImpl#visitMapping(org.eclipse.qvtd.pivot.qvtimperative.Mapping)
     */
    public @Nullable Object visitMapping(@NonNull Mapping mapping) {
        
    	if (mapping.getDomain().size() > 1) {
    		LtoMMappingError(mapping, "Max supported number of domains is 1.");
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
     * L to M Mapping error.
     *
     * @param node the node
     * @param cause the cause
     */
    private void LtoMMappingError(Element node, String cause) {
    	// TODO add logger
        throw new IllegalArgumentException("Unsupported " + node.eClass().getName()
                + " specification in Left to Middle mapping. " + cause);
    }

}
