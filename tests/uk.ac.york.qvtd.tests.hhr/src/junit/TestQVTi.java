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
import org.eclipse.ocl.examples.domain.utilities.ProjectMap;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManagerResourceSetAdapter;
import org.eclipse.ocl.examples.xtext.essentialocl.services.EssentialOCLLinkingService;
import org.eclipse.qvtd.xtext.qvtbase.tests.LoadTestCase;
import org.eclipse.qvtd.xtext.qvtimperative.QVTimperativeStandaloneSetup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.york.qvtd.pivot.qvtimperative.evaluation.QVTimperativeEvaluator;

/**
 * Test001 is a set if simple tests on the QVTc API.
 * @author hhoyos
 *
 */
public class TestQVTi extends LoadTestCase {
	
	
	private Map<String, Resource> typeModelResourceMap = new HashMap<String, Resource>();
	private Map<String, Resource> typeModelValidationResourceMap = new HashMap<String, Resource>();
	
	@Before
    public void setUp() throws Exception {
	    
		EssentialOCLLinkingService.DEBUG_RETRY = true;
		super.setUp();
		QVTimperativeStandaloneSetup.doSetup();
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
        
    	
        QVTimperativeEvaluator minimalEvaluator = new QVTimperativeEvaluator(metaModelManager,
        		"platform:/plugin/uk.ac.york.qvtd.tests.hhr/src/qvti/Graph2GraphMinimal.qvti");
        minimalEvaluator.addModel("upperGraph", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/SimpleGraph.xmi",
        		QVTimperativeEvaluator.INPUT_MODE);
        minimalEvaluator.addModel("lowerGraph", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model-gen/Graph2GraphMinimal.xmi",
        		QVTimperativeEvaluator.OUTPUT_MODE);
        // Validation TypeModel resources 
        typeModelValidationResourceMap.clear();
        Map<String,String> typeModelValidationFileMap = new HashMap<String,String>();
        typeModelValidationFileMap.put("upperGraph", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/SimpleGraphValidate.xmi");
        typeModelValidationFileMap.put("lowerGraph", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/Graph2GraphMinimalValidate.xmi");
        loadValidationResources(typeModelValidationFileMap);
        doTest(minimalEvaluator, typeModelValidationResourceMap);
    }

    /*
     * Hierarchical N object to N object QVTi transformation working.
     */
    @Test
    public void testHierarchicalN2N() {
    	QVTimperativeEvaluator minimalEvaluator = new QVTimperativeEvaluator(metaModelManager,
    			 "platform:/plugin/uk.ac.york.qvtd.tests.hhr/src/qvti/Graph2GraphHierarchical.qvti");
    	minimalEvaluator.addModel("upperGraph", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/SimpleGraph.xmi",
         		QVTimperativeEvaluator.INPUT_MODE);
        minimalEvaluator.addModel("lowerGraph", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model-gen/Graph2GraphHierarchical.xmi",
         		QVTimperativeEvaluator.OUTPUT_MODE);
    	// validation TypeModel resources 
        typeModelValidationResourceMap.clear();
        Map<String,String> typeModelValidationFileMap = new HashMap<String,String>();
        typeModelValidationFileMap.put("upperGraph", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/SimpleGraphValidate.xmi");
        typeModelValidationFileMap.put("lowerGraph", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/Graph2GraphHierarchicalValidate.xmi");
        loadValidationResources(typeModelValidationFileMap);
        doTest(minimalEvaluator, typeModelValidationResourceMap);
    }
    
    @Test
    public void testRecursiveN2N() {
    	QVTimperativeEvaluator minimalEvaluator = new QVTimperativeEvaluator(metaModelManager,
    			"platform:/plugin/uk.ac.york.qvtd.tests.hhr/src/qvti/HSVtoHLS.qvti");
    	minimalEvaluator.addModel("hsv", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/HSVNode.xmi",
         		QVTimperativeEvaluator.INPUT_MODE);
        minimalEvaluator.addModel("hls", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model-gen/HLSNode.xmi",
         		QVTimperativeEvaluator.OUTPUT_MODE);
        typeModelValidationResourceMap.clear();
        Map<String,String> typeModelValidationFileMap = new HashMap<String,String>();
        typeModelValidationFileMap.put("hsv", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/HSVNodeValidate.xmi");
        typeModelValidationFileMap.put("hls", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/HLSNodeValidate.xmi");
        loadValidationResources(typeModelValidationFileMap);
        doTest(minimalEvaluator, typeModelValidationResourceMap);
    }
    
    
    @Test
    public void testN2LessNGuarded() {
    	QVTimperativeEvaluator minimalEvaluator = new QVTimperativeEvaluator(metaModelManager,
    			"platform:/plugin/uk.ac.york.qvtd.tests.hhr/src/qvti/ClassToRDBMSSchedule.qvti");
    	minimalEvaluator.addModel("uml", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/SimpleUMLPeople.xmi",
         		QVTimperativeEvaluator.INPUT_MODE);
        minimalEvaluator.addModel("rdbms", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model-gen/SimpleRDBMSPeople.xmi",
         		QVTimperativeEvaluator.OUTPUT_MODE);
        typeModelValidationResourceMap.clear();
        Map<String,String> typeModelValidationFileMap = new HashMap<String,String>();
        //typeModelValidationFileMap.put("hsv", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/HSVNodeValidate.xmi");
        //typeModelValidationFileMap.put("hls", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/HLSNodeValidate.xmi");
        loadValidationResources(typeModelValidationFileMap);
        doTest(minimalEvaluator, typeModelValidationResourceMap);
    }
    /**
     * Do test.
     *
     * @param typeModelResourceMap the TypeModel Resource map
     * @param transformationURI the transformation uri
     * @param typeModelValidationResourceMap the TypeModel validation Resource map
     */
    private void doTest(QVTimperativeEvaluator evaluator,
            Map<String, Resource> typeModelValidationResourceMap) {
        
    	boolean result = false;
        try {
        	result = evaluator.loadTransformation();
        } catch (IOException ex) {
        	fail(ex.getMessage());
        }
        if (result) {
        	try {
				evaluator.loadModels();
			} catch (IOException ex1) {
				fail(ex1.getMessage());
			}
            result = evaluator.execute();
            assertTrue("QVTcoreEVNodeTypeImpl should not return null.", result);
            evaluator.saveModels("platform:/plugin/uk.ac.york.qvtd.tests.hhr/model-gen/");
            System.out.println("Result of the transformation was " + (Boolean)result);
            
            // Validate against reference models
            typeModelResourceMap = evaluator.getModels();
            Iterator<Entry<String, Resource>> it = typeModelResourceMap.entrySet().iterator();
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
            evaluator.dispose();
            
        }
    }
    
    /* ================== NON - TEST ========================= */
    
    private void loadValidationResources(Map<String, String> typeModelValidationFileMap) {
    	
    	ResourceSet resourceSet = new ResourceSetImpl();
    	ProjectMap projectMap = new ProjectMap();
    	projectMap.initializeResourceSet(resourceSet);
        metaModelManager = new MetaModelManager();
        MetaModelManagerResourceSetAdapter.getAdapter(resourceSet, metaModelManager);
        Iterator<Entry<String, String>> itV = typeModelValidationFileMap.entrySet().iterator();
        while (itV.hasNext()) {
            Map.Entry<String, String> pairs = (Map.Entry<String, String>)itV.next();
            Resource resource = resourceSet.getResource(URI.createURI(pairs.getValue()), true);
            typeModelValidationResourceMap.put(pairs.getKey(), resource);
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
