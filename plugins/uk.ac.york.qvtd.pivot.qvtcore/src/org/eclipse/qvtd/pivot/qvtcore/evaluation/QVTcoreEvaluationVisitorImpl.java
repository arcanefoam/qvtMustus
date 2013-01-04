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

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.pivot.Environment;
import org.eclipse.ocl.examples.pivot.Variable;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitor;
import org.eclipse.qvtd.pivot.qvtbase.Domain;
import org.eclipse.qvtd.pivot.qvtbase.Predicate;
import org.eclipse.qvtd.pivot.qvtbase.Rule;
import org.eclipse.qvtd.pivot.qvtbase.Transformation;
import org.eclipse.qvtd.pivot.qvtbase.TypedModel;
import org.eclipse.qvtd.pivot.qvtbase.evaluation.QVTbaseEvaluationVisitorImpl;
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

    /** The valid binding for a variable for a given loop. */
    protected HashMap<Variable, EObject> variableValues = new HashMap<Variable, EObject>();

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
        HashMap<Variable, HashSet<EObject>> patternValidBindings = new HashMap<>();
        for (Variable var : bottomPattern.getVariable()) {
            // Find the bindings for the variable
            if (bottomPattern.getArea() instanceof CoreDomain) {
                // Values of variables in a CoreDomain exist in the domain's TypedModel
                CoreDomain d = (CoreDomain) bottomPattern.getArea();
                TypedModel m = d.getTypedModel();
                HashSet<EObject> objectSet = new HashSet<>();
                Resource resource = ((QVTcDomainManager) modelManager).getTypeModelResource(m);
                for (TreeIterator<EObject> contents = resource.getAllContents(); contents.hasNext();) {
                    EObject object = contents.next();
                    if (object.eClass().getName().equals(var.getType().getName())) {
                        objectSet.add(object);
                    }
                }
                if (d.isIsCheckable() && objectSet.size() == 0) {
                    return false;
                }
                patternValidBindings.put(var, objectSet);
                /*if (!variableValues.containsKey(var.getName())) {
                } else 
                    variableValues.put(var, objectSet);
                }*/
            } else { // if (bottomPattern.getArea() instanceof CoreDomain)
                throw new UnsupportedOperationException("MiddleBottomPattern should not have Variables.");
            }
        }
        for (RealizedVariable var : bottomPattern.getRealizedVariable()) {
            if (bottomPattern.getArea() instanceof CoreDomain) {
                CoreDomain d = (CoreDomain) bottomPattern.getArea();
                if (!d.isIsEnforceable()) {
                    throw new UnsupportedOperationException("Realized variables can only exist in Enforced domains");
                }
            }
            var.accept(this);
        }
        // Assignments should be related to realized variables?
        for (Assignment assigment : bottomPattern.getAssignment()) {
            assigment.accept(this);
        }
        // Predicates validate bindings for variables (and realized variables?)
        // Evaluate the predicate for each binding of the variables
        Iterator<Entry<Variable, HashSet<EObject>>> it = patternValidBindings.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Variable, HashSet<EObject>> pairs = (Map.Entry<Variable, HashSet<EObject>>)it.next();
            Iterator<EObject> eIt = pairs.getValue().iterator();
            while (eIt.hasNext())
            {
                // Create a nested visitor where
                EvaluationVisitor ne = createNestedEvaluator();
                Variable var = pairs.getKey();
                if(var != null) {
                    ne.getEvaluationEnvironment().add(var, eIt.next());
                    for (Predicate predicate : bottomPattern.getPredicate()) {
                        // If the predicate is not true, the binding is not valid
                        Boolean result = (Boolean) predicate.accept(ne);
                        if (result != null && !result) {
                            // If any of the predicates fails, the binding is not valid
                            it.remove();
                            break;
                        }
                    }
                }
            }
        }
        for (EnforcementOperation enforceOp : bottomPattern
                .getEnforcementOperation()) {
            enforceOp.accept(this);
        }
        return true;
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
        // TODO Implement guard visit methods
        //boolean guardMet = (Boolean)coreDomain.getGuardPattern().accept(this);
        //if(guardMet) {
            return coreDomain.getBottomPattern().accept(this);
        //}
        //return false;

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
        Transformation transformation = ((Transformation) coreModel
                .getNestedPackage().get(0));
        for (Rule rule : transformation.getRule()) {
            ((Mapping) rule).accept(this);
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
        for (Domain domain : mapping.getDomain()) {
            // Input domains should be treated differently than output domains
            // Input domains define the loop counter variable, output domains
            // not
            // MiddleBottomPatern and nested mappings should be executed for
            // each
            // valid binding of the input domain.

            // Input domain is checked and not enforced
            if (domain.isIsCheckable() && !domain.isIsEnforceable()) {
                // Cast to avoid visiting the base evaluator
                // Do we return a var, a count or the mapping?
                HashMap<Variable, HashSet<EObject>> loopVariableValues = (HashMap) ((CoreDomain) domain).accept(this);
                for (Variable var : loopVariableValues.keySet()) {
                    for (EObject e : loopVariableValues.get(var)) {
                        // Use each of the bindings for evaluation in the loop
                        replaceVariableValue(var, e);
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
            } else if (domain.isIsEnforceable()) {
                ((CoreDomain) domain).accept(this);
            } else {
                System.out.println("All domains must be either check or check+enforce");
            }

        }
        // TODO Implement guard visit methods
        // boolean guardMet = (Boolean)mapping.getGuardPattern().accept(this);
        // if(guardMet) {
        // MiddleBottomPattern (aka where clause)
        mapping.getBottomPattern().accept(this);

        // Nested mappings
        for (Mapping localMapping : mapping.getLocal()) {
            localMapping.accept(this);
        }
        return true;
        // }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitPropertyAssignment
     * (org.eclipse.qvtd.pivot.qvtcore.PropertyAssignment)
     */
    @Nullable
    public Object visitPropertyAssignment(@NonNull PropertyAssignment object) {
        // TODO Add visit function or decide if it should never be implemented
        throw new UnsupportedOperationException(
                "Visit method not implemented yet");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitRealizedVariable
     * (org.eclipse.qvtd.pivot.qvtcore.RealizedVariable)
     */
    @Nullable
    public Object visitRealizedVariable(@NonNull RealizedVariable object) {
        // TODO Add visit function or decide if it should never be implemented
        throw new UnsupportedOperationException(
                "Visit method not implemented yet");
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

    // This method should only be called for nested environments
    private EObject replaceVariableValue(Variable variable, EObject value) {
        return variableValues.put(variable, value);
    }
    
    public void setVariableValues(HashMap<Variable, EObject> variableValues) {
        this.variableValues = variableValues;
    }
    
    
    @Override
    public @NonNull EvaluationVisitor createNestedEvaluator() {
        QVTcoreEvaluationVisitorImpl ne = (QVTcoreEvaluationVisitorImpl) super.createNestedEvaluator();
        ne.setVariableValues(variableValues);
        return ne;
    }

}
