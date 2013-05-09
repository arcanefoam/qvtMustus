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
import org.eclipse.qvtd.pivot.qvtbase.Domain;
import org.eclipse.qvtd.pivot.qvtbase.Rule;
import org.eclipse.qvtd.pivot.qvtbase.Transformation;
import org.eclipse.qvtd.pivot.qvtbase.TypedModel;
import org.eclipse.qvtd.pivot.qvtcorebase.Area;
import org.eclipse.qvtd.pivot.qvtcorebase.BottomPattern;
import org.eclipse.qvtd.pivot.qvtcorebase.CoreDomain;
import org.eclipse.qvtd.pivot.qvtcorebase.PropertyAssignment;
import org.eclipse.qvtd.pivot.qvtimperative.ImperativeModel;
import org.eclipse.qvtd.pivot.qvtimperative.Mapping;
import org.eclipse.qvtd.pivot.qvtimperative.MappingCall;
import org.eclipse.qvtd.pivot.qvtimperative.MappingCallBinding;

import uk.ac.york.qvtd.library.executor.QVTcDomainManager;

/**
 * QVTimperativeAbstractEvaluationVisitor is the class for ...
 */
public abstract class QVTimperativeAbstractEvaluationVisitor extends QVTcoreBaseAbstractEvaluationVisitor
        implements QVTimperativeEvaluationVisitor {
	
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
    public QVTimperativeAbstractEvaluationVisitor(@NonNull Environment env,
            @NonNull EvaluationEnvironment evalEnv,
            @NonNull QVTcDomainManager modelManager) {
        super(env, evalEnv, modelManager);
        // TODO Auto-generated constructor stub
    }

    public abstract @NonNull EvaluationVisitor createNestedEvaluator();
    
    @Override
	public @NonNull QVTcDomainManager getModelManager() {
		return (QVTcDomainManager) modelManager;
	}
    
    /**
     * Obtains the visitor on which I perform nested
     * {@link Visitable#accept(org.eclipse.ocl.utilities.Visitor)} calls.  This
     * handles the case in which I am decorated by another visitor that must
     * intercept every <tt>visitXxx()</tt> method.  If I internally just
     * recursively visit myself, then this decorator is cut out of the picture.
     * 
     * @return my delegate visitor, which may be my own self or some other
     *
    @SuppressWarnings("null")
	public @NonNull QVTimperativeEvaluationVisitor getQVTUndecoratedVisitor() {
        return (QVTimperativeEvaluationVisitor) this.undecoratedVisitor;
    } */
    
    
    
    /*
     * Perform the recursion for the BoundVariable that loops over the Iterables at depth in the
     * loopedVariables and loopedValues. The recursion proceeds to greater depths and once all
     * depths are exhausted invokes the mapping. 
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

    public @Nullable Object visitImperativeModel(@NonNull ImperativeModel imperativeModel) {
    	for (org.eclipse.ocl.examples.pivot.Package pkge : imperativeModel.getNestedPackage()) {
    		pkge.accept(getUndecoratedVisitor());
    	}
        return true;
    }

	public @Nullable Object visitMappingCall(@NonNull MappingCall mappingCall) {
    	
    	Mapping calledMapping = mappingCall.getReferredMapping();
        EvaluationVisitorImpl nv = null;
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

	public @Nullable Object visitMappingCallBinding(@NonNull MappingCallBinding object) {
		return visiting(object);	// MappingCallBinding is serviced by the parent MappingCall
    }
    
    @Override
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
    
    @Override
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
    
    
    public EvaluationVisitorImpl createNestedLMVisitor() {
    	
        EnvironmentFactory factory = environment.getFactory();
        EvaluationEnvironment nestedEvaluationEnvironment = factory.createEvaluationEnvironment(evaluationEnvironment); 
    	return new QVTimperativeLMEvaluationVisitor(environment, nestedEvaluationEnvironment, getModelManager());
    }
    
    public EvaluationVisitorImpl createNestedMMVisitor() {
    	
    	EnvironmentFactory factory = environment.getFactory();
        EvaluationEnvironment nestedEvaluationEnvironment = factory.createEvaluationEnvironment(evaluationEnvironment); 
    	return new QVTimperativeMMEvaluationVisitor(environment, nestedEvaluationEnvironment, getModelManager());
    }
    
    public EvaluationVisitorImpl createNestedMRVisitor() {
    	
    	EnvironmentFactory factory = environment.getFactory();
        EvaluationEnvironment nestedEvaluationEnvironment = factory.createEvaluationEnvironment(evaluationEnvironment); 
    	return new QVTimperativeMREvaluationVisitor(environment, nestedEvaluationEnvironment, getModelManager());
    }
}
