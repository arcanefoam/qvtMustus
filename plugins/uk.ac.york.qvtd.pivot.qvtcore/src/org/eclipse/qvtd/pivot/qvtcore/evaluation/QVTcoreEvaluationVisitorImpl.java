/*******************************************************************************
 * Copyright (c) 2013 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     hhoyos - initial API and implementation
 ******************************************************************************/
package org.eclipse.qvtd.pivot.qvtcore.evaluation;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.pivot.Environment;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.qvtd.pivot.qvtcore.CoreModel;
import org.eclipse.qvtd.pivot.qvtcore.Mapping;
import org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor;

import uk.ac.york.qvtd.pivot.qvtcorebase.evaluation.QVTcoreBaseEvaluationVisitorImpl;

public class QVTcoreEvaluationVisitorImpl extends QVTcoreBaseEvaluationVisitorImpl
        implements QVTcoreVisitor<Object> {

    
    public QVTcoreEvaluationVisitorImpl(Environment env,
            EvaluationEnvironment evalEnv, DomainModelManager modelManager) {
        super(env, evalEnv, modelManager);
        
    }

    @Override
    @Nullable
    public Object visitCoreModel(CoreModel object) {
     // TODO Add visit function or decide if it should never be implemented
        throw new UnsupportedOperationException(
                "Visit method not implemented yet");
    }

    @Override
    @Nullable
    public Object visitMapping(Mapping object) {
     // TODO Add visit function or decide if it should never be implemented
        throw new UnsupportedOperationException(
                "Visit method not implemented yet");
    }

    

}
