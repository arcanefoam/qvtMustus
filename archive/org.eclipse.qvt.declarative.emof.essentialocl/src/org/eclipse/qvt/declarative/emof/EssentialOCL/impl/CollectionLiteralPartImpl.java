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
 * $Id: CollectionLiteralPartImpl.java,v 1.2 2009/11/15 08:17:48 ewillink Exp $
 */
package org.eclipse.qvt.declarative.emof.EssentialOCL.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

import org.eclipse.qvt.declarative.emof.EMOF.impl.TypedElementImpl;

import org.eclipse.qvt.declarative.emof.EssentialOCL.CollectionLiteralExp;
import org.eclipse.qvt.declarative.emof.EssentialOCL.CollectionLiteralPart;
import org.eclipse.qvt.declarative.emof.EssentialOCL.EssentialOCLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Collection Literal Part</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.qvt.declarative.emof.EssentialOCL.impl.CollectionLiteralPartImpl#getCollectionLiteralExp <em>Collection Literal Exp</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class CollectionLiteralPartImpl extends TypedElementImpl implements CollectionLiteralPart {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CollectionLiteralPartImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EssentialOCLPackage.Literals.COLLECTION_LITERAL_PART;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CollectionLiteralExp getCollectionLiteralExp() {
		if (eContainerFeatureID() != EssentialOCLPackage.COLLECTION_LITERAL_PART__COLLECTION_LITERAL_EXP) return null;
		return (CollectionLiteralExp)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCollectionLiteralExp(CollectionLiteralExp newCollectionLiteralExp, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newCollectionLiteralExp, EssentialOCLPackage.COLLECTION_LITERAL_PART__COLLECTION_LITERAL_EXP, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCollectionLiteralExp(CollectionLiteralExp newCollectionLiteralExp) {
		if (newCollectionLiteralExp != eInternalContainer() || (eContainerFeatureID() != EssentialOCLPackage.COLLECTION_LITERAL_PART__COLLECTION_LITERAL_EXP && newCollectionLiteralExp != null)) {
			if (EcoreUtil.isAncestor(this, newCollectionLiteralExp))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newCollectionLiteralExp != null)
				msgs = ((InternalEObject)newCollectionLiteralExp).eInverseAdd(this, EssentialOCLPackage.COLLECTION_LITERAL_EXP__PART, CollectionLiteralExp.class, msgs);
			msgs = basicSetCollectionLiteralExp(newCollectionLiteralExp, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EssentialOCLPackage.COLLECTION_LITERAL_PART__COLLECTION_LITERAL_EXP, newCollectionLiteralExp, newCollectionLiteralExp));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EssentialOCLPackage.COLLECTION_LITERAL_PART__COLLECTION_LITERAL_EXP:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetCollectionLiteralExp((CollectionLiteralExp)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EssentialOCLPackage.COLLECTION_LITERAL_PART__COLLECTION_LITERAL_EXP:
				return basicSetCollectionLiteralExp(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case EssentialOCLPackage.COLLECTION_LITERAL_PART__COLLECTION_LITERAL_EXP:
				return eInternalContainer().eInverseRemove(this, EssentialOCLPackage.COLLECTION_LITERAL_EXP__PART, CollectionLiteralExp.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EssentialOCLPackage.COLLECTION_LITERAL_PART__COLLECTION_LITERAL_EXP:
				return getCollectionLiteralExp();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case EssentialOCLPackage.COLLECTION_LITERAL_PART__COLLECTION_LITERAL_EXP:
				setCollectionLiteralExp((CollectionLiteralExp)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case EssentialOCLPackage.COLLECTION_LITERAL_PART__COLLECTION_LITERAL_EXP:
				setCollectionLiteralExp((CollectionLiteralExp)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case EssentialOCLPackage.COLLECTION_LITERAL_PART__COLLECTION_LITERAL_EXP:
				return getCollectionLiteralExp() != null;
		}
		return super.eIsSet(featureID);
	}

} //CollectionLiteralPartImpl
