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
package junit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ocl.examples.domain.utilities.DomainUtil;
import org.eclipse.ocl.examples.domain.utilities.ProjectMap;
import org.eclipse.ocl.examples.pivot.evaluation.PivotEvaluationEnvironment;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManagerResourceSetAdapter;
import org.eclipse.ocl.examples.pivot.utilities.PivotEnvironment;
import org.eclipse.ocl.examples.pivot.utilities.PivotEnvironmentFactory;
import org.eclipse.ocl.examples.pivot.utilities.PivotResource;
import org.eclipse.ocl.examples.xtext.base.utilities.BaseCSResource;
import org.eclipse.ocl.examples.xtext.base.utilities.CS2PivotResourceAdapter;
import org.eclipse.ocl.examples.xtext.essentialocl.services.EssentialOCLLinkingService;
import org.eclipse.qvtd.pivot.qvtbase.Transformation;
import org.eclipse.qvtd.pivot.qvtbase.TypedModel;
import org.eclipse.qvtd.pivot.qvtimperative.ImperativeModel;
import org.eclipse.qvtd.pivot.qvtimperative.util.QVTimperativeVisitor;
import org.eclipse.qvtd.xtext.qvtbase.tests.LoadTestCase;
import org.eclipse.qvtd.xtext.qvtimperative.QVTimperativeStandaloneSetup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.york.qvtd.library.executor.QVTcDomainManager;
import uk.ac.york.qvtd.pivot.qvtimperative.evaluation.QVTimperativeEvaluationVisitor;

/**
 * Test001 is a set if simple tests on the QVTc API.
 * @author hhoyos
 *
 */
public class TestQVTi extends LoadTestCase {
	
	private static ProjectMap projectMap = null;
	private static ResourceSet resourceSet = new ResourceSetImpl();
	private Map<String, Resource> typeModelResourceMap = new HashMap<String, Resource>();
	private Map<String, Resource> typeModelValidationResourceMap = new HashMap<String, Resource>();
	
	@Before
    public void setUp() throws Exception {
	    
		EssentialOCLLinkingService.DEBUG_RETRY = true;
		super.setUp();
		QVTimperativeStandaloneSetup.doSetup();
		
        getProjectMap().initializeResourceSet(resourceSet);
        metaModelManager = new MetaModelManager();
        MetaModelManagerResourceSetAdapter.getAdapter(resourceSet, metaModelManager);
    }
 
    @After
    public void tearDown() throws Exception {
		super.tearDown();
    }
    
    /*
     * Minimal 1 object to 1 object QVTi transformation
     */
    @Test
    public void testMinimalQVTi() {
        
        final String transformationURI = "platform:/plugin/uk.ac.york.qvtd.tests.hhr/src/qvti/Graph2GraphMinimal.qvti";
        // Load the TypeModel resources
        typeModelResourceMap.clear();
        // This is maps reflects how in the future the user input can be passed to the engine
        Map<String, String> typeModelFileMap = new HashMap<String, String>();
        typeModelFileMap.put("upperGraph", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/SimpleGraph.xmi");
        typeModelFileMap.put("lowerGraph", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model-gen/Graph2GraphMinimal.xmi");
        Map<String, Boolean> typeModelModeMap = new HashMap<String, Boolean>();     // Load or create, true = create
        typeModelModeMap.put("upperGraph", Boolean.FALSE);
        typeModelModeMap.put("lowerGraph", Boolean.TRUE);
        // Validation TypeModel resources 
        typeModelValidationResourceMap.clear();
        Map<String,String> typeModelValidationFileMap = new HashMap<String,String>();
        typeModelValidationFileMap.put("upperGraph", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/SimpleGraphValidate.xmi");
        typeModelValidationFileMap.put("lowerGraph", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/Graph2GraphMinimalValidate.xmi");
        loadResources(typeModelFileMap, typeModelModeMap, typeModelValidationFileMap);
        doTest(typeModelResourceMap, transformationURI, typeModelValidationResourceMap);
    }

    /*
     * Hierarchical N object to N object QVTi transformation working.
     */
    @Test
    public void testHierarchicalN2N() {
        final String transformationURI = "platform:/plugin/uk.ac.york.qvtd.tests.hhr/src/qvti/Graph2GraphHierarchical.qvti";
        // Load the TypeModel resources
        typeModelResourceMap.clear();
        // This is map reflects how in the future the user input can be passed to the engine
        Map<String,String> typeModelFileMap = new HashMap<String,String>();
        typeModelFileMap.put("upperGraph", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/SimpleGraph.xmi");
        typeModelFileMap.put("lowerGraph", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model-gen/Graph2GraphHierarchical.xmi");
        Map<String, Boolean> typeModelModeMap = new HashMap<String, Boolean>();     // Load or create, true = create
        typeModelModeMap.put("upperGraph", Boolean.FALSE);
        typeModelModeMap.put("lowerGraph", Boolean.TRUE);
        // validation TypeModel resources 
        typeModelValidationResourceMap.clear();
        Map<String,String> typeModelValidationFileMap = new HashMap<String,String>();
        typeModelValidationFileMap.put("upperGraph", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/SimpleGraphValidate.xmi");
        typeModelValidationFileMap.put("lowerGraph", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/Graph2GraphHierarchicalValidate.xmi");
        loadResources(typeModelFileMap, typeModelModeMap, typeModelValidationFileMap);
        doTest(typeModelResourceMap, transformationURI, typeModelValidationResourceMap);
    }
    
    @Test
    public void testRecursiveN2N() {
        final String transformationURI = "platform:/plugin/uk.ac.york.qvtd.tests.hhr/src/qvti/HSVtoHLS.qvti";
        // Load the TypeModel resources
        typeModelResourceMap.clear();
        // This is map reflects how in the future the user input can be passed to the engine
        Map<String,String> typeModelFileMap = new HashMap<String,String>();
        typeModelFileMap.put("hsv", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/HSVNode.xmi");
        typeModelFileMap.put("hls", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model-gen/HLSNode.xmi");
        Map<String, Boolean> typeModelModeMap = new HashMap<String, Boolean>();     // Load or create, true = create
        typeModelModeMap.put("hsv", Boolean.FALSE);
        typeModelModeMap.put("hls", Boolean.TRUE);
        // validation TypeModel resources
        typeModelValidationResourceMap.clear();
        Map<String,String> typeModelValidationFileMap = new HashMap<String,String>();
        typeModelValidationFileMap.put("hsv", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/HSVNodeValidate.xmi");
        typeModelValidationFileMap.put("hls", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/HLSNodeValidate.xmi");
        loadResources(typeModelFileMap, typeModelModeMap, typeModelValidationFileMap);
        doTest(typeModelResourceMap, transformationURI, typeModelValidationResourceMap);
        
    }
    
    
    @Test
    public void testN2LessNGuarded() {
        
    }
    
    /**
     * Do test.
     *
     * @param typeModelResourceMap the TypeModel Resource map
     * @param transformationURI the transformation uri
     * @param typeModelValidationResourceMap the TypeModel validation Resource map
     */
    private void doTest(Map<String, Resource> typeModelResourceMap,
            String transformationURI,
            Map<String, Resource> typeModelValidationResourceMap) {
        
        ResourceSet resourceSet = new ResourceSetImpl();
        getProjectMap().initializeResourceSet(resourceSet);
        metaModelManager = new MetaModelManager();
        MetaModelManagerResourceSetAdapter.getAdapter(resourceSet, metaModelManager);
        
        // Load the transformation resource
        BaseCSResource xtextResource = null;
        PivotResource qvtResource = null;
        try {
            xtextResource = (BaseCSResource) resourceSet.getResource(URI.createURI(transformationURI), true);
            if (xtextResource != null) {
                qvtResource = createPivotFromXtext(metaModelManager, xtextResource);
            } else {
                fail("There was an error loading the QVTc file");
            }
        } catch (IOException e) {
            e.printStackTrace();
            fail("There was an error loading the QVTc file");
        }
        if (qvtResource != null) {
            ImperativeModel imperativeModel = (ImperativeModel) qvtResource.getContents().get(0);
            PivotEnvironmentFactory envFactory = new PivotEnvironmentFactory(null, metaModelManager);
            PivotEnvironment env = envFactory.createEnvironment();
            PivotEvaluationEnvironment evalEnv = new PivotEvaluationEnvironment(metaModelManager);
            
            QVTcDomainManager modelManager = new QVTcDomainManager();
            Transformation transformation = ((Transformation)imperativeModel.getNestedPackage().get(0));
            TypedModel typedModel;
            /* MODELS ARE NOW ADDED AS TypeModels, so we need to get them from the ast */
            Iterator<Entry<String, Resource>> it = typeModelResourceMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Resource> pairs = (Map.Entry<String, Resource>)it.next();
                typedModel = DomainUtil.getNamedElement(transformation.getModelParameter(), pairs.getKey());
                modelManager.addModel(typedModel, pairs.getValue());
            }
            QVTimperativeVisitor<Object> visitor = new QVTimperativeEvaluationVisitor(env, evalEnv, modelManager);
            Object sucess = imperativeModel.accept(visitor);
            assertNotNull("QVTcoreEVNodeTypeImpl should not return null.", sucess);
            modelManager.saveModels();
            modelManager.saveTrace(imperativeModel.getNestedPackage().get(0).getName(), resourceSet, "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model-gen/");
            System.out.println("Result of the transformation was " + (Boolean)sucess);
            
            // Validate against reference models
            it = typeModelResourceMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Resource> pairs = (Map.Entry<String, Resource>)it.next();
                //Comparison comparison = compare(typeModelValidationResourceMap.get(pairs.getKey()), pairs.getValue());
                //List<Diff> differences = comparison.getDifferences();
                //assertEquals("Generated model for TypedModel " + pairs.getKey() + " is different than expected Model.", 0, differences.size());
                try {
                    org.eclipse.ocl.examples.xtext.tests.XtextTestCase.assertSameModel(typeModelValidationResourceMap.get(pairs.getKey()), pairs.getValue());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            modelManager.dispose();
        }
    }
    
    /* ================== NON - TEST ========================= */
    
    private void loadResources(Map<String, String> typeModelFileMap,
            Map<String, Boolean> typeModelModeMap, Map<String, String> typeModelValidationFileMap) {
        
        Iterator<Entry<String, String>> it = typeModelFileMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pairs = (Map.Entry<String, String>)it.next();
            // TODO Check if the output resource needs the second parameter in false
            Resource resource = null;
            if(typeModelModeMap.get(pairs.getKey())) {
                resource = resourceSet.createResource(URI.createURI(pairs.getValue()));
            } else {
                resource = resourceSet.getResource(URI.createURI(pairs.getValue()), true);
            }
            if (resource == null) {
                fail("Unable to load the resource for " + pairs.getKey() + " TypeModel.");
            }
            typeModelResourceMap.put(pairs.getKey(), resource);
        }
        // Validation Models
        Iterator<Entry<String, String>> itV = typeModelValidationFileMap.entrySet().iterator();
        while (itV.hasNext()) {
            Map.Entry<String, String> pairs = (Map.Entry<String, String>)itV.next();
            Resource resource = resourceSet.getResource(URI.createURI(pairs.getValue()), true);
            typeModelValidationResourceMap.put(pairs.getKey(), resource);
        }
        
    }
    
    private static ProjectMap getProjectMap() {
		if (projectMap == null) {
			projectMap = new ProjectMap();
		}
		return projectMap;
	}
	
	
	private BaseCSResource createXtextFromURI(MetaModelManager metaModelManager, URI xtextURI) throws IOException {
		ResourceSet resourceSet2 = metaModelManager.getExternalResourceSet();
		ProjectMap.initializeURIResourceMap(resourceSet2);
		ProjectMap.initializeURIResourceMap(null);
		BaseCSResource xtextResource = (BaseCSResource) resourceSet2.getResource(xtextURI, true);
		//assertNoResourceErrors("Load failed", xtextResource);
		return xtextResource;
	}
	
	
	private PivotResource createPivotFromXtext(MetaModelManager metaModelManager, @NonNull BaseCSResource xtextResource) throws IOException {
		CS2PivotResourceAdapter adapter = null;
		try {
			adapter = CS2PivotResourceAdapter.getAdapter(xtextResource, null);
			PivotResource pivotResource = (PivotResource)adapter.getPivotResource(xtextResource);
			return pivotResource;
		}
		finally {
			if (adapter != null) {
				adapter.dispose();
			}
		}
	}
	
	/*private Comparison compare(Resource modelA, Resource modelB) {
	    // Load the two input models
	    ResourceSet resourceSet1 = new ResourceSetImpl();
	    ResourceSet resourceSet2 = new ResourceSetImpl();
	    resourceSet1.getResources().add(modelA);
	    resourceSet2.getResources().add(modelB);
	    
	    //String xmi1 = "path/to/first/model.xmi";
	    //String xmi2 = "path/to/second/model.xmi";
	    //load(xmi1, resourceSet1);
	    //load(xmi2, resourceSet2);
	 
	    // Configure EMF Compare
	    IEObjectMatcher matcher = DefaultMatchEngine.createDefaultEObjectMatcher(UseIdentifiers.NEVER);
	    IComparisonFactory comparisonFactory = new DefaultComparisonFactory(new DefaultEqualityHelperFactory());
	    IMatchEngine matchEngine = new DefaultMatchEngine(matcher, comparisonFactory);
	    EMFCompare comparator = EMFCompare.builder().setMatchEngine(matchEngine).build();
	 
	    // Compare the two models
	    IComparisonScope scope = EMFCompare. createDefaultScope(resourceSet1, resourceSet2);
	    return comparator.compare(scope);
	}*/


}
