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
import java.util.Map.Entry;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.domain.values.CollectionValue;
import org.eclipse.ocl.examples.pivot.Environment;
import org.eclipse.ocl.examples.pivot.OCLExpression;
import org.eclipse.ocl.examples.pivot.Property;
import org.eclipse.ocl.examples.pivot.Variable;
import org.eclipse.ocl.examples.pivot.VariableExp;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.qvtd.pivot.qvtbase.Domain;
import org.eclipse.qvtd.pivot.qvtbase.Transformation;
import org.eclipse.qvtd.pivot.qvtcorebase.Area;
import org.eclipse.qvtd.pivot.qvtcorebase.Assignment;
import org.eclipse.qvtd.pivot.qvtcorebase.BottomPattern;
import org.eclipse.qvtd.pivot.qvtcorebase.PropertyAssignment;
import org.eclipse.qvtd.pivot.qvtimperative.ImperativeModel;
import org.eclipse.qvtd.pivot.qvtimperative.Mapping;
import org.eclipse.qvtd.pivot.qvtimperative.MappingCall;
import org.eclipse.qvtd.pivot.qvtimperative.MappingCallBinding;
import org.eclipse.qvtd.pivot.qvtimperative.util.QVTimperativeVisitor;

import uk.ac.york.qvtd.pivot.qvtcorebase.evaluation.QVTcoreBaseEvaluationVisitorImpl;

/**
 * QVTcoreEvaluationVisitorImpl is the class for ...
 */
public class QVTimperativeEvaluationVisitorImpl extends QVTcoreBaseEvaluationVisitorImpl
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
    public QVTimperativeEvaluationVisitorImpl(@NonNull Environment env,
            @NonNull EvaluationEnvironment evalEnv,
            @NonNull DomainModelManager modelManager) {
        super(env, evalEnv, modelManager);
        // TODO Auto-generated constructor stub
    }

    @Override
    @Nullable
    public Object visitImperativeModel(@NonNull ImperativeModel imperativeModel) {
        // CoreModel has a transformation (nestedPackage)
        // DEFINE Can a single QVT model has multiple transformations?
        Transformation transformation = ((Transformation) imperativeModel.getNestedPackage().get(0));
        QVTimperativeLMEvaluationVisitor LMVisitor = new QVTimperativeLMEvaluationVisitor(
                getEnvironment(), getEvaluationEnvironment(), modelManager);
        transformation.getRule().get(0).accept(LMVisitor);
        return true;
    }

    
    @Override
    @Nullable
    public Object visitMappingCall(@NonNull MappingCall object) {
        // TODO Add visit function or decide if it should never be implemented
        throw new UnsupportedOperationException(
                "Visit method not implemented yet");
    }

    @Override
    @Nullable
    public Object visitMappingCallBinding(@NonNull MappingCallBinding object) {
        // TODO Add visit function or decide if it should never be implemented
        throw new UnsupportedOperationException(
                "Visit method not implemented yet");
    }
    
    @Override
    @Nullable
    public Object visitPropertyAssignment(@NonNull PropertyAssignment propertyAssignment) {
        
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
    
    @Override
    @Nullable
    public Object visitAssignment(@NonNull Assignment object) {
        // TODO Add visit function or decide if it should never be implemented
        throw new UnsupportedOperationException(
                "Visit method not implemented yet");
    }
    
    @Override
    @Nullable
    public Object visitMapping(@NonNull Mapping object) {
        throw new UnsupportedOperationException(
                "Visit method not implemented yet");
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
    
    /**
     * Visit bound mapping. In a regular mapping bindings are created form the
     * guard and bottom patterns of the domains. In a MappingCall, bindings are
     * done before invocation. Either way, after bindings exist the visit is the
     * same.
     * 
     * @param mapping the mapping
     */
    protected void finishMappingVisit(Mapping mapping) {
        mapping.getBottomPattern().accept(this);
        for (MappingCall mappingCall : mapping.getMappingCall()) {
            List<List<Map<Variable, Object>>> bindingCartesian = new ArrayList<>();
            for (MappingCallBinding binding : ((MappingCall)mappingCall).getBinding()) {
                OCLExpression value = binding.getValue();
                Object result = safeVisit(value);
                List<Map<Variable, Object>> bindingValues = new ArrayList<>();
                if (result instanceof CollectionValue) {
                    // Create a binding for each of the elements in the collection
                    for (Object resValue : ((CollectionValue)result).asCollection()) {
                        Map<Variable, Object> varValue = new HashMap<>();
                        varValue.put(binding.getBoundVariable(), resValue);
                        bindingValues.add(varValue);
                   }
                } else {
                    Map<Variable, Object> varValue = new HashMap<>();
                    varValue.put(binding.getBoundVariable(), result);
                    bindingValues.add(varValue);
                }
                bindingCartesian.add(bindingValues);
            }
            // Calculate the Cartesian list of bindings
            List<List<Map<Variable, Object>>> cartesian = cartesianBindings(bindingCartesian);
            for (List<Map<Variable, Object>> bindings : cartesian) {
                for (Map<Variable, Object> binding : bindings) {
                    Iterator<Entry<Variable, Object>> it = binding.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<Variable, Object> pairs = (Map.Entry<Variable, Object>)it.next();
                        getEvaluationEnvironment().replace(pairs.getKey(), pairs.getValue());
                    }
                }
                mappingCall.accept(this);
            }
        }
    }

}
