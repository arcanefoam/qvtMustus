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
import org.eclipse.ocl.examples.pivot.OCLExpression;
import org.eclipse.ocl.examples.pivot.Variable;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitor;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitorImpl;
import org.eclipse.ocl.examples.pivot.evaluation.TracingEvaluationVisitor;
import org.eclipse.ocl.examples.pivot.prettyprint.PrettyPrinter;
import org.eclipse.qvtd.pivot.qvtbase.Predicate;
import org.eclipse.qvtd.pivot.qvtbase.Transformation;
import org.eclipse.qvtd.pivot.qvtcorebase.BottomPattern;
import org.eclipse.qvtd.pivot.qvtcorebase.CoreDomain;
import org.eclipse.qvtd.pivot.qvtcorebase.GuardPattern;
import org.eclipse.qvtd.pivot.qvtimperative.ImperativeModel;
import org.eclipse.qvtd.pivot.qvtimperative.Mapping;
import org.eclipse.qvtd.pivot.qvtimperative.MappingCall;
import org.eclipse.qvtd.pivot.qvtimperative.MappingCallBinding;



public class QVTimperativeTracingEvaluationVisitor extends
		QVTimperativeEvaluationVisitorDecorator {
	
	private static int identLevel;

	public QVTimperativeTracingEvaluationVisitor(
			QVTimperativeEvaluationVisitor decorated) {
		super(decorated);

	}
	
	@Override
	public @NonNull EvaluationVisitor createNestedEvaluator() {
		return new TracingEvaluationVisitor(super.createNestedEvaluator());
	}
	
	@Override
    public @Nullable Object visitBottomPattern(@NonNull BottomPattern bottomPattern) {
		
		if (bottomPattern.getArea() instanceof CoreDomain) {
			System.out.println(getIdent() + "Visiting CoreDomain BottomPattern");
		}
		if (bottomPattern.getArea() instanceof Mapping) {
			System.out.println(getIdent() + "Visiting Mapping BottomPattern");
		}
		identLevel++;
		Object result = ((QVTimperativeEvaluationVisitor)getDelegate()).visitBottomPattern(bottomPattern);
		if (bottomPattern.getPredicate().size() == 0) {
			System.out.println(getIdent() + "BottomPattern has no predicates.");
		} else {
			System.out.println(getIdent() + "BottomPattern result: " + result);
		}
		identLevel--;
		return result;
	}
	
	@Override
    public @Nullable Object visitCoreDomain(@NonNull CoreDomain coreDomain) {
		System.out.println(getIdent() + "CoreDomain " + coreDomain.getName());
		identLevel++;
		Object result = ((QVTimperativeEvaluationVisitor)getDelegate()).visitCoreDomain(coreDomain);
		identLevel--;
    	return result;
    }
	
	@Override
	public @Nullable Object visitGuardPattern(@NonNull GuardPattern guardPattern) {
		
		if (guardPattern.getArea() instanceof CoreDomain) {
			System.out.println(getIdent() + "Visiting CoreDomain GuardPattern");
		}
		if (guardPattern.getArea() instanceof Mapping) {
			System.out.println(getIdent() + "Visiting Mapping GuardPattern");
		}
		identLevel++;
		Object result = ((QVTimperativeEvaluationVisitor)getDelegate()).visitGuardPattern(guardPattern);
		if (guardPattern.getPredicate().size() == 0) {
			System.out.println(getIdent() + "GuardPattern has no predicates.");
		} else {
			System.out.println(getIdent() + "GuardPattern result: " + result);
		}
		identLevel--;
    	return result;
	}
	
	@Override
    public @Nullable Object visitImperativeModel(ImperativeModel imperativeModel) {
		return ((QVTimperativeEvaluationVisitor)getDelegate()).visitImperativeModel(imperativeModel);
	}
	
	@Override
	public @Nullable Object visitIteratorExp(@NonNull org.eclipse.ocl.examples.pivot.IteratorExp object) {
		return getDelegate().visitIteratorExp(object);
	}
	
	@Override
	public @Nullable Object visitMappingCall(@NonNull MappingCall mappingCall) {
		System.out.println(getIdent() + "Visiting MappingCall, calling: " + mappingCall.getReferredMapping().getName());
		identLevel++;
		System.out.println(getIdent() + "Bindings");
		for (MappingCallBinding binding : mappingCall.getBinding()) {
			Variable boundVariable = binding.getBoundVariable();
			System.out.println(getIdent() + boundVariable.getName() + ": " +  PrettyPrinter.print(binding.getValue()));
			/*
			if (!binding.isIsLoop()) {
				DomainType valueType = metaModelManager.getIdResolver().getDynamicTypeOf(valueOrValues);
				if (valueType.conformsTo(metaModelManager, boundVariable.getType())) {
					nv.getEvaluationEnvironment().add(boundVariable, valueOrValues);
				}
				else {
					return null;		
				}
			}
			else if (valueOrValues instanceof Iterable<?>) {
				if (loopedVariables == null) {
					loopedVariables = new ArrayList<Variable>();
					loopedValues = new ArrayList<Iterable<?>>();
				}
				loopedVariables.add(boundVariable);
				loopedValues.add((Iterable<?>)valueOrValues);
				nv.getEvaluationEnvironment().add(boundVariable, null);
			} else {
				// FIXME Error message;
			}
			*/
    	}
		Object result = ((QVTimperativeEvaluationVisitor)getDelegate()).visitMappingCall(mappingCall);
		identLevel--;
		return result;
	}
	
	@Override
	public @Nullable Object visitTransformation(@NonNull Transformation transformation) {
		System.out.println("\n");
		System.out.println("---- Transformation " + transformation.getName() + " ----");
		identLevel =+1;
		return ((QVTimperativeEvaluationVisitor)getDelegate()).visitTransformation(transformation);
	}
	
	@Override
	public @Nullable Object visitMapping(@NonNull Mapping mapping) {
		System.out.println(getIdent() + "Mapping " + mapping.getName());
		identLevel++;
		Object result = ((QVTimperativeEvaluationVisitor)getDelegate()).visitMapping(mapping);
		identLevel--;
		return result;
	}
	
	@Override
	public @Nullable Object visitPredicate(@NonNull Predicate predicate) {
		
		OCLExpression exp = predicate.getConditionExpression();
		System.out.println(getIdent() + "Predicate " + PrettyPrinter.print(exp));
		return ((QVTimperativeEvaluationVisitor)getDelegate()).visitPredicate(predicate);
		
	}
	

	public EvaluationVisitorImpl createNestedLMVisitor() {
		
		System.out.println("(Creating nested LM Visitor)");
		QVTimperativeTracingEvaluationVisitor decorator = new QVTimperativeTracingEvaluationVisitor(
				(QVTimperativeEvaluationVisitor) ((QVTimperativeEvaluationVisitor)getDelegate()).createNestedLMVisitor());
		return (EvaluationVisitorImpl) decorator.getDelegate();
	}


	public EvaluationVisitorImpl createNestedMMVisitor() {
		
		System.out.println("(Creating nested MM Visitor)");
		QVTimperativeTracingEvaluationVisitor decorator = new QVTimperativeTracingEvaluationVisitor(
				(QVTimperativeEvaluationVisitor) ((QVTimperativeEvaluationVisitor)getDelegate()).createNestedMMVisitor());
		return (EvaluationVisitorImpl) decorator.getDelegate();
	}


	public EvaluationVisitorImpl createNestedMRVisitor() {
		
		System.out.println("(Creating nested MR Visitor)");
		QVTimperativeTracingEvaluationVisitor decorator = new QVTimperativeTracingEvaluationVisitor(
				(QVTimperativeEvaluationVisitor) ((QVTimperativeEvaluationVisitor)getDelegate()).createNestedMRVisitor());
		return (EvaluationVisitorImpl) decorator.getDelegate();
	}
	
	private String getIdent() {
		StringBuffer outputBuffer = new StringBuffer(identLevel);
		for (int i = 0; i < identLevel; i++){
		   outputBuffer.append("\t");
		}
		return outputBuffer.toString();
	}
	

}
