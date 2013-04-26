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
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.pivot.Environment;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitor;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitorImpl;
import org.eclipse.ocl.examples.pivot.util.AbstractExtendingVisitor;
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
public abstract class QVTimperativeEvaluationVisitorDecorator extends AbstractExtendingVisitor<Object, Object>
        implements QVTimperativeEvaluationVisitor<Object> {
        
	private final QVTimperativeEvaluationVisitor<Object> delegate;
	
	public QVTimperativeEvaluationVisitorDecorator(@NonNull QVTimperativeEvaluationVisitor<Object> decorated) {
		super(Object.class);
		assert decorated != null : "cannot decorate a null visitor"; //$NON-NLS-1$

        this.delegate = decorated;
        
        decorated.setUndecoratedVisitor(this);
    }
	
	/**
     * Delegates to my decorated visitor.
     */
	public @NonNull EvaluationVisitor createNestedEvaluator() {
        return getDelegate().createNestedEvaluator();
	}
  
    /**
     * Obtains the visitor that I decorate.
     * 
     * @return my decorated visitor
     */
	protected final @NonNull QVTimperativeEvaluationVisitor<Object> getDelegate() {
        return delegate;
    }
    
    /**
     * Obtains my delegate's environment.
     */
    public Environment getEnvironment() {
        return getDelegate().getEnvironment();
    }

    /**
     * Obtains my delegate's evaluation environment.
     */
    public @NonNull EvaluationEnvironment getEvaluationEnvironment() {
        return getDelegate().getEvaluationEnvironment();
    }

    /**
     * Obtains my delegate's extent map.
     */
    public @NonNull DomainModelManager getModelManager() {
        return getDelegate().getModelManager();
    }

    /**
     * Delegates to my decorated visitor.
     */
	public void setUndecoratedVisitor(QVTimperativeEvaluationVisitor<Object> evaluationVisitor) {
        getDelegate().setUndecoratedVisitor(evaluationVisitor);
	}
	
    
	/**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitAssignment(@NonNull Assignment assignment) {
		return getDelegate().visitAssignment(assignment);
    }
	
    /**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitBaseModel(@NonNull BaseModel baseModel) {
		return getDelegate().visitBaseModel(baseModel);
	}
	
	/**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitBottomPattern(@NonNull BottomPattern bottomPattern) {
    	return getDelegate().visitBottomPattern(bottomPattern);
    }
    
    /**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitCoreDomain(@NonNull CoreDomain coreDomain) {
    	return getDelegate().visitCoreDomain(coreDomain);
    }

    /**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitDomain(@NonNull Domain domain) {
		return getDelegate().visitDomain(domain);
	}

	/**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitCorePattern(@NonNull CorePattern corePattern) {
    	return getDelegate().visitCorePattern(corePattern);
    }

    /**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitEnforcementOperation(@NonNull EnforcementOperation enforcementOperation) {
    	return getDelegate().visitEnforcementOperation(enforcementOperation);
    }

    /**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitFunction(@NonNull Function function) {
		return getDelegate().visitFunction(function);
	}

	/**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitFunctionParameter(@NonNull FunctionParameter functionParameter) {
		return getDelegate().visitFunctionParameter(functionParameter);
	}

	/**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitGuardPattern(@NonNull GuardPattern guardPattern) {
    	return getDelegate().visitGuardPattern(guardPattern);
    }
    
    /**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitPattern(@NonNull Pattern pattern) {
		return getDelegate().visitPattern(pattern);
	}

	/**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitPredicate(@NonNull Predicate predicate) {
		return getDelegate().visitPredicate(predicate);
	}

	/**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitPropertyAssignment(@NonNull PropertyAssignment propertyAssignment) {
    	return getDelegate().visitPropertyAssignment(propertyAssignment);
    }

    /**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitRealizedVariable(@NonNull RealizedVariable realizedVariable) {
    	return getDelegate().visitRealizedVariable(realizedVariable);
    }

    /**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitRule(@NonNull Rule rule) {
		return getDelegate().visitRule(rule);
	}

	/**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitTransformation(@NonNull Transformation transformation) {
		return getDelegate().visitTransformation(transformation);
	}

	/**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitTypedModel(@NonNull TypedModel typedModel) {
		return getDelegate().visitTypedModel(typedModel);
	}

	/**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitUnit(@NonNull Unit unit) {
		return getDelegate().visitUnit(unit);
	}

	/**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitVariableAssignment(@NonNull VariableAssignment variableAssignment) {
    	return getDelegate().visitVariableAssignment(variableAssignment);
    }

    /**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitImperativeModel(ImperativeModel imperativeModel) {
    	return getDelegate().visitImperativeModel(imperativeModel);
	}

	/**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitMapping(Mapping mapping) {
    	return getDelegate().visitMapping(mapping);
	}

	/**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitMappingCall(MappingCall mappingCall) {
    	return getDelegate().visitMappingCall(mappingCall);
	}

	/**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitMappingCallBinding(MappingCallBinding mappingCallBinding) {
    	return getDelegate().visitMappingCallBinding(mappingCallBinding);
	}

    /**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visiting(Visitable visitable) {
    	return getDelegate().visiting(visitable);
	}

	public EvaluationVisitorImpl createNestedLMVisitor() {
		return getDelegate().createNestedLMVisitor();
	}

	public EvaluationVisitorImpl createNestedMMVisitor() {
		return getDelegate().createNestedMMVisitor();
	}

	public EvaluationVisitorImpl createNestedMRVisitor() {
		return getDelegate().createNestedMRVisitor();
	}

}
