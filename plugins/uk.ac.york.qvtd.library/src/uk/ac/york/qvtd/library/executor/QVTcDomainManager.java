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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.elements.DomainType;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.pivot.Type;
import org.eclipse.qvtd.pivot.qvtbase.TypedModel;

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
	private Map<TypedModel, Resource> modelResourceMap = new HashMap<TypedModel, Resource>();
	
	private Map<TypedModel, EList<EObject>> modelElementsMap = new HashMap<TypedModel, EList<EObject>>();
	
	/**
	 * Instantiates a new QVTc Domain Manager. Responsible for creating new
	 * instances of the middle model and the middle model EFactory.
	 *
	 */
	public QVTcDomainManager() {
	    
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
		
	    modelResourceMap.put(typedModel, model);
	}
	
	
	/**
	 * Gets the model (resource) for a given TypedModel.
	 *
	 * @param typedModel the typed model
	 * @return the resource
	 */
	public Resource getTypeModelResource(@NonNull TypedModel typedModel) {
		return modelResourceMap.get(typedModel);
	}
	
	
	/**
	 * Gets the resources for all the models.
	 *
	 * @return a collection of all the resources
	 */
	public Collection<Resource> getAllModelResources() {
		return modelResourceMap.values();
	}

	
	/**
	 * Gets the middle model.
	 *
	 * @return the middle model
	 */
	public Resource getMiddleModel() {
		return modelResourceMap.get(MIDDLE_MODEL);
	}
	
	
	/**
     * Adds the model element to the resource of the given TypeModel
     *
     * @param tm the TypeModel
     * @param element the element
     */
    public void addModelElement(@Nullable TypedModel model, @NonNull Object element) {
        
        EList<EObject> elements = null;
        if (modelElementsMap.containsKey(model)) {
            elements = modelElementsMap.get(model);
        } else {
            elements = new BasicEList<EObject>();
            if (model != MIDDLE_MODEL) {
                elements.addAll(modelResourceMap.get(model).getContents());
            }
        }
        elements.add((EObject) element);
        modelElementsMap.put(model, elements);
    }
    
    
    /**
     * Gets the all the instances of the specified Type in the given TypeModel
     *
     * @param tm the TypeModel (can be null if it is the middle model)
     * @param type the type of the elements that are retrieved
     * @return the instances
     */
    public List<Object> getElementsByType(@Nullable TypedModel model, @NonNull Type type) {
        
        List<Object> elements = new ArrayList<Object>();
        // Is the TypedModel the middle or output, hence we have elements in the elementsMap
        if (modelElementsMap.containsKey(model)) {
            for (EObject root :  modelElementsMap.get(model)) {
                if (root.eClass().getName().equals(type.getName())) {
                    elements.add(root);
                }
                for (TreeIterator<EObject> contents = root.eAllContents(); contents.hasNext();) {
                    EObject element = contents.next();
                    if (((EClass) type.getETarget()).getName().equals(element.eClass().getName())) {
                        elements.add(element);
                    }
                }
            }
        }
        else {
            for (TreeIterator<EObject> contents = modelResourceMap.get(model).getAllContents(); contents.hasNext();) {
                EObject element = contents.next();
                //System.out.println(type.getETarget());
                //System.out.println(((EClassifier) type.getETarget()).getName());
                //System.out.println(object.eClass().getName());
                if (((EClass) type.getETarget()).getName().equals(element.eClass().getName())) {
                    elements.add(element);
                }
            }
        }
        return elements;
    }


	/**
	 * Saves all the models managed by the domain manager.
	 */
    public void saveModels() {
        for (Map.Entry<TypedModel, Resource> entry : modelResourceMap.entrySet()) {
            Resource model = entry.getValue();
            TypedModel key = entry.getKey();
            if (key != MIDDLE_MODEL) {      // Don't save the middle model
                if (modelElementsMap.containsKey(key)) {       // Only save modified models
                    // Move elements without container to the resource contents
                    for (EObject e : modelElementsMap.get(key)) {
                        if (e.eContainer() == null) {
                            model.getContents().add(e);
                        }
                    }
                    try{
                        Map<Object, Object> options = new HashMap<Object, Object>();
                        options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
                        model.save(options);
                       }catch (IOException e) {
                          e.printStackTrace();
                       }
                }
            }    
        }
    }
    
    public void saveTrace(String name, ResourceSet resourceSet, String uriPath) {
        
        uriPath += "trace" + name + ".xmi";
        Resource r = resourceSet.createResource(URI.createURI(uriPath));
        for (EObject e : modelElementsMap.get(MIDDLE_MODEL)) {
            if (e.eContainer() == null) {
                r.getContents().add(e);
            }
        }
        try{
            Map<Object, Object> options = new HashMap<Object, Object>();
            options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
            r.save(options);
           } catch (IOException e) {
              e.printStackTrace();
           }
    }
    
    
	/**
	 * Dispose.
	 */
	public void dispose() {
		// TODO Auto-generated method stub
		modelResourceMap = null;
		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ocl.examples.domain.evaluation.DomainModelManager#get(org.eclipse.ocl.examples.domain.elements.DomainType)
	 */
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
