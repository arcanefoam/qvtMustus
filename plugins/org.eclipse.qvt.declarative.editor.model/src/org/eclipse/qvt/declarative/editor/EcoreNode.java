/**
 * <copyright>
 * 
 * Copyright (c) 2008 E.D.Willink and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * E.D.Willink - initial API and implementation
 * 
 * </copyright>
 *
 * $Id: EcoreNode.java,v 1.1 2008/08/08 16:39:54 ewillink Exp $
 */
package org.eclipse.qvt.declarative.editor;

import org.eclipse.emf.ecore.EClassifier;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ecore Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.qvt.declarative.editor.EcoreNode#getElement <em>Element</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.qvt.declarative.editor.EditorPackage#getEcoreNode()
 * @model
 * @generated
 */
public interface EcoreNode extends Node {
	/**
	 * Returns the value of the '<em><b>Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element</em>' reference.
	 * @see #setElement(EClassifier)
	 * @see org.eclipse.qvt.declarative.editor.EditorPackage#getEcoreNode_Element()
	 * @model required="true"
	 * @generated
	 */
	EClassifier getElement();

	/**
	 * Sets the value of the '{@link org.eclipse.qvt.declarative.editor.EcoreNode#getElement <em>Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Element</em>' reference.
	 * @see #getElement()
	 * @generated
	 */
	void setElement(EClassifier value);

} // EcoreNode