/*******************************************************************************
 * Copyright (c) 2013 The University of York and Willink Transformations.
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
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitor;
import org.eclipse.ocl.examples.pivot.evaluation.TracingEvaluationVisitor;
import org.eclipse.qvtd.pivot.qvtbase.Transformation;
import org.eclipse.qvtd.pivot.qvtimperative.ImperativeModel;



public class QVTimperativeTracingEvaluationVisitor extends
		QVTimperativeEvaluationVisitorDecorator {

	public QVTimperativeTracingEvaluationVisitor(
			QVTimperativeEvaluationVisitor<Object> decorated) {
		super(decorated);

	}
	
	@Override
	public @NonNull EvaluationVisitor createNestedEvaluator() {
		return new TracingEvaluationVisitor(super.createNestedEvaluator());
	}
	
	@Override
    public @Nullable Object visitImperativeModel(ImperativeModel imperativeModel) {
		return getDelegate().visitImperativeModel(imperativeModel);
	}
	
	@Override
	public @Nullable Object visitTransformation(@NonNull Transformation transformation) {
		System.out.println("Transformation " + transformation.getName());
		return getDelegate().visitTransformation(transformation);
	}
	
	
	
	/*public EvaluationVisitorImpl createNestedLMVisitor() {
		return new TracingEvaluationVisitor(super.createNestedLMVisitor());
	}

	public EvaluationVisitorImpl createNestedMMVisitor() {
		return getDelegate().createNestedMMVisitor();
	}

	public EvaluationVisitorImpl createNestedMRVisitor() {
		return getDelegate().createNestedMRVisitor();
	}*/
	

}
