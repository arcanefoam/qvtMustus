package test001;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.ocl.examples.domain.utilities.ProjectMap;
import org.eclipse.ocl.examples.pivot.ecore.Ecore2Pivot;
import org.eclipse.ocl.examples.pivot.Root;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManagerResourceSetAdapter;
import org.eclipse.ocl.examples.pivot.utilities.PivotResource;
import org.eclipse.ocl.examples.xtext.base.utilities.BaseCSResource;
import org.eclipse.ocl.examples.xtext.base.utilities.CS2PivotResourceAdapter;
import org.eclipse.qvtd.pivot.qvtbase.BaseModel;
import org.eclipse.qvtd.pivot.qvtbase.util.QVTbaseVisitor;
import org.eclipse.qvtd.pivot.qvtcore.QVTcorePivotStandaloneSetup;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test001 is a set if simple tests on the QVTc API.
 * @author hhoyos
 *
 */
public class Test001 {
	
	private final String inputModelURI = "platform:/plugin/uk.ac.york.qvtd.tests.hhr/src/test001/Graph001.xmi";
	private final String outputModelURI = "platform:/plugin/uk.ac.york.qvtd.tests.hhr/src/test001/Graph001Lower.xmi";
	private final String qvtcSource = "platform:/plugin/uk.ac.york.qvtd.tests.hhr/src/test001/UpperToLower.qvtc";
	
	private static ProjectMap projectMap = null;
	
	@Before
    public void setUp() {
		QVTcorePivotStandaloneSetup.doSetup();
    }
 
    @After
    public void tearDown() {
        
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
		MetaModelManager metaModelManager = new MetaModelManager();
		MetaModelManagerResourceSetAdapter.getAdapter(resourceSet, metaModelManager);
		try {
			// Load the input model from a ResourceSet for the given URI
			Resource inputResource = resourceSet.getResource(URI.createURI(inputModelURI), true);
			
			// Create a new Resource to be the middle model
			
			
			// Load the qvtc file
			BaseCSResource xtextResource = createXtextFromURI(metaModelManager, URI.createURI(qvtcSource));
			PivotResource pivotResource = createPivotFromXtext(metaModelManager, xtextResource);
			BaseModel baseModel = (BaseModel) pivotResource.getContents().get(0);
			QVTbaseEvaluationVisitor visitor = new QVTcoreEvaluationVisitorImpl(metaModelManager, pivotResource, inputModel, middleModel, outputModel);
			
			
			
			Value result = baseModel.accept(visitor);
			outputModel.save();
			
			
		} finally {
			metaModelManager.dispose();
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
	
	
	public PivotResource createPivotFromXtext(MetaModelManager metaModelManager, BaseCSResource xtextResource) throws IOException {
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
