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

/**
 * QVTcoreMREvaluationVisitor is the class for ...
 */
public class QVTimperativeMREvaluationVisitor extends QVTimperativeAbstractEvaluationVisitor
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

    @Override
    public @NonNull EvaluationVisitor createNestedEvaluator() {
        Environment environment = getEnvironment();
        EnvironmentFactory factory = environment.getFactory();
        EvaluationEnvironment nestedEvalEnv = factory.createEvaluationEnvironment(getEvaluationEnvironment());
        QVTimperativeMREvaluationVisitor ne = new QVTimperativeMREvaluationVisitor(environment, nestedEvalEnv, getModelManager());
        return ne;
    }

    /* (non-Javadoc)
     * @see org.eclipse.qvtd.pivot.qvtcore.evaluation.QVTcoreAbstractEvaluationVisitorImpl#visitBottomPattern(org.eclipse.qvtd.pivot.qvtcore.BottomPattern)
     */
    @Override
    public @Nullable Object visitBottomPattern(@NonNull BottomPattern bottomPattern) {
        
        //Map<Variable, Set<Object>> patternValidBindings = new HashMap<>();
        Area area = bottomPattern.getArea();
        if (area instanceof CoreDomain) {
            for (RealizedVariable rVar : bottomPattern.getRealizedVariable()) {
                rVar.accept(this);
            }
            for (Assignment assigment : bottomPattern.getAssignment()) {
                assigment.accept(this);
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
            /*assert bottomPattern.getRealizedVariable().size() == 0 : "Unsupported " + bottomPattern.eClass().getName() + " defines 1 or more realized variables.";
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
            }*/
            for (Assignment assigment : bottomPattern.getAssignment()) {
                assigment.accept(this);
            }
            for (EnforcementOperation enforceOp : bottomPattern
                    .getEnforcementOperation()) {
                enforceOp.accept(this);
            }
        }
        //return patternValidBindings;
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
        
    	/*// THERE SHULD BE NO GUARD PATTERN IN THE R CoreDomain
        coreDomain.getGuardPattern().accept(this);
        */
        boolean result = (Boolean) coreDomain.getGuardPattern().accept(this);
        if (result) {
        	coreDomain.getBottomPattern().accept(this);
        }
        return result;
    }
    
    
    /* (non-Javadoc)
     * @see org.eclipse.qvtd.pivot.qvtcore.evaluation.QVTcoreAbstractEvaluationVisitorImpl#visitMapping(org.eclipse.qvtd.pivot.qvtcore.Mapping)
     */
    public @Nullable Object visitMapping(@NonNull Mapping mapping) {
        
    	if (mapping.getDomain().size() > 1) {
        	MtoRMappingError(mapping, "Max supported number of domains is 1.");
        }
        boolean result = (Boolean) mapping.getGuardPattern().accept(this);
        if (result) {
        	for (Domain domain : mapping.getDomain()) {
                result = (Boolean) domain.accept(this);
            }
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
     * MtoR mapping error.
     *
     * @param node the node
     * @param cause the cause
     */
    private void MtoRMappingError(Element node, String cause) {
        throw new IllegalArgumentException("Unsupported " + node.eClass().getName()
                + " specification in Middle to Right mapping. " + cause);
    }
}
