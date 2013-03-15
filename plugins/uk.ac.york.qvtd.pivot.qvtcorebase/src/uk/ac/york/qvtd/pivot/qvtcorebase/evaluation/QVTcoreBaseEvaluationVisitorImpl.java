/*******************************************************************************
 * Copyright (c) 2012 The University of York and Willink Transformations.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Horacio Hoyos - initial API and implementation
 ******************************************************************************/
package uk.ac.york.qvtd.pivot.qvtcorebase.evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.pivot.Environment;
import org.eclipse.ocl.examples.pivot.EnvironmentFactory;
import org.eclipse.ocl.examples.pivot.Variable;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitor;
import org.eclipse.qvtd.pivot.qvtbase.Predicate;
import org.eclipse.qvtd.pivot.qvtbase.TypedModel;
import org.eclipse.qvtd.pivot.qvtbase.evaluation.QVTbaseEvaluationVisitorImpl;
import org.eclipse.qvtd.pivot.qvtcorebase.Area;
import org.eclipse.qvtd.pivot.qvtcorebase.Assignment;
import org.eclipse.qvtd.pivot.qvtcorebase.BottomPattern;
import org.eclipse.qvtd.pivot.qvtcorebase.CoreDomain;
import org.eclipse.qvtd.pivot.qvtcorebase.CorePattern;
import org.eclipse.qvtd.pivot.qvtcorebase.EnforcementOperation;
import org.eclipse.qvtd.pivot.qvtcorebase.GuardPattern;
import org.eclipse.qvtd.pivot.qvtcorebase.PropertyAssignment;
import org.eclipse.qvtd.pivot.qvtcorebase.RealizedVariable;
import org.eclipse.qvtd.pivot.qvtcorebase.VariableAssignment;
import org.eclipse.qvtd.pivot.qvtcorebase.util.QVTcoreBaseVisitor;

import uk.ac.york.qvtd.library.executor.QVTcDomainManager;


/**
 * QVTcoreEvaluationVisitorImpl is the class for ...
 */
public class QVTcoreBaseEvaluationVisitorImpl extends QVTbaseEvaluationVisitorImpl
        implements QVTcoreBaseVisitor<Object> {
        
    /**
     * Instantiates a new qV tcore evaluation visitor impl.
     * 
     * @param env
     *            the env
     * @param evalEnv
     *            the eval env
     * @param modelManager
     *            the model manager
     */
    public QVTcoreBaseEvaluationVisitorImpl(@NonNull Environment env,
            @NonNull EvaluationEnvironment evalEnv,
            @NonNull DomainModelManager modelManager) {
        super(env, evalEnv, modelManager);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitAssignment(org
     * .eclipse.qvtd.pivot.qvtcore.Assignment)
     */
    @Nullable
    public Object visitAssignment(@NonNull Assignment object) {
        // TODO Add visit function or decide if it should never be implemented
        throw new UnsupportedOperationException(
                "Visit method not implemented yet");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitBottomPattern
     * (org.eclipse.qvtd.pivot.qvtcore.BottomPattern)
     */
    @Nullable
    public Object visitBottomPattern(@NonNull BottomPattern bottomPattern) {
        throw new UnsupportedOperationException(
                "Visit method not implemented yet");
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
        throw new UnsupportedOperationException(
                "Visit method not implemented yet");
        
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitCorePattern(org
     * .eclipse.qvtd.pivot.qvtcore.CorePattern)
     */
    @Nullable
    public Object visitCorePattern(@NonNull CorePattern object) {
        // TODO Add visit function or decide if it should never be implemented
        throw new UnsupportedOperationException(
                "Visit method not implemented yet");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitEnforcementOperation
     * (org.eclipse.qvtd.pivot.qvtcore.EnforcementOperation)
     */
    @Nullable
    public Object visitEnforcementOperation(@NonNull EnforcementOperation object) {
        // TODO Add visit function or decide if it should never be implemented
        throw new UnsupportedOperationException(
                "Visit method not implemented yet");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitGuardPattern(
     * org.eclipse.qvtd.pivot.qvtcore.GuardPattern)
     */
    @Override
    public Object visitGuardPattern(@NonNull GuardPattern guardPattern) {
        
        Area area = guardPattern.getArea();
        Map<Variable, Set<Object>> patternValidBindings = new HashMap<>();
        assert guardPattern.getVariable().size() > 0 : "Unsupported " + guardPattern.eClass().getName() + " defines no variables.";
        for (Variable var : guardPattern.getVariable()) {
            // Add the variable to the environment so we can assign it a value later
            getEvaluationEnvironment().add(var, null);
            TypedModel m;
            if (area instanceof CoreDomain) {
                 m = ((CoreDomain)area).getTypedModel();                // L to M
            } else {
                m = ((QVTcDomainManager) modelManager).MIDDLE_MODEL;    // M to R
            }
            Set<Object> bindingValuesSet = ((QVTcDomainManager) modelManager).getElementsByType(m, var.getType());
            patternValidBindings.put(var, bindingValuesSet);
        }
        // For each binding visit the constraints, remove bindings that do not meet any
        // of the constraints
        for (Map.Entry<Variable, Set<Object>> entry : patternValidBindings.entrySet()) {
            Iterator<Object> bindingIt = entry.getValue().iterator();
            while (bindingIt.hasNext()) {
                Variable var = entry.getKey();
                if (var != null) {
                    getEvaluationEnvironment().replace(var, bindingIt.next());
                    for (Predicate predicate : guardPattern.getPredicate()) {
                        // If the predicate is not true, the binding is not valid
                        boolean result = (boolean) predicate.accept(this);
                        if (!result) {
                            // If the predicates fails, the binding is not valid
                            bindingIt.remove();
                            break;
                        }
                    }
                }
            }
        }
        return patternValidBindings;
        
    }
    
     
    /* (non-Javadoc)
     * @see org.eclipse.qvtd.pivot.qvtcorebase.util.QVTcoreBaseVisitor#visitPropertyAssignment(org.eclipse.qvtd.pivot.qvtcorebase.PropertyAssignment)
     */
    @Nullable
    public Object visitPropertyAssignment(@NonNull PropertyAssignment propertyAssignment) {

        throw new UnsupportedOperationException(
                "Visit method not implemented yet");
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
        
        // LtoM Mapping. Realized variables are in the mapping's bottom pattern
        // and create elements in the middle model. The realized variables
        // are being visited for each binding of variable in the mapping. 
        Area area = ((BottomPattern)realizedVariable.eContainer()).getArea();
        Object element =  realizedVariable.getType().createInstance();
        TypedModel tm = QVTcDomainManager.MIDDLE_MODEL;     // L to M
        if (area instanceof CoreDomain) {
            tm = ((CoreDomain)area).getTypedModel();        // M to R
        }
        ((QVTcDomainManager)modelManager).addModelElement(tm, element);
        // Add the realize variable binding to the environment
        if (getEvaluationEnvironment().getValueOf(realizedVariable) == null) {
            getEvaluationEnvironment().add(realizedVariable, element);
        } else {
            getEvaluationEnvironment().replace(realizedVariable, element);
        }
        return element;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitVariableAssignment
     * (org.eclipse.qvtd.pivot.qvtcore.VariableAssignment)
     */
    @Nullable
    public Object visitVariableAssignment(@NonNull VariableAssignment object) {
        // TODO Add visit function or decide if it should never be implemented
        throw new UnsupportedOperationException(
                "Visit method not implemented yet");
    }

    /* ========== HELPER METHODS ========== */
    /* (non-Javadoc)
     * @see org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitorImpl#createNestedEvaluator()
     */
    @Override
    public @NonNull EvaluationVisitor createNestedEvaluator() {
        Environment environment = getEnvironment();
        EnvironmentFactory factory = environment.getFactory();
        EvaluationEnvironment nestedEvalEnv = factory.createEvaluationEnvironment(getEvaluationEnvironment());
        QVTcoreBaseEvaluationVisitorImpl ne = new QVTcoreBaseEvaluationVisitorImpl(environment, nestedEvalEnv, getModelManager());
        return ne;
    }
    
    
    
    /**
     * Cartesian bindings.
     *
     * @param lists the lists
     * @return the list
     */
    protected List<List<Map<Variable, Object>>> cartesianBindings(List<List<Map<Variable, Object>>> lists) {
        List<List<Map<Variable, Object>>> resultLists = new ArrayList<List<Map<Variable, Object>>>();
        if (lists.size() == 0) {
          resultLists.add(new ArrayList<Map<Variable, Object>>());
          return resultLists;
        } else {
            List<Map<Variable, Object>> firstList = lists.get(0);
            List<List<Map<Variable, Object>>> remainingLists = cartesianBindings(lists.subList(1, lists.size()));
            for (Map<Variable, Object> condition : firstList) {
                for (List<Map<Variable, Object>> remainingList : remainingLists) {
                    List<Map<Variable, Object>> resultList = new ArrayList<Map<Variable, Object>>();
                    resultList.add(condition);
                    resultList.addAll(remainingList);
                    resultLists.add(resultList);
            }
          }
        }
        return resultLists;
      }

}
