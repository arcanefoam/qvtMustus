package test001;

import static org.junit.Assert.*;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.ocl.examples.domain.utilities.ProjectMap;
import org.eclipse.ocl.examples.pivot.ecore.Ecore2Pivot;
import org.eclipse.ocl.examples.pivot.Root;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManagerResourceSetAdapter;
import org.eclipse.qvtd.pivot.qvtbase.BaseModel;
import org.eclipse.qvtd.pivot.qvtbase.util.QVTbaseVisitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.Test;

public class Test001 {
	
	private final String inputModelURI = "platform:/plugin/uk.ac.york.qvtd.tests.hhr/src/test001/Graph001.xmi";
	private final String outputModelURI = "platform:/plugin/uk.ac.york.qvtd.tests.hhr/src/test001/Graph001Lower.xmi";
	private final String qvtcSource = "platform:/plugin/uk.ac.york.qvtd.tests.hhr/src/test001/UpperToLower.qvtc";
	
	private static ProjectMap projectMap = null;
	
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
			
			// Load the qvtc file
			Resource xtextResource = resourceSet.getResource(URI.createURI(qvtcSource), true);
			for (TreeIterator<EObject> tit = xtextResource.getAllContents(); tit.hasNext(); ) {
				EObject eObject = tit.next();
				if (eObject instanceof BaseModel) {
					BaseModel baseModel = (BaseModel) eObject;
					//QVTbaseVisitor visitor = new QVTbaseVisitor(); ??
					baseModel.accept(visitor);
				}
			}
			
			
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

}
