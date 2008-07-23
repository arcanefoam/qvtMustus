/*******************************************************************************
 * Copyright (c) 2007,2008 E.D.Willink and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     E.D.Willink - initial API and implementation
 *******************************************************************************/
package org.eclipse.qvt.declarative.parser.qvt.environment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ocl.LookupException;
import org.eclipse.ocl.cst.CSTNode;
import org.eclipse.ocl.ecore.CallOperationAction;
import org.eclipse.ocl.ecore.CollectionType;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.OCLExpression;
import org.eclipse.ocl.ecore.OperationCallExp;
import org.eclipse.ocl.ecore.SendSignalAction;
import org.eclipse.ocl.ecore.TupleType;
import org.eclipse.ocl.ecore.Variable;
import org.eclipse.ocl.ecore.internal.EcoreForeignMethods;
import org.eclipse.ocl.expressions.CollectionKind;
import org.eclipse.ocl.utilities.TypedElement;
import org.eclipse.ocl.utilities.UMLReflection;
import org.eclipse.qvt.declarative.ecore.QVTBase.Function;
import org.eclipse.qvt.declarative.ecore.QVTBase.FunctionParameter;
import org.eclipse.qvt.declarative.ecore.QVTBase.Transformation;
import org.eclipse.qvt.declarative.parser.environment.CSTEnvironment;
import org.eclipse.qvt.declarative.parser.plugin.QVTParserPlugin;
import org.eclipse.qvt.declarative.parser.utils.OCLUtils;

/**
 * QVTEnvironment revises the EcoreEnvironment behaviour to support operation in an
 * a deep environment hierarchy, in which lookups are presumed to be resolved
 * by an parent environment and the results may be ambiguous. 
 * <p>
 * Look-ups are performed by default using the parent implementation. A failed
 * parent look-up is then delegated to the derived OCL look-up.
 * <p>
 * This provides a default semantics that may often be useful. It is expected
 * that derived environments will re-implement whenever another semantics is needed.
 * <br>
 * @param <E> The derived IQVTEnvironment from which all environments derive
 * @param <P> The derived E of the parent environment
 */
public abstract class QVTEnvironment<E extends IQVTEnvironment, P extends E> extends CSTEnvironment<E,P> implements IQVTEnvironment
{
	protected QVTFormattingHelper formatter;

	protected QVTEnvironment(EPackage.Registry reg) {
		super(reg);
	}

	protected QVTEnvironment(EPackage.Registry reg, Resource resource) {
		super(reg, resource);
	}
	
	protected QVTEnvironment(P parent, CSTNode cstNode) {
		super(parent, cstNode);
	}
	
	@Override protected void addedVariable(String name, org.eclipse.ocl.expressions.Variable<EClassifier, EParameter> variable, boolean isExplicit) {
		if (variable == null)
			return;
		if (!(variable instanceof Variable))
			QVTParserPlugin.logError("non-derived Variable in " + getClass().getName() + ".addedVariable", null);
	}

	public boolean checkFeatureCompatibility(CSTNode cstNode, EClassifier featureType, OCLExpression oclExpression) {
		if (featureType == null)
			return false;
		if (OCLUtils.isUnresolved(featureType))
			return false;
		EClassifier expressionType = getUMLReflection().getOCLType(oclExpression.getType());
		if (expressionType == null)
			return false;
		if (OCLUtils.isUnresolved(expressionType))
			return false;
		if (featureType == expressionType)
			return true;
		else if (featureType instanceof EEnum) {
			String enumLiteralText = oclExpression.toString();
			if (enumLiteralText.length() >= 2)
				enumLiteralText = enumLiteralText.substring(1, enumLiteralText.length()-1);
			EEnumLiteral enumLiteral = ((EEnum)featureType).getEEnumLiteral(enumLiteralText);
			if (enumLiteral != null)
				return true;
			String message = "Incompatible enum '" + formatType(featureType) + "' for match with " +  enumLiteralText;
			analyzerError(message, "FeatureCompatibility", cstNode);
		}
		else if (featureType instanceof CollectionType) {
			CollectionKind featureKind = ((CollectionType) featureType).getKind();
			EClassifier featureElementType = ((CollectionType) featureType).getElementType();
			CollectionKind expressionKind = null;
			EClassifier expressionElementType = expressionType;
			if (expressionType instanceof CollectionType) {
				expressionKind = ((CollectionType) expressionType).getKind();
				expressionElementType = ((CollectionType) expressionType).getElementType();
			}
			if (expressionElementType == null)
				return false;
			if (OCLUtils.isUnresolved(expressionElementType))
				return false;
			if (!(expressionElementType instanceof EClass)) {
				String message = "Incompatible class '" + formatType(featureElementType) + "' for match with '" +  formatType(expressionElementType) + "'";
				analyzerError(message, "FeatureCompatibility", cstNode);
			}
			else if (!((EClass) featureElementType).isSuperTypeOf((EClass) expressionElementType)) {
				String message = "Incompatible class '" + formatType(featureElementType) + "' for match with '" +  formatType(expressionElementType) + "'";
				analyzerError(message, "FeatureCompatibility", cstNode);
			}
//			else if ((expressionKind != null) && QVTrUtils.isOrdered(featureKind) && !QVTrUtils.isOrdered(expressionKind))
//				analyzerWarning(cstNode, null, "Ordered collection '" + formatType(featureType) + "' for match with '" +  formatType(expressionType) + "'");
			else if ((expressionKind != null) && OCLUtils.isUnique(featureKind) && !OCLUtils.isUnique(expressionKind)) {
				String message = "Unique collection '" + formatType(featureType) + "' for match with '" +  formatType(expressionType) + "'";
				analyzerWarning(message, "FeatureCompatibility", cstNode);
			}
			else
				return true;
		}
		else if (featureType instanceof EDataType) {
			String message = "Incompatible data type '" + formatType(featureType) + "' for match with '" +  formatType(expressionType) + "'";
			analyzerError(message, "FeatureCompatibility", cstNode);
		}
		else if (featureType instanceof TupleType) {
			String message = "Incompatible tuple type '" + formatType(featureType) + "' for match with '" +  formatType(expressionType) + "'";
			analyzerError(message, "FeatureCompatibility", cstNode);
		}
		else if (featureType instanceof EClass) {
			if (!(expressionType instanceof EClass)) {
				String message = "Incompatible data type '" + formatType(featureType) + "' for match with '" +  formatType(expressionType) + "'";
				analyzerError(message, "FeatureCompatibility", cstNode);
			}
			else if (!((EClass) featureType).isSuperTypeOf((EClass) expressionType)) {
				String message = "Incompatible class '" + formatType(featureType) + "' for match with '" +  formatType(expressionType) + "'";
				analyzerError(message, "FeatureCompatibility", cstNode);
			}
			else
				return true;
		}
		else {
			String message = "Unsupported feature type '" + formatType(featureType) + "'";
			analyzerError(message, "FeatureCompatibility", cstNode);
		}
		return false;
	}

	protected abstract QVTFormattingHelper createFormatter();

	@Override
	protected QVTTypeResolverImpl createTypeResolver(Resource resource) {
		return new QVTTypeResolverImpl(this, resource);
	}

	public List<Function> findMatchingQueries(Transformation transformation, String queryName, List<OCLExpression> args) {
		List<Function> queries = new ArrayList<Function>();
		int iMax = args.size();
		for (EOperation eOperation : transformation.getEAllOperations()) {
			if (!(eOperation instanceof Function))
				continue;
			if (!queryName.equals(eOperation.getName()))
				continue;
			List<EParameter> params = eOperation.getEParameters();
			if (params.size() != iMax)
				continue;
			int i = 0;
			for ( ; i < iMax; i++) {
				OCLExpression arg = args.get(i);
				EParameter param = params.get(i);
				EClassifier paramType = param.getEType();
				if (!isAssignableTo(paramType, arg)) 
					break; 
			}
			if (i < iMax)
				continue;
			queries.add((Function) eOperation); 
		}
		return queries;
	}

	public Function findMatchingQuery(Transformation transformation, String name, List<FunctionParameter> parameters) {
		Function match = null;
		int iMax = parameters.size();
		for (EOperation candidateOperation : transformation.getEOperations()) {
			if (!(candidateOperation instanceof Function))
				continue;
			if (!name.equals(candidateOperation.getName()))
				continue;
			List<EParameter> params = candidateOperation.getEParameters();
			if (params.size() != iMax)
				continue;
			int i = 0;
			for ( ; i < iMax; i++) {
				EParameter candidateParameter = candidateOperation.getEParameters().get(i);
				EParameter queryParameter = parameters.get(i);
				EClassifier candidateType = candidateParameter.getEType();
				EClassifier queryType = queryParameter.getEType();
				if (candidateType != queryType) 
					break; 
			}
			if (i < iMax)
				continue;
			if (match != null)
				QVTParserPlugin.logError("Duplicate match for query '" + formatName(match) + "'", null);
			match = (Function) candidateOperation;
		}
		return match;
	}
	@Override
	public QVTFormattingHelper getFormatter() {
		if (formatter == null) {
			P parent = getParentEnvironment();
			if (parent != null)
				formatter = parent.getFormatter();
			if (formatter == null)
				formatter = createFormatter();
		}
		return formatter;
	}

	public String getModelName(EObject object) {
		P parent = getParentEnvironment();
		if (parent != null)
			return parent.getModelName(object);
		else
			return null;
	}	
	
	/**
	 * Add any classifiers that can be located in ePackage corresponding to
	 * names to resolutions.
	 * <p>
	 * 
	 * 
	 * @param resolutions Set to receive any result values
	 * @param currentPackage package within which to resolve names
	 * @param names list of package-names and classifier-name to find
	 * @param hasScope true for match within ePackage, false for nested packages too
	 */
	protected boolean getPackagedClassifiers(List<EClassifier> resolutions, EPackage currentPackage, List<String> names, boolean hasScope) {
		int namesSize = names.size();
		if (namesSize <= 0)
			return false;									// Need at least classifierName
		else if (namesSize == 1) {							// Just a classifier-name
			String classifierName = names.get(0);
			EClassifier eClassifier = getPackagedClassifier(currentPackage, classifierName);
			if (eClassifier != null) {
				if (!resolutions.contains(eClassifier))
					resolutions.add(eClassifier);
				return true;								// Found explicit classifierName
			}
			if (hasScope)									// Got scope so cannot try nested packages
				return false;
		}
		else {												// Package-qualified classifier-name
			String packageName = names.get(0);
			for (EPackage searchPackage = currentPackage; searchPackage != null; searchPackage = searchPackage.getESuperPackage()) {
				EPackage ePackage = EcoreForeignMethods.getESubpackage(searchPackage, packageName);
				if (ePackage != null) {
					if (getPackagedClassifiers(resolutions, ePackage, names.subList(1, namesSize), true))
						return true;						// Found first packageName
				}
				if (hasScope)								// Got scope so cannot try surrounding packages
					return false;
				if (EcoreForeignMethods.isNamed(packageName, currentPackage)) {
					if (getPackagedClassifiers(resolutions, currentPackage, names.subList(1, namesSize), true))
						return true;						// Found first packageName					
				}					
			}
		}
		boolean found = false;
		for (EPackage ePackage : currentPackage.getESubpackages())
			if (getPackagedClassifiers(resolutions, ePackage, names, false))
				found = true;
		return found;
	}

	protected EClassifier getPackagedClassifier(EPackage currentPackage, String classifierName) {
		if ((currentPackage instanceof EClassifier) && classifierName.equals(currentPackage.getName()))
			return (EClassifier) currentPackage;		// Use of Transformation which is EPackage and EClass
		else
			return EcoreForeignMethods.getEClassifier(currentPackage, classifierName);
	}

	public Transformation getTransformation() {
		return getParentEnvironment().getTransformation();
	}

	@Override public QVTTypeResolverImpl getTypeResolver() {
		return (QVTTypeResolverImpl) super.getTypeResolver();
	}

	@Override
	public UMLReflection<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint> getUMLReflection() {
		return QVTReflectionImpl.INSTANCE;
	}

	public boolean isAssignableTo(EClassifier featureType, OCLExpression oclExpression) {
		if (featureType == null)
			return false;
		if (OCLUtils.isUnresolved(featureType))
			return false;
		EClassifier expressionType = getUMLReflection().getOCLType(oclExpression.getType());
		if (expressionType == null)
			return false;
		if (OCLUtils.isUnresolved(expressionType))
			return false;
		if (featureType == expressionType)
			return true;
		else if (featureType instanceof EEnum) {
			String enumLiteralText = oclExpression.toString();
			if (enumLiteralText.length() >= 2)
				enumLiteralText = enumLiteralText.substring(1, enumLiteralText.length()-1);
			EEnumLiteral enumLiteral = ((EEnum)featureType).getEEnumLiteral(enumLiteralText);
			if (enumLiteral != null)
				return true;
		}
		else if (featureType instanceof CollectionType) {
			CollectionKind featureKind = ((CollectionType) featureType).getKind();
			EClassifier featureElementType = ((CollectionType) featureType).getElementType();
			CollectionKind expressionKind = null;
			EClassifier expressionElementType = expressionType;
			if (expressionType instanceof CollectionType) {
				expressionKind = ((CollectionType) expressionType).getKind();
				expressionElementType = ((CollectionType) expressionType).getElementType();
			}
			if (expressionElementType == null)
				return false;
			if (OCLUtils.isUnresolved(expressionElementType))
				return false;
			if (!(expressionElementType instanceof EClass))
				;
			else if (!((EClass) featureElementType).isSuperTypeOf((EClass) expressionElementType))
				;
//			else if ((expressionKind != null) && QVTrUtils.isOrdered(featureKind) && !QVTrUtils.isOrdered(expressionKind))
//				;
			else if ((expressionKind != null) && OCLUtils.isUnique(featureKind) && !OCLUtils.isUnique(expressionKind))
				;
			else
				return true;
		}
		else if (featureType instanceof EDataType) {
			;
		}
		else if (featureType instanceof TupleType) {
			;
		}
		else if (featureType instanceof EClass) {
			if (!(expressionType instanceof EClass))
				;
			else if (!((EClass) featureType).isSuperTypeOf((EClass) expressionType))
				;
			else
				return true;
		}
		else
			;
		return false;
	}

	public Transformation lookupImportedTransformation(String name) {
		P parent = getParentEnvironment();
		if (parent != null)
			return parent.lookupImportedTransformation(name);
		else
			return null;
	}

	@Override
	public Variable lookupImplicitSourceForOperation(String name, List<? extends TypedElement<EClassifier>> params) {
		P parent = getParentEnvironment();
		if (parent != null)
			return parent.lookupImplicitSourceForOperation(name, params);
		else
			return lookupOCLImplicitSourceForOperation(name, params);
	}

	@Override
	public Variable lookupImplicitSourceForProperty(String name) {
		P parent = getParentEnvironment();
		if (parent != null)
			return parent.lookupImplicitSourceForProperty(name);
		else
			return lookupOCLImplicitSourceForProperty(name);
	}
	
	@Override protected void removedVariable(String name, org.eclipse.ocl.expressions.Variable<EClassifier, EParameter> variable, boolean isExplicit) {
	}

	/**
	 * QVT classifier lookups must occur with respect to multiple context packages.
	 * The inherited behaviour must therefore be re-implemented by derived classes.
	 */
	@Override public EClassifier tryLookupClassifier(List<String> names) throws LookupException {
//		EClassifier result = super.lookupClassifier(names);
//		if (result != null)
//			return result;
		P parent = getParentEnvironment();
		if (parent != null)
			return parent.tryLookupClassifier(names);
		return null;
	}
	
	public EClassifier tryLookupClassifier(Collection<EPackage> contextPackages, List<String> names) throws LookupException {
		if (contextPackages.isEmpty())
			return null;
		List<EClassifier> resolutions = new ArrayList<EClassifier>(2);
		for (EPackage contextPackage : contextPackages)
			getPackagedClassifiers(resolutions, contextPackage, names, false);
		if (resolutions.size() <= 0)
			return null;
		if (resolutions.size() == 1)
			return resolutions.get(0);
		throw new LookupException("Ambiguous name '" + formatPath(names) + "'", resolutions);
	}
	
	public Transformation tryLookupTransformation(List<String> pathName) throws LookupException {
		P parent = getParentEnvironment();
		if (parent != null)
			return parent.tryLookupTransformation(pathName);
		else
			return null;
	}

	public Variable tryLookupVariable(String name) throws LookupException {
		P parent = getParentEnvironment();
		if (parent != null)
			return parent.tryLookupVariable(name);
		else
			return null;
	}

	@Override
	public void validatorError(String problemMessage, String problemContext, Object problemObject) {
		// FIXME Workaround for ???
		if ((problemObject instanceof OperationCallExp) && (((OperationCallExp)problemObject).getReferredOperation() instanceof Function) && problemMessage.startsWith("Null source in operation call "))
			return;
		super.validatorError(problemMessage, problemContext, problemObject);
	}
}
