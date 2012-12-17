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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.pivot.Environment;
import org.eclipse.ocl.examples.pivot.Variable;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.qvtd.pivot.qvtbase.Domain;
import org.eclipse.qvtd.pivot.qvtbase.Rule;
import org.eclipse.qvtd.pivot.qvtbase.Transformation;
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

// TODO: Auto-generated Javadoc
/**
 * QVTcoreEvaluationVisitorImpl is the class for ...
 */
public class QVTcoreEvaluationVisitorImpl extends QVTbaseEvaluationVisitorImpl implements QVTcoreVisitor<Object> {
	
	
	/** The variable values. */
	protected HashMap<Variable, HashSet<EObject>> variableValues = new HashMap<Variable, HashSet<EObject>>();
	
	
	/**
	 * Instantiates a new qV tcore evaluation visitor impl.
	 *
	 * @param env the env
	 * @param evalEnv the eval env
	 * @param modelManager the model manager
	 */
	public QVTcoreEvaluationVisitorImpl(@NonNull Environment env,
			@NonNull EvaluationEnvironment evalEnv, @NonNull DomainModelManager modelManager) {
		super(env, evalEnv, modelManager);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitAssignment(org.eclipse.qvtd.pivot.qvtcore.Assignment)
	 */
	@Nullable
	public Object visitAssignment(@NonNull Assignment object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitBottomPattern(org.eclipse.qvtd.pivot.qvtcore.BottomPattern)
	 */
	@Nullable
	public Object visitBottomPattern(@NonNull BottomPattern bottomPattern) {
		for (Variable var : bottomPattern.getVariable()) {
			// Find the correct value(s) for the variable
			if (bottomPattern.getArea() instanceof CoreDomain) {
				// Values of variables in a CoreDomain exist in the domain's TypedModel
				if (!variableValues.containsKey(var.getName())) {
					// Get all the objects from the TypeModel of the Domain
					CoreDomain d = (CoreDomain)bottomPattern.getArea();
					TypedModel m = d.getTypedModel();
					HashSet<EObject> objectSet = new HashSet<>();
					// TODO How to use the allInstances?
					Resource resource = ((QvtModelManager)modelManager).getTypeModelResource(m);
					for (TreeIterator<EObject> contents = resource.getAllContents(); contents.hasNext();) {
						EObject object = contents.next();
						if (object.eClass().getName().equals(var.getType().getName())) {
							objectSet.add(object);
						}
					}
					// Validate the check/enforce conditions
					if (d.isIsCheckable() && d.isIsEnforceable()) {
						if (objectSet.size() == 0) {
							// Create the new object for the variable, but how many? One because that is the mapping?
							Resource resource1 = ((QvtModelManager)modelManager).getTypeModelResource(m);
							// TODO Define how to create the new objects. Do we need a reference to the
							// model's mm so we can get a factory and create new EObjects?
							System.out.println("Need to code the enforcement");
						} /* else {
							// Enforce the Constraints, but that happens when visiting the Constraints of the domain
						}*/
					} else if (d.isIsCheckable() && objectSet.size() == 0) {
						return false;
					}
					variableValues.put(var, objectSet);
				}
			} else { // if (bottomPattern.getArea() instanceof CoreDomain)
				// Values of variables in the Mapping exist in middle model
				HashSet<EObject> objectSet = new HashSet<>();
				//objectSet.add(varObject);
				variableValues.put(var, objectSet);
			}
		}
		for (RealizedVariable var : bottomPattern.getRealizedVariable()) {
			var.accept(this);
		}
		// TODO verify that the Predicate is a BooleanOCLExpr, is the spec wrong and should be OCLExpr ?
		for (Predicate predicate : bottomPattern.getPredicate()) {
			predicate.accept(this);
		}
		for(Assignment assigment : bottomPattern.getAssignment()){
			assigment.accept(this);
		}
		for(EnforcementOperation enforceOp : bottomPattern.getEnforcementOperation()) {
			enforceOp.accept(this);
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitCoreDomain(org.eclipse.qvtd.pivot.qvtcore.CoreDomain)
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

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitCoreModel(org.eclipse.qvtd.pivot.qvtcore.CoreModel)
	 */
	@Nullable
	public Object visitCoreModel(@NonNull CoreModel coreModel) {
		// CoreModel has a transformation (nestedPackage)
		// DEFINE Can a single QVT model has multiple transformations?
		Transformation transformation =	((Transformation)coreModel.getNestedPackage().get(0));
		for (Rule rule : transformation.getRule()){
			((Mapping)rule).accept(this);
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitCorePattern(org.eclipse.qvtd.pivot.qvtcore.CorePattern)
	 */
	@Nullable
	public Object visitCorePattern(@NonNull CorePattern object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitEnforcementOperation(org.eclipse.qvtd.pivot.qvtcore.EnforcementOperation)
	 */
	@Nullable
	public Object visitEnforcementOperation(@NonNull EnforcementOperation object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitGuardPattern(org.eclipse.qvtd.pivot.qvtcore.GuardPattern)
	 */
	@Nullable
	public Object visitGuardPattern(@NonNull GuardPattern object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitMapping(org.eclipse.qvtd.pivot.qvtcore.Mapping)
	 */
	@Nullable
	public Object visitMapping(@NonNull Mapping mapping) {
		for (Domain domain : mapping.getDomain()){
			((CoreDomain)domain).accept(this);
		}
		// TODO Implement guard visit methods
		//boolean guardMet = (Boolean)mapping.getGuardPattern().accept(this);
		//if(guardMet) {
			// MiddleBottomPattern (aka where clause)
			mapping.getBottomPattern().accept(this);
			
			// Nested mappings
			for (Mapping localMapping : mapping.getLocal()) {
				localMapping.accept(this);
			}
			return true;
		//}
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitPropertyAssignment(org.eclipse.qvtd.pivot.qvtcore.PropertyAssignment)
	 */
	@Nullable
	public Object visitPropertyAssignment(@NonNull PropertyAssignment object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitRealizedVariable(org.eclipse.qvtd.pivot.qvtcore.RealizedVariable)
	 */
	@Nullable
	public Object visitRealizedVariable(@NonNull RealizedVariable object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitVariableAssignment(org.eclipse.qvtd.pivot.qvtcore.VariableAssignment)
	 */
	@Nullable
	public Object visitVariableAssignment(@NonNull VariableAssignment object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	
}
