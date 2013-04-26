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
import org.eclipse.qvtd.pivot.qvtcorebase.Area;
import org.eclipse.qvtd.pivot.qvtcorebase.BottomPattern;
import org.eclipse.qvtd.pivot.qvtcorebase.PropertyAssignment;
import org.eclipse.qvtd.pivot.qvtcorebase.util.QVTcoreBaseVisitor;
import org.eclipse.qvtd.pivot.qvtimperative.ImperativeModel;
import org.eclipse.qvtd.pivot.qvtimperative.Mapping;
import org.eclipse.qvtd.pivot.qvtimperative.MappingCall;
import org.eclipse.qvtd.pivot.qvtimperative.MappingCallBinding;

import uk.ac.york.qvtd.library.executor.QVTcDomainManager;

/**
 * QVTimperativeAbstractEvaluationVisitor is the class for ...
 */
public abstract class QVTimperativeAbstractEvaluationVisitor extends QVTcoreBaseAbstractEvaluationVisitor
        implements QVTimperativeEvaluationVisitor<Object> {

	protected QVTcoreBaseVisitor<Object> undecoratedVisitor;
        
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

    public @Nullable Object visitImperativeModel(@NonNull ImperativeModel object) {
		return visiting(object);
    }
    
    public abstract @NonNull EvaluationVisitor createNestedEvaluator();

    /*
     * Perform the recursion for the BoundVariable that loops over the Iterables at depth in the
     * loopedVariables and loopedValues. The recursion proceeds to greater depths and once all
     * depths are exhausted invokes the mapping. 
     */
    private void doMappingCallRecursion(@NonNull EvaluationVisitorImpl nestedEvaluator, @NonNull Mapping mapping,
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
					doMappingCallRecursion(nestedEvaluator, mapping, loopedVariables, loopedValues, nestedDepth);				
				}
			}
		}
	}

    @Override
	public @NonNull QVTcDomainManager getModelManager() {
		return (QVTcDomainManager) modelManager;
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
			doMappingCallRecursion(nv, calledMapping, loopedVariables, loopedValues, 0);
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
    
    /**
     * Sets the visitor on which I perform nested
     * {@link Visitable#accept(org.eclipse.ocl.utilities.Visitor)} calls.
     * 
     * @param visitor my delegate visitor
     * 
     * @see #getUndecoratedVisitor()
     */
	public void setUndecoratedVisitor(
			QVTimperativeEvaluationVisitor<Object> evaluationVisitor) {
		this.undecoratedVisitor = evaluationVisitor;
		
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
