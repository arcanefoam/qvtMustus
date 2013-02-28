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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.pivot.Environment;
import org.eclipse.ocl.examples.pivot.Variable;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.qvtd.pivot.qvtbase.Domain;
import org.eclipse.qvtd.pivot.qvtbase.Predicate;
import org.eclipse.qvtd.pivot.qvtbase.TypedModel;
import org.eclipse.qvtd.pivot.qvtcore.Area;
import org.eclipse.qvtd.pivot.qvtcore.Assignment;
import org.eclipse.qvtd.pivot.qvtcore.BottomPattern;
import org.eclipse.qvtd.pivot.qvtcore.CoreDomain;
import org.eclipse.qvtd.pivot.qvtcore.EnforcementOperation;
import org.eclipse.qvtd.pivot.qvtcore.Mapping;
import org.eclipse.qvtd.pivot.qvtcore.NestedMapping;
import org.eclipse.qvtd.pivot.qvtcore.PropertyAssignment;
import org.eclipse.qvtd.pivot.qvtcore.RealizedVariable;
import org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor;

import uk.ac.york.qvtd.library.executor.QVTcDomainManager;

public class QVTcoreMREvaluationVisitor extends QVTcoreEvaluationVisitorImpl
        implements QVTcoreVisitor<Object> {

    public QVTcoreMREvaluationVisitor(@NonNull Environment env,
            @NonNull EvaluationEnvironment evalEnv,
            @NonNull DomainModelManager modelManager) {
        super(env, evalEnv, modelManager);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public Object visitMapping(@NonNull Mapping mapping) {
        
        Map<Variable, Set<Object>>  loopVariableValues = new HashMap<>();
        // TODO Implement guard visit methods
        // boolean guardMet = (Boolean)mapping.getGuardPattern().accept(this);
        //if(guardMet) {
        //    MiddleBottomPattern (aka where clause)
            //@SuppressWarnings("unchecked")
            try {
                loopVariableValues.putAll((Map<Variable, Set<Object>>)mapping.getBottomPattern().accept(this));
            } catch (NullPointerException e) {
                // The bottom pattern no variables where bind
                // Maybe log this?
            }
        //}
        // TODO I am not doing a Cartesian product visit of variable bindings!!! Although
        // there should be only 1 variable binding per domain. Do we assert that there
        // should be only one variable?
        //assert loopVariableValues.size() == 1 : "Unsupported " + mapping.eClass().getName() + ". Nested domains provided more than 1 variable binding.";
        if (loopVariableValues.size() > 0) {
            for (Variable var : loopVariableValues.keySet()) {
                for (Object e : loopVariableValues.get(var)) {
                    // Use each of the bindings for evaluation in the loop
                    getEvaluationEnvironment().replace(var, e);
                    for (Domain domain : mapping.getDomain()) {
                        ((CoreDomain) domain).accept(this);
                    }
                    for (NestedMapping localMapping : mapping.getLocal()) {
                        localMapping.accept(this);
                    }
                }
            }
        }
        return true;
    }
    
    @Override
    public Object visitBottomPattern(@NonNull BottomPattern bottomPattern) {
        
        Object visited = super.visitBottomPattern(bottomPattern);
        if (visited != null) {
            Area area = bottomPattern.getArea();
            /*
             * MtoR Mapping. The bottomPattern belongs to a mapping, get all possible
             * variable bindings from the middle model and then use the predicates to leave
             * only the valid bindings
             */
            if (area instanceof Mapping) {
                Map<Variable, Set<Object>> patternValidBindings = new HashMap<>();
                for (Variable var : bottomPattern.getVariable()) {
                    // We are in a M->R
                    // Values of variables in a where clause exist in the middle model
                    Set<Object> bindingValuesSet = ((QVTcDomainManager) modelManager).getElementsByType(
                            QVTcDomainManager.MIDDLE_MODEL, var.getType());
                    patternValidBindings.put(var, bindingValuesSet);
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
                                    // If the predicates fails, the binding is not valid
                                    it.remove();
                                    break;
                                }
                            }
                        }
                    }
                }
                return patternValidBindings;
            }
            /*
             * MtoRM Mapping. The bottomPattern belongs to a CoreDomain and it is visited once per
             * binding of the middle model. The bottom pattern should have the realized variables of the
             * R model. Use the assignments to set values to their properties
             * 
             */
            else if (area instanceof CoreDomain) {
                for (RealizedVariable rVar : bottomPattern.getRealizedVariable()) {
                    if (bottomPattern.getArea() instanceof CoreDomain && !((CoreDomain)bottomPattern.getArea()).isIsEnforceable()) {
                        throw new UnsupportedOperationException("Unsupported " + bottomPattern.eClass().getName()
                                + " specification. Realized variables can only exist in Enforced domains");
                    }
                    rVar.accept(this);
                }
                for (Assignment assigment : bottomPattern.getAssignment()) {
                    assigment.accept(this);
                }
                // Probably enforcement operations must be called too
                for (EnforcementOperation enforceOp : bottomPattern
                        .getEnforcementOperation()) {
                    enforceOp.accept(this);
                }
            }
        }
        return null;
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
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitRealizedVariable
     * (org.eclipse.qvtd.pivot.qvtcore.RealizedVariable)
     */
    @Override
    @Nullable
    public Object visitRealizedVariable(@NonNull RealizedVariable realizedVariable) {
        
        // MtoR Mapping. Realized variables are in the R core domain bottom pattern
        // and create elements in the R model. The realized variables
        // are being visited for each binding of variable in the mapping. 
        Area area = ((BottomPattern)realizedVariable.eContainer()).getArea();
        if (area instanceof CoreDomain) {
            TypedModel tm = ((CoreDomain)area).getTypedModel();
            // Create an element in the R model that has a kind equal to the variable type
            Object element = realizedVariable.getType().createInstance();
            // Add the element to the R resource
            ((QVTcDomainManager)modelManager).addModelElement(tm, element);
            // Add the realize variable binding to the environment
            if (getEvaluationEnvironment().getValueOf(realizedVariable) == null) {
                getEvaluationEnvironment().add(realizedVariable, element);
            } else {
                getEvaluationEnvironment().replace(realizedVariable, element);
            }
            return element;
        }
        return null;
    }

}
