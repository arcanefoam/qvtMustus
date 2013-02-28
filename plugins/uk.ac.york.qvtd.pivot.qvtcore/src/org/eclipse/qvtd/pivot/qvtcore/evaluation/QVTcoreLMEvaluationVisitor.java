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
import org.eclipse.qvtd.pivot.qvtcore.RealizedVariable;
import org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor;

import uk.ac.york.qvtd.library.executor.QVTcDomainManager;

public class QVTcoreLMEvaluationVisitor extends QVTcoreEvaluationVisitorImpl 
        implements QVTcoreVisitor<Object> {

    public QVTcoreLMEvaluationVisitor(@NonNull Environment env,
            @NonNull EvaluationEnvironment evalEnv,
            @NonNull DomainModelManager modelManager) {
        super(env, evalEnv, modelManager);
        // TODO Auto-generated constructor stub
    }
    
    
    @Override
    public Object visitMapping(@NonNull Mapping mapping) {
        
        Map<Variable, Set<Object>>  loopVariableValues = new HashMap<>();
        // Asserrt that there should be only one domain?
        //assert mapping.getDomain().size() == 1 : "Unsupported " + mapping.eClass().getName() + ". Max supported number of domains is 1."; 
        for (Domain domain : mapping.getDomain()) {
            try {
                loopVariableValues.putAll((Map<Variable, Set<Object>>)((CoreDomain) domain).accept(this));
            } catch (NullPointerException e) {
                // The domain guard was not met or no variables where bind
                // Maybe log this?
            }
        }
        // TODO I am not doing a Cartesian product visit of variable bindings!!! Although
        // there should be only 1 variable binding per domain. Do we assert that there
        // should be only one variable?
        //assert loopVariableValues.size() == 1 : "Unsupported " + mapping.eClass().getName() + ". Nested domains provided more than 1 variable binding.";
        if (loopVariableValues.size() > 0) {
            for (Map.Entry<Variable, Set<Object>> entry : loopVariableValues.entrySet()) {
                Variable var = entry.getKey();
                for (Object e : entry.getValue()) {
                    // Use each of the bindings for evaluation in the loop
                    getEvaluationEnvironment().replace(var, e);
                    // TODO Implement guard visit methods
                    // boolean guardMet = (Boolean)mapping.getGuardPattern().accept(this);
                    //if(guardMet) {
                    //    MiddleBottomPattern (aka where clause)
                        mapping.getBottomPattern().accept(this);
                    //}
                    // Nested mappings
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
        /*
         * LtoM Mapping. The bottomPattern belongs to a Domain, get all possible
         * variable bindings from the L model and then use the predicates to leave
         * only the valid bindings
         */
        Object visited = super.visitBottomPattern(bottomPattern);
        if (visited != null) {
            Area area = bottomPattern.getArea();
            if (area instanceof CoreDomain) {
                Map<Variable, Set<Object>> patternValidBindings = new HashMap<>();
                for (Variable var : bottomPattern.getVariable()) {
                    // Add the variable to the environment so we can assign it a value
                    // later
                    getEvaluationEnvironment().add(var, null);
                    // We are in a L->M
                    // Values of variables in a CoreDomain exist in the domain's TypedModel
                    TypedModel m = ((CoreDomain)area).getTypedModel();
                    Set<Object> bindingValuesSet = ((QVTcDomainManager) modelManager).getElementsByType(m, var.getType());
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
             * LtoM Mapping. The bottomPattern belongs to a Mapping and it is visited once per
             * binding of the L domain. The bottom pattern should have the realized variables of the
             * middle model. Use the assignments to set values to their properties
             * 
             */
            else if (area instanceof Mapping) {
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
    
    // IT WAS IDENTICAL TO THE OTHER CASE SO NOT NEEDED
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitPropertyAssignment
     * (org.eclipse.qvtd.pivot.qvtcore.PropertyAssignment)
     */
    /*@Override
    @Nullable
    public Object visitPropertyAssignment(@NonNull PropertyAssignment propertyAssignment) {
        
        OCLExpression slotExp = propertyAssignment.getSlotExpression(); 
        //LtoM or MtoR Mapping. Property assignments are in the mapping's bottom pattern
        // and define values for middle model element's attributes. The property
        // assignments are being visited for each binding of variable in the mapping. 
        // TODO Assignments in check domains should be treated as predicates.
        Area area = ((BottomPattern)propertyAssignment.eContainer()).getArea();
        if (area instanceof Mapping && ((Mapping)area).getDomain().size() != 0) {
            if (slotExp instanceof VariableExp ) {      // What other type of expressions are there?
                Variable slotVar = (Variable) ((VariableExp)slotExp).getReferredVariable();
                if(slotVar != null) {
                 // Assign the value to a binding of the slotVar
                    Object slotBinding = getEvaluationEnvironment().getValueOf(slotVar);
                    if (slotBinding != null) {
                        // The nested evaluation environment is created in the mapping loop
                        Object value = safeVisit(propertyAssignment.getValue());
                        // TODO what happens if the target property is not a simple attribute?
                        Property p = propertyAssignment.getTargetProperty();
                        p.initValue(slotBinding, value);
                    } else {
                        throw new IllegalArgumentException("Unsupported " + propertyAssignment.eClass().getName()
                                + " specification. The referred variable of the slot expression (" + slotExp.getType().getName() 
                                + ") was not found.");
                    }
                    
                }
                
            } else {
                super.visitPropertyAssignment(propertyAssignment);
            }
        }
        return true;
    }*/
    
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
        
        // LtoM Mapping. Realized variables are in the mapping's bottom pattern
        // and create elements in the middle model. The realized variables
        // are being visited for each binding of variable in the mapping. 
        Area area = ((BottomPattern)realizedVariable.eContainer()).getArea();
        if (area instanceof Mapping) {
            Object element =  realizedVariable.getType().createInstance();
            ((QVTcDomainManager)modelManager).addModelElement(
                    QVTcDomainManager.MIDDLE_MODEL, element);
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
