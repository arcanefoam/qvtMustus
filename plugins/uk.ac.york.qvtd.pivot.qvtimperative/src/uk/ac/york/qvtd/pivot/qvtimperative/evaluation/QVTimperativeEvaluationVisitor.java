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
package uk.ac.york.qvtd.pivot.qvtimperative.evaluation;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.pivot.Environment;
import org.eclipse.ocl.examples.pivot.EnvironmentFactory;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitor;
import org.eclipse.qvtd.pivot.qvtbase.Transformation;
import org.eclipse.qvtd.pivot.qvtimperative.ImperativeModel;

/**
 * QVTimperativeEvaluationVisitor is the class for ...
 */
public class QVTimperativeEvaluationVisitor extends QVTimperativeAbstractEvaluationVisitor {

        
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
    public QVTimperativeEvaluationVisitor(@NonNull Environment env,
            @NonNull EvaluationEnvironment evalEnv,
            @NonNull DomainModelManager modelManager) {
        super(env, evalEnv, modelManager);
    }

    @Override
    public @NonNull EvaluationVisitor createNestedEvaluator() {
        Environment environment = getEnvironment();
        EnvironmentFactory factory = environment.getFactory();
        EvaluationEnvironment nestedEvalEnv = factory.createEvaluationEnvironment(getEvaluationEnvironment());
        QVTimperativeEvaluationVisitor ne = new QVTimperativeEvaluationVisitor(environment, nestedEvalEnv, getModelManager());
        return ne;
    }

    @Override
    public @Nullable Object visitImperativeModel(@NonNull ImperativeModel imperativeModel) {
        // CoreModel has a transformation (nestedPackage)
        // DEFINE Can a single QVT model has multiple transformations?
        Transformation transformation = ((Transformation) imperativeModel.getNestedPackage().get(0));
        QVTimperativeLMEvaluationVisitor LMVisitor = new QVTimperativeLMEvaluationVisitor(
                getEnvironment(), getEvaluationEnvironment(), modelManager);
        transformation.getRule().get(0).accept(LMVisitor);
        return true;
    }
}
