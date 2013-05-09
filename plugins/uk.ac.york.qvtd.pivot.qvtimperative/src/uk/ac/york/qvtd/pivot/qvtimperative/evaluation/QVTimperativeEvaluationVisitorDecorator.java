/*******************************************************************************
 * Copyright (c) 2012 The University of York and Willink Transformations.
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
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitorDecorator;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitorImpl;
import org.eclipse.ocl.examples.pivot.util.Visitable;
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


/**
 * QVTcoreEvaluationVisitorImpl is the class for ...
 */
public abstract class QVTimperativeEvaluationVisitorDecorator extends EvaluationVisitorDecorator
	implements QVTimperativeEvaluationVisitor {

	protected final @NonNull QVTimperativeAbstractEvaluationVisitor delegate;	// FIXME replace with inherited value
	
	public QVTimperativeEvaluationVisitorDecorator(@NonNull QVTimperativeAbstractEvaluationVisitor decorated) {
		super(decorated);
		this.delegate = decorated;
	}

    /**
     * Delegates to my decorated visitor.
     */
	public void setQVTUndecoratedVisitor(QVTimperativeEvaluationVisitor evaluationVisitor) {
        delegate.setUndecoratedVisitor(evaluationVisitor);
	}
	
    
	/**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitAssignment(@NonNull Assignment assignment) {
		return delegate.visitAssignment(assignment);
    }
	
    /**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitBaseModel(@NonNull BaseModel baseModel) {
		return delegate.visitBaseModel(baseModel);
	}
	
	/**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitBottomPattern(@NonNull BottomPattern bottomPattern) {
    	return delegate.visitBottomPattern(bottomPattern);
    }
    
    /**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitCoreDomain(@NonNull CoreDomain coreDomain) {
    	return delegate.visitCoreDomain(coreDomain);
    }

    /**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitDomain(@NonNull Domain domain) {
		return delegate.visitDomain(domain);
	}

	/**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitCorePattern(@NonNull CorePattern corePattern) {
    	return delegate.visitCorePattern(corePattern);
    }

    /**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitEnforcementOperation(@NonNull EnforcementOperation enforcementOperation) {
    	return delegate.visitEnforcementOperation(enforcementOperation);
    }

    /**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitFunction(@NonNull Function function) {
		return delegate.visitFunction(function);
	}

	/**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitFunctionParameter(@NonNull FunctionParameter functionParameter) {
		return delegate.visitFunctionParameter(functionParameter);
	}

	/**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitGuardPattern(@NonNull GuardPattern guardPattern) {
    	return delegate.visitGuardPattern(guardPattern);
    }
    
    /**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitPattern(@NonNull Pattern pattern) {
		return delegate.visitPattern(pattern);
	}

	/**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitPredicate(@NonNull Predicate predicate) {
		return delegate.visitPredicate(predicate);
	}

	/**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitPropertyAssignment(@NonNull PropertyAssignment propertyAssignment) {
    	return delegate.visitPropertyAssignment(propertyAssignment);
    }

    /**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitRealizedVariable(@NonNull RealizedVariable realizedVariable) {
    	return delegate.visitRealizedVariable(realizedVariable);
    }

    /**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitRule(@NonNull Rule rule) {
		return delegate.visitRule(rule);
	}

	/**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitTransformation(@NonNull Transformation transformation) {
		return delegate.visitTransformation(transformation);
	}

	/**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitTypedModel(@NonNull TypedModel typedModel) {
		return delegate.visitTypedModel(typedModel);
	}

	/**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitUnit(@NonNull Unit unit) {
		return delegate.visitUnit(unit);
	}

	/**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitVariableAssignment(@NonNull VariableAssignment variableAssignment) {
    	return delegate.visitVariableAssignment(variableAssignment);
    }

    /**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitImperativeModel(ImperativeModel imperativeModel) {
    	return delegate.visitImperativeModel(imperativeModel);
	}

	/**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitMapping(Mapping mapping) {
    	return delegate.visitMapping(mapping);
	}

	/**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitMappingCall(MappingCall mappingCall) {
    	return delegate.visitMappingCall(mappingCall);
	}

	/**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitMappingCallBinding(MappingCallBinding mappingCallBinding) {
    	return delegate.visitMappingCallBinding(mappingCallBinding);
	}

    /**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visiting(Visitable visitable) {
    	return delegate.visiting(visitable);
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

}
