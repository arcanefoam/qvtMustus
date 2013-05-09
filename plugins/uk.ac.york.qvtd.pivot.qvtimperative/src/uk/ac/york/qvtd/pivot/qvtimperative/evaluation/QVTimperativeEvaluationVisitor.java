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
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.pivot.Environment;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitor;
import org.eclipse.qvtd.pivot.qvtimperative.util.QVTimperativeVisitor;

public interface QVTimperativeEvaluationVisitor extends QVTimperativeVisitor<Object>, EvaluationVisitor {
	
	@NonNull EvaluationVisitor createNestedEvaluator();
	
	/**
     * Obtains the environment that provides the metamodel semantics for the
     * expression to be evaluated.
     *  
	 * @return the environment
	 */
	Environment getEnvironment();
	
	/**
     * Obtains the evaluation environment that keeps track of variable values
     * and knows how to call operations, navigate properties, etc.
     * 
	 * @return the evaluation environment
	 */
	@NonNull EvaluationEnvironment getEvaluationEnvironment();
	
	/**
     * Obtains the mapping of model classes to their extents.
     * 
	 * @return the model manager
	 */
	@NonNull DomainModelManager getModelManager();
	
	QVTimperativeEvaluationVisitor createNestedLMVisitor();
	
	QVTimperativeEvaluationVisitor createNestedMMVisitor();
	
	QVTimperativeEvaluationVisitor createNestedMRVisitor();
	
	
}
