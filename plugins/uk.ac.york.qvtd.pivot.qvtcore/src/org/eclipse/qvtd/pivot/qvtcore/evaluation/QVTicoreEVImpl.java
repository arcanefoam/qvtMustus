/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     hhoyos - initial API and implementation
 ******************************************************************************/
package org.eclipse.qvtd.pivot.qvtcore.evaluation;

import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.pivot.Environment;
import org.eclipse.ocl.examples.pivot.Variable;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.qvtd.pivot.qvtbase.Domain;
import org.eclipse.qvtd.pivot.qvtbase.Predicate;
import org.eclipse.qvtd.pivot.qvtbase.Rule;
import org.eclipse.qvtd.pivot.qvtbase.Transformation;
import org.eclipse.qvtd.pivot.qvtbase.TypedModel;
import org.eclipse.qvtd.pivot.qvtbase.evaluation.QVTiBaseEVImpl;
import org.eclipse.qvtd.pivot.qvtbase.evaluation.QvtModelManager;
import org.eclipse.qvtd.pivot.qvtcore.Assignment;
import org.eclipse.qvtd.pivot.qvtcore.BottomPattern;
import org.eclipse.qvtd.pivot.qvtcore.CoreDomain;
import org.eclipse.qvtd.pivot.qvtcore.CoreModel;
import org.eclipse.qvtd.pivot.qvtcore.CorePattern;
import org.eclipse.qvtd.pivot.qvtcore.EnforcementOperation;
import org.eclipse.qvtd.pivot.qvtcore.GuardPattern;
import org.eclipse.qvtd.pivot.qvtcore.Mapping;
import org.eclipse.qvtd.pivot.qvtcore.PropertyAssignment;
import org.eclipse.qvtd.pivot.qvtcore.RealizedVariable;
import org.eclipse.qvtd.pivot.qvtcore.VariableAssignment;
import org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor;



/**
 * The Class QVTicoreEVImpl. This is the Evaluation Visitor for the QVTi language
 */
public class QVTicoreEVImpl extends QVTiBaseEVImpl implements QVTcoreVisitor<Object> {
	
	
	private Resource outputResource;
	
	/**
	 * Instantiates a new qV ticore ev impl.
	 *
	 * @param env the env
	 * @param evalEnv the eval env
	 * @param modelManager the model manager
	 */
	public QVTicoreEVImpl(@NonNull Environment env,
			@NonNull EvaluationEnvironment evalEnv, @NonNull DomainModelManager modelManager) {
		super(env, evalEnv, modelManager);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitCoreModel(org.eclipse.qvtd.pivot.qvtcore.CoreModel)
	 */
	@Nullable
	public Object visitCoreModel(@NonNull CoreModel coreModel) {
		// CoreModel has a transformation (nestedPackage)
		Transformation transformation =	((Transformation)coreModel.getNestedPackage().get(0));
		// Initialise the variable map
		if(varMap == null) {
			varMap = new HashMap<Variable, HashSet<EObject>>();
		}
		// A transformation has a set of mappings
		for(Rule rule : transformation.getRule()){
			Object accept = ((Mapping)rule).accept(this);
			if(accept != null) {
				boolean domainOK = (boolean) accept;
				// TODO Decide whether we visit all the domains or end on error
				if(!domainOK) {
					return false;
				}
			}
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitMapping(org.eclipse.qvtd.pivot.qvtcore.Mapping)
	 */
	@Nullable
	public Object visitMapping(@NonNull Mapping mapping) {
		System.out.println("mapping " + mapping.getName());
		// Domains
		for(Domain domain : mapping.getDomain()){
			Object accept = ((CoreDomain)domain).accept(this);
			if(accept != null) {
				boolean domainOK = (boolean) accept;
				// TODO Decide whether we visit all the domains or end on error
				if(!domainOK) {
					return false;
				}
			}
		}
		// MiddleGuardPattern (aka where guard)
		boolean guardMet = (Boolean)mapping.getGuardPattern().accept(this);
		if(guardMet == true) {
			// Only visit the rest of the mappping if the guard is met
			// MiddleBottomPattern (aka where clause)
			mapping.getBottomPattern().accept(this);
			// Nested mappings
			for(Mapping localMapping : mapping.getLocal()) {
				localMapping.accept(this);
			}
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitCoreDomain(org.eclipse.qvtd.pivot.qvtcore.CoreDomain)
	 */
	@Nullable
	public Object visitCoreDomain(@NonNull CoreDomain coreDomain) {
		System.out.println("coreDomain " + coreDomain.getName());
		/* If the domain is enforced then we know it is the output domain and we
		 * can create the factory and the reference to the resource to load created elements
		 */
		if(coreDomain.isIsEnforceable()) {
			if (outputResource == null) {
				outputResource = ((QvtModelManager)modelManager).getTypeModelResource(coreDomain.getTypedModel());
				EcoreFactory theCoreFactory = EcoreFactory.eINSTANCE;
				EPackage outputEPackage = theCoreFactory.createEPackage();
				outputEPackage.setName(outputResource.getURI().lastSegment().split(",")[0]);
				EPackage model = (EPackage)outputResource.getContents().get(0);
				outputFactory = model.getEFactoryInstance();
			}
		}
		// DomainGuardPattern
		boolean guardMet = (Boolean)coreDomain.getGuardPattern().accept(this);
		if(guardMet == true) {
			// Only visit the DomainBottomPattern if the guard is met
			return coreDomain.getBottomPattern().accept(this);
		}
		return true;
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitGuardPattern(org.eclipse.qvtd.pivot.qvtcore.GuardPattern)
	 */
	@Nullable
	public Object visitGuardPattern(@NonNull GuardPattern guardPattern) {
		
		// Each GuardPatter introduces zero or one unbounded variable
		if(guardPattern.getVariable().size() > 0) {
			Variable var = guardPattern.getVariable().get(0);
			// Get all the objects from the TypeModel of the Domain that have the same
			// type as the variable
			TypedModel m = ((CoreDomain)guardPattern.getArea()).getTypedModel();
			HashSet<EObject> objectSet = new HashSet<>();
			// TODO How to use the allInstances?
			Resource resource = ((QvtModelManager)modelManager).getTypeModelResource(m);
			for (TreeIterator<EObject> contents = resource.getAllContents(); contents.hasNext();) {
				EObject object = contents.next();
				if (object.eClass().getName().equals(var.getType().getName())) {
					objectSet.add(object);
				}
			}
			// Bind the variable to the elements of the model
			varMap.put(var, objectSet);
			// TODO verify that the Predicate is a BooleanOCLExpr, is the spec wrong and should be OCLExpr ?
			// Use the predicate to filter the variables to match the predicates
			for(Predicate predicate : guardPattern.getPredicate()) {
				predicate.accept(this);
			}
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitBottomPattern(org.eclipse.qvtd.pivot.qvtcore.BottomPattern)
	 */
	@Nullable
	public Object visitBottomPattern(@NonNull BottomPattern bottomPattern) {
		// TODO Add visit function or decide if it should never be implemented
		for(Variable var : bottomPattern.getVariable()) {
			/* There are two options for BottomPatterns. In the DomainBottomPattern
			 * we are defining the variables used for pattern matching and their
			 * conditions. In the MiddleBottomPattern we define the associations
			 * that those variables create in the middle model
			 */
			// If the bottomPatter Area is a CoreDomain, we are in a DomainBottomPattern
			if(bottomPattern.getArea().eClass().getName().equals("CoreDomain")) {
				// Test if the variable is in the Map, if not add it
				if(!varMap.containsKey(var.getName())) {
					// Get all the objects from the TypeModel of the Domain
					CoreDomain d = (CoreDomain)bottomPattern.getArea();
					TypedModel m = d.getTypedModel();
					HashSet<EObject> objectSet = new HashSet<>();
					// TODO How to use the allInstances?
					Resource resource = ((QvtModelManager)modelManager).getTypeModelResource(m);
					for (TreeIterator<EObject> contents = resource.getAllContents(); contents.hasNext();) {
						EObject object = contents.next();
						if (object.eClass().getName().equals(var.getType().getName())) {
							objectSet.add(object);
						}
					}
					
					// Validate the check/enforce conditions
					if(d.isIsCheckable() && d.isIsEnforceable()) {
						if(objectSet.size() == 0) {
							// Create the new object for the variable, but how many? One because that is the mapping?
							Resource resource1 = ((QvtModelManager)modelManager).getTypeModelResource(m);
							// TODO Define how to create the new objects. Do we need a reference to the
							// model's mm so we can get a factory and create new EObjects?
							System.out.println("Need to code the enforcement");
						} /* else {
							// Enforce the Constraints, but that happens when visiting the Constraints of the domain
						}*/
					} else if(d.isIsCheckable() && objectSet.size() == 0) {
						return false;
					}
					varMap.put(var, objectSet);
				}
			} else { // if(bottomPattern.getArea().eClass().getName().equals("CoreDomain"))
				// We are in a MiddleBottomPattern
				// What to to with MiddleBottomPattern? Modify the middle model
				// by creating the associations defined in the constraints?
				// The type of objects depends on the var type
				//1. Create an element in the middle model that has a kind equal to the variable type
				EClass clazz = (EClass)middleFactory.getEPackage().getEClassifier(var.getType().getName());
				EObject varObject = middleFactory.create(clazz);
				middleModel.add(varObject);
				// How do we tell the predicate to find this EObject? We have to
				// create a the association in the varMap
				HashSet<EObject> objectSet = new HashSet<>();
				objectSet.add(varObject);
				varMap.put(var, objectSet);
			}
		}
		// TODO verify that the Predicate is a BooleanOCLExpr, is the spec wrong and should be OCLExpr ?
		for(Predicate predicate : bottomPattern.getPredicate()) {
			predicate.accept(this);
		}
		// TODO Only enforce domains can have realized variables
		for(RealizedVariable var : bottomPattern.getRealizedVariable()) {
			var.accept(this); // No idea what this will do
		}
		// TODO Only enforce domains can have assignments
		for(Assignment assigment : bottomPattern.getAssignment()){
			assigment.accept(this);
		}
		for(EnforcementOperation enforceOp : bottomPattern.getEnforcementOperation()) {
			enforceOp.accept(this);
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitAssignment(org.eclipse.qvtd.pivot.qvtcore.Assignment)
	 */
	@Nullable
	public Object visitAssignment(@NonNull Assignment assignment) {
		// TODO Add visit function or decide if it should never be implemented
		
		assignment.getValue().accept(this);
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitPropertyAssignment(org.eclipse.qvtd.pivot.qvtcore.PropertyAssignment)
	 */
	@Nullable
	public Object visitPropertyAssignment(@NonNull PropertyAssignment propertyAssignment) {
		// TODO Add visit function or decide if it should never be implemented
		
		// TODO check this when we know what to do with OCL things
		//propertyAssignment.getValue().accept(this);
		//propertyAssignment.getSlotExpression().accept(this);
		//propertyAssignment.getTargetProperty().accept(this);
		return null;
	}
	

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitEnforcementOperation(org.eclipse.qvtd.pivot.qvtcore.EnforcementOperation)
	 */
	@Nullable
	public Object visitEnforcementOperation(@NonNull EnforcementOperation enforcementOperation) {
		// TODO Add visit function or decide if it should never be implemented
		
		return null;
	}


	
	
	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitCorePattern(org.eclipse.qvtd.pivot.qvtcore.CorePattern)
	 */
	@Nullable
	public Object visitCorePattern(@NonNull CorePattern corePattern) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitRealizedVariable(org.eclipse.qvtd.pivot.qvtcore.RealizedVariable)
	 */
	@Nullable
	public Object visitRealizedVariable(@NonNull RealizedVariable var) {
		// This creates elements in the output model
		
		//1. Create an element in the output model that has a kind equal to the variable type
		EClass clazz = (EClass)outputFactory.getEPackage().getEClassifier(var.getType().getName());
		EObject varObject = outputFactory.create(clazz);
		outputResource.getContents().add(varObject);
		//2. Create the variable binding
		HashSet<EObject> objectSet = new HashSet<>();
		objectSet.add(varObject);
		varMap.put(var, objectSet);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitVariableAssignment(org.eclipse.qvtd.pivot.qvtcore.VariableAssignment)
	 */
	@Nullable
	public Object visitVariableAssignment(@NonNull VariableAssignment object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}
	
}
