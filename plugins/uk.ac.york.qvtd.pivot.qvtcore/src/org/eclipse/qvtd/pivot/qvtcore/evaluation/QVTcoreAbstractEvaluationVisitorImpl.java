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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import org.eclipse.qvtd.pivot.qvtcore.MappingCall;
import org.eclipse.qvtd.pivot.qvtcore.MappingCallBinding;
import org.eclipse.qvtd.pivot.qvtcore.NestedMapping;
import org.eclipse.qvtd.pivot.qvtcore.PropertyAssignment;
import org.eclipse.qvtd.pivot.qvtcore.RealizedVariable;
import org.eclipse.qvtd.pivot.qvtcore.VariableAssignment;
import org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor;

import uk.ac.york.qvtd.library.executor.QVTcDomainManager;

/**
 * QVTcoreEvaluationVisitorImpl is the class for ...
 */
public class QVTcoreAbstractEvaluationVisitorImpl extends QVTbaseEvaluationVisitorImpl
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
    public QVTcoreAbstractEvaluationVisitorImpl(@NonNull Environment env,
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
        throw new UnsupportedOperationException(
                "Visit method not implemented yet");
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
        throw new UnsupportedOperationException(
                "Visit method not implemented yet");
        
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
            /*getEvaluationEnvironment().clear();
            for (NestedMapping m : ((Mapping) rule).getLocal()) {
                QVTcoreMREvaluationVisitor MRVisitor = new QVTcoreMREvaluationVisitor(
                        getEnvironment(), getEvaluationEnvironment(), modelManager);
                if (isMtoMMapping(m)) {
                    m.accept(MRVisitor);
                }
            }*/
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
        QVTcoreAbstractEvaluationVisitorImpl ne = new QVTcoreAbstractEvaluationVisitorImpl(environment, nestedEvalEnv, getModelManager());
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
