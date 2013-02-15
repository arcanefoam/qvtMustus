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
package uk.ac.york.qvtd.library.executor;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.elements.DomainType;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.pivot.Type;
import org.eclipse.qvtd.pivot.qvtbase.TypedModel;


// TODO: Auto-generated Javadoc
/**
 * QVTc Domain Manager is the class responsible for managing the QVTc virtual
 * machine meta-models and models. 
 * A QVTc Domain Manager object encapsulates the domain information need to 
 * modify the domains's models. 
 */
public class QVTcDomainManager implements DomainModelManager {
    
    
    /** The Constant MIDDLE_MODEL. */
    public static final TypedModel MIDDLE_MODEL = null;
    
	// TODO how to manage aliases?
	/** Map a typed model to its resource (model). */
	private Map<TypedModel, Resource> modelMap = new HashMap<>();

	/**
	 * Instantiates a new QVTc Domain Manager. Responsible for creating new
	 * instances of the middle model and the middle model EFactory.
	 *
	 */
	public QVTcDomainManager() {
	    // null entries in the modelMap and pivotMap will be for the middle model
	    modelMap.put(MIDDLE_MODEL, new ResourceImpl());
	}
	
	/**
	 * Adds the model to the list of models managed by this domain manager. The
	 * domain manager supports only one root resource per typed model, this means that
	 * if a model was already binded to the TypedModel it will be replaced.
	 *
	 * @param typedModel the type model associated to the model
	 * @param model the resource
	 */
	// TODO support multiple model instances by alias
	public void addModel(@NonNull TypedModel typedModel, @NonNull Resource model) {
		
	    modelMap.put(typedModel, model);
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
	
	
	/**
	 * Gets the resources for all the models.
	 *
	 * @return a collection of all the resources
	 */
	public Collection<Resource> getAllModelResources() {
		return modelMap.values();
	}

	
	/**
	 * Gets the middle model.
	 *
	 * @return the middle model
	 */
	public Resource getMiddleModel() {
		return modelMap.get(MIDDLE_MODEL);
	}
	
    
	
    /**
     * Adds the model element to the resource of the given TypeModel
     *
     * @param tm the TypeModel
     * @param element the element
     * @return true, if successful
     */
    public boolean addModelElement(@Nullable TypedModel tm, @NonNull Object element) {
        
        Resource r = modelMap.get(tm);
        return r.getContents().add((EObject) element);
    }

    
    /**
     * Gets the all the instances of the specified Type in the given TypeModel
     *
     * @param tm the TypeModel (can be null if it is the middle model)
     * @param type the type of the elements that are retrieved
     * @return the instances
     */
    public Set<Object> getInstances(@Nullable TypedModel tm, @NonNull Type type) {
        
        Set<Object> objectSet = new HashSet<>();
        
        for (TreeIterator<EObject> contents = modelMap.get(tm).getAllContents(); contents.hasNext();) {
            Object object = contents.next();
            //object.getClass().getName().equals(var.getType().getName())
            if (object.getClass().equals(type)) {
                objectSet.add(object);
            }
        }
        return objectSet;
    }


	/**
	 * Saves all the models managed by the domain manager.
	 */
	// TODO only save the output models 
	public void saveModels() {
		for (Map.Entry<TypedModel, Resource> entry : modelMap.entrySet()) {
		    Resource model = entry.getValue();
		    if(entry.getKey() != null) {      // Don't sabe the middle model
		        try {
	                Map<Object, Object> options = new HashMap<Object, Object>();
	                options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
	                model.save(options);
	               } catch (IOException e) {
	                  e.printStackTrace();
	               }
		    }
		}
	}

	/**
	 * Save trace.
	 */
    // TODO How to set the save path? Pass it as a parameter here or in the constructor?
	public void saveTrace() {
		try{
			Map<Object, Object> options = new HashMap<Object, Object>();
	    	options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
	    	Resource model = modelMap.get(MIDDLE_MODEL);
	    	model.save(null);
	       }catch (IOException e) {
	          e.printStackTrace();
	       }
	}

	/**
	 * Dispose.
	 */
	public void dispose() {
		// TODO Auto-generated method stub
		modelMap = null;
		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ocl.examples.domain.evaluation.DomainModelManager#get(org.eclipse.ocl.examples.domain.elements.DomainType)
	 */
	@Override
	public @NonNull Set<EObject> get(@NonNull DomainType type) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Implemented by subclasses to determine whether the specified element
	 * is an instance of the specified class, according to the metamodel
	 * semantics implemented by the environment that created this extent map.
	 *
	 * @param type the type
	 * @param element a potential run-time (M0) instance of that class
	 * @return <code>true</code> if this element is an instance of the given
	 * class; <code>false</code> otherwise
	 */
	protected boolean isInstance(@NonNull DomainType type, @NonNull EObject element) {
		return false;
	}

}
