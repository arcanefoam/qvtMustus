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
package org.eclipse.qvtd.pivot.qvtbase.evaluation;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.pivot.Environment;
import org.eclipse.ocl.examples.pivot.OCLExpression;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitorImpl;
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

public class QVTbaseEvaluationVisitorImpl extends EvaluationVisitorImpl implements QVTbaseVisitor<Object> {

	public QVTbaseEvaluationVisitorImpl( @NonNull Environment env,  @NonNull EvaluationEnvironment evalEnv, @NonNull DomainModelManager modelManager) {
		super(env, evalEnv, modelManager);
	}
	
	@Nullable
	public Object visitBaseModel(@NonNull BaseModel object) {
		// TODO Add visit function or decide if it should never be implemented
		return visiting(object);
	}

	@Nullable
	public Object visitDomain(@NonNull Domain object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	@Nullable
	public Object visitFunction(@NonNull Function object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	@Nullable
	public Object visitFunctionParameter(@NonNull FunctionParameter object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	@Nullable
	public Object visitPattern(@NonNull Pattern object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	@Nullable
	public Object visitPredicate(@NonNull Predicate predicate) {
        
        // Each predicate has a conditionExpression that is an OCLExpression
        OCLExpression exp = predicate.getConditionExpression();
        // The predicated is visited with a nested environment
        Object expResult = exp.accept(this);
        return expResult;
	}

	@Nullable
	public Object visitRule(@NonNull Rule object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	@Nullable
	public Object visitTransformation(@NonNull Transformation object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	@Nullable
	public Object visitTypedModel(@NonNull TypedModel object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	@Nullable
	public Object visitUnit(@NonNull Unit object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	
}
