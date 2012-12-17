package qvti;

import java.io.IOException;

import static org.junit.Assert.*;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jdt.annotation.NonNull;
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
import org.eclipse.qvtd.pivot.qvtcore.CoreModel;
import org.eclipse.qvtd.pivot.qvtcore.evaluation.QVTcoreEvaluationVisitorImpl;
import org.eclipse.qvtd.pivot.qvtcore.evaluation.QVTicoreEVImplTrivial;
import org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor;
import org.eclipse.qvtd.xtext.qvtbase.tests.LoadTestCase;
import org.eclipse.qvtd.xtext.qvtcore.QVTcoreStandaloneSetup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.york.qvtd.library.executor.QVTcDomainManager;
import uk.ac.york.qvtd.library.executor.QvtModelManager;

/**
 * Test001 is a set if simple tests on the QVTc API.
 * @author hhoyos
 *
 */
public class TestQVTi extends LoadTestCase {
	
	private final String inputModelURI = "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/SimpleGraphUpper.xmi";
	private final String inputModelmmURI = "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/SimpleGraph.ecore";
	private final String outputModelURI = "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/SimpleGraphLower.xmi";
	private final String outputModelmmURI = "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/SimpleGraph.ecore";
	private final String middleMetaModelmmURI = "platform:/plugin/uk.ac.york.qvtd.tests.hhr/model/SimpleGraph2Graph.ecore";
	private final String qvtcSource = "platform:/plugin/uk.ac.york.qvtd.tests.hhr/src/qvti/UpperToLowerTrivial.qvti.qvtc";
		
	private static ProjectMap projectMap = null;
	
	@Before
    public void setUp() throws Exception {
		EssentialOCLLinkingService.DEBUG_RETRY = true;
		super.setUp();
		QVTcoreStandaloneSetup.doSetup();
    }
 
    @After
    public void tearDown() throws Exception {
		super.tearDown();
    }
    
    @Test
    public void testTrivialQVTi() {
    	
    }
    
    @Test
    public void testN2NNested() {
    	
    }
    
    @Test
    public void testN2LessNGuarded() {
    	
    }
    
	
	@Test
	public void test() {
		/*
		 * loads the transformation and obtains the PIvot AST
		 * creates a trivial input model with an Hello instance
		 * evaluates the Pivot AST transformation on the Hello input model, and saves the World output model
		 * checks that the generated World output matches the expectation
		 */
		ResourceSet resourceSet = new ResourceSetImpl();
		getProjectMap().initializeResourceSet(resourceSet);
		metaModelManager = new MetaModelManager();
		MetaModelManagerResourceSetAdapter.getAdapter(resourceSet, metaModelManager);
		try {
			// Load the input model from a ResourceSet for the given URI
			Resource inputResource = resourceSet.getResource(URI.createURI(inputModelURI), true);
			Resource inputmm = resourceSet.getResource(URI.createURI(inputModelmmURI), true);
			
			// Create a new Resource for the output model
			// TODO  this assumes that the output model does not exist and therefore 
			// it is the target model  by default. There must be a way to configure
			// this
			// NOTE Why does createResource does not owrk and returns an empty resource with no "link" to the metamodel? 
			Resource outputResource = resourceSet.createResource(URI.createURI(outputModelURI));
			Resource outputmm = resourceSet.getResource(URI.createURI(outputModelmmURI), true);
			
			// Create a resource for the middle model: load the metamodel as a resource
			// so we can use it to create EObjects that reflect the EClasses in the mm
			//resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put( "ecore", new EcoreResourceFactoryImpl());
			Resource middlemm = resourceSet.getResource(URI.createURI(middleMetaModelmmURI), true);
			
			
			// Load the qvtc file
			BaseCSResource xtextResource = null;
			PivotResource qvtResource = null;
			try {
				//xtextResource = createXtextFromURI(metaModelManager, URI.createURI(qvtcSource));
				xtextResource = (BaseCSResource) resourceSet.getResource(URI.createURI(qvtcSource), true);
				if(xtextResource != null) {
					qvtResource = createPivotFromXtext(metaModelManager, xtextResource);
				}else {
					fail("There was an error loading the QVTc file");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail("There was an error loading the QVTc file");
			}
			if(qvtResource != null) {
				CoreModel coreModel = (CoreModel) qvtResource.getContents().get(0);
				// This is to pass the correct arguments to the constructor, but I don't
				// really understand their use. 
				// TODO check this with Ed
				PivotEnvironmentFactory envFactory = new PivotEnvironmentFactory(null, metaModelManager);
				PivotEnvironment env = envFactory.createEnvironment();
				PivotEvaluationEnvironment evalEnv = new PivotEvaluationEnvironment(metaModelManager);
				
				
				QVTcDomainManager modelManager = new QVTcDomainManager(middlemm);
				/* MODELS ARE NOW ADDED AS TypeModels, so we need to get them from the ast */
				if(inputResource != null && inputmm != null) {
					modelManager.addModel(coreModel, "upperGraph", inputResource, inputmm);
				}else {
					fail("There was an error loading the input model");
				}
				if(outputResource != null && outputmm != null) {
					modelManager.addModel(coreModel, "lowerGraph", outputResource, outputmm);
				}else {
					fail("There was an error loading the output model");
				}
				
				QVTcoreVisitor<Object> visitor = new QVTcoreEvaluationVisitorImpl(env, evalEnv, modelManager);
				Object sucess = coreModel.accept(visitor);
				assertNotNull("QVTcoreEVNodeTypeImpl should not return null.", sucess);
				System.out.println("Result of the transformation was " + (Boolean)sucess);
				modelManager.saveModels();
				//modelManager.saveTrace();
				modelManager.dispose();
			}
		} finally {
			
		}
		
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
			//assertNoResourceErrors("To Pivot errors", xtextResource);
			//assertNoUnresolvedProxies("Unresolved proxies", xtextResource);
			//List<EObject> pivotContents = pivotResource.getContents();
			//assertEquals(expectedContentCount, pivotContents.size());
			//assertNoValidationErrors("Pivot validation errors", pivotContents.get(0));
			return pivotResource;
		}
		finally {
			if (adapter != null) {
				adapter.dispose();
			}
		}
	}


}
