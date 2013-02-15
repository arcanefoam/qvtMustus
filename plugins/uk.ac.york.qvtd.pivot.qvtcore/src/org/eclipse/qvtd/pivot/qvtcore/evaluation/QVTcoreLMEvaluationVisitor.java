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
        
        for (Domain domain : mapping.getDomain()) {
            @SuppressWarnings("unchecked")
            Map<Variable, Set<Object>>  loopVariableValues = (Map<Variable, Set<Object>>)((CoreDomain) domain).accept(this);
            if (loopVariableValues != null) {
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

}
