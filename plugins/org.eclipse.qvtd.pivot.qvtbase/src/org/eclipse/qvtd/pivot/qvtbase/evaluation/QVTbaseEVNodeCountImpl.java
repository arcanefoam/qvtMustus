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

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.domain.values.Value;
import org.eclipse.ocl.examples.pivot.Environment;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitorImpl;
import org.eclipse.ocl.examples.pivot.utilities.PivotResource;
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

public class QVTbaseEVNodeTypeImpl extends EvaluationVisitorImpl implements QVTbaseVisitor<Object> {
	
	
	private PivotResource astRoot;
	private Resource inputModel;
	private Resource outputModel;
	
	// Node counters
	private int ruleCount;
	private int predicateCount;
	

	/**
	 * Constructor
	 * 
	 * @param env
	 *            an evaluation environment (map of variable names to values)
	 * @param modelManager
	 *            a map of classes to their instance lists
	 */
	public QVTbaseEVNodeTypeImpl( @NonNull Environment env,  @NonNull EvaluationEnvironment evalEnv, @NonNull DomainModelManager modelManager) {
		super(env, evalEnv, modelManager);
	}
	
	public QVTbaseEVNodeTypeImpl(Environment env,
			EvaluationEnvironment evalEnv,
			DomainModelManager modelManager, PivotResource astRoot,
			Resource inputModel, Resource outputModel) {
		
		super(env, evalEnv, modelManager);
		this.astRoot = astRoot;
		this.inputModel = inputModel;
		this.outputModel = outputModel;
	}


	@Nullable
	public Object visitBaseModel(BaseModel object) {
		// Should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	@Nullable
	public Object visitDomain(Domain object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	@Nullable
	public Object visitFunction(Function object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	@Nullable
	public Object visitFunctionParameter(FunctionParameter object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	@Nullable
	public Object visitPattern(Pattern object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	@Nullable
	public Value visitPredicate(Predicate predicate) {
		// TODO Add visit function or decide if it should never be implemented
		predicateCount ++;
		System.out.println("Predicate" + predicateCount);
		// TODO Sort out how to handle OCLExpressions
		//predicate.getConditionExpression().accept(this);
		return null;
	}

	@Nullable
	public Object visitRule(Rule object) {
		// TODO Add visit function or decide if it should never be implemented
		ruleCount ++;
		System.out.println("Rule" + ruleCount);
		return null;
	}

	@Nullable
	public Object visitTransformation(Transformation transformation) {
		// The transformation only contains information relevant to domain specification
		// No visit required
		throw new UnsupportedOperationException("Transformation should not be visited");
	}

	@Nullable
	public Object visitTypedModel(TypedModel object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	@Nullable
	public Object visitUnit(Unit object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	
}
