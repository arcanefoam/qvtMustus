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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.pivot.Variable;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitorImpl;
import org.eclipse.qvtd.pivot.qvtbase.Domain;
import org.eclipse.qvtd.pivot.qvtbase.Rule;
import org.eclipse.qvtd.pivot.qvtbase.Transformation;
import org.eclipse.qvtd.pivot.qvtbase.TypedModel;
import org.eclipse.qvtd.pivot.qvtcorebase.CoreDomain;

import uk.ac.york.qvtd.library.executor.QVTcDomainManager;


public class QVTimperativeTracingEvaluationVisitor extends
		QVTimperativeEvaluationVisitorDecorator {

	public QVTimperativeTracingEvaluationVisitor(
			QVTimperativeEvaluationVisitor<Object> decorated) {
		super(decorated);

	}
	
	@Override
    public @Nullable Object visitTransformation(@NonNull Transformation transformation) {
		System.out.println("Transformation: " + transformation.getName());
		Object result = getDelegate().visitTransformation(transformation);
		System.out.println("Result of the transformation was " + (Boolean)result);
		return result;
        
    }


}
