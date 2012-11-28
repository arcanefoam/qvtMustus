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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.pivot.Environment;
import org.eclipse.ocl.examples.pivot.OCLExpression;
import org.eclipse.ocl.examples.pivot.Property;
import org.eclipse.ocl.examples.pivot.PropertyCallExp;
import org.eclipse.ocl.examples.pivot.Type;
import org.eclipse.ocl.examples.pivot.Variable;
import org.eclipse.ocl.examples.pivot.VariableExp;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitor;
import org.eclipse.qvtd.pivot.qvtbase.Domain;
import org.eclipse.qvtd.pivot.qvtbase.Predicate;
import org.eclipse.qvtd.pivot.qvtbase.Rule;
import org.eclipse.qvtd.pivot.qvtbase.Transformation;
import org.eclipse.qvtd.pivot.qvtbase.TypedModel;
import org.eclipse.qvtd.pivot.qvtbase.evaluation.QVTiBaseEVImplTrivial;
import org.eclipse.qvtd.pivot.qvtbase.evaluation.QvtModelManager;
import org.eclipse.qvtd.pivot.qvtcore.Area;
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
public class QVTicoreEVImplTrivial extends QVTiBaseEVImplTrivial implements QVTcoreVisitor<Object> {
	
	/**
	 * Instantiates a new qV ticore ev impl.
	 *
	 * @param env the env
	 * @param evalEnv the eval env
	 * @param modelManager the model manager
	 */
	public QVTicoreEVImplTrivial(@NonNull Environment env,
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
		// Trivial example does not have guards
		//boolean guardMet = (Boolean)mapping.getGuardPattern().accept(this);
		
		// MiddleBottomPattern (aka where clause)
		mapping.getBottomPattern().accept(this);
		
		// Nested mappings
		for(Mapping localMapping : mapping.getLocal()) {
			localMapping.accept(this);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitCoreDomain(org.eclipse.qvtd.pivot.qvtcore.CoreDomain)
	 */
	@Nullable
	public Object visitCoreDomain(@NonNull CoreDomain coreDomain) {
		System.out.println("coreDomain " + coreDomain.getName());
		// DomainGuardPattern
		// Trivial example does not have guards
		//boolean guardMet = (Boolean)coreDomain.getGuardPattern().accept(this);
		return coreDomain.getBottomPattern().accept(this);
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitGuardPattern(org.eclipse.qvtd.pivot.qvtcore.GuardPattern)
	 */
	@Nullable
	public Object visitGuardPattern(@NonNull GuardPattern guardPattern) {
		// Trivial examples should not have guard patterns
		throw new UnsupportedOperationException("Trivial examples should not have guard patterns");
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
				// TODO get the factory from the model manager
				//EClass clazz = (EClass)middleFactory.getEPackage().getEClassifier(var.getType().getName());
				//EObject varObject = middleFactory.create(clazz);
				//middleModel.getContents().add(varObject);
				
				// How do we tell the predicate to find this EObject? We have to
				// create a the association in the varMap
				HashSet<EObject> objectSet = new HashSet<>();
				//objectSet.add(varObject);
				varMap.put(var, objectSet);
			}
		}
		for(RealizedVariable var : bottomPattern.getRealizedVariable()) {
			var.accept(this);
		}
		// TODO verify that the Predicate is a BooleanOCLExpr, is the spec wrong and should be OCLExpr ?
		for(Predicate predicate : bottomPattern.getPredicate()) {
			predicate.accept(this);
		}
		for(Assignment assigment : bottomPattern.getAssignment()){
			assigment.accept(this);
		}
		for(EnforcementOperation enforceOp : bottomPattern.getEnforcementOperation()) {
			enforceOp.accept(this);
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitRealizedVariable(org.eclipse.qvtd.pivot.qvtcore.RealizedVariable)
	 */
	@Nullable
	public Object visitRealizedVariable(@NonNull RealizedVariable realizedVariable) {
		// This creates elements in the middle or output model
		// 1.Identify to what model does the variable belongs to:
		if( ((BottomPattern)realizedVariable.eContainer()).getArea() instanceof CoreDomain) {
			// The model is the output model, input model (domains) does not have realized variables
			//2. Create an element in the output model that has a kind equal to the variable type.
			// There should be 1 new element in the output model for each binding of an element in the middle
			// model in the context (i.e., the CoreDomiain's map owner's bottompattern)
			EFactory outputFactory = ((QvtModelManager)modelManager).getTypeModelFactory(
					((CoreDomain)((BottomPattern)realizedVariable.eContainer()).getArea()).getTypedModel());
			EClass clazz = (EClass)outputFactory.getEPackage().getEClassifier(realizedVariable.getType().getName());
			HashSet<EObject> objectSet = new HashSet<>();
			for(int i = 0; i < varMap.get(((Mapping)((CoreDomain)((BottomPattern)realizedVariable.eContainer()).getArea()).getRule()).getContext().getBottomPattern().getRealizedVariable().get(0)).size(); i++ ) {
				EObject varObject = outputFactory.create(clazz);
				((QvtModelManager)modelManager).getTypeModelResource(
						((CoreDomain)((BottomPattern)realizedVariable.eContainer()).getArea()).getTypedModel()).getContents().add(varObject);
				/* From Epsilon, we should attach a listener to the EObject so if its hierarchy changes it will be moved out of the
				 * resource and placed correctly
				 */
				varObject.eAdapters().add(new ContainmentChangeAdapter(varObject, ((QvtModelManager)modelManager).getTypeModelResource(
						((CoreDomain)((BottomPattern)realizedVariable.eContainer()).getArea()).getTypedModel())));
				objectSet.add(varObject);
			}
			//3. Create the variable binding
			varMap.put(realizedVariable, objectSet);
		} else { // The BottomPattern owner is a Mapping, mapping always references the middle model
			//2. Create an element in the middle  model that has a kind equal to the variable type
			// There should be 1 new element in the middle model for each of the bindings of the 
			// variables in the Patterns of the mappings
			Set<Variable> vars = new HashSet<>();
			for(Domain d : ((Mapping)((BottomPattern)realizedVariable.eContainer()).getArea()).getDomain()) {
				vars.addAll( ((Area)d).getBottomPattern().getVariable());
			}
			EFactory f = ((QvtModelManager)modelManager).getMiddleFactory();
			EClass clazz = (EClass)f.getEPackage().getEClassifier(realizedVariable.getType().getName());
			// Add created objects
			HashSet<EObject> objectSet = new HashSet<>();
			for(Variable var : vars) {
				for(int i = 0; i < varMap.get(var).size(); i++) {
					EObject varObject = ((QvtModelManager)modelManager).getMiddleFactory().create(clazz);
					((QvtModelManager)modelManager).getMiddleModel().getContents().add(varObject);
					objectSet.add(varObject);
				}
			}
			//3. Create the variable binding
			varMap.put(realizedVariable, objectSet);
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitPropertyAssignment(org.eclipse.qvtd.pivot.qvtcore.PropertyAssignment)
	 */
	@Nullable
	public Object visitPropertyAssignment(@NonNull PropertyAssignment propertyAssignment) {
		OCLExpression exp = propertyAssignment.getSlotExpression(); 
		if(exp instanceof VariableExp ) {
			Variable slotVar = (Variable) ((VariableExp)exp).getReferredVariable();
			// Use the var type to know the direction
			EFactory f = ((QvtModelManager)modelManager).getMiddleFactory();
			EClass clazz = (EClass)f.getEPackage().getEClassifier(slotVar.getType().getName());
			if(clazz == null) { // The variable does not reference the middle model
				for(EFactory df : ((QvtModelManager)modelManager).getFactories()) {
					clazz = (EClass)df.getEPackage().getEClassifier(slotVar.getType().getName());
					if(clazz != null) {
						break;
					}
				}
			}
			if(clazz != null) { 
				// We need to supply the OCLExpression accept method with a visitor
				// that provides values (context) for the variables.
				Variable valueVar = null;
				if(propertyAssignment.getValue() instanceof PropertyCallExp) {
					if(((PropertyCallExp)propertyAssignment.getValue()).getSource() instanceof VariableExp ) {
						valueVar = (Variable) ((VariableExp)((PropertyCallExp)propertyAssignment.getValue()).getSource()).getReferredVariable();
					} else if(((PropertyCallExp)propertyAssignment.getValue()).getSource() instanceof PropertyCallExp) {
						if( ((PropertyCallExp)((PropertyCallExp)propertyAssignment.getValue()).getSource()).getSource() instanceof VariableExp ) {
							valueVar = (Variable) ((VariableExp)((PropertyCallExp)((PropertyCallExp)propertyAssignment.getValue()).getSource()).getSource()).getReferredVariable();
						}
					}
				} else if(propertyAssignment.getValue() instanceof VariableExp) {
					valueVar = (Variable) ((VariableExp)propertyAssignment.getValue()).getReferredVariable();
				}
				// Assume the value has only only binding, or that all the bindings are to the same object/value
				// A binding class is definitively needed to manage the cartesian product of domains
				if(varMap.get(valueVar).size() == 1) {
					// There is a unique value
					EObject valueObject = varMap.get(valueVar).iterator().next();
					EvaluationVisitor ne = createNestedEvaluator();
					ne.getEvaluationEnvironment().add(valueVar, valueObject);
					Object value = propertyAssignment.getValue().accept(ne);
					if(value instanceof String) {
						value = ((String)value).toLowerCase();
					}
					// Loop through the slot vars so all get an assignment
					for(EObject e : varMap.get(slotVar)) {
						// Assign the value to a binding of the slotVar
						// TODO what happens if the target property is not a simple attribute
						// (e.g. cannot find it by name)
						String feature = (String) propertyAssignment.getTargetProperty().getName();
						e.eSet(e.eClass().getEStructuralFeature(feature), value);
					}
				} else if (varMap.get(valueVar).size() == varMap.get(slotVar).size()){
					Iterator<EObject> valueIterator = varMap.get(valueVar).iterator();
					// Loop through the slot vars so all get an assignment
					for(EObject e : varMap.get(slotVar)) {
						EvaluationVisitor ne = createNestedEvaluator();
						ne.getEvaluationEnvironment().add(valueVar, valueIterator.next());
						Object value = propertyAssignment.getValue().accept(ne);
						if(value instanceof String) {
							value = ((String)value).toLowerCase();
						}
						// Assign the value to a binding of the slotVar
						// TODO what happens if the target property is not a simple attribute
						// (e.g. cannot find it by name)
						String feature = (String) propertyAssignment.getTargetProperty().getName();
						e.eSet(e.eClass().getEStructuralFeature(feature), value);
					}
				} /*else {
					// Can they be different?
					 * 
				}
				*/
				
			}	
		}
		// Get the Object represented by the property, again 1 object per variable
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitVariableAssignment(org.eclipse.qvtd.pivot.qvtcore.VariableAssignment)
	 */
	@Nullable
	public Object visitVariableAssignment(@NonNull VariableAssignment object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
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
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitAssignment(org.eclipse.qvtd.pivot.qvtcore.Assignment)
	 */
	@Nullable
	public Object visitAssignment(@NonNull Assignment assignment) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit to assigments shuld be to the specific type.");
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.qvtd.pivot.qvtcore.util.QVTcoreVisitor#visitCorePattern(org.eclipse.qvtd.pivot.qvtcore.CorePattern)
	 */
	@Nullable
	public Object visitCorePattern(@NonNull CorePattern corePattern) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	
	
}
