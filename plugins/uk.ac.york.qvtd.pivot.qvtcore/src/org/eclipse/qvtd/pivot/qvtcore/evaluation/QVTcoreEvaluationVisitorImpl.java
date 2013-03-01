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

import java.util.Map;
import java.util.Set;

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
            Map<Variable, Set<Object>>  guardBindings = (Map<Variable, Set<Object>>) coreDomain.getGuardPattern().accept(this);
            if(guardBindings != null) {
                // Add the bindings to the visitor environment
                // Evaluate the bottom pattern for each binding
                // No predicates in CodeDomain bottom Patterns, so no use to visit bottom patterns at all??
                for (Map.Entry<Variable, Set<Object>> entry : guardBindings.entrySet()) {
                    Variable var = entry.getKey();
                    for (Object e : entry.getValue()) {
                        // Use each of the bindings for evaluation in the loop
                        getEvaluationEnvironment().replace(var, e);
                        coreDomain.getBottomPattern().accept(this); 
                    }
                }
            }
            return guardBindings;
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
            // Remove all bindings to evaluate MtoM
            getEvaluationEnvironment().clear();
            for (NestedMapping m : ((Mapping) rule).getLocal()) {
                QVTcoreMREvaluationVisitor MRVisitor = new QVTcoreMREvaluationVisitor(
                        getEnvironment(), getEvaluationEnvironment(), modelManager);
                if (isMtoMMapping(m)) {
                    assert l2mStarted;
                    m2mStarted = true;
                    m.accept(MRVisitor);
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
    public Object visitGuardPattern(@NonNull GuardPattern guardPattern) {
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

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitRealizedVariable
     * (org.eclipse.qvtd.pivot.qvtcore.RealizedVariable)
     */
    @Nullable
    public Object visitRealizedVariable(@NonNull RealizedVariable realizedVariable) {
        throw new UnsupportedOperationException(
                "Visit method should be implemented by extending evaluators.");
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
    
    private boolean isMtoMMapping(NestedMapping nestedMapping) {
        Mapping mapping;
        if (nestedMapping instanceof MappingCall) {
            mapping = ((MappingCall)nestedMapping).getReferredMapping();
        }
        else {
            mapping = (Mapping)nestedMapping;
        }
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
    private boolean isLtoMMapping(NestedMapping nestedMapping) {
        Mapping mapping;
        if (nestedMapping instanceof MappingCall) {
            mapping = ((MappingCall)nestedMapping).getReferredMapping();
        }
        else {
            // FIXME nestedMappings are abstract and dont inherit from mapping?!
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

}
