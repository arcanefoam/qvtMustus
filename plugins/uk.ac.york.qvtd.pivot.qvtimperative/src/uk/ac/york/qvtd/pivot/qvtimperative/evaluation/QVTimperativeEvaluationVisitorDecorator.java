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
import org.eclipse.ocl.examples.domain.elements.DomainExpression;
import org.eclipse.ocl.examples.domain.elements.DomainStandardLibrary;
import org.eclipse.ocl.examples.domain.elements.DomainType;
import org.eclipse.ocl.examples.domain.evaluation.DomainLogger;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.domain.types.IdResolver;
import org.eclipse.ocl.examples.domain.values.impl.InvalidValueException;
import org.eclipse.ocl.examples.domain.values.util.ValuesUtil;
import org.eclipse.ocl.examples.pivot.Environment;
import org.eclipse.ocl.examples.pivot.ExpressionInOCL;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitor;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitorDecorator;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitorImpl;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
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
	
	
	public QVTimperativeEvaluationVisitorDecorator(@NonNull QVTimperativeEvaluationVisitor decorated) {
		super((EvaluationVisitor) decorated);
	}
	
	/**
     * Delegates to my decorated visitor.
     */
	public @NonNull EvaluationVisitor createNestedEvaluator() {
        return getDelegate().createNestedEvaluator();
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
	public void setUndecoratedVisitor(QVTimperativeEvaluationVisitor evaluationVisitor) {
        getDelegate().setUndecoratedVisitor(evaluationVisitor);
	}
	
	/**
     * Delegates to my decorated visitor.
     */
	@Nullable public Object evaluate(@NonNull DomainExpression body) {
		
		return getDelegate().evaluate(body);
	}

	/**
     * Delegates to my decorated visitor.
     */
	@Nullable public Object evaluate(@NonNull ExpressionInOCL expressionInOCL) {
		
		return getDelegate().evaluate(expressionInOCL);
	}

	/**
     * Delegates to my decorated visitor.
     */
	@NonNull public EvaluationVisitor getEvaluator() {
		
		return getDelegate().getEvaluator();
	}

	/**
     * Delegates to my decorated visitor.
     */
	@NonNull public MetaModelManager getMetaModelManager() {
		
		return getDelegate().getMetaModelManager();
	}

	/**
     * Delegates to my decorated visitor.
     */
	@NonNull public DomainStandardLibrary getStandardLibrary() {
		
		return getDelegate().getStandardLibrary();
	}

	/**
     * Delegates to my decorated visitor.
     */
	@NonNull public IdResolver getIdResolver() {

		return getDelegate().getIdResolver();
	}

	/**
     * Delegates to my decorated visitor.
     */
	@Nullable public DomainLogger getLogger() {
		
		return getDelegate().getLogger();
	}

	/**
     * Delegates to my decorated visitor.
     */
	@NonNull public DomainType getStaticTypeOf(@Nullable Object value) {
		
		return getDelegate().getStaticTypeOf(value);
	}

	/**
     * Delegates to my decorated visitor.
     */
	@NonNull public DomainType getStaticTypeOf(@Nullable Object value,
			@NonNull Object... values) {
		
		return getDelegate().getStaticTypeOf(value, values);
	}

	/**
     * Delegates to my decorated visitor.
     */
	@NonNull public DomainType getStaticTypeOf(@Nullable Object value,
			@NonNull Iterable<?> values) {
		
		return getDelegate().getStaticTypeOf(value, values);
	}

	/**
     * Delegates to my decorated visitor.
     */
	public boolean isCanceled() {
		
		return getDelegate().isCanceled();
	}

	/**
     * Delegates to my decorated visitor.
     */
	public void setCanceled(boolean isCanceled) {
		
		getDelegate().setCanceled(isCanceled);
	}

	/**
     * Delegates to my decorated visitor.
     */
	public void setLogger(@Nullable DomainLogger loger) {
		
		getDelegate().setLogger(loger);
	}
	
	@Override
	public Object safeVisit(@Nullable Visitable v) {
		return ((EvaluationVisitorImpl)getDelegate()).safeVisit(v);
	}
	
	/**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitAssignment(@NonNull Assignment assignment) {
		return ((QVTimperativeEvaluationVisitor)getDelegate()).visitAssignment(assignment);
    }
	
    /**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitBaseModel(@NonNull BaseModel baseModel) {
		return ((QVTimperativeEvaluationVisitor)getDelegate()).visitBaseModel(baseModel);
	}
	
	/**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitBottomPattern(@NonNull BottomPattern bottomPattern) {
    	return ((QVTimperativeEvaluationVisitor)getDelegate()).visitBottomPattern(bottomPattern);
    }
    
    /**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitCoreDomain(@NonNull CoreDomain coreDomain) {
    	return ((QVTimperativeEvaluationVisitor)getDelegate()).visitCoreDomain(coreDomain);
    }

    /**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitDomain(@NonNull Domain domain) {
		return ((QVTimperativeEvaluationVisitor)getDelegate()).visitDomain(domain);
	}

	/**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitCorePattern(@NonNull CorePattern corePattern) {
    	return ((QVTimperativeEvaluationVisitor)getDelegate()).visitCorePattern(corePattern);
    }

    /**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitEnforcementOperation(@NonNull EnforcementOperation enforcementOperation) {
    	return ((QVTimperativeEvaluationVisitor)getDelegate()).visitEnforcementOperation(enforcementOperation);
    }

    /**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitFunction(@NonNull Function function) {
		return ((QVTimperativeEvaluationVisitor)getDelegate()).visitFunction(function);
	}

	/**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitFunctionParameter(@NonNull FunctionParameter functionParameter) {
		return ((QVTimperativeEvaluationVisitor)getDelegate()).visitFunctionParameter(functionParameter);
	}

	/**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitGuardPattern(@NonNull GuardPattern guardPattern) {
    	return ((QVTimperativeEvaluationVisitor)getDelegate()).visitGuardPattern(guardPattern);
    }
    
    /**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitPattern(@NonNull Pattern pattern) {
		return ((QVTimperativeEvaluationVisitor)getDelegate()).visitPattern(pattern);
	}

	/**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitPredicate(@NonNull Predicate predicate) {
		return ((QVTimperativeEvaluationVisitor)getDelegate()).visitPredicate(predicate);
	}

	/**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitPropertyAssignment(@NonNull PropertyAssignment propertyAssignment) {
    	return ((QVTimperativeEvaluationVisitor)getDelegate()).visitPropertyAssignment(propertyAssignment);
    }

    /**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitRealizedVariable(@NonNull RealizedVariable realizedVariable) {
    	return ((QVTimperativeEvaluationVisitor)getDelegate()).visitRealizedVariable(realizedVariable);
    }

    /**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitRule(@NonNull Rule rule) {
		return ((QVTimperativeEvaluationVisitor)getDelegate()).visitRule(rule);
	}

	/**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitTransformation(@NonNull Transformation transformation) {
		return ((QVTimperativeEvaluationVisitor)getDelegate()).visitTransformation(transformation);
	}

	/**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitTypedModel(@NonNull TypedModel typedModel) {
		return ((QVTimperativeEvaluationVisitor)getDelegate()).visitTypedModel(typedModel);
	}

	/**
     * Delegates to my decorated visitor.
     */
	public @Nullable Object visitUnit(@NonNull Unit unit) {
		return ((QVTimperativeEvaluationVisitor)getDelegate()).visitUnit(unit);
	}

	/**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitVariableAssignment(@NonNull VariableAssignment variableAssignment) {
    	return ((QVTimperativeEvaluationVisitor)getDelegate()).visitVariableAssignment(variableAssignment);
    }

    /**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitImperativeModel(ImperativeModel imperativeModel) {
    	return ((QVTimperativeEvaluationVisitor)getDelegate()).visitImperativeModel(imperativeModel);
	}

	/**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitMapping(Mapping mapping) {
    	return ((QVTimperativeEvaluationVisitor)getDelegate()).visitMapping(mapping);
	}

	/**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitMappingCall(MappingCall mappingCall) {
    	return ((QVTimperativeEvaluationVisitor)getDelegate()).visitMappingCall(mappingCall);
	}

	/**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visitMappingCallBinding(MappingCallBinding mappingCallBinding) {
    	return ((QVTimperativeEvaluationVisitor)getDelegate()).visitMappingCallBinding(mappingCallBinding);
	}

    /**
     * Delegates to my decorated visitor.
     */
    public @Nullable Object visiting(Visitable visitable) {
    	return getDelegate().visiting(visitable);
	}
    
    public abstract EvaluationVisitorImpl createNestedLMVisitor();
    
    public abstract EvaluationVisitorImpl createNestedMMVisitor();
    
    public abstract EvaluationVisitorImpl createNestedMRVisitor();

}
