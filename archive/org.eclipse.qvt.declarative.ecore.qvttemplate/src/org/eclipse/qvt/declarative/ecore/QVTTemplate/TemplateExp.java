/**
 * <copyright>
 * 
 * Copyright (c) 2007, 2008 E.D.Willink and others.
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
 * $Id: TemplateExp.java,v 1.2 2008/12/31 17:43:44 ewillink Exp $
 */
package org.eclipse.qvt.declarative.ecore.QVTTemplate;

import org.eclipse.ocl.ecore.LiteralExp;
import org.eclipse.ocl.ecore.OCLExpression;
import org.eclipse.ocl.ecore.Variable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Template Exp</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.qvt.declarative.ecore.QVTTemplate.TemplateExp#getBindsTo <em>Binds To</em>}</li>
 *   <li>{@link org.eclipse.qvt.declarative.ecore.QVTTemplate.TemplateExp#getWhere <em>Where</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.qvt.declarative.ecore.QVTTemplate.QVTTemplatePackage#getTemplateExp()
 * @model abstract="true"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore constraints='DomainExists\r\nPatternExists\r\nVariableIsBoundByPattern\r\nVariableTypeIsDeclaredByDomain\r\nWhereIsBoolean\r\n'"
 * @generated
 */
public interface TemplateExp extends LiteralExp {
	/**
	 * Returns the value of the '<em><b>Binds To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Binds To</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Binds To</em>' reference.
	 * @see #setBindsTo(Variable)
	 * @see org.eclipse.qvt.declarative.ecore.QVTTemplate.QVTTemplatePackage#getTemplateExp_BindsTo()
	 * @model
	 * @generated
	 */
	Variable getBindsTo();

	/**
	 * Sets the value of the '{@link org.eclipse.qvt.declarative.ecore.QVTTemplate.TemplateExp#getBindsTo <em>Binds To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Binds To</em>' reference.
	 * @see #getBindsTo()
	 * @generated
	 */
	void setBindsTo(Variable value);

	/**
	 * Returns the value of the '<em><b>Where</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Where</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Where</em>' containment reference.
	 * @see #setWhere(OCLExpression)
	 * @see org.eclipse.qvt.declarative.ecore.QVTTemplate.QVTTemplatePackage#getTemplateExp_Where()
	 * @model containment="true"
	 *        annotation="http://schema.omg.org/spec/MOF/2.0/emof.xml#Property.oppositeRoleName body='owner'"
	 * @generated
	 */
	OCLExpression getWhere();

	/**
	 * Sets the value of the '{@link org.eclipse.qvt.declarative.ecore.QVTTemplate.TemplateExp#getWhere <em>Where</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Where</em>' containment reference.
	 * @see #getWhere()
	 * @generated
	 */
	void setWhere(OCLExpression value);

} // TemplateExp
