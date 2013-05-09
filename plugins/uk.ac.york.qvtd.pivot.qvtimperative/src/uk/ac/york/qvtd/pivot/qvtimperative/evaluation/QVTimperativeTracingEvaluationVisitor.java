/*******************************************************************************
 * Copyright (c) 2013 The University of York and Willink Transformations.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Horacio Hoyos - initial API and implementation
 ******************************************************************************/
package uk.ac.york.qvtd.pivot.qvtimperative.evaluation;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitorImpl;
import org.eclipse.ocl.examples.pivot.evaluation.TracingEvaluationVisitor;
import org.eclipse.qvtd.pivot.qvtbase.BaseModel;
import org.eclipse.qvtd.pivot.qvtbase.Domain;
import org.eclipse.qvtd.pivot.qvtbase.Function;
import org.eclipse.qvtd.pivot.qvtbase.FunctionParameter;
import org.eclipse.qvtd.pivot.qvtbase.Pattern;
import org.eclipse.qvtd.pivot.qvtbase.Predicate;
import org.eclipse.qvtd.pivot.qvtbase.Rule;
import org.eclipse.qvtd.pivot.qvtbase.Transformation;
import org.eclipse.qvtd.pivot.qvtbase.TypedModel;
import org.eclipse.qvtd.pivot.qvtbase.Unit;
import org.eclipse.qvtd.pivot.qvtcorebase.Assignment;
import org.eclipse.qvtd.pivot.qvtcorebase.BottomPattern;
import org.eclipse.qvtd.pivot.qvtcorebase.CoreDomain;
import org.eclipse.qvtd.pivot.qvtcorebase.CorePattern;
import org.eclipse.qvtd.pivot.qvtcorebase.EnforcementOperation;
import org.eclipse.qvtd.pivot.qvtcorebase.GuardPattern;
import org.eclipse.qvtd.pivot.qvtcorebase.PropertyAssignment;
import org.eclipse.qvtd.pivot.qvtcorebase.RealizedVariable;
import org.eclipse.qvtd.pivot.qvtcorebase.VariableAssignment;
import org.eclipse.qvtd.pivot.qvtimperative.ImperativeModel;
import org.eclipse.qvtd.pivot.qvtimperative.Mapping;
import org.eclipse.qvtd.pivot.qvtimperative.MappingCall;
import org.eclipse.qvtd.pivot.qvtimperative.MappingCallBinding;


public class QVTimperativeTracingEvaluationVisitor extends TracingEvaluationVisitor implements QVTimperativeEvaluationVisitor
{
	protected final @NonNull QVTimperativeEvaluationVisitor delegate;
	
	public QVTimperativeTracingEvaluationVisitor(@NonNull QVTimperativeEvaluationVisitor decorated) {
		super(decorated);
		this.delegate = decorated;
	}

	public EvaluationVisitorImpl createNestedLMVisitor() {
		return delegate.createNestedLMVisitor();
	}

	public EvaluationVisitorImpl createNestedMMVisitor() {
		return delegate.createNestedMMVisitor();
	}

	public EvaluationVisitorImpl createNestedMRVisitor() {
		return delegate.createNestedMRVisitor();
	}

	public @Nullable Object visitImperativeModel(ImperativeModel object) {
		return trace(object, delegate.visitImperativeModel(object));
	}

	public @Nullable Object visitMapping(Mapping object) {
		return trace(object, delegate.visitMapping(object));
	}

	public @Nullable Object visitMappingCall(MappingCall object) {
		return trace(object, delegate.visitMappingCall(object));
	}

	public @Nullable Object visitMappingCallBinding(MappingCallBinding object) {
		return trace(object, delegate.visitMappingCallBinding(object));
	}

	public @Nullable Object visitAssignment(Assignment object) {
		return trace(object, delegate.visitAssignment(object));
	}

	public @Nullable Object visitBottomPattern(BottomPattern object) {
		return trace(object, delegate.visitBottomPattern(object));
	}

	public @Nullable Object visitCoreDomain(CoreDomain object) {
		return trace(object, delegate.visitCoreDomain(object));
	}

	public @Nullable Object visitCorePattern(CorePattern object) {
		return trace(object, delegate.visitCorePattern(object));
	}

	public @Nullable Object visitEnforcementOperation(EnforcementOperation object) {
		return trace(object, delegate.visitEnforcementOperation(object));
	}

	public @Nullable Object visitGuardPattern(GuardPattern object) {
		return trace(object, delegate.visitGuardPattern(object));
	}

	public @Nullable Object visitPropertyAssignment(PropertyAssignment object) {
		return trace(object, delegate.visitPropertyAssignment(object));
	}

	public @Nullable Object visitRealizedVariable(RealizedVariable object) {
		return trace(object, delegate.visitRealizedVariable(object));
	}

	public @Nullable Object visitVariableAssignment(VariableAssignment object) {
		return trace(object, delegate.visitVariableAssignment(object));
	}

	public @Nullable Object visitBaseModel(BaseModel object) {
		return trace(object, delegate.visitBaseModel(object));
	}

	public @Nullable Object visitDomain(Domain object) {
		return trace(object, delegate.visitDomain(object));
	}

	public @Nullable Object visitFunction(Function object) {
		return trace(object, delegate.visitFunction(object));
	}

	public @Nullable Object visitFunctionParameter(FunctionParameter object) {
		return trace(object, delegate.visitFunctionParameter(object));
	}

	public @Nullable Object visitPattern(Pattern object) {
		return trace(object, delegate.visitPattern(object));
	}

	public @Nullable Object visitPredicate(Predicate object) {
		return trace(object, delegate.visitPredicate(object));
	}

	public @Nullable Object visitRule(Rule object) {
		return trace(object, delegate.visitRule(object));
	}

	public @Nullable Object visitTransformation(Transformation object) {
		return trace(object, delegate.visitTransformation(object));
	}
	
//	@Override
//    public @Nullable Object visitTransformation(@NonNull Transformation transformation) {
//		System.out.println("Transformation: " + transformation.getName());
//		Object result = getDelegate().visitTransformation(transformation);
//		System.out.println("Result of the transformation was " + (Boolean)result);
//		return result;
//        
//    }

	public @Nullable Object visitTypedModel(TypedModel object) {
		return trace(object, delegate.visitTypedModel(object));
	}

	public @Nullable Object visitUnit(Unit object) {
		return trace(object, delegate.visitUnit(object));
	}
}
