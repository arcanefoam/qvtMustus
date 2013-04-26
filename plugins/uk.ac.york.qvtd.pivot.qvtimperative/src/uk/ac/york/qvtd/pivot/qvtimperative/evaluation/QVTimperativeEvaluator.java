package uk.ac.york.qvtd.pivot.qvtimperative.evaluation;

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
import org.eclipse.ocl.examples.pivot.evaluation.TracingEvaluationVisitor;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManagerResourceSetAdapter;
import org.eclipse.ocl.examples.pivot.utilities.PivotEnvironment;
import org.eclipse.ocl.examples.pivot.utilities.PivotEnvironmentFactory;
import org.eclipse.ocl.examples.pivot.utilities.PivotResource;
import org.eclipse.ocl.examples.xtext.base.utilities.BaseCSResource;
import org.eclipse.ocl.examples.xtext.base.utilities.CS2PivotResourceAdapter;
import org.eclipse.qvtd.pivot.qvtbase.Transformation;
import org.eclipse.qvtd.pivot.qvtbase.TypedModel;
import org.eclipse.qvtd.pivot.qvtimperative.ImperativeModel;
import org.eclipse.qvtd.pivot.qvtimperative.util.QVTimperativeVisitor;

import uk.ac.york.qvtd.library.executor.QVTcDomainManager;

public class QVTimperativeEvaluator {
	
	private Map<String, Resource> typeModelResourceMap;
	private Map<String, String> typeModelURIMap;
	private String transformationURI;
	private MetaModelManager metaModelManager;
	private Map<String, Boolean> typeModelModeMap;
	private PivotResource qvtiResource = null;
	private QVTcDomainManager modelManager;
	
    private static ProjectMap projectMap = null;
	private static ResourceSet resourceSet;
    
    public static boolean INPUT_MODE = false;
    public static boolean OUTPUT_MODE = true;
    
    private boolean traceEvaluation;
    
    public QVTimperativeEvaluator(MetaModelManager metaModelManager) {
    	
    	this.metaModelManager = metaModelManager;
    	modelManager = new QVTcDomainManager(metaModelManager);
    	typeModelResourceMap = new HashMap<String, Resource>();
    	typeModelURIMap = new HashMap<String, String>();
    	typeModelModeMap = new HashMap<String, Boolean>();
    	transformationURI = "";
    	projectMap = new ProjectMap();
    	resourceSet = new ResourceSetImpl();
        getProjectMap().initializeResourceSet(resourceSet);
        MetaModelManagerResourceSetAdapter.getAdapter(resourceSet, metaModelManager);
    }
    
    public QVTimperativeEvaluator(MetaModelManager metaModelManager, String transformationURI) {
    	
    	this(metaModelManager);
    	this.transformationURI = transformationURI;
    }

	public String getTransformationURI() {
		return transformationURI;
	}

	public void setTransformationURI(String transformationURI) {
		this.transformationURI = transformationURI;
	}

	
	/**
	 * Adds the model.
	 *
	 * @param name the name
	 * @param uri the uri
	 * @param type the type boolean to indicate is the model is input (false) output (true)
	 */
	public void addModel(String name, String uri, boolean type) {
		 typeModelURIMap.put(name, uri);
		 typeModelModeMap.put(name, type);
	}
	
	public boolean loadTransformation() throws IOException {
		
		// Load the transformation resource
        BaseCSResource xtextResource = null;
        try {
            xtextResource = (BaseCSResource) resourceSet.getResource(URI.createURI(transformationURI), true);
            if (xtextResource != null) {
                qvtiResource = createPivotFromXtext(metaModelManager, xtextResource);
            } else {
            	// TODO Can I get the parsing errors?
                return false;
            }
        } catch (IOException e) {
            // e.printStackTrace();
            throw new IOException("There was an error loading the QVTc file. " + e.getMessage());
        }
        return true;
	}
	
	public void loadModels() throws IOException {
        
        Iterator<Entry<String, String>> it = typeModelURIMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pairs = (Map.Entry<String, String>)it.next();
            Resource resource = null;
            if(typeModelModeMap.get(pairs.getKey())) {
                resource = resourceSet.createResource(URI.createURI(pairs.getValue()));
            } else {
                resource = resourceSet.getResource(URI.createURI(pairs.getValue()), true);
            }
            if (resource == null) {
                throw new IOException("Unable to load the resource for " + pairs.getKey() + " TypeModel.");
            }
            typeModelResourceMap.put(pairs.getKey(), resource);
        }
    }
	
	public boolean execute() {
		
		ImperativeModel imperativeModel = getImperativeModel();
        PivotEnvironmentFactory envFactory = new PivotEnvironmentFactory(null, metaModelManager);
        PivotEnvironment env = envFactory.createEnvironment();
        PivotEvaluationEnvironment evalEnv = new PivotEvaluationEnvironment(metaModelManager);
        Transformation transformation = (Transformation)imperativeModel.getNestedPackage().get(0);
        TypedModel typedModel;
        /* MODELS ARE NOW ADDED AS TypeModels, so we need to get them from the ast */
        Iterator<Entry<String, Resource>> it = typeModelResourceMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Resource> pairs = (Map.Entry<String, Resource>)it.next();
            typedModel = DomainUtil.getNamedElement(transformation.getModelParameter(), pairs.getKey());
            modelManager.addModel(typedModel, pairs.getValue());
        }
        QVTimperativeEvaluationVisitor<Object> visitor = new QVTimperativeEvaluationVisitorImpl(env, evalEnv, modelManager);
        if (isEvaluationTracingEnabled()) {
            // decorate the evaluation visitor with tracing support
        	visitor = new QVTimperativeTracingEvaluationVisitor(visitor);
        }
        return (Boolean) imperativeModel.accept(visitor);
	}
	
	public ImperativeModel getImperativeModel() {
		return (ImperativeModel) qvtiResource.getContents().get(0);
	}
	
	private static ProjectMap getProjectMap() {
		if (projectMap == null) {
			projectMap = new ProjectMap();
		}
		return projectMap;
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

	public void saveModels() {
	
		modelManager.saveModels();
	}
	
	public void saveModels(String traceURI) {
		
		this.saveModels();
		modelManager.saveTrace(getImperativeModel().getNestedPackage().get(0).getName(), resourceSet, traceURI);
	}

	public void dispose() {
		
		modelManager.dispose();
	}

	public Map<String, Resource> getModels() {
		
		return typeModelResourceMap;
	}
	
	 /**
     * Queries whether tracing of evaluation is enabled.  Tracing
     * logs the progress of evaluation to the console, which may
     * be of use in diagnosing problems.
     * <p>
     * In an Eclipse environment, tracing is also enabled by turning on the
     * <tt>org.eclipse.ocl/debug/evaluation</tt> debug option. 
     * </p>
     * 
     * @return whether evaluation tracing is enabled
     * 
     * @see #setEvaluationTracingEnabled(boolean)
     */
    protected boolean isEvaluationTracingEnabled() {
        return traceEvaluation;
    }
    
    /**
     * Sets whether tracing of evaluation is enabled.  Tracing logs
     * the progress of parsing to the console, which may be of use in diagnosing
     * problems.
     * <p>
     * In an Eclipse environment, tracing is also enabled by turning on the
     * <tt>org.eclipse.ocl/debug/evaluation</tt> debug option. 
     * </p>
     * 
     * param b whether evaluation tracing is enabled
     * 
     * @see #isEvaluationTracingEnabled()
     */
    public void setEvaluationTracingEnabled(boolean b) {
        traceEvaluation = b;
    }

}
