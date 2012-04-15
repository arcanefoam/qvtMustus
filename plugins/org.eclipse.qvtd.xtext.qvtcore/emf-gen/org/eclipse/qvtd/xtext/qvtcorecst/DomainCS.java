/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.qvtd.xtext.qvtcorecst;

import org.eclipse.qvtd.pivot.qvtcore.CoreDomain;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Domain CS</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.qvtd.xtext.qvtcorecst.DomainCS#isCheck <em>Check</em>}</li>
 *   <li>{@link org.eclipse.qvtd.xtext.qvtcorecst.DomainCS#isEnforce <em>Enforce</em>}</li>
 *   <li>{@link org.eclipse.qvtd.xtext.qvtcorecst.DomainCS#getDirection <em>Direction</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.qvtd.xtext.qvtcorecst.QVTcoreCSTPackage#getDomainCS()
 * @model
 * @generated
 */
public interface DomainCS extends AreaCS {
	/**
	 * Returns the value of the '<em><b>Check</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Check</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Check</em>' attribute.
	 * @see #setCheck(boolean)
	 * @see org.eclipse.qvtd.xtext.qvtcorecst.QVTcoreCSTPackage#getDomainCS_Check()
	 * @model
	 * @generated
	 */
	boolean isCheck();

	/**
	 * Sets the value of the '{@link org.eclipse.qvtd.xtext.qvtcorecst.DomainCS#isCheck <em>Check</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Check</em>' attribute.
	 * @see #isCheck()
	 * @generated
	 */
	void setCheck(boolean value);

	/**
	 * Returns the value of the '<em><b>Enforce</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enforce</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enforce</em>' attribute.
	 * @see #setEnforce(boolean)
	 * @see org.eclipse.qvtd.xtext.qvtcorecst.QVTcoreCSTPackage#getDomainCS_Enforce()
	 * @model
	 * @generated
	 */
	boolean isEnforce();

	/**
	 * Sets the value of the '{@link org.eclipse.qvtd.xtext.qvtcorecst.DomainCS#isEnforce <em>Enforce</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enforce</em>' attribute.
	 * @see #isEnforce()
	 * @generated
	 */
	void setEnforce(boolean value);

	/**
	 * Returns the value of the '<em><b>Direction</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Direction</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Direction</em>' reference.
	 * @see #setDirection(CoreDomain)
	 * @see org.eclipse.qvtd.xtext.qvtcorecst.QVTcoreCSTPackage#getDomainCS_Direction()
	 * @model
	 * @generated
	 */
	CoreDomain getDirection();

	/**
	 * Sets the value of the '{@link org.eclipse.qvtd.xtext.qvtcorecst.DomainCS#getDirection <em>Direction</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Direction</em>' reference.
	 * @see #getDirection()
	 * @generated
	 */
	void setDirection(CoreDomain value);

} // DomainCS
