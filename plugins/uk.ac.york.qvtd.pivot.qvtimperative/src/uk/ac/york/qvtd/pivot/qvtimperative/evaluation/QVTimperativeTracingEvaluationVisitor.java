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

import java.util.ArrayList;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.elements.DomainType;
import org.eclipse.ocl.examples.pivot.OCLExpression;
import org.eclipse.ocl.examples.pivot.Variable;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitor;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitorImpl;
import org.eclipse.ocl.examples.pivot.evaluation.TracingEvaluationVisitor;
import org.eclipse.qvtd.pivot.qvtbase.Predicate;
import org.eclipse.qvtd.pivot.qvtbase.Transformation;
import org.eclipse.qvtd.pivot.qvtcorebase.Assignment;
import org.eclipse.qvtd.pivot.qvtcorebase.BottomPattern;
import org.eclipse.qvtd.pivot.qvtcorebase.CoreDomain;
import org.eclipse.qvtd.pivot.qvtcorebase.GuardPattern;
import org.eclipse.qvtd.pivot.qvtcorebase.PropertyAssignment;
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
		
		System.out.println(getIdent() + "BottomPattern");
		identLevel++;
		Object result = ((QVTimperativeEvaluationVisitor)getDelegate()).visitBottomPattern(bottomPattern);
		// Print the created (realized) variables
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
		
		
		System.out.println(getIdent() + "GuardPattern");
		// Print the variable bindings
		for (Variable var : guardPattern.getVariable()) {
			try {
				System.out.println(getIdent() + "Var: " + var.getName() + ", value: " + getDelegate().getEvaluationEnvironment().getValueOf(var));
			} catch (Exception ex) {
				// tracing must not interfere with evaluation
				System.out.println(getIdent() + "Var: " + var.getName() + ", was not found in the environment.");
			}
		}
		identLevel++;
		Object result = ((QVTimperativeEvaluationVisitor)getDelegate()).visitGuardPattern(guardPattern);
		try {
			if (guardPattern.getPredicate().size() == 0) {
				System.out.println(getIdent() + "GuardPattern has no predicates.");
			} else {
				System.out.println(getIdent() + "GuardPattern result: " + result);
			}
		} catch (Exception e) {
            // tracing must not interfere with evaluation
        }
		identLevel--;
    	return result;
	}
	
	@Override
    public @Nullable Object visitImperativeModel(ImperativeModel imperativeModel) {
		return ((QVTimperativeEvaluationVisitor)getDelegate()).visitImperativeModel(imperativeModel);
	}
	
	@Override
	public @Nullable Object visitMappingCall(@NonNull MappingCall mappingCall) {
		System.out.println(getIdent() + "Visiting MappingCall, calling: " + mappingCall.getReferredMapping().getName());
		identLevel++;
		Object result = ((QVTimperativeEvaluationVisitor)getDelegate()).visitMappingCall(mappingCall);
		identLevel--;
		return result;
	}

	
	@Override
	public @Nullable Object visitPropertyAssignment(@NonNull PropertyAssignment propertyAssignment) {
		
		System.out.println(getIdent() + "visitAssignment " + propertyAssignment.getSlotExpression()
				+ "." + propertyAssignment.getTargetProperty().getName());
		identLevel++;
		Object result = ((QVTimperativeEvaluationVisitor)getDelegate()).visitPropertyAssignment(propertyAssignment);
		identLevel--;
		return result;
    }
	
	@Override
	public @Nullable Object visitTransformation(@NonNull Transformation transformation) {
		System.out.println("\n");
		System.out.println("---- Transformation " + transformation.getName() + " ----");
		identLevel++;
		Object result = ((QVTimperativeEvaluationVisitor)getDelegate()).visitTransformation(transformation);
		identLevel--;
		System.out.println("---- Transformation End ----");
		return result;
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
		System.out.println("Predicate " + exp.getName());
		return ((QVTimperativeEvaluationVisitor)getDelegate()).visitPredicate(predicate);
		
	}
	

	public EvaluationVisitor createNestedLMVisitor() {
		
		System.out.println("(Creating nested LM Visitor)");
		QVTimperativeTracingEvaluationVisitor decorator = new QVTimperativeTracingEvaluationVisitor(
				(QVTimperativeEvaluationVisitor) ((QVTimperativeEvaluationVisitor)getDelegate()).createNestedLMVisitor());
		return decorator;//.getDelegate();
	}


	public EvaluationVisitor createNestedMMVisitor() {
		
		System.out.println("(Creating nested MM Visitor)");
		QVTimperativeTracingEvaluationVisitor decorator = new QVTimperativeTracingEvaluationVisitor(
				(QVTimperativeEvaluationVisitor) ((QVTimperativeEvaluationVisitor)getDelegate()).createNestedMMVisitor());
		return decorator;
	}


	public EvaluationVisitor createNestedMRVisitor() {
		
		System.out.println("(Creating nested MR Visitor)");
		QVTimperativeTracingEvaluationVisitor decorator = new QVTimperativeTracingEvaluationVisitor(
				(QVTimperativeEvaluationVisitor) ((QVTimperativeEvaluationVisitor)getDelegate()).createNestedMRVisitor());
		return decorator;
	}
	
	private String getIdent() {
		StringBuffer outputBuffer = new StringBuffer(identLevel);
		for (int i = 0; i < identLevel; i++){
		   outputBuffer.append(" ");
		}
		return outputBuffer.toString();
	}
	

}
