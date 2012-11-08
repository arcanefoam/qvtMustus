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
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ocl.examples.pivot.evaluation.PivotModelManager;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;


/**
 * The Qvt Model Manager is responsible for keeping the QVT engine models. It
 * knows which models are input, which are output and the name of the TypedModel
 * that the model represents 
 */
public class QvtModelManager extends PivotModelManager {

	
	private HashMap<String, HashSet<Resource>> modelMap;
	
	/**
	 * Instantiates a new qvt model manager.
	 *
	 * @param metaModelManager the meta model manager
	 * @param context the context
	 */
	public QvtModelManager(@NonNull MetaModelManager metaModelManager, EObject context, int modelCount) {
		super(metaModelManager, context);
		this.modelMap = new HashMap<>(modelCount);
				
	}
	
	/**
	 * Adds the model to the list of models identified by the typed model name.
	 *
	 * @param typedModelName the typedModel name
	 * @param model the model
	 */
	public void addModel(String typedModelName, Resource model) {
		if(modelMap.containsKey(typedModelName)) {
			modelMap.get(typedModelName).add(model);
		} else {
			HashSet<Resource> set = new HashSet<Resource>();
			set.add(model);
			modelMap.put(typedModelName, set);
		}
	}
	
	
	/**
	 * Gets the models for a given typeModel (by name)
	 *
	 * @param typeModelName the type model name
	 * @return the models
	 */
	public Set<Resource> getTypeModelModels(String typeModelName) {
		return modelMap.get(typeModelName);
	}
	

}
