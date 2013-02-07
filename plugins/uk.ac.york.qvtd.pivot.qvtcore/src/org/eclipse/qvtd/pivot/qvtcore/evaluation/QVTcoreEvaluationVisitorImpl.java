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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
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
import org.eclipse.qvtd.pivot.qvtbase.Predicate;
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
        /*
         * The bottom pattern must be visited differently if the pattern belongs
         * to a LtoM domain or a MtoR Mapping
         */
        
        /*
         * LtoM Mapping. The bottomPattern belongs to a Domain, get all possible
         * variable bindings from the L model and then use the predicates to leave
         * only the valid bindings
         */
        Area area = bottomPattern.getArea();
        if (area instanceof CoreDomain && isLtoMMapping((Mapping) ((CoreDomain)area).getRule())) {
            Map<Variable, Set<EObject>> patternValidBindings = new HashMap<>();
            for (Variable var : bottomPattern.getVariable()) {
                // Add the variable to the environment so we can assign it a value
                // later
                getEvaluationEnvironment().add(var, null);
                // We are in a L->M
                // Values of variables in a CoreDomain exist in the domain's TypedModel
                TypedModel m = ((CoreDomain)area).getTypedModel();
                Set<EObject> objectSet = new HashSet<>();
                Resource resource = ((QVTcDomainManager) modelManager).getTypeModelResource(m);
                for (TreeIterator<EObject> contents = resource.getAllContents(); contents.hasNext();) {
                    EObject object = contents.next();
                    if (object.eClass().getName().equals(var.getType().getName())) {
                        objectSet.add(object);
                    }
                }
                patternValidBindings.put(var, objectSet);
            }
            // For each binding visit the predicates to leave only the valid bindings
            Iterator<Entry<Variable, Set<EObject>>> it = patternValidBindings.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Variable, Set<EObject>> pairs = (Map.Entry<Variable, Set<EObject>>)it.next();
                Iterator<EObject> bindingIt = pairs.getValue().iterator();
                while (bindingIt.hasNext()) {
                    Variable var = pairs.getKey();
                    // Create a nested visitor with the binding value assigned to the variable
                    //EvaluationVisitor ne = createNestedEvaluator();
                    //ne.getEvaluationEnvironment().add(var, null);
                    if (var != null) {
                        //ne.getEvaluationEnvironment().replace(var, bindingIt.next());
                        getEvaluationEnvironment().replace(var, bindingIt.next());
                        for (Predicate predicate : bottomPattern.getPredicate()) {
                            // If the predicate is not true, the binding is not valid
                            Boolean result = (Boolean) predicate.accept(this);
                            if (result != null && !result) {
                                // If the predicates fails, the binding is not valid
                                it.remove();
                                break;
                            }
                        }
                    }
                }
            }
            return patternValidBindings;
        }
        /*
         * LtoM Mapping. The bottomPattern belongs to a Mapping and it is visited once per
         * binding of the L domain. The bottom pattern should have the realized variables of the
         * middle model. Use the assignments to set values to their properties
         * 
         */
        else if (area instanceof Mapping && isLtoMMapping((Mapping)area)) {
            for (RealizedVariable rVar : bottomPattern.getRealizedVariable()) {
                if (bottomPattern.getArea() instanceof CoreDomain && !((CoreDomain)bottomPattern.getArea()).isIsEnforceable()) {
                    throw new UnsupportedOperationException("Unsupported " + bottomPattern.eClass().getName()
                            + " specification. Realized variables can only exist in Enforced domains");
                }
                EObject element = (EObject) rVar.accept(this);
            }
            for (Assignment assigment : bottomPattern.getAssignment()) {
                assigment.accept(this);
            }
            // Probably enforcement operations must be called too
            for (EnforcementOperation enforceOp : bottomPattern
                    .getEnforcementOperation()) {
                enforceOp.accept(this);
            }
        }
        /*
         * MtoR Mapping. The bottomPattern belongs to a mapping, get all possible
         * variable bindings from the middle model and then use the predicates to leave
         * only the valid bindings
         */
        else if (area instanceof Mapping && isMtoRMapping((Mapping) area)) {
            Map<Variable, Set<EObject>> patternValidBindings = new HashMap<>();
            for (Variable var : bottomPattern.getVariable()) {
                // We are in a M->R
                // Values of variables in a where clause exist in the middle model
                Set<EObject> objectSet = new HashSet<>();
                Resource resource = ((QVTcDomainManager) modelManager).getTypeModelResource(null);
                for (TreeIterator<EObject> contents = resource.getAllContents(); contents.hasNext();) {
                    EObject object = contents.next();
                    if (object.eClass().getName().equals(var.getType().getName())) {
                        objectSet.add(object);
                    }
                }
                patternValidBindings.put(var, objectSet);
            }
            // For each binding visit the predicates to leave only the valid bindings
            Iterator<Entry<Variable, Set<EObject>>> it = patternValidBindings.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Variable, Set<EObject>> pairs = (Map.Entry<Variable, Set<EObject>>)it.next();
                Iterator<EObject> bindingIt = pairs.getValue().iterator();
                while (bindingIt.hasNext()) {
                    Variable var = pairs.getKey();
                    // Create a nested visitor with the binding value assigned to the variable
                    //EvaluationVisitor ne = createNestedEvaluator();
                    //ne.getEvaluationEnvironment().add(var, null);
                    if (var != null) {
                        //ne.getEvaluationEnvironment().replace(var, bindingIt.next());
                        getEvaluationEnvironment().replace(var, bindingIt.next());
                        for (Predicate predicate : bottomPattern.getPredicate()) {
                            // If the predicate is not true, the binding is not valid
                            Boolean result = (Boolean) predicate.accept(this);
                            if (result != null && !result) {
                                // If the predicates fails, the binding is not valid
                                it.remove();
                                break;
                            }
                        }
                    }
                }
            }
            return patternValidBindings;
        }
        /*
         * MtoRM Mapping. The bottomPattern belongs to a CoreDomain and it is visited once per
         * binding of the middle model. The bottom pattern should have the realized variables of the
         * R model. Use the assignments to set values to their properties
         * 
         */
        else if (area instanceof CoreDomain && isMtoRMapping((Mapping) ((CoreDomain)area).getRule())) {
            for (RealizedVariable rVar : bottomPattern.getRealizedVariable()) {
                if (bottomPattern.getArea() instanceof CoreDomain && !((CoreDomain)bottomPattern.getArea()).isIsEnforceable()) {
                    throw new UnsupportedOperationException("Unsupported " + bottomPattern.eClass().getName()
                            + " specification. Realized variables can only exist in Enforced domains");
                }
                EObject element = (EObject) rVar.accept(this);
            }
            for (Assignment assigment : bottomPattern.getAssignment()) {
                assigment.accept(this);
            }
            // Probably enforcement operations must be called too
            for (EnforcementOperation enforceOp : bottomPattern
                    .getEnforcementOperation()) {
                enforceOp.accept(this);
            }
        }
        // Only visit assignments and operations?
        else if (area instanceof Mapping && ((Mapping)area).getDomain().size() == 0) {
            // What is the environment??
            for (Assignment assigment : bottomPattern.getAssignment()) {
                assigment.accept(this);
            }
            // Probably enforcement operations must be called too
            for (EnforcementOperation enforceOp : bottomPattern.getEnforcementOperation()) {
                enforceOp.accept(this);
            }
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
            for (Mapping m : ((Mapping) rule).getLocal()) {
                if (isLtoMMapping(m)) {
                    m.accept(this);
                }
            }
            // Remove all bindings to evaluate MtoR
            getEvaluationEnvironment().clear();
            for (Mapping m : ((Mapping) rule).getLocal()) {
                if (isMtoRMapping(m)) {
                    m.accept(this);
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
        } else if (isLtoMMapping(mapping)) {
            visitLMMapping(mapping);
        } else if (isMtoRMapping(mapping)) {
            visitMRMapping(mapping);
        } else {
            throw new IllegalArgumentException("Unsupported " + mapping.eClass().getName()
                    + " specification. Mappings can only have check or check+enforce domains");
        }
        return true;
    }
    
    /**
     * Visit left to middle mapping. Mapping's domains define the loop variables 
     * for the middle bottom pattern and nested mappings. 
     *
     * @param mapping the mapping
     * @return the object
     */
    private Object visitLMMapping(Mapping mapping) {
        for (Domain domain : mapping.getDomain()) {
            Map<Variable, Set<EObject>>  loopVariableValues = (Map<Variable, Set<EObject>>)((CoreDomain) domain).accept(this);
            if (loopVariableValues != null) {
                // Create a nested visitor with the binding value assigned to the variable
                //EvaluationVisitor ne = createNestedEvaluator();
                for (Map.Entry<Variable, Set<EObject>> entry : loopVariableValues.entrySet()) {
                    Variable var = entry.getKey();
                    //ne.getEvaluationEnvironment().add(var, null);
                    for (EObject e : entry.getValue()) {
                        // Use each of the bindings for evaluation in the loop
                        //ne.getEvaluationEnvironment().replace(var, e);
                        getEvaluationEnvironment().replace(var, e);
                        // TODO Implement guard visit methods
                        // boolean guardMet = (Boolean)mapping.getGuardPattern().accept(this);
                        //if(guardMet) {
                        //    MiddleBottomPattern (aka where clause)
                            mapping.getBottomPattern().accept(this);
                        //}
                        // Nested mappings
                        for (Mapping localMapping : mapping.getLocal()) {
                            localMapping.accept(this);
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     *  Visit middle to right mapping. Mapping's bottom pattern define the loop 
     *  variables for the domains and nested mappings. 
     *
     * @param mapping the mapping
     * @return the object
     */
    private Object visitMRMapping(Mapping mapping) {
        
        Map<Variable, Set<EObject>>  loopVariableValues = null;
        // TODO Implement guard visit methods
        // boolean guardMet = (Boolean)mapping.getGuardPattern().accept(this);
        //if(guardMet) {
        //    MiddleBottomPattern (aka where clause)
            loopVariableValues = (Map<Variable, Set<EObject>>)mapping.getBottomPattern().accept(this);
        //}
        if (loopVariableValues != null) {
            // Create a nested visitor to assign the binding value to the variable
            //EvaluationVisitor ne = createNestedEvaluator();
            for (Variable var : loopVariableValues.keySet()) {
                //ne.getEvaluationEnvironment().add(var, null);
                for (EObject e : loopVariableValues.get(var)) {
                    // Use each of the bindings for evaluation in the loop
                    getEvaluationEnvironment().replace(var, e);
                    for (Domain domain : mapping.getDomain()) {
                        ((CoreDomain) domain).accept(this);
                    }
                    for (Mapping localMapping : mapping.getLocal()) {
                        localMapping.accept(this);
                    }
                }
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
    private boolean isMtoRMapping(Mapping mapping) {
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
    private boolean isLtoMMapping(Mapping mapping) {
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
        
        OCLExpression exp = propertyAssignment.getSlotExpression(); 
        /*
         * LtoM or MtoR Mapping. Property assignments are in the mapping's bottom pattern
         * and define values for middle model element's attributes. The property
         * assignments are being visited for each binding of variable in the mapping. 
         */
        // TODO Assignments in check domains should be treated as predicates.
        Area area = ((BottomPattern)propertyAssignment.eContainer()).getArea();
        if (area instanceof Mapping && isLtoMMapping((Mapping)area)) {
            if (exp instanceof VariableExp ) {      // What other type of expressions are there?
                Variable slotVar = (Variable) ((VariableExp)exp).getReferredVariable();
                // The nested evaluation environment is created in the mapping loop
                Object value = propertyAssignment.getValue().accept(this);
                // Assign the value to a binding of the slotVar
                //EObject slotBinding = tempRealizedElements.get(slotVar);
                Object slotBinding = getEvaluationEnvironment().getValueOf(slotVar);
                // TODO what happens if the target property is not a simple attribute?
                if (slotBinding != null) {
                    //ObjectValue ov = ValuesUtil.createObjectValue(slotBinding);
                    Property p = propertyAssignment.getTargetProperty();
                    p.initValue(slotBinding, value);
                }
            } else {
                throw new IllegalArgumentException("Unsupported " + propertyAssignment.eClass().getName()
                        + " specification. The slot expression type (" + exp.getType().getName() 
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
            if (exp instanceof VariableExp ) {      // What other type of expressions are there?
                Variable slotVar = (Variable) ((VariableExp)exp).getReferredVariable();
                //EObject slotBinding = variableValues.get(slotVar);
                Object slotBinding = getEvaluationEnvironment().getValueOf(slotVar);
                if(slotBinding != null) {
                    Object value = propertyAssignment.getValue().accept(this);
                    Property p = propertyAssignment.getTargetProperty();
                    //if (p.getOpposite() != null && p.getOpposite().isComposite()) {
                    //    p = p.getOpposite();
                    //    EObject eTarget = p.getETarget();
                    //    if (eTarget instanceof EStructuralFeature) {
                    //        EStructuralFeature eFeature = (EStructuralFeature) eTarget;
                    //        EList childs = (EList) ((EObject)value).eGet(eFeature);
                    //        childs.add(slotBinding);
                    //    }
                    //} else {
                        p.initValue(slotBinding, value);
                    //}
                } else {
                    throw new IllegalArgumentException("Unsupported " + propertyAssignment.eClass().getName()
                            + " specification. The assigment refers to a variable not defined in the" +
                            " current environment");
                }
            } else {
                throw new IllegalArgumentException("Unsupported " + propertyAssignment.eClass().getName()
                        + " specification. The slot expression type (" + exp.getType().getName() 
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
            // Create an element in the middle  model that has a kind equal to the variable type
            EObject element = (EObject) realizedVariable.getType().createInstance();
            // Add the EObject to the middle resource
            Resource mModel = ((QVTcDomainManager)modelManager).getMiddleModel();
            mModel.getContents().add(element);
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
            // Create an element in the R model that has a kind equal to the variable type
            EObject element = (EObject) realizedVariable.getType().createInstance();
            // Add the EObject to the R resource
            TypedModel tm = ((CoreDomain)area).getTypedModel();
            Resource rModel = ((QVTcDomainManager)modelManager).getTypeModelResource(tm);
            rModel.getContents().add(element);
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
