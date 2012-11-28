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

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
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
	private HashMap<TypedModel, EFactory> factoryMap;
	private CoreModel context;
	private EFactory middleFactory;
	private Resource middleModel;
	
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
		this.factoryMap = new HashMap<>(modelCount);
				
	}
	
	/**
	 * Adds the model to the list of models identified by the typed model, by
	 * looking for a typedModel with the parameter name. If a model was already
	 * binded to the TypedModel it will be replaced.
	 *
	 * @param typedModel the typedModel
	 * @param model the model
	 */
	public void addModel(String typedModelName, @NonNull Resource model, @NonNull Resource metamodel) {
		// Get the typeModel from the typedModelName
		// CoreModel has a transformation (nestedPackage)
		// TODO what if a qvt file has multiple transformations?
		Transformation transformation =	((Transformation)context.getNestedPackage().get(0));
		modelMap.put(transformation.getModelParameter(typedModelName), model);
		EPackage mEPackage = (EPackage)metamodel.getContents().get(0);
		factoryMap.put(transformation.getModelParameter(typedModelName), mEPackage.getEFactoryInstance());
	}
	
	
	/**
	 * Gets the model (resource) for a given TypedModel.
	 *
	 * @param typedModel the typed model
	 * @return the resource
	 */
	public Resource getTypeModelResource(@NonNull TypedModel typedModel) {
		return modelMap.get(typedModel);
	}
	
	public EFactory getTypeModelFactory(@NonNull TypedModel typedModel) {
		return factoryMap.get(typedModel);
	}
	
	
	public CoreModel getContext() {
		return this.context;
	}
	
	public Collection<Resource> getAllModelResources() {
		return modelMap.values();
	}

	public void createMiddleModel(@NonNull Resource metamodel) {
		// TODO Auto-generated method stub
		middleModel = new ResourceImpl();
		EPackage mEPackage = (EPackage)metamodel.getContents().get(0);
		middleFactory = mEPackage.getEFactoryInstance();
	}
	
	public void createMiddleModelWithTrace(@NonNull Resource model, @NonNull Resource metamodel) {
		// TODO Auto-generated method stub
		middleModel = model;
		EPackage mEPackage = (EPackage)metamodel.getContents().get(0);
		middleFactory = mEPackage.getEFactoryInstance();
	}
	
	public EFactory getMiddleFactory() {
		return this.middleFactory;
	}
	
	public Resource getMiddleModel() {
		return this.middleModel;
	}
	
	public Collection<EFactory> getFactories() {
		return factoryMap.values();
	}

	public void saveModels() {
		for (Map.Entry<TypedModel, Resource> entry : modelMap.entrySet()) {
		    Resource value = entry.getValue();
		    try{
		        /*
		        * Save the resource
		        */
		    	Map<Object, Object> options = new HashMap<Object, Object>();
		    	options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
		        value.save(options);
		       }catch (IOException e) {
		          e.printStackTrace();
		       }
		}
		
	}

	public void saveTrace() {
		try{
	        /*
	        * Save the resource
	        */
			middleModel.save(null);
	       }catch (IOException e) {
	          e.printStackTrace();
	       }
	}

	public void dispose() {
		// TODO Auto-generated method stub
		modelMap = null;
		factoryMap = null;
		middleFactory = null;
		middleModel = null;
		
	}
	

}
