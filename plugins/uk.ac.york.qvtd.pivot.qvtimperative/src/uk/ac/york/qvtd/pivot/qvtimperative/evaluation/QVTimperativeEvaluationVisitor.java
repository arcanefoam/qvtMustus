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

import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitor;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitorImpl;
import org.eclipse.qvtd.pivot.qvtimperative.util.QVTimperativeVisitor;

public interface QVTimperativeEvaluationVisitor extends QVTimperativeVisitor<Object>, EvaluationVisitor {
	
	EvaluationVisitorImpl createNestedLMVisitor();
	
	EvaluationVisitorImpl createNestedMMVisitor();
	
	EvaluationVisitorImpl createNestedMRVisitor();
}
