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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.pivot.Environment;
import org.eclipse.ocl.examples.pivot.EnvironmentFactory;
import org.eclipse.ocl.examples.pivot.Variable;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.qvtd.pivot.qvtbase.Domain;
import org.eclipse.qvtd.pivot.qvtbase.Rule;
import org.eclipse.qvtd.pivot.qvtbase.Transformation;
import org.eclipse.qvtd.pivot.qvtbase.TypedModel;
import org.eclipse.qvtd.pivot.qvtcorebase.CoreDomain;
import org.eclipse.qvtd.pivot.qvtimperative.ImperativeModel;

import uk.ac.york.qvtd.library.executor.QVTcDomainManager;

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
    public @NonNull QVTimperativeEvaluationVisitor createNestedEvaluator() {
        Environment environment = getEnvironment();
        EnvironmentFactory factory = environment.getFactory();
        EvaluationEnvironment nestedEvalEnv = factory.createEvaluationEnvironment(getEvaluationEnvironment());
        QVTimperativeEvaluationVisitor ne = new QVTimperativeEvaluationVisitor(environment, nestedEvalEnv, getModelManager());
        return ne;
    }

    @Override
    public @Nullable Object visitImperativeModel(@NonNull ImperativeModel imperativeModel) {
    	for (org.eclipse.ocl.examples.pivot.Package pkge : imperativeModel.getNestedPackage()) {
    		pkge.accept(this);
    	}
        return true;
    }

    @Override
    public @Nullable Object visitPackage(@NonNull org.eclipse.ocl.examples.pivot.Package pkge) {
        return true;
    }

    @Override
    public @Nullable Object visitTransformation(@NonNull Transformation transformation) {
        QVTimperativeLMEvaluationVisitor LMVisitor = new QVTimperativeLMEvaluationVisitor(
                getEnvironment(), getEvaluationEnvironment(), modelManager);
    	for (Rule rule : transformation.getRule()) {
    		// Find bindings before invoking the mapping so all visitors are equal
    		Map<Variable, List<Object>>  mappingBindings = new HashMap<Variable, List<Object>>();
    		for (Domain domain : rule.getDomain()) {
                for (Variable var : ((CoreDomain)domain).getGuardPattern().getVariable()) {
                	getEvaluationEnvironment().add(var, null);
                    TypedModel m;
                    m = ((CoreDomain)domain).getTypedModel();
                    List<Object> bindingValuesSet = ((QVTcDomainManager) modelManager).getElementsByType(m, var.getType());
                    mappingBindings.put(var, bindingValuesSet);
                }
            }
    		// FIXME This does not work if the root mapping has two or more variables
    		for (Map.Entry<Variable, List<Object>> mappingBindingEntry : mappingBindings.entrySet()) {
    			Variable var = mappingBindingEntry.getKey();
                for (Object binding : mappingBindingEntry.getValue()) {
                	getEvaluationEnvironment().replace(var, binding);
                	// The MiddleGuardPattern should be empty in the root mapping, i.e. no need to find bindings
                	rule.accept(LMVisitor);
                }
    		}
    		break;		// FIXME ?? multiple rules
    	}
        return true;
    }
}
