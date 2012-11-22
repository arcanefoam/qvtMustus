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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ocl.examples.pivot.evaluation.PivotModelManager;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
import org.eclipse.qvtd.pivot.qvtbase.Transformation;
import org.eclipse.qvtd.pivot.qvtbase.TypedModel;
import org.eclipse.qvtd.pivot.qvtcore.CoreModel;


/**
 * The Qvt Model Manager is responsible for keeping the QVT engine models. It
 * knows which models are input, which are output and the name of the TypedModel
 * that the model represents 
 */
public class QvtModelManager extends PivotModelManager {

	// Only one resource per TypedModel TODO how to manage aliases?
	private HashMap<TypedModel, Resource> modelMap;
	private CoreModel context;
	
	/**
	 * Instantiates a new qvt model manager.
	 *
	 * @param metaModelManager the meta model manager
	 * @param context the context
	 */
	public QvtModelManager(@NonNull MetaModelManager metaModelManager, EObject context, int modelCount) {
		super(metaModelManager, context);
		this.context = (CoreModel)context;
		this.modelMap = new HashMap<>(modelCount);
				
	}
	
	/**
	 * Adds the model to the list of models identified by the typed model, by
	 * looking for a typedModel with the parameter name. If a model was already
	 * binded to the TypedModel it will be replaced.
	 *
	 * @param typedModel the typedModel
	 * @param model the model
	 */
	public void addModel(String typedModelName, Resource model) {
		// Get the typeModel from the typedModelName
		// CoreModel has a transformation (nestedPackage)
		// TODO what if a qvt file has multiple transformations?
		Transformation transformation =	((Transformation)context.getNestedPackage().get(0));
		modelMap.put(transformation.getModelParameter(typedModelName), model);
	}
	
	
	/**
	 * Gets the model (resource) for a given TypedModel.
	 *
	 * @param typedModel the typed model
	 * @return the resource
	 */
	public Resource getTypeModelResource(TypedModel typedModel) {
		return modelMap.get(typedModel);
	}
	
	public CoreModel getContext() {
		return this.context;
	}
	

}
