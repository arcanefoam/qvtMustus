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

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.pivot.Environment;
import org.eclipse.ocl.examples.pivot.Variable;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.ocl.examples.pivot.utilities.PivotResource;
import org.eclipse.qvtd.pivot.qvtbase.evaluation.QVTbaseEVNodeTypeImpl;
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

import org.eclipse.qvtd.pivot.qvtbase.Domain;
import org.eclipse.qvtd.pivot.qvtbase.Predicate;
import org.eclipse.qvtd.pivot.qvtbase.Rule;
import org.eclipse.qvtd.pivot.qvtbase.Transformation;

public class QVTcoreEVNodeTypeImpl extends QVTbaseEVNodeTypeImpl implements QVTcoreVisitor<Object> {

	private int coreModelCount;
	private int transformationCount;
	private int mappingCount;
	private int coreDomainCount;
	private int bottomPatternCount;
	private int guardPatternCount;
	private int assignmentCount;
	private int propertyAssignmentCount;
	private int variableCount;
	private int enforcementOperationCount;

	public QVTcoreEVNodeTypeImpl(Environment env,
			EvaluationEnvironment evalEnv, DomainModelManager modelManager) {
		super(env, evalEnv, modelManager);
		// TODO Auto-generated constructor stub
	}

	public QVTcoreEVNodeTypeImpl(Environment env,
			EvaluationEnvironment evalEnv, DomainModelManager modelManager,
			PivotResource astRoot, Resource inputModel,
			Resource outputModel) {
		super(env, evalEnv, modelManager, astRoot, inputModel, outputModel);
	}
	
	@Nullable
	public Object visitAssignment(Assignment assignment) {
		// TODO Add visit function or decide if it should never be implemented
		assignmentCount ++;
		System.out.println("Assignment" + assignmentCount);
		assignment.getValue().accept(this);
		return null;
	}

	@Nullable
	public Object visitBottomPattern(BottomPattern bottomPattern) {
		// TODO Add visit function or decide if it should never be implemented
		bottomPatternCount ++;
		System.out.println("BottomPattern" + bottomPatternCount);
		for(Variable var : bottomPattern.getVariable()) {
			if(var != null) {
				// TODO how to initialize variables? I guess in this case we will
				// have to look at the Direction to which the domain points
				// and fetch all model elements of the variable Type
				//var.accept(this); // No idea what this will do
				// TODO remove this!!
				variableCount ++;
				System.out.println("Variable" + variableCount);
			}
		}
		for(RealizedVariable var : bottomPattern.getRealizedVariable()) {
			var.accept(this); // No idea what this will do
		}
		// Although present in the CS, GuardPatterns only have Predicates
		for(Predicate predicate : bottomPattern.getPredicate()) {
			predicate.accept(this);
		}
		for(Assignment assigment : bottomPattern.getAssignment()){
			assigment.accept(this);
		}
		for(EnforcementOperation enforceOp : bottomPattern.getEnforcementOperation()) {
			enforceOp.accept(this);
		}
		return null;
	}

	@Nullable
	public Object visitCoreDomain(CoreDomain coreDomain) {
		// TODO Add visit function or decide if it should never be implemented
		coreDomainCount ++;
		System.out.println("CoreDomain" + coreDomainCount);
		// DomainGuardPattern
		coreDomain.getGuardPattern().accept(this);
		// DomainBottomPattern
		coreDomain.getBottomPattern().accept(this);
		return null;
	}

	@Nullable
	public Object visitCoreModel(CoreModel coreModel) {
		// TODO Add visit function or decide if it should never be implemented
		coreModelCount ++;
		System.out.println("CoreModel" + coreModelCount);
		// CoreMode has a transformation (nestedPackage)
		Transformation transformation =	((Transformation)coreModel.getNestedPackage().get(0));
		transformationCount ++;
		System.out.println("Transformation" + transformationCount);
		// A transformation has a set of mappings
		for(Rule rule : transformation.getRule()){
			((Mapping)rule).accept(this);
		}
		return null;
	}

	@Nullable
	public Object visitCorePattern(CorePattern object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	@Nullable
	public Object visitEnforcementOperation(EnforcementOperation enforcementOperation) {
		// TODO Add visit function or decide if it should never be implemented
		enforcementOperationCount ++;
		System.out.println("EnforcementOperation" + enforcementOperationCount);
		return null;
	}

	@Nullable
	public Object visitGuardPattern(GuardPattern guardPattern) {
		// TODO Add visit function or decide if it should never be implemented
		guardPatternCount ++;
		System.out.println("GuardPattern" + guardPatternCount);
		for(Variable var : guardPattern.getVariable()) {
			// TODO this is the same as for BottomPattern
			//var.accept(this); // No idea what this will do
			// TODO remove this!!
			variableCount ++;
			System.out.println("Variable" + variableCount);
		}
		// Although present in the CS, GuardPatterns only have Predicates
		for(Predicate predicate : guardPattern.getPredicate()) {
			predicate.accept(this);
		}
		return null;
	}

	@Nullable
	public Object visitMapping(Mapping mapping) {
		// TODO Add visit function or decide if it should never be implemented
		mappingCount ++;
		System.out.println("Mapping" + mappingCount);
		// Domains
		for(Domain domain : mapping.getDomain()){
			((CoreDomain)domain).accept(this);
		}
		// MiddleGuardPattern (aka where guard)
		mapping.getGuardPattern().accept(this);
		// MiddleBottomPattern (aka where clause)
		mapping.getBottomPattern().accept(this);
		// Nested mappings
		for(Mapping localMapping : mapping.getLocal()) {
			localMapping.accept(this);
		}
		return null;
	}

	@Nullable
	public Object visitPropertyAssignment(PropertyAssignment propertyAssignment) {
		// TODO Add visit function or decide if it should never be implemented
		propertyAssignmentCount ++;
		System.out.println("PropertyAssignment" + propertyAssignmentCount);
		// TODO check this when we know what to do with OCL things
		//propertyAssignment.getValue().accept(this);
		//propertyAssignment.getSlotExpression().accept(this);
		//propertyAssignment.getTargetProperty().accept(this);
		return null;
	}

	@Nullable
	public Object visitRealizedVariable(RealizedVariable object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}

	@Nullable
	public Object visitVariableAssignment(VariableAssignment object) {
		// TODO Add visit function or decide if it should never be implemented
		throw new UnsupportedOperationException("Visit method not implemented yet");
	}
	
}
