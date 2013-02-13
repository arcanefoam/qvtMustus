/*
 * 
 */
package qvti;

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
import org.eclipse.qvtd.pivot.qvtcore.CoreModel;
import org.eclipse.qvtd.pivot.qvtcore.evaluation.QVTcoreEvaluationVisitorImpl;
import org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor;
import org.eclipse.qvtd.xtext.qvtbase.tests.LoadTestCase;
import org.eclipse.qvtd.xtext.qvtcore.QVTcoreStandaloneSetup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.york.qvtd.library.executor.QVTcDomainManager;

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
		QVTcoreStandaloneSetup.doSetup();
		
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
        
        final String transformationURI = "platform:/plugin/uk.ac.york.qvtd.tests.hhr/src/qvti/Graph2GraphMinimal.qvti.qvtc";
        // Load the TypeModel resources
        typeModelResourceMap.clear();
        // This is map reflects how in the future the user input can be passed to the engine
        Map<String,String> typeModelFileMap = new HashMap<String,String>();
        typeModelFileMap.put("upperGraph", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/SimpleGraph.xmi");
        typeModelFileMap.put("lowerGraph", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model-gen/Graph2GraphMinimal.xmi");
        Iterator<Entry<String, String>> it = typeModelFileMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pairs = (Map.Entry<String, String>)it.next();
            // TODO Check if the output resource needs the second parameter in false
            Resource tmResource = resourceSet.getResource(URI.createURI(pairs.getValue()), true);
            if (tmResource == null) {
                fail("Unable to load the resource for " + pairs.getKey() + " TypeModel.");
            }
            typeModelResourceMap.put(pairs.getKey(), tmResource);
        }
        // Load the validation TypeModel resources 
        typeModelValidationResourceMap.clear();
        Map<String,String> typeModelValidationFileMap = new HashMap<String,String>();
        typeModelValidationFileMap.put("upperGraph", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/SimpleGraphValidate.xmi");
        typeModelValidationFileMap.put("lowerGraph", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/Graph2GraphMinimalValidate.xmi");
        doTest(typeModelResourceMap, transformationURI, typeModelValidationResourceMap);
    }
    
    /*
     * Hierarchical N object to N object QVTi transformation working.
     */
    @Test
    public void testHierarchicalN2N() {
        final String transformationURI = "platform:/plugin/uk.ac.york.qvtd.tests.hhr/src/qvti/Graph2GraphMinimal.qvti.qvtc";
        // Load the TypeModel resources
        typeModelResourceMap.clear();
        // This is map reflects how in the future the user input can be passed to the engine
        Map<String,String> typeModelFileMap = new HashMap<String,String>();
        typeModelFileMap.put("upperGraph", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/SimpleGraph.xmi");
        typeModelFileMap.put("lowerGraph", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model-gen/Graph2GraphHierarchical.xmi");
        Iterator<Entry<String, String>> it = typeModelFileMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pairs = (Map.Entry<String, String>)it.next();
            // TODO Check if the output resource needs the second parameter in false
            Resource tmResource = resourceSet.getResource(URI.createURI(pairs.getValue()), true);
            if (tmResource == null) {
                fail("Unable to load the resource for " + pairs.getKey() + " TypeModel.");
            }
            typeModelResourceMap.put(pairs.getKey(), tmResource);
        }
        // Load the validation TypeModel resources 
        typeModelValidationResourceMap.clear();
        Map<String,String> typeModelValidationFileMap = new HashMap<String,String>();
        typeModelValidationFileMap.put("upperGraph", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/SimpleGraphValidate.xmi");
        typeModelValidationFileMap.put("lowerGraph", "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/Graph2GraphHierarchicalValidate.xmi");
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
            CoreModel coreModel = (CoreModel) qvtResource.getContents().get(0);
            PivotEnvironmentFactory envFactory = new PivotEnvironmentFactory(null, metaModelManager);
            PivotEnvironment env = envFactory.createEnvironment();
            PivotEvaluationEnvironment evalEnv = new PivotEvaluationEnvironment(metaModelManager);
            
            QVTcDomainManager modelManager = new QVTcDomainManager();
            Transformation transformation = ((Transformation)coreModel.getNestedPackage().get(0));
            TypedModel typedModel;
            /* MODELS ARE NOW ADDED AS TypeModels, so we need to get them from the ast */
            Iterator<Entry<String, Resource>> it = typeModelResourceMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Resource> pairs = (Map.Entry<String, Resource>)it.next();
                typedModel = DomainUtil.getNamedElement(transformation.getModelParameter(), pairs.getKey());
                modelManager.addModel(coreModel, typedModel, pairs.getValue());
            }
            //typedModel = DomainUtil.getNamedElement(transformation.getModelParameter(), "lowerGraph");
            //modelManager.addModel(coreModel, typedModel, outputResource);
            QVTcoreVisitor<Object> visitor = new QVTcoreEvaluationVisitorImpl(env, evalEnv, modelManager);
            Object sucess = coreModel.accept(visitor);
            assertNotNull("QVTcoreEVNodeTypeImpl should not return null.", sucess);
            modelManager.saveModels();
            //modelManager.saveTrace();
            System.out.println("Result of the transformation was " + (Boolean)sucess);
            
            // Validate against reference models
            it = typeModelResourceMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Resource> pairs = (Map.Entry<String, Resource>)it.next();
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
    
    
    
	public static ProjectMap getProjectMap() {
		if (projectMap == null) {
			projectMap = new ProjectMap();
		}
		return projectMap;
	}
	
	
	public BaseCSResource createXtextFromURI(MetaModelManager metaModelManager, URI xtextURI) throws IOException {
		ResourceSet resourceSet2 = metaModelManager.getExternalResourceSet();
		ProjectMap.initializeURIResourceMap(resourceSet2);
		ProjectMap.initializeURIResourceMap(null);
		BaseCSResource xtextResource = (BaseCSResource) resourceSet2.getResource(xtextURI, true);
		//assertNoResourceErrors("Load failed", xtextResource);
		return xtextResource;
	}
	
	
	public PivotResource createPivotFromXtext(MetaModelManager metaModelManager, @NonNull BaseCSResource xtextResource) throws IOException {
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


}
