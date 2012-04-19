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

import org.eclipse.ocl.examples.xtext.base.cs2pivot.CS2PivotConversion;
import org.eclipse.ocl.examples.xtext.base.cs2pivot.Continuation;
import org.eclipse.ocl.examples.xtext.essentialocl.cs2pivot.EssentialOCLPreOrderVisitor;
import org.eclipse.qvtd.xtext.qvtrelationcst.util.QVTrelationCSVisitor;

public class AbstractQVTrelationPreOrderVisitor extends EssentialOCLPreOrderVisitor implements QVTrelationCSVisitor<Continuation<?>>
{
	//
	//	This file is maintained by copying from AbstractExtendingQVTrelationCSVisitor and changing R to Continuation<?>.
	//
	public AbstractQVTrelationPreOrderVisitor(CS2PivotConversion context) {
		super(context);
	}

	public Continuation<?> visitAbstractDomainCS(org.eclipse.qvtd.xtext.qvtrelationcst.AbstractDomainCS object) {
		return visitModelElementCS(object);
	}

	public Continuation<?> visitAnyElementCS(org.eclipse.qvtd.xtext.qvtrelationcst.AnyElementCS object) {
		return visitExpCS(object);
	}

	public Continuation<?> visitCollectionTemplateCS(org.eclipse.qvtd.xtext.qvtrelationcst.CollectionTemplateCS object) {
		return visitTemplateCS(object);
	}

	public Continuation<?> visitDefaultValueCS(org.eclipse.qvtd.xtext.qvtrelationcst.DefaultValueCS object) {
		return visitModelElementCS(object);
	}

	public Continuation<?> visitDomainCS(org.eclipse.qvtd.xtext.qvtrelationcst.DomainCS object) {
		return visitAbstractDomainCS(object);
	}

	public Continuation<?> visitKeyDeclCS(org.eclipse.qvtd.xtext.qvtrelationcst.KeyDeclCS object) {
		return visitModelElementCS(object);
	}

	public Continuation<?> visitModelDeclCS(org.eclipse.qvtd.xtext.qvtrelationcst.ModelDeclCS object) {
		return visitNamedElementCS(object);
	}

	public Continuation<?> visitObjectTemplateCS(org.eclipse.qvtd.xtext.qvtrelationcst.ObjectTemplateCS object) {
		return visitTemplateCS(object);
	}

	public Continuation<?> visitParamDeclarationCS(org.eclipse.qvtd.xtext.qvtrelationcst.ParamDeclarationCS object) {
		return visitNamedElementCS(object);
	}

	public Continuation<?> visitPrimitiveTypeDomainCS(org.eclipse.qvtd.xtext.qvtrelationcst.PrimitiveTypeDomainCS object) {
		return visitTemplateVariableCS(object);
	}

	public Continuation<?> visitPropertyTemplateCS(org.eclipse.qvtd.xtext.qvtrelationcst.PropertyTemplateCS object) {
		return visitModelElementCS(object);
	}

	public Continuation<?> visitQueryCS(org.eclipse.qvtd.xtext.qvtrelationcst.QueryCS object) {
		return visitModelElementCS(object);
	}

	public Continuation<?> visitRelationCS(org.eclipse.qvtd.xtext.qvtrelationcst.RelationCS object) {
		return visitNamedElementCS(object);
	}

	public Continuation<?> visitTemplateCS(org.eclipse.qvtd.xtext.qvtrelationcst.TemplateCS object) {
		return visitTemplateVariableCS(object);
	}

	public Continuation<?> visitTemplateVariableCS(org.eclipse.qvtd.xtext.qvtrelationcst.TemplateVariableCS object) {
		return visitModelElementCS(object);
	}

	public Continuation<?> visitTopLevelCS(org.eclipse.qvtd.xtext.qvtrelationcst.TopLevelCS object) {
		return visitRootPackageCS(object);
	}

	public Continuation<?> visitTransformationCS(org.eclipse.qvtd.xtext.qvtrelationcst.TransformationCS object) {
		return visitPackageCS(object);
	}

	public Continuation<?> visitUnitCS(org.eclipse.qvtd.xtext.qvtrelationcst.UnitCS object) {
		return visitModelElementCS(object);
	}

	public Continuation<?> visitVarDeclarationCS(org.eclipse.qvtd.xtext.qvtrelationcst.VarDeclarationCS object) {
		return visitModelElementCS(object);
	}

	public Continuation<?> visitWhenCS(org.eclipse.qvtd.xtext.qvtrelationcst.WhenCS object) {
		return visitModelElementCS(object);
	}

	public Continuation<?> visitWhereCS(org.eclipse.qvtd.xtext.qvtrelationcst.WhereCS object) {
		return visitModelElementCS(object);
	}
}