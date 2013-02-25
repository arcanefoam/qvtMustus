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
package org.eclipse.qvtd.pivot.qvtcore.evaluation;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.pivot.Environment;
import org.eclipse.ocl.examples.pivot.EnvironmentFactory;
import org.eclipse.ocl.examples.pivot.OCLExpression;
import org.eclipse.ocl.examples.pivot.Property;
import org.eclipse.ocl.examples.pivot.Variable;
import org.eclipse.ocl.examples.pivot.VariableExp;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitor;
import org.eclipse.qvtd.pivot.qvtbase.Domain;
import org.eclipse.qvtd.pivot.qvtbase.Rule;
import org.eclipse.qvtd.pivot.qvtbase.Transformation;
import org.eclipse.qvtd.pivot.qvtbase.TypedModel;
import org.eclipse.qvtd.pivot.qvtbase.evaluation.QVTbaseEvaluationVisitorImpl;
import org.eclipse.qvtd.pivot.qvtcore.Area;
import org.eclipse.qvtd.pivot.qvtcore.Assignment;
import org.eclipse.qvtd.pivot.qvtcore.BottomPattern;
import org.eclipse.qvtd.pivot.qvtcore.CoreDomain;
import org.eclipse.qvtd.pivot.qvtcore.CoreModel;
import org.eclipse.qvtd.pivot.qvtcore.CorePattern;
import org.eclipse.qvtd.pivot.qvtcore.EnforcementOperation;
import org.eclipse.qvtd.pivot.qvtcore.GuardPattern;
import org.eclipse.qvtd.pivot.qvtcore.Mapping;
import org.eclipse.qvtd.pivot.qvtcore.MappingCall;
import org.eclipse.qvtd.pivot.qvtcore.MappingCallBinding;
import org.eclipse.qvtd.pivot.qvtcore.NestedMapping;
import org.eclipse.qvtd.pivot.qvtcore.PropertyAssignment;
import org.eclipse.qvtd.pivot.qvtcore.RealizedVariable;
import org.eclipse.qvtd.pivot.qvtcore.VariableAssignment;
import org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor;

import uk.ac.york.qvtd.library.executor.QVTcDomainManager;

// TODO: Auto-generated Javadoc
/**
 * QVTcoreEvaluationVisitorImpl is the class for ...
 */
public class QVTcoreEvaluationVisitorImpl extends QVTbaseEvaluationVisitorImpl
        implements QVTcoreVisitor<Object> {
    
    
    private boolean l2mStarted = false;
    private boolean m2mStarted = false;
    private boolean m2rStarted = false;
        
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
    public QVTcoreEvaluationVisitorImpl(@NonNull Environment env,
            @NonNull EvaluationEnvironment evalEnv,
            @NonNull DomainModelManager modelManager) {
        super(env, evalEnv, modelManager);
        // TODO Auto-generated constructor stub
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
        
        // This visit should be called for MiddleBottomPatterns with no domains
        Area area = bottomPattern.getArea();
        if (area instanceof Mapping && ((Mapping)area).getDomain().size() == 0) {
            // What is the environment??
            for (Assignment assigment : bottomPattern.getAssignment()) {
                assigment.accept(this);
            }
            // Probably enforcement operations must be called too
            for (EnforcementOperation enforceOp : bottomPattern.getEnforcementOperation()) {
                enforceOp.accept(this);
            }
            return true;
        }
        return null;
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
        
        if (isLtoMMapping((Mapping) coreDomain.getRule())) {
            /*
             * LtoM Mapping. The visit to the core domain should return the map
             * of valid bindings for the domain variables (in the L model)
             */
            // TODO Implement guard visit methods
            //boolean guardMet = (Boolean)coreDomain.getGuardPattern().accept(this);
            //if(guardMet) {
                return coreDomain.getBottomPattern().accept(this);
            //}
            
        } else if (isMtoRMapping((Mapping) coreDomain.getRule())) {
            /*
             * MtoR Mapping. The visit to the core domain should create the realized
             * variables (in the R model). The core domain is visited once per
             * binding, so only create the realized variables and visit the
             * assignments once
             */
            // TODO Implement guard visit methods
            //boolean guardMet = (Boolean)coreDomain.getGuardPattern().accept(this);
            //if(guardMet) {
                return coreDomain.getBottomPattern().accept(this);
            //}
            
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitCoreModel(org
     * .eclipse.qvtd.pivot.qvtcore.CoreModel)
     */
    @Nullable
    public Object visitCoreModel(@NonNull CoreModel coreModel) {
        // CoreModel has a transformation (nestedPackage)
        // DEFINE Can a single QVT model has multiple transformations?
        Transformation transformation = ((Transformation) coreModel.getNestedPackage().get(0));
        for (Rule rule : transformation.getRule()) {
            // The transformation only has one mapping, the root mapping. Call
            // nested mappings in correct order, i.e. call all LtoM first then
            // all MtoR
            for (NestedMapping m : ((Mapping) rule).getLocal()) {
                QVTcoreLMEvaluationVisitor LMVisitor = new QVTcoreLMEvaluationVisitor(
                        getEnvironment(), getEvaluationEnvironment(), modelManager);
                if (isLtoMMapping(m)) {
                    assert !m2mStarted && !m2rStarted;
                    l2mStarted = true;
                    m.accept(LMVisitor);
                }
            }
            // Remove all bindings to evaluate MtoR
            getEvaluationEnvironment().clear();
            for (NestedMapping m : ((Mapping) rule).getLocal()) {
                QVTcoreMREvaluationVisitor MRVisitor = new QVTcoreMREvaluationVisitor(
                        getEnvironment(), getEvaluationEnvironment(), modelManager);
                if (isMtoRMapping(m)) {
                    assert l2mStarted;
                    m2rStarted = true;
                    m.accept(MRVisitor);
                }
            }
            // Where does the m to m mapping occurs?
        }
        return true;
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
    @Nullable
    public Object visitGuardPattern(@NonNull GuardPattern object) {
        // TODO Add visit function or decide if it should never be implemented
        throw new UnsupportedOperationException(
                "Visit method not implemented yet");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitMapping(org.eclipse
     * .qvtd.pivot.qvtcore.Mapping)
     */
    @Nullable
    public Object visitMapping(@NonNull Mapping mapping) {
        /* There can be four types of mappings: root mapping, L->M, M->R and
         * domain-less mappings
         * Mappings with only check domains are L->M, mappings with only
         * enforce domains are M->R 
         */
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

    /**
     * Checks if the mapping is a middle to right mapping. Middle to Right mappings
     * must have enforce domains
     *
     * @param mapping the mapping
     * @return true, if is mto r mapping
     */
    private boolean isMtoRMapping(NestedMapping nestedMapping) {
        Mapping mapping;
        if (nestedMapping instanceof MappingCall) {
            mapping = ((MappingCall)nestedMapping).getReferredMapping();
        }
        else {
            mapping = (Mapping)nestedMapping;
        }
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
     * Checks if the mapping is a left to middle mapping. Left to middle mappings
     * can not have enforce domains
     *
     * @param mapping the mapping
     * @return true, if is lto m mapping
     */
    private boolean isLtoMMapping(NestedMapping nestedMapping) {
        Mapping mapping;
        if (nestedMapping instanceof MappingCall) {
            mapping = ((MappingCall)nestedMapping).getReferredMapping();
        }
        else {
            mapping = (Mapping)nestedMapping;
        }
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

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitPropertyAssignment
     * (org.eclipse.qvtd.pivot.qvtcore.PropertyAssignment)
     */
    @Nullable
    public Object visitPropertyAssignment(@NonNull PropertyAssignment propertyAssignment) {
        
        OCLExpression slotExp = propertyAssignment.getSlotExpression(); 
        /*
         * LtoM or MtoR Mapping. Property assignments are in the mapping's bottom pattern
         * and define values for middle model element's attributes. The property
         * assignments are being visited for each binding of variable in the mapping. 
         */
        // TODO Assignments in check domains should be treated as predicates.
        Area area = ((BottomPattern)propertyAssignment.eContainer()).getArea();
        if (area instanceof Mapping && isLtoMMapping((Mapping)area)) {
            if (slotExp instanceof VariableExp ) {      // What other type of expressions are there?
                Variable slotVar = (Variable) ((VariableExp)slotExp).getReferredVariable();
                if(slotVar != null) {
                    // The nested evaluation environment is created in the mapping loop
                    Object value = safeVisit(propertyAssignment.getValue());
                    // Assign the value to a binding of the slotVar
                    Object slotBinding = getEvaluationEnvironment().getValueOf(slotVar);
                    // TODO what happens if the target property is not a simple attribute?
                    if (slotBinding != null) {
                        Property p = propertyAssignment.getTargetProperty();
                        p.initValue(slotBinding, value);
                    } else {
                        throw new IllegalArgumentException("Unsupported " + propertyAssignment.eClass().getName()
                                + " specification. The referred variable of the slot expression (" + slotExp.getType().getName() 
                                + ") was not found.");
                    }
                    
                }
                
            } else {
                throw new IllegalArgumentException("Unsupported " + propertyAssignment.eClass().getName()
                        + " specification. The slot expression type (" + slotExp.getType().getName() 
                        + ") is not supported yet.");
            }
        }
        /*
         * MtoR Mapping. Property assignments are in the mapping's bottom pattern
         * and define values for middle model element's attributes. The property
         * assignments are being visited for each binding of variable in the mapping. 
         */
        // So far this case never happens
        /*
         * LtoM or MtoR nested mapping without domains. Property assignments are
         * in the mapping's bottom pattern and define values for middle model 
         * element's attributes (complete trace) or values for the R model.  The property
         * assignments are being visited for each binding of variable in the mapping. 
         */
        else if (area instanceof Mapping && ((Mapping)area).getDomain().size() == 0) {
            // slot vars can either be in L, M or R, this is, they have either a value
            // in validBindings (loop variable) or in tempRealizedElements (realized variables)
            // 1. Test if the slot variable is in the variableValues
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

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitRealizedVariable
     * (org.eclipse.qvtd.pivot.qvtcore.RealizedVariable)
     */
    @Nullable
    public Object visitRealizedVariable(@NonNull RealizedVariable realizedVariable) {
        /*
         * LtoM Mapping. Realized variables are in the mapping's bottom pattern
         * and create elements in the middle model. The realized variables
         * are being visited for each binding of variable in the mapping. 
         */
        Area area = ((BottomPattern)realizedVariable.eContainer()).getArea();
        if (area instanceof Mapping && isLtoMMapping((Mapping)area)) {
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
        /*
         * MtoR Mapping. Realized variables are in the R core domain bottom pattern
         * and create elements in the R model. The realized variables
         * are being visited for each binding of variable in the mapping. 
         */
        else if (area instanceof CoreDomain && isMtoRMapping((Mapping) ((CoreDomain)area).getRule())) {
            TypedModel tm = ((CoreDomain)area).getTypedModel();
            // Create an element in the R model that has a kind equal to the variable type
            Object element = realizedVariable.getType().createInstance();
            // Add the element to the R resource
            ((QVTcDomainManager)modelManager).addModelElement(tm, element);
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
    @Override
    public @NonNull EvaluationVisitor createNestedEvaluator() {
        Environment environment = getEnvironment();
        EnvironmentFactory factory = environment.getFactory();
        EvaluationEnvironment nestedEvalEnv = factory.createEvaluationEnvironment(getEvaluationEnvironment());
        QVTcoreEvaluationVisitorImpl ne = new QVTcoreEvaluationVisitorImpl(environment, nestedEvalEnv, getModelManager());
        return ne;
    }

}
