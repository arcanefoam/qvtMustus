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
package uk.ac.york.qvtd.pivot.qvtimperative.evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.pivot.Environment;
import org.eclipse.ocl.examples.pivot.OCLExpression;
import org.eclipse.ocl.examples.pivot.Variable;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitor;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitorImpl;
import org.eclipse.qvtd.pivot.qvtbase.BaseModel;
import org.eclipse.qvtd.pivot.qvtbase.Domain;
import org.eclipse.qvtd.pivot.qvtbase.Function;
import org.eclipse.qvtd.pivot.qvtbase.FunctionParameter;
import org.eclipse.qvtd.pivot.qvtbase.Pattern;
import org.eclipse.qvtd.pivot.qvtbase.Predicate;
import org.eclipse.qvtd.pivot.qvtbase.Rule;
import org.eclipse.qvtd.pivot.qvtbase.Transformation;
import org.eclipse.qvtd.pivot.qvtbase.TypedModel;
import org.eclipse.qvtd.pivot.qvtbase.Unit;
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
public abstract class QVTcoreBaseEvaluationVisitor extends EvaluationVisitorImpl
        implements QVTcoreBaseVisitor<Object> {
        
    /**
     * Instantiates a new QVTCore evaluation visitor implementation.
     * 
     * @param env
     *            the env
     * @param evalEnv
     *            the eval env
     * @param modelManager
     *            the model manager
     */
    public QVTcoreBaseEvaluationVisitor(@NonNull Environment env,
            @NonNull EvaluationEnvironment evalEnv,
            @NonNull DomainModelManager modelManager) {
        super(env, evalEnv, modelManager);
    }
	
    public abstract @NonNull EvaluationVisitor createNestedEvaluator();

    public @Nullable Object visitAssignment(@NonNull Assignment object) {
		return visiting(object);
    }
	
	public @Nullable Object visitBaseModel(@NonNull BaseModel object) {
		return visiting(object);
	}

    public @Nullable Object visitBottomPattern(@NonNull BottomPattern object) {
		return visiting(object);
    }

    public @Nullable Object visitCoreDomain(@NonNull CoreDomain object) {
		return visiting(object);
    }

	public @Nullable Object visitDomain(@NonNull Domain object) {
		return visiting(object);
	}

    public @Nullable Object visitCorePattern(@NonNull CorePattern object) {
		return visiting(object);
    }

    public @Nullable Object visitEnforcementOperation(@NonNull EnforcementOperation object) {
		return visiting(object);
    }

	public @Nullable Object visitFunction(@NonNull Function object) {
		return visiting(object);
	}

	public @Nullable Object visitFunctionParameter(@NonNull FunctionParameter object) {
		return visiting(object);
	}

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitGuardPattern(
     * org.eclipse.qvtd.pivot.qvtcore.GuardPattern)
     */
    public @Nullable Object visitGuardPattern(@NonNull GuardPattern guardPattern) {
        
        Area area = guardPattern.getArea();
        Map<Variable, List<Object>> patternValidBindings = new HashMap<Variable, List<Object>>();
        for (Variable var : guardPattern.getVariable()) {
            // Add the variable to the environment so we can assign it a value later
        	try {
        		getEvaluationEnvironment().add(var, null);
        	} catch (IllegalArgumentException ex){
        		// The variable is already defined
        	}
        	TypedModel m;
            if (area instanceof CoreDomain) {
                 m = ((CoreDomain)area).getTypedModel();                // L to M
            } else {
                m = QVTcDomainManager.MIDDLE_MODEL;    					// M to R
            }
            List<Object> bindingValuesSet = ((QVTcDomainManager) modelManager).getElementsByType(m, var.getType());
            patternValidBindings.put(var, bindingValuesSet);
        }
        // For each binding visit the constraints, remove bindings that do not meet any
        // of the constraints
        for (Map.Entry<Variable, List<Object>> entry : patternValidBindings.entrySet()) {
            Iterator<Object> bindingIt = entry.getValue().iterator();
            while (bindingIt.hasNext()) {
                Variable var = entry.getKey();
                if (var != null) {
                    getEvaluationEnvironment().replace(var, bindingIt.next());
                    for (Predicate predicate : guardPattern.getPredicate()) {
                        // If the predicate is not true, the binding is not valid
                        boolean result = (Boolean) predicate.accept(this);
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

	public @Nullable Object visitPattern(@NonNull Pattern object) {
		return visiting(object);
	}

	public @Nullable Object visitPredicate(@NonNull Predicate predicate) {
        
        // Each predicate has a conditionExpression that is an OCLExpression
        OCLExpression exp = predicate.getConditionExpression();
        // The predicated is visited with a nested environment
        Object expResult = exp.accept(this);
        return expResult;
	}

    public @Nullable Object visitPropertyAssignment(@NonNull PropertyAssignment object) {
		return visiting(object);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitRealizedVariable
     * (org.eclipse.qvtd.pivot.qvtcore.RealizedVariable)
     */
    public @Nullable Object visitRealizedVariable(@NonNull RealizedVariable realizedVariable) {
        
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
        try {
            getEvaluationEnvironment().add(realizedVariable, element);
        } catch (IllegalArgumentException ex) {
            getEvaluationEnvironment().replace(realizedVariable, element);
        }
        return element;
    }

	public @Nullable Object visitRule(@NonNull Rule object) {
		return visiting(object);
	}

	public @Nullable Object visitTransformation(@NonNull Transformation object) {
		return visiting(object);
	}

	public @Nullable Object visitTypedModel(@NonNull TypedModel object) {
		return visiting(object);
	}

	public @Nullable Object visitUnit(@NonNull Unit object) {
		return visiting(object);
	}

    public @Nullable Object visitVariableAssignment(@NonNull VariableAssignment object) {
		return visiting(object);
    }

    /* ========== HELPER METHODS ========== */
    
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
