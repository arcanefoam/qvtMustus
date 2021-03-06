/**
 * <copyright>
 *
 * Copyright (c) 2010 E.D.Willink and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     E.D.Willink - initial API and implementation
 *
 * </copyright>
 *
 * $Id: CompleteOCLPreOrderVisitor.java,v 1.11 2011/05/20 15:26:50 ewillink Exp $
 */
package org.eclipse.qvtd.xtext.qvtrelation.cs2pivot;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ocl.examples.pivot.Element;
import org.eclipse.ocl.examples.xtext.base.cs2pivot.CS2PivotConversion;
import org.eclipse.ocl.examples.xtext.essentialocl.cs2pivot.EssentialOCLLeft2RightVisitor;
import org.eclipse.qvtd.xtext.qvtrelationcst.util.QVTrelationCSVisitor;

public class AbstractQVTrelationLeft2RightVisitor extends EssentialOCLLeft2RightVisitor implements QVTrelationCSVisitor<Element>
{
	//
	//	This file is maintained by copying from AbstractExtendingQVTrelationCSVisitor and changing R to Element.
	//
	public AbstractQVTrelationLeft2RightVisitor(@NonNull CS2PivotConversion context) {
		super(context);
	}

	public Element visitAbstractDomainCS(@NonNull org.eclipse.qvtd.xtext.qvtrelationcst.AbstractDomainCS object) {
		return visitModelElementCS(object);
	}

	public Element visitCollectionTemplateCS(@NonNull org.eclipse.qvtd.xtext.qvtrelationcst.CollectionTemplateCS object) {
		return visitTemplateCS(object);
	}

	public Element visitDefaultValueCS(@NonNull org.eclipse.qvtd.xtext.qvtrelationcst.DefaultValueCS object) {
		return visitModelElementCS(object);
	}

	public Element visitDomainCS(@NonNull org.eclipse.qvtd.xtext.qvtrelationcst.DomainCS object) {
		return visitAbstractDomainCS(object);
	}

	public Element visitDomainPatternCS(@NonNull org.eclipse.qvtd.xtext.qvtrelationcst.DomainPatternCS object) {
		return visitModelElementCS(object);
	}

	public Element visitElementTemplateCS(@NonNull org.eclipse.qvtd.xtext.qvtrelationcst.ElementTemplateCS object) {
		return visitTemplateVariableCS(object);
	}

	public Element visitKeyDeclCS(@NonNull org.eclipse.qvtd.xtext.qvtrelationcst.KeyDeclCS object) {
		return visitModelElementCS(object);
	}

	public Element visitModelDeclCS(@NonNull org.eclipse.qvtd.xtext.qvtrelationcst.ModelDeclCS object) {
		return visitNamedElementCS(object);
	}

	public Element visitObjectTemplateCS(@NonNull org.eclipse.qvtd.xtext.qvtrelationcst.ObjectTemplateCS object) {
		return visitTemplateCS(object);
	}

	public Element visitParamDeclarationCS(@NonNull org.eclipse.qvtd.xtext.qvtrelationcst.ParamDeclarationCS object) {
		return visitTypedElementCS(object);
	}

	public Element visitPatternCS(@NonNull org.eclipse.qvtd.xtext.qvtrelationcst.PatternCS object) {
		return visitModelElementCS(object);
	}

	public Element visitPredicateCS(@NonNull org.eclipse.qvtd.xtext.qvtrelationcst.PredicateCS object) {
		return visitModelElementCS(object);
	}

	public Element visitPrimitiveTypeDomainCS(@NonNull org.eclipse.qvtd.xtext.qvtrelationcst.PrimitiveTypeDomainCS object) {
		return visitTemplateVariableCS(object);
	}

	public Element visitPropertyTemplateCS(@NonNull org.eclipse.qvtd.xtext.qvtrelationcst.PropertyTemplateCS object) {
		return visitModelElementCS(object);
	}

	public Element visitQueryCS(@NonNull org.eclipse.qvtd.xtext.qvtrelationcst.QueryCS object) {
		return visitTypedElementCS(object);
	}

	public Element visitRelationCS(@NonNull org.eclipse.qvtd.xtext.qvtrelationcst.RelationCS object) {
		return visitNamedElementCS(object);
	}

	public Element visitTemplateCS(@NonNull org.eclipse.qvtd.xtext.qvtrelationcst.TemplateCS object) {
		return visitTemplateVariableCS(object);
	}

	public Element visitTemplateVariableCS(@NonNull org.eclipse.qvtd.xtext.qvtrelationcst.TemplateVariableCS object) {
		return visitNamedElementCS(object);
	}

	public Element visitTopLevelCS(@NonNull org.eclipse.qvtd.xtext.qvtrelationcst.TopLevelCS object) {
		return visitRootPackageCS(object);
	}

	public Element visitTransformationCS(@NonNull org.eclipse.qvtd.xtext.qvtrelationcst.TransformationCS object) {
		return visitPackageCS(object);
	}

	public Element visitUnitCS(@NonNull org.eclipse.qvtd.xtext.qvtrelationcst.UnitCS object) {
		return visitModelElementCS(object);
	}

	public Element visitVarDeclarationCS(@NonNull org.eclipse.qvtd.xtext.qvtrelationcst.VarDeclarationCS object) {
		return visitModelElementCS(object);
	}

	public Element visitVarDeclarationIdCS(@NonNull org.eclipse.qvtd.xtext.qvtrelationcst.VarDeclarationIdCS object) {
		return visitNamedElementCS(object);
	}
}