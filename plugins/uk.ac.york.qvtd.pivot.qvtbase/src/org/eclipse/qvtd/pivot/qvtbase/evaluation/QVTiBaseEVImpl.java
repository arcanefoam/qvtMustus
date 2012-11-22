/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     hhoyos - initial API and implementation
 ******************************************************************************/
package org.eclipse.qvtd.pivot.qvtbase.evaluation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.pivot.Environment;
import org.eclipse.ocl.examples.pivot.OCLExpression;
import org.eclipse.ocl.examples.pivot.OperationCallExp;
import org.eclipse.ocl.examples.pivot.PropertyCallExp;
import org.eclipse.ocl.examples.pivot.VariableExp;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitorImpl;
import org.eclipse.ocl.examples.pivot.internal.impl.UnlimitedNaturalLiteralExpImpl;
import org.eclipse.ocl.examples.pivot.internal.impl.VariableExpImpl;
import org.eclipse.qvtd.pivot.qvtbase.BaseModel;
import org.eclipse.qvtd.pivot.qvtbase.Domain;
import org.eclipse.qvtd.pivot.qvtbase.Function;
import org.eclipse.qvtd.pivot.qvtbase.FunctionParameter;
import org.eclipse.qvtd.pivot.qvtbase.Pattern;
import org.eclipse.qvtd.pivot.qvtbase.Predicate;
import org.eclipse.qvtd.pivot.qvtbase.Rule;
import org.eclipse.qvtd.pivot.qvtbase.Transformation;
import org.eclipse.qvtd.pivot.qvtbase.TypedModel;
import org.eclipse.qvtd.pivot.qvtbase.Unit;
import org.eclipse.qvtd.pivot.qvtbase.util.QVTbaseVisitor;

// TODO: Auto-generated Javadoc
/**
 * The Class QVTbaseEVNodeTypeImpl.
 */
public class QVTiBaseEVImpl extends EvaluationVisitorImpl implements QVTbaseVisitor<Object> {
	

	/** The middle factory to create EObjects from the middle metamodel. */
	protected EFactory middleFactory;
	
	protected Set<EObject> middleModel;
	
	// Hashmap for variables. This keeps track of the binding of a variable
	// to a model object
	/* FIXME The spec says "This means that the bottom pattern is evaluated using
		the variable values of the valid binding of the guard pattern." Should
		we keep a separate reference of the variables in the area?
	*/ 
	// FIXME Probably the List of objects is more useful as an ArrayList
	protected HashMap<String, HashSet<EObject>> varMap;

	/**
	 * Constructor.
	 *
	 * @param env an evaluation environment (map of variable names to values)
	 * @param evalEnv the eval env
	 * @param modelManager a map of classes to their instance lists
	 */
	public QVTiBaseEVImpl( @NonNull Environment env,  @NonNull EvaluationEnvironment evalEnv, @NonNull DomainModelManager modelManager) {
		super(env, evalEnv, modelManager);
		Transformation transformation =	(Transformation)((QvtModelManager)modelManager).getContext().getNestedPackage().get(0);
		Resource mr = ((QvtModelManager)modelManager).getTypeModelResource(transformation.getModelParameter(""));
		EPackage model = (EPackage)mr.getContents().get(0);
		middleFactory = model.getEFactoryInstance();
		// For creating the middleModel we need a model to hold the elements, which is basically a set
		middleModel = new HashSet<EObject>();
	}


	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtbase.util.QVTbaseVisitor#visitBaseModel(org.eclipse.qvtd.pivot.qvtbase.BaseModel)
	 */
	@Nullable
	public Object visitBaseModel(@NonNull BaseModel object) {
		// Should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtbase.util.QVTbaseVisitor#visitDomain(org.eclipse.qvtd.pivot.qvtbase.Domain)
	 */
	@Nullable
	public Object visitDomain(@NonNull Domain object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtbase.util.QVTbaseVisitor#visitFunction(org.eclipse.qvtd.pivot.qvtbase.Function)
	 */
	@Nullable
	public Object visitFunction(@NonNull Function object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtbase.util.QVTbaseVisitor#visitFunctionParameter(org.eclipse.qvtd.pivot.qvtbase.FunctionParameter)
	 */
	@Nullable
	public Object visitFunctionParameter(@NonNull FunctionParameter object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtbase.util.QVTbaseVisitor#visitPattern(org.eclipse.qvtd.pivot.qvtbase.Pattern)
	 */
	@Nullable
	public Object visitPattern(@NonNull Pattern pattern) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtbase.util.QVTbaseVisitor#visitPredicate(org.eclipse.qvtd.pivot.qvtbase.Predicate)
	 */
	@Nullable
	public Object visitPredicate(@NonNull Predicate predicate) {
		// If the predicate's pattern  is a GuardPatter, the predicate validates.
		// If the patter is a BottomPattern the predicate defines bindings
		
		// Each predicate has a conditionExpression that is an OCLExpression
		OCLExpression exp = predicate.getConditionExpression();
		/* 
		 * There has to be some type of interpretation of the OCL expression
		 */
		Object expResult = exp.accept(this);
		return true;	// The OCL expression evaluated to true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtbase.util.QVTbaseVisitor#visitRule(org.eclipse.qvtd.pivot.qvtbase.Rule)
	 */
	@Nullable
	public Object visitRule(@NonNull Rule object) {
		// TODO Add visit function or decide if it should never be implemented
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtbase.util.QVTbaseVisitor#visitTransformation(org.eclipse.qvtd.pivot.qvtbase.Transformation)
	 */
	@Nullable
	public Object visitTransformation(@NonNull Transformation transformation) {
		// The transformation only contains information relevant to domain specification
		// No visit required
		throw new UnsupportedOperationException("Transformation should not be visited");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtbase.util.QVTbaseVisitor#visitTypedModel(org.eclipse.qvtd.pivot.qvtbase.TypedModel)
	 */
	@Nullable
	public Object visitTypedModel(@NonNull TypedModel object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtbase.util.QVTbaseVisitor#visitUnit(org.eclipse.qvtd.pivot.qvtbase.Unit)
	 */
	@Nullable
	public Object visitUnit(@NonNull Unit object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	
}
