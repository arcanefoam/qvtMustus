package qvti;

import java.io.IOException;

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
	
	private final String inputModelURI = "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/SimpleGraphUpper.xmi";
	private final String inputModelmmURI = "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/SimpleGraph.ecore";
	private final String middleMetaModelmmURI = "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/SimpleGraph2Graph.ecore";
	
	private static ProjectMap projectMap = null;
	private static ResourceSet resourceSet = new ResourceSetImpl();
	private Resource inputResource;
    private Resource inputmm;
    private Resource middlemm;
	
	
	@Before
    public void setUp() throws Exception {
		EssentialOCLLinkingService.DEBUG_RETRY = true;
		super.setUp();
		QVTcoreStandaloneSetup.doSetup();
		
		// Load the models and metamodels shared by all tests
        getProjectMap().initializeResourceSet(resourceSet);
        metaModelManager = new MetaModelManager();
        MetaModelManagerResourceSetAdapter.getAdapter(resourceSet, metaModelManager);
        // Load the input model from a ResourceSet for the given URI
        inputResource = resourceSet.getResource(URI.createURI(inputModelURI), true);
        if (inputResource == null) {
            throw new IllegalArgumentException("Unable to load the input model.");
        }
        inputmm = resourceSet.getResource(URI.createURI(inputModelmmURI), true);
        if (inputmm == null) {
            throw new IllegalArgumentException("Unable to load the input metamodel.");
        }
        // Load the moddle metamodel as a resource
        middlemm = resourceSet.getResource(URI.createURI(middleMetaModelmmURI), true);
        if (middlemm == null) {
            throw new IllegalArgumentException("Unable to load the middle metamodel.");
        }
        
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
        
        final String outputModelURI = "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model-gen/Graph2GraphMinimal.xmi";
        final String outputModelmmURI = "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/SimpleGraph.ecore";
        final String qvtcSource = "platform:/plugin/uk.ac.york.qvtd.tests.hhr/src/qvti/Graph2GraphMinimal.qvti.qvtc";
        ResourceSet resourceSet = new ResourceSetImpl();
        getProjectMap().initializeResourceSet(resourceSet);
        metaModelManager = new MetaModelManager();
        MetaModelManagerResourceSetAdapter.getAdapter(resourceSet, metaModelManager);
        // Create a new Resource for the output model
        // TODO  this assumes that the output model does not exist and therefore 
        // it is the target model  by default. There must be a way to configure
        // this
        Resource outputResource = resourceSet.createResource(URI.createURI(outputModelURI));
        if (outputResource == null) {
           fail("Unable to load/create the output model.");
        }
        // Load the qvtc file
        BaseCSResource xtextResource = null;
        PivotResource qvtResource = null;
        try {
            xtextResource = (BaseCSResource) resourceSet.getResource(URI.createURI(qvtcSource), true);
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
            // This is to pass the correct arguments to the constructor, but I don't
            // really understand their use. 
            // TODO check this with Ed
            PivotEnvironmentFactory envFactory = new PivotEnvironmentFactory(null, metaModelManager);
            PivotEnvironment env = envFactory.createEnvironment();
            PivotEvaluationEnvironment evalEnv = new PivotEvaluationEnvironment(metaModelManager);
            
            QVTcDomainManager modelManager = new QVTcDomainManager();
            Transformation transformation = ((Transformation)coreModel.getNestedPackage().get(0));
            TypedModel typedModel;
            /* MODELS ARE NOW ADDED AS TypeModels, so we need to get them from the ast */
            typedModel = DomainUtil.getNamedElement(transformation.getModelParameter(), "upperGraph");
            modelManager.addModel(coreModel, typedModel, inputResource);
            typedModel = DomainUtil.getNamedElement(transformation.getModelParameter(), "lowerGraph");
            modelManager.addModel(coreModel, typedModel, outputResource);
            QVTcoreVisitor<Object> visitor = new QVTcoreEvaluationVisitorImpl(env, evalEnv, modelManager);
            Object sucess = coreModel.accept(visitor);
            assertNotNull("QVTcoreEVNodeTypeImpl should not return null.", sucess);
            System.out.println("Result of the transformation was " + (Boolean)sucess);
            modelManager.saveModels();
            //modelManager.saveTrace();
            modelManager.dispose();
        }
    }
    
    /*
     * Hierarchical N object to N object QVTi transformation working.
     */
    @Test
    public void testHierarchicalN2N() {
        final String outputModelURI = "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model-gen/Graph2GraphHierarchical.xmi";
        final String outputModelmmURI = "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/SimpleGraph.ecore";
        final String qvtcSource = "platform:/plugin/uk.ac.york.qvtd.tests.hhr/src/qvti/Graph2GraphHierarchical.qvti.qvtc";
        ResourceSet resourceSet = new ResourceSetImpl();
        getProjectMap().initializeResourceSet(resourceSet);
        metaModelManager = new MetaModelManager();
        MetaModelManagerResourceSetAdapter.getAdapter(resourceSet, metaModelManager);
        //try {
            
        // Create a new Resource for the output model
        // TODO  this assumes that the output model does not exist and therefore 
        // it is the target model  by default. There must be a way to configure
        // this
        Resource outputResource = resourceSet.createResource(URI.createURI(outputModelURI));
        if (outputResource == null) {
           fail("Unable to load/create the output model.");
        }
        // Load the qvtc file
        BaseCSResource xtextResource = null;
        PivotResource qvtResource = null;
        try {
            xtextResource = (BaseCSResource) resourceSet.getResource(URI.createURI(qvtcSource), true);
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
            // This is to pass the correct arguments to the constructor, but I don't
            // really understand their use. 
            // TODO check this with Ed
            PivotEnvironmentFactory envFactory = new PivotEnvironmentFactory(null, metaModelManager);
            PivotEnvironment env = envFactory.createEnvironment();
            PivotEvaluationEnvironment evalEnv = new PivotEvaluationEnvironment(metaModelManager);
            
            QVTcDomainManager modelManager = new QVTcDomainManager();
            Transformation transformation = ((Transformation)coreModel.getNestedPackage().get(0));
            TypedModel typedModel;
            /* MODELS ARE NOW ADDED AS TypeModels, so we need to get them from the ast */
            typedModel = DomainUtil.getNamedElement(transformation.getModelParameter(), "upperGraph");
            modelManager.addModel(coreModel, typedModel, inputResource);
            typedModel = DomainUtil.getNamedElement(transformation.getModelParameter(), "lowerGraph");
            modelManager.addModel(coreModel, typedModel, outputResource);
            QVTcoreVisitor<Object> visitor = new QVTcoreEvaluationVisitorImpl(env, evalEnv, modelManager);
            Object sucess = coreModel.accept(visitor);
            assertNotNull("QVTcoreEVNodeTypeImpl should not return null.", sucess);
            System.out.println("Result of the transformation was " + (Boolean)sucess);
            modelManager.saveModels();
            //modelManager.saveTrace();
            modelManager.dispose();
        }
    	
    }
    
    @Test
    public void testN2LessNGuarded() {
    	
    }
    

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
