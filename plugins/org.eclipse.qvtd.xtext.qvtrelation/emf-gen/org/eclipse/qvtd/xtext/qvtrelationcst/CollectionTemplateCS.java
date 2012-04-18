/**
 * <copyright>
 *
 * Copyright (c) 2012 E.D.Willink and others.
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
 * $Id$
 */
package org.eclipse.qvtd.xtext.qvtrelationcst;

import org.eclipse.emf.common.util.EList;

import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.ExpCS;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Collection Template CS</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.qvtd.xtext.qvtrelationcst.CollectionTemplateCS#getMemberIdentifier <em>Member Identifier</em>}</li>
 *   <li>{@link org.eclipse.qvtd.xtext.qvtrelationcst.CollectionTemplateCS#getRestIdentifier <em>Rest Identifier</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.qvtd.xtext.qvtrelationcst.QVTrelationCSTPackage#getCollectionTemplateCS()
 * @model
 * @generated
 */
public interface CollectionTemplateCS extends TemplateCS {
	/**
	 * Returns the value of the '<em><b>Member Identifier</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.ExpCS}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Member Identifier</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Member Identifier</em>' containment reference list.
	 * @see org.eclipse.qvtd.xtext.qvtrelationcst.QVTrelationCSTPackage#getCollectionTemplateCS_MemberIdentifier()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<ExpCS> getMemberIdentifier();

	/**
	 * Returns the value of the '<em><b>Rest Identifier</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rest Identifier</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rest Identifier</em>' containment reference.
	 * @see #setRestIdentifier(ExpCS)
	 * @see org.eclipse.qvtd.xtext.qvtrelationcst.QVTrelationCSTPackage#getCollectionTemplateCS_RestIdentifier()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ExpCS getRestIdentifier();

	/**
	 * Sets the value of the '{@link org.eclipse.qvtd.xtext.qvtrelationcst.CollectionTemplateCS#getRestIdentifier <em>Rest Identifier</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rest Identifier</em>' containment reference.
	 * @see #getRestIdentifier()
	 * @generated
	 */
	void setRestIdentifier(ExpCS value);

} // CollectionTemplateCS