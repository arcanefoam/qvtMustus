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



import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.pivot.Environment;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.qvtd.pivot.qvtbase.Domain;
import org.eclipse.qvtd.pivot.qvtbase.Rule;
import org.eclipse.qvtd.pivot.qvtbase.Transformation;
import org.eclipse.qvtd.pivot.qvtimperative.ImperativeModel;
import org.eclipse.qvtd.pivot.qvtimperative.Mapping;
import org.eclipse.qvtd.pivot.qvtimperative.MappingCall;
import org.eclipse.qvtd.pivot.qvtimperative.MappingCallBinding;
import org.eclipse.qvtd.pivot.qvtimperative.util.QVTimperativeVisitor;

import uk.ac.york.qvtd.pivot.qvtcorebase.evaluation.QVTcoreBaseEvaluationVisitorImpl;

/**
 * QVTcoreEvaluationVisitorImpl is the class for ...
 */
public class QVTimperativeAbstractEvaluationVisitorImpl extends QVTcoreBaseEvaluationVisitorImpl
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
    public QVTimperativeAbstractEvaluationVisitorImpl(@NonNull Environment env,
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
        QVTimperativeMREvaluationVisitor MRVisitor = new QVTimperativeMREvaluationVisitor(
                getEnvironment(), getEvaluationEnvironment(), modelManager);
        /*
        QVTcoreMREvaluationVisitor MRVisitor = new QVTcoreMREvaluationVisitor(
                getEnvironment(), getEvaluationEnvironment(), modelManager);
            
         */
        for (Rule rule : transformation.getRule()) {
            if (isLtoMMapping((Mapping) rule)) {
                rule.accept(LMVisitor);
                LMVisitor.getEvaluationEnvironment().clear();   // Mappings at the same level dont share environment
            }
            // Remove all bindings to evaluate MtoM
            /*
            getEvaluationEnvironment().clear();
            
            if (isMtoMMapping(m)) {
                m.accept(MRVisitor);
            }
            */
            // Remove all bindings to evaluate MtoR
            getEvaluationEnvironment().clear();
            if (isMtoRMapping((Mapping) rule)) {
                rule.accept(MRVisitor);
                MRVisitor.getEvaluationEnvironment().clear();   // Mappings at the same level dont share environment
            }
        }
        return true;
    }

    /*
    @Override(non-Javadoc)
    @Nullable
    public Object visitMapping(Mapping object) {
        // TODO Auto-generated method stub
        return null;
    }
    */
    
    @Override
    @Nullable
    public Object visitMappingCall(@NonNull MappingCall object) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Nullable
    public Object visitMappingCallBinding(@NonNull MappingCallBinding object) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
    @Nullable
    public Object visitAssignment(@NonNull Assignment object) {
        // TODO Add visit function or decide if it should never be implemented
        throw new UnsupportedOperationException(
                "Visit method not implemented yet");
    }

    
    @Nullable
    public Object visitBottomPattern(@NonNull BottomPattern bottomPattern) {
        throw new UnsupportedOperationException(
                "Visit method not implemented yet");
    }


    @Nullable
    public Object visitCoreDomain(@NonNull CoreDomain coreDomain) {
        throw new UnsupportedOperationException(
                "Visit method not implemented yet");
        
    }

    
    @Nullable
    public Object visitCoreModel(@NonNull CoreModel coreModel) {
        // CoreModel has a transformation (nestedPackage)
        // DEFINE Can a single QVT model has multiple transformations?
        Transformation transformation = ((Transformation) coreModel.getNestedPackage().get(0));
        QVTcoreLMEvaluationVisitor LMVisitor = new QVTcoreLMEvaluationVisitor(
                getEnvironment(), getEvaluationEnvironment(), modelManager);
        for (Rule rule : transformation.getRule()) {
            // The transformation only has one mapping, the root mapping. Call
            // nested mappings in correct order, i.e. call all LtoM first then
            // all MtoR
            for (NestedMapping m : ((Mapping) rule).getLocal()) {
                if (isLtoMMapping(m)) {
                    m.accept(LMVisitor);
                    LMVisitor.getEvaluationEnvironment().clear();   // Mappings at the same level dont share environment
                }
            }
            // Remove all bindings to evaluate MtoM
            // getEvaluationEnvironment().clear();
            //for (NestedMapping m : ((Mapping) rule).getLocal()) {
            //    QVTcoreMREvaluationVisitor MRVisitor = new QVTcoreMREvaluationVisitor(
            //            getEnvironment(), getEvaluationEnvironment(), modelManager);
            //    if (isMtoMMapping(m)) {
            //        m.accept(MRVisitor);
            //    }
            //}
            // Remove all bindings to evaluate MtoR
            getEvaluationEnvironment().clear();
            QVTcoreMREvaluationVisitor MRVisitor = new QVTcoreMREvaluationVisitor(
                    getEnvironment(), getEvaluationEnvironment(), modelManager);
            
            for (NestedMapping m : ((Mapping) rule).getLocal()) {
                if (isMtoRMapping(m)) {
                    m.accept(MRVisitor);
                    MRVisitor.getEvaluationEnvironment().clear();   // Mappings at the same level dont share environment
                }
            }
        }
        return true;
    }


    
    @Nullable
    public Object visitCorePattern(@NonNull CorePattern object) {
        // TODO Add visit function or decide if it should never be implemented
        throw new UnsupportedOperationException(
                "Visit method not implemented yet");
    }

    
    @Nullable
    public Object visitEnforcementOperation(@NonNull EnforcementOperation object) {
        // TODO Add visit function or decide if it should never be implemented
        throw new UnsupportedOperationException(
                "Visit method not implemented yet");
    }

    
    @Override
    public Object visitGuardPattern(@NonNull GuardPattern guardPattern) {
        
        Area area = guardPattern.getArea();
        Map<Variable, Set<Object>> patternValidBindings = new HashMap<>();
        assert guardPattern.getVariable().size() == 1 : "Unsupported " + guardPattern.eClass().getName() + " defines more than 1 variable.";
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

    
    @Nullable
    public Object visitMapping(@NonNull Mapping mapping) {
        
        if (mapping.getDomain().size() == 0) {
            // Only visit the bottom pattern, which should have middle model assignments.
            // Since this should be a nested mapping, it will be called once per
            // binding.
            mapping.getBottomPattern().accept(this);
        } else {
            throw new IllegalArgumentException("Unsupported " + mapping.eClass().getName()
                    + " specification. Mappings can only have check or check+enforce domains");
        }
        return true;
    }
    
    @Override
    @Nullable
    public Object visitMappingCall(@NonNull Mapping object) {
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

    
    
    @Nullable
    public Object visitPropertyAssignment(@NonNull PropertyAssignment propertyAssignment) {
        
        OCLExpression slotExp = propertyAssignment.getSlotExpression(); 
        Area area = ((BottomPattern)propertyAssignment.eContainer()).getArea();
        if (area instanceof Mapping) {
            // slot vars can either be in L, M or R, this is, they have either a value
            // in validBindings (loop variable) or in tempRealizedElements (realized variables)
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

    
    @Nullable
    public Object visitVariableAssignment(@NonNull VariableAssignment object) {
        // TODO Add visit function or decide if it should never be implemented
        throw new UnsupportedOperationException(
                "Visit method not implemented yet");
    }
     */
    
    /**
     * Checks if the mapping is a middle to right mapping. Middle to Right mappings
     * must have enforce domains
     *
     * @param mapping the mapping
     * @return true, if is mto r mapping
     */
    private boolean isMtoRMapping(Mapping mapping) {
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
    private boolean isLtoMMapping(Mapping mapping) {
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

    @Override
    @Nullable
    public
    Object visitMapping(@NonNull Mapping object) {
        throw new UnsupportedOperationException(
                "Visit method not implemented yet");
    }
   

}
