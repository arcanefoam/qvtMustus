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
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.elements.DomainType;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.pivot.Environment;
import org.eclipse.ocl.examples.pivot.EnvironmentFactory;
import org.eclipse.ocl.examples.pivot.OCLExpression;
import org.eclipse.ocl.examples.pivot.Property;
import org.eclipse.ocl.examples.pivot.Type;
import org.eclipse.ocl.examples.pivot.Variable;
import org.eclipse.ocl.examples.pivot.VariableExp;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitor;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitorImpl;
import org.eclipse.ocl.examples.pivot.manager.PivotIdResolver;
import org.eclipse.ocl.examples.pivot.util.Visitable;
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
import org.eclipse.qvtd.pivot.qvtimperative.ImperativeModel;
import org.eclipse.qvtd.pivot.qvtimperative.Mapping;
import org.eclipse.qvtd.pivot.qvtimperative.MappingCall;
import org.eclipse.qvtd.pivot.qvtimperative.MappingCallBinding;

import uk.ac.york.qvtd.library.executor.QVTcDomainManager;

// TODO: Auto-generated Javadoc
/**
 * QVTimperativeAbstractEvaluationVisitor is the class for ...
 */
public abstract class QVTimperativeAbstractEvaluationVisitor extends EvaluationVisitorImpl
		implements QVTimperativeEvaluationVisitor {
	
	
    /**
     * Instantiates a new QVTimperative abstract evaluation visitor.
     *
     *  @param env The current environment
     * @param evalEnv The evaluation environment
     * @param modelManager The model manager that maps classes to their instances
     */
    protected QVTimperativeAbstractEvaluationVisitor(Environment env,
			EvaluationEnvironment evalEnv, DomainModelManager modelManager) {
		super(env, evalEnv, modelManager);

	}
    
    /* (non-Javadoc)
     * @see org.eclipse.qvtd.pivot.qvtcorebase.util.QVTcoreBaseVisitor#visitAssignment(org.eclipse.qvtd.pivot.qvtcorebase.Assignment)
     */
    public @Nullable Object visitAssignment(@NonNull Assignment object) {
		return visiting(object);
    }
	
	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtbase.util.QVTbaseVisitor#visitBaseModel(org.eclipse.qvtd.pivot.qvtbase.BaseModel)
	 */
	public @Nullable Object visitBaseModel(@NonNull BaseModel object) {
		return visiting(object);
	}

    /* (non-Javadoc)
     * @see org.eclipse.qvtd.pivot.qvtcorebase.util.QVTcoreBaseVisitor#visitBottomPattern(org.eclipse.qvtd.pivot.qvtcorebase.BottomPattern)
     */
    public @Nullable Object visitBottomPattern(@NonNull BottomPattern object) {
		return visiting(object);
    }

    /* (non-Javadoc)
     * @see org.eclipse.qvtd.pivot.qvtcorebase.util.QVTcoreBaseVisitor#visitCoreDomain(org.eclipse.qvtd.pivot.qvtcorebase.CoreDomain)
     */
    public @Nullable Object visitCoreDomain(@NonNull CoreDomain object) {
		return visiting(object);
    }

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtbase.util.QVTbaseVisitor#visitDomain(org.eclipse.qvtd.pivot.qvtbase.Domain)
	 */
	public @Nullable Object visitDomain(@NonNull Domain object) {
		return visiting(object);
	}

    /* (non-Javadoc)
     * @see org.eclipse.qvtd.pivot.qvtcorebase.util.QVTcoreBaseVisitor#visitCorePattern(org.eclipse.qvtd.pivot.qvtcorebase.CorePattern)
     */
    public @Nullable Object visitCorePattern(@NonNull CorePattern object) {
		return visiting(object);
    }

    /* (non-Javadoc)
     * @see org.eclipse.qvtd.pivot.qvtcorebase.util.QVTcoreBaseVisitor#visitEnforcementOperation(org.eclipse.qvtd.pivot.qvtcorebase.EnforcementOperation)
     */
    public @Nullable Object visitEnforcementOperation(@NonNull EnforcementOperation object) {
		return visiting(object);
    }

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtbase.util.QVTbaseVisitor#visitFunction(org.eclipse.qvtd.pivot.qvtbase.Function)
	 */
	public @Nullable Object visitFunction(@NonNull Function object) {
		return visiting(object);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtbase.util.QVTbaseVisitor#visitFunctionParameter(org.eclipse.qvtd.pivot.qvtbase.FunctionParameter)
	 */
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
    	
    	// The bindings are already defined, test the constraints
        boolean result = true;
        for (Predicate predicate : guardPattern.getPredicate()) {
            // If the predicate is not true, the binding is not valid
            result = (Boolean) predicate.accept(getUndecoratedVisitor());
            if (!result) {
            	break;
            }
        }
        return result;
    }

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtbase.util.QVTbaseVisitor#visitPattern(org.eclipse.qvtd.pivot.qvtbase.Pattern)
	 */
	public @Nullable Object visitPattern(@NonNull Pattern object) {
		return visiting(object);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtbase.util.QVTbaseVisitor#visitPredicate(org.eclipse.qvtd.pivot.qvtbase.Predicate)
	 */
	public @Nullable Object visitPredicate(@NonNull Predicate predicate) {
        
        // Each predicate has a conditionExpression that is an OCLExpression
        OCLExpression exp = predicate.getConditionExpression();
        // The predicated is visited with a nested environment
        Object expResult = exp.accept(getUndecoratedVisitor());
        return expResult;
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
            evaluationEnvironment.add(realizedVariable, element);
        } catch (IllegalArgumentException ex) {
            evaluationEnvironment.replace(realizedVariable, element);
        }
        return element;
    }

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtbase.util.QVTbaseVisitor#visitRule(org.eclipse.qvtd.pivot.qvtbase.Rule)
	 */
	public @Nullable Object visitRule(@NonNull Rule object) {
		return visiting(object);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtbase.util.QVTbaseVisitor#visitTypedModel(org.eclipse.qvtd.pivot.qvtbase.TypedModel)
	 */
	public @Nullable Object visitTypedModel(@NonNull TypedModel object) {
		return visiting(object);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtbase.util.QVTbaseVisitor#visitUnit(org.eclipse.qvtd.pivot.qvtbase.Unit)
	 */
	public @Nullable Object visitUnit(@NonNull Unit object) {
		return visiting(object);
	}

    /* (non-Javadoc)
     * @see org.eclipse.qvtd.pivot.qvtcorebase.util.QVTcoreBaseVisitor#visitVariableAssignment(org.eclipse.qvtd.pivot.qvtcorebase.VariableAssignment)
     */
    public @Nullable Object visitVariableAssignment(@NonNull VariableAssignment object) {
		return visiting(object);
    }
    
    /*
     * Perform the recursion for the BoundVariable that loops over the Iterables at depth in the
     * loopedVariables and loopedValues. The recursion proceeds to greater depths and once all
     * depths are exhausted invokes the mapping. 
     */
    /**
     * Do mapping call recursion.
     *
     * @param mapping the mapping
     * @param nestedEvaluator the nested evaluator
     * @param loopedVariables the looped variables
     * @param loopedValues the looped values
     * @param depth the depth
     */
    private void doMappingCallRecursion(@NonNull Mapping mapping, @NonNull EvaluationVisitorImpl nestedEvaluator, 
    		@NonNull List<Variable> loopedVariables, @NonNull List<Iterable<?>> loopedValues, int depth) {
		assert depth < loopedVariables.size();
		EvaluationEnvironment nestedEvaluationEnvironment = ((EvaluationVisitor) nestedEvaluator).getEvaluationEnvironment();
		Variable boundVariable = loopedVariables.get(depth);
		Type guardType = boundVariable.getType();
		PivotIdResolver idResolver = metaModelManager.getIdResolver();
		int nestedDepth = depth+1;
		Mapping invokeMapping = nestedDepth >= loopedVariables.size() ? mapping : null;
//		Map.Entry<DomainTypedElement, Object> entry = nestedEvaluationEnvironment.getEntry(boundVariable);
		for (Object value : loopedValues.get(depth)) {
//			entry.setValue(value);
			DomainType valueType = idResolver.getDynamicTypeOf(value);
			if (valueType.conformsTo(metaModelManager, guardType)) {
				nestedEvaluationEnvironment.replace(boundVariable, value);
				if (invokeMapping != null) {
					nestedEvaluator.safeVisit(invokeMapping);
				}
				else {
					doMappingCallRecursion(mapping, nestedEvaluator, loopedVariables, loopedValues, nestedDepth);				
				}
			}
		}
	}
    
    /**
     * Do mapping call recursion.
     *
     * @param rule the rule
     * @param rootVariables the root variables
     * @param rootBindings the root bindings
     * @param depth the depth
     */
    private void doMappingCallRecursion(@NonNull Rule rule, @NonNull List<Variable> rootVariables,
    		@NonNull List<List<Object>> rootBindings, int depth) {
		int nextDepth = depth+1;
		int maxDepth = rootVariables.size();
		Variable var = rootVariables.get(depth);
		Type guardType = var.getType();
		PivotIdResolver idResolver = metaModelManager.getIdResolver();
        for (Object binding : rootBindings.get(depth)) {
			DomainType valueType = idResolver.getDynamicTypeOf(binding);
			if (valueType.conformsTo(metaModelManager, guardType)) {
	        	evaluationEnvironment.replace(var, binding);
	        	if (nextDepth < maxDepth) {
	        		doMappingCallRecursion(rule, rootVariables, rootBindings, nextDepth);
	        	}
	        	else {
	        		// The MiddleGuardPattern should be empty in the root mapping, i.e. no need to find bindings
	            	rule.accept(getUndecoratedVisitor());
	        	}
			}
        }
	}

    /* (non-Javadoc)
     * @see org.eclipse.qvtd.pivot.qvtimperative.util.QVTimperativeVisitor#visitImperativeModel(org.eclipse.qvtd.pivot.qvtimperative.ImperativeModel)
     */
    public @Nullable Object visitImperativeModel(@NonNull ImperativeModel imperativeModel) {
    	for (org.eclipse.ocl.examples.pivot.Package pkge : imperativeModel.getNestedPackage()) {
    		pkge.accept(getUndecoratedVisitor());
    	}
        return true;
    }

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtimperative.util.QVTimperativeVisitor#visitMappingCall(org.eclipse.qvtd.pivot.qvtimperative.MappingCall)
	 */
	public @Nullable Object visitMappingCall(@NonNull MappingCall mappingCall) {
    	
    	Mapping calledMapping = mappingCall.getReferredMapping();
    	QVTimperativeAbstractEvaluationVisitor nv = null;
		if (isLtoMMapping(calledMapping)) {
			nv = createNestedLMVisitor();
		}
    	else if (isMtoRMapping(calledMapping)) {
    		nv = createNestedMRVisitor();
    	}
    	else if (isMtoMMapping(calledMapping)) {
    		nv = createNestedMMVisitor();
    	} else {
    		// FIXME Error
    	}
		//
		//	Initialize nested environment directly with the bound values for non-looped bindings,
		//	and build matching lists of boundVariables and boundIterables for looped bindings. 
		//
		List<Variable> loopedVariables = null;
		List<Iterable<?>> loopedValues = null;
		for (MappingCallBinding binding : mappingCall.getBinding()) {
			Variable boundVariable = binding.getBoundVariable();
			Object valueOrValues = safeVisit(binding.getValue());
			if (!binding.isIsLoop()) {
				DomainType valueType = metaModelManager.getIdResolver().getDynamicTypeOf(valueOrValues);
				if (valueType.conformsTo(metaModelManager, boundVariable.getType())) {
					nv.getEvaluationEnvironment().add(boundVariable, valueOrValues);
				}
				else {
					return null;		
				}
			}
			else if (valueOrValues instanceof Iterable<?>) {
				if (loopedVariables == null) {
					loopedVariables = new ArrayList<Variable>();
					loopedValues = new ArrayList<Iterable<?>>();
				}
				loopedVariables.add(boundVariable);
				loopedValues.add((Iterable<?>)valueOrValues);
				nv.getEvaluationEnvironment().add(boundVariable, null);
			} else {
				// FIXME Error message;
			}
    	}
		//
		//	In the absence of any looped bindings invoke the nested mapping directly,
		//	otherwise recurse over the boundVariables that need to loop.
		//
		if (loopedVariables == null) {
			nv.safeVisit(calledMapping);
		}
		else {
			doMappingCallRecursion(calledMapping, nv, loopedVariables, loopedValues, 0);
		}
    	return null;
    }

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtimperative.util.QVTimperativeVisitor#visitMappingCallBinding(org.eclipse.qvtd.pivot.qvtimperative.MappingCallBinding)
	 */
	public @Nullable Object visitMappingCallBinding(@NonNull MappingCallBinding object) {
		return visiting(object);	// MappingCallBinding is serviced by the parent MappingCall
    }
    
    /* (non-Javadoc)
     * @see uk.ac.york.qvtd.pivot.qvtimperative.evaluation.QVTcoreBaseAbstractEvaluationVisitor#visitPropertyAssignment(org.eclipse.qvtd.pivot.qvtcorebase.PropertyAssignment)
     */
    public @Nullable Object visitPropertyAssignment(@NonNull PropertyAssignment propertyAssignment) {
        
        OCLExpression slotExp = propertyAssignment.getSlotExpression(); 
        Area area = ((BottomPattern)propertyAssignment.eContainer()).getArea();
        if (area instanceof Mapping) {
        	// TODO Check this approach
        	//if (!(exp instanceof VariableExp)) {
        	//    return modelManager.illFormedModelClass(VariableExp.class, exp, "visitPropertyAssignment");
        	//}
        	//VariableExp variableExp = (VariableExp)exp;
            if (slotExp instanceof VariableExp ) {      // What other type of expressions are there?
                Variable slotVar = (Variable) ((VariableExp)slotExp).getReferredVariable();
                if(slotVar != null) {
                    Object slotBinding = evaluationEnvironment.getValueOf(slotVar);
                    if(slotBinding != null) {
                        Object value = safeVisit(propertyAssignment.getValue());
                        Property p = propertyAssignment.getTargetProperty();
                        p.initValue(slotBinding, value);
                    } else {
                        throw new IllegalArgumentException("Unsupported " + propertyAssignment.eClass().getName()
                                + " specification. The assigment refers to a variable not defined in the" +
                                " current environment");
                    } 
                } else {
                    throw new IllegalArgumentException("Unsupported " + propertyAssignment.eClass().getName()
                            + " specification. The referred variable of the slot expression (" + slotExp.getType().getName() 
                            + ") was not found.");
                }
            } else {
                throw new IllegalArgumentException("Unsupported " + propertyAssignment.eClass().getName()
                        + " specification. The slot expression type (" + slotExp.getType().getName() 
                        + ") is not supported yet.");
            }
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see uk.ac.york.qvtd.pivot.qvtimperative.evaluation.QVTcoreBaseAbstractEvaluationVisitor#visitTransformation(org.eclipse.qvtd.pivot.qvtbase.Transformation)
     */
    public @Nullable Object visitTransformation(@NonNull Transformation transformation) {
		for (Rule rule : transformation.getRule()) {
    		// Find bindings before invoking the mapping so all visitors are equal
    		Map<Variable, List<Object>>  mappingBindings = new HashMap<Variable, List<Object>>();
    		List<Variable> rootVariables = new ArrayList<Variable>();
    		List<List<Object>> rootBindings = new ArrayList<List<Object>>();
    		for (Domain domain : rule.getDomain()) {
                CoreDomain coreDomain = (CoreDomain)domain;
                TypedModel m = coreDomain.getTypedModel();
				for (Variable var : coreDomain.getGuardPattern().getVariable()) {
                	evaluationEnvironment.add(var, null);
                	rootVariables.add(var);
                    List<Object> bindingValuesSet = ((QVTcDomainManager)modelManager).getElementsByType(m, var.getType());
                	rootBindings.add(bindingValuesSet);
                    mappingBindings.put(var, bindingValuesSet);
                }
            }
    		doMappingCallRecursion(rule, rootVariables, rootBindings, 0);
    		break;		// FIXME ?? multiple rules
    	}
        return true;
    }

    
    /**
     * Checks if the mapping is a middle to right mapping. Middle to Right mappings
     * must have enforce domains
     *
     * @param mapping the mapping
     * @return true, if is m to r mapping
     */
    protected boolean isMtoRMapping(Mapping mapping) {
        if (mapping.getDomain().size() == 0) {
            return false;
        }
        for (Domain domain : mapping.getDomain()) {
            if (!domain.isIsEnforceable()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Checks if is mto m mapping.
     *
     * @param mapping the mapping
     * @return true, if is mto m mapping
     */
    private boolean isMtoMMapping(Mapping mapping) {
        if (mapping.getDomain().size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the mapping is a left to middle mapping. Left to middle mappings
     * can not have enforce domains
     *
     * @param mapping the mapping
     * @return true, if is l to m mapping
     */
    protected boolean isLtoMMapping(Mapping mapping) {
        if (mapping.getDomain().size() == 0) {
            return false;
        }
        for (Domain domain : mapping.getDomain()) {
            if (domain.isIsEnforceable()) {
                return false;
            }
        }
        return true;
    }
    
    
    /* (non-Javadoc)
     * @see uk.ac.york.qvtd.pivot.qvtimperative.evaluation.QVTimperativeEvaluationVisitor#createNestedLMVisitor()
     */
    public QVTimperativeAbstractEvaluationVisitor createNestedLMVisitor() {
    	
        EnvironmentFactory factory = environment.getFactory();
        EvaluationEnvironment nestedEvaluationEnvironment = factory.createEvaluationEnvironment(evaluationEnvironment); 
    	return new QVTimperativeLMEvaluationVisitor(environment, nestedEvaluationEnvironment, getModelManager());
    }
    
    /* (non-Javadoc)
     * @see uk.ac.york.qvtd.pivot.qvtimperative.evaluation.QVTimperativeEvaluationVisitor#createNestedMMVisitor()
     */
    public QVTimperativeAbstractEvaluationVisitor createNestedMMVisitor() {
    	
    	EnvironmentFactory factory = environment.getFactory();
        EvaluationEnvironment nestedEvaluationEnvironment = factory.createEvaluationEnvironment(evaluationEnvironment); 
    	return new QVTimperativeMMEvaluationVisitor(environment, nestedEvaluationEnvironment, getModelManager());
    }
    
    /* (non-Javadoc)
     * @see uk.ac.york.qvtd.pivot.qvtimperative.evaluation.QVTimperativeEvaluationVisitor#createNestedMRVisitor()
     */
    public QVTimperativeAbstractEvaluationVisitor createNestedMRVisitor() {
    	
    	EnvironmentFactory factory = environment.getFactory();
        EvaluationEnvironment nestedEvaluationEnvironment = factory.createEvaluationEnvironment(evaluationEnvironment); 
    	return new QVTimperativeMREvaluationVisitor(environment, nestedEvaluationEnvironment, getModelManager());
    }
}
