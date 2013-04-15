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
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.pivot.Environment;
import org.eclipse.ocl.examples.pivot.OCLExpression;
import org.eclipse.ocl.examples.pivot.Property;
import org.eclipse.ocl.examples.pivot.Variable;
import org.eclipse.ocl.examples.pivot.VariableExp;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitor;
import org.eclipse.qvtd.pivot.qvtbase.Domain;
import org.eclipse.qvtd.pivot.qvtcorebase.Area;
import org.eclipse.qvtd.pivot.qvtcorebase.BottomPattern;
import org.eclipse.qvtd.pivot.qvtcorebase.PropertyAssignment;
import org.eclipse.qvtd.pivot.qvtimperative.ImperativeModel;
import org.eclipse.qvtd.pivot.qvtimperative.Mapping;
import org.eclipse.qvtd.pivot.qvtimperative.MappingCall;
import org.eclipse.qvtd.pivot.qvtimperative.MappingCallBinding;
import org.eclipse.qvtd.pivot.qvtimperative.util.QVTimperativeVisitor;

/**
 * QVTimperativeAbstractEvaluationVisitor is the class for ...
 */
public abstract class QVTimperativeAbstractEvaluationVisitor extends QVTcoreBaseEvaluationVisitor
        implements QVTimperativeVisitor<Object> {

        
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
            @NonNull DomainModelManager modelManager) {
        super(env, evalEnv, modelManager);
        // TODO Auto-generated constructor stub
    }

    public @Nullable Object visitImperativeModel(@NonNull ImperativeModel object) {
		return visiting(object);
    }
    
    public abstract @NonNull EvaluationVisitor createNestedEvaluator();

    /*
     * Perform the recursion for the BoundVariable that loops over the Iterables at deoth in the
     * loopedVariables and loopedValues. The recursion proceeds to greater depths and once all
     * depthas are exhausted invokes the mapping. 
     */
    private void doMappingCallRecursion(@NonNull QVTimperativeAbstractEvaluationVisitor nestedEvaluator, @NonNull Mapping mapping,
    		@NonNull List<Variable> loopedVariables, @NonNull List<Iterable<?>> loopedValues, int depth) {
		assert depth < loopedVariables.size();
		EvaluationEnvironment nestedEvaluationEnvironment = nestedEvaluator.getEvaluationEnvironment();
		Variable boundVariable = loopedVariables.get(depth);
		int nestedDepth = depth+1;
		Mapping invokeMapping = nestedDepth >= loopedVariables.size() ? mapping : null;
//		Map.Entry<DomainTypedElement, Object> entry = nestedEvaluationEnvironment.getEntry(boundVariable);
		for (Object value : loopedValues.get(depth)) {
//			entry.setValue(value);
			nestedEvaluationEnvironment.replace(boundVariable, value);
			if (invokeMapping != null) {
				nestedEvaluator.safeVisit(invokeMapping);
			}
			else {
				doMappingCallRecursion(nestedEvaluator, mapping, loopedVariables, loopedValues, nestedDepth);				
			}
		}
	}
    
    public @Nullable Object visitMapping(@NonNull Mapping object) {
		return visiting(object);
    }

    public @Nullable Object visitMappingCall(@NonNull MappingCall mappingCall) {
    	QVTimperativeAbstractEvaluationVisitor nestedEvaluator = (QVTimperativeAbstractEvaluationVisitor) createNestedEvaluator();
		EvaluationEnvironment nestedEvaluationEnvironment = nestedEvaluator.getEvaluationEnvironment();
    	// FIXME ?? clear nested evaluation environment
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
				nestedEvaluationEnvironment.add(boundVariable, valueOrValues);
			}
			else if (valueOrValues instanceof Iterable<?>) {
				if (loopedVariables == null) {
					loopedVariables = new ArrayList<Variable>();
					loopedValues = new ArrayList<Iterable<?>>();
				}
				loopedVariables.add(boundVariable);
				loopedValues.add((Iterable<?>)valueOrValues);
				nestedEvaluationEnvironment.add(boundVariable, null);
			}
			else {
				// FIXME Error message;
			}		
    	}
		//
		//	In the absence of and looped bindings invoke the nested mapping directly,
		//	otherwise recurse over the boundVariables that need to loop.
		//
		Mapping calledMapping = mappingCall.getReferredMapping();
		if (loopedVariables == null) {
			nestedEvaluator.safeVisit(calledMapping);
		}
		else {
			doMappingCallRecursion(nestedEvaluator, calledMapping, loopedVariables, loopedValues, 0);
		}
/*    	if (bindingIt.hasNext()) {
    		MappingCallBinding binding = bindingIt.next();
    		bindingIt.remove();
    	    OCLExpression valueExpression = binding.getValue();
            Object value = safeVisit(valueExpression);
            // To cope with possible multiple collection bindings, do recursive call
            if (value instanceof CollectionValue) {
                for (Object resValue : ((CollectionValue)value).asCollection()) {
                	getEvaluationEnvironment().replace(binding.getBoundVariable(), resValue);
                	visitMappingCallRecursive(mappingCall, bindings);
               }
            } else {
            	getEvaluationEnvironment().replace(binding.getBoundVariable(), value);
            	visitMappingCallRecursive(mappingCall, bindings);
            }
        } */
    	return null;
    }

	public @Nullable Object visitMappingCallBinding(@NonNull MappingCallBinding object) {
		return visiting(object);
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
                    Object slotBinding = getEvaluationEnvironment().getValueOf(slotVar);
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
    
    /**
     * Checks if the mapping is a middle to right mapping. Middle to Right mappings
     * must have enforce domains
     *
     * @param mapping the mapping
     * @return true, if is mto r mapping
     */
    protected boolean isMtoRMapping(Mapping mapping) {
        /*Mapping mapping;
        if (mapping instanceof MappingCall) {
            mapping = ((MappingCall)mapping).getReferredMapping();
        }
        else {
            mapping = (Mapping)mapping;
        }
        */
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
        /*Mapping mapping;
        if (nestedMapping instanceof MappingCall) {
            mapping = ((MappingCall)nestedMapping).getReferredMapping();
        }
        else {
            mapping = (Mapping)nestedMapping;
        }
        */
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
     * @return true, if is lto m mapping
     */
    protected boolean isLtoMMapping(Mapping mapping) {
        /*Mapping mapping;
        if (nestedMapping instanceof MappingCall) {
            mapping = ((MappingCall)nestedMapping).getReferredMapping();
        }
        else {
            // FIXME nestedMappings are abstract and dont inherit from mapping?!
            mapping = (Mapping)nestedMapping;
        }
        */
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


/*	private void visitMappingCallRecursive(MappingCall mappingCall,
			EList<MappingCallBinding> bindings) {
		
		Iterator<MappingCallBinding> bindingIt = bindings.iterator();
    	if (bindingIt.hasNext()) {
    		MappingCallBinding binding = bindingIt.next();
    		bindingIt.remove();
    	    OCLExpression valueExpression = binding.getValue();
            Object value = safeVisit(valueExpression);
            // To cope with possible multiple collection bindings, do recursive call
            if (value instanceof CollectionValue) {
                for (Object resValue : ((CollectionValue)value).asCollection()) {
                	getEvaluationEnvironment().replace(binding.getBoundVariable(), resValue);
                	visitMappingCallRecursive(mappingCall, bindings);
               }
            } else {
            	getEvaluationEnvironment().replace(binding.getBoundVariable(), value);
            	visitMappingCallRecursive(mappingCall, bindings);
            }
        } else {
        	Mapping referredMapping = mappingCall.getReferredMapping();
        	QVTimperativeVisitor<Object> nv;
    		if (isLtoMMapping(referredMapping)) {
    			nv = new QVTimperativeLMNestedEvaluationVisitor(environment, getEvaluationEnvironment(), modelManager);
    		}
        	else if (isMtoRMapping(referredMapping)) {
        		nv = new QVTimperativeMRNestedEvaluationVisitor(environment, getEvaluationEnvironment(), modelManager);
        	}
        	else {
        		nv = new QVTimperativeMMEvaluationVisitor(environment, getEvaluationEnvironment(), modelManager);
        	}
    		referredMapping.accept(nv);
        }
    	
	} */

}
