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
 * $Id: PatternImpl.java,v 1.1 2008/07/23 09:39:08 qglineur Exp $
 */
package org.eclipse.qvt.declarative.ecore.QVTBase.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EModelElementImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.ocl.ecore.Variable;

import org.eclipse.qvt.declarative.ecore.QVTBase.Pattern;
import org.eclipse.qvt.declarative.ecore.QVTBase.Predicate;
import org.eclipse.qvt.declarative.ecore.QVTBase.QVTBasePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pattern</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.qvt.declarative.ecore.QVTBase.impl.PatternImpl#getPredicate <em>Predicate</em>}</li>
 *   <li>{@link org.eclipse.qvt.declarative.ecore.QVTBase.impl.PatternImpl#getBindsTo <em>Binds To</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PatternImpl extends EModelElementImpl implements Pattern {
	/**
	 * The cached value of the '{@link #getPredicate() <em>Predicate</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPredicate()
	 * @generated
	 * @ordered
	 */
	protected EList<Predicate> predicate;

	/**
	 * The cached value of the '{@link #getBindsTo() <em>Binds To</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBindsTo()
	 * @generated
	 * @ordered
	 */
	protected EList<Variable> bindsTo;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PatternImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return QVTBasePackage.Literals.PATTERN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Predicate> getPredicate() {
		if (predicate == null) {
			predicate = new EObjectContainmentWithInverseEList<Predicate>(Predicate.class, this, QVTBasePackage.PATTERN__PREDICATE, QVTBasePackage.PREDICATE__PATTERN);
		}
		return predicate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Variable> getBindsTo() {
		if (bindsTo == null) {
			bindsTo = new EObjectResolvingEList<Variable>(Variable.class, this, QVTBasePackage.PATTERN__BINDS_TO);
		}
		return bindsTo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case QVTBasePackage.PATTERN__PREDICATE:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getPredicate()).basicAdd(otherEnd, msgs);
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
			case QVTBasePackage.PATTERN__PREDICATE:
				return ((InternalEList<?>)getPredicate()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case QVTBasePackage.PATTERN__PREDICATE:
				return getPredicate();
			case QVTBasePackage.PATTERN__BINDS_TO:
				return getBindsTo();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case QVTBasePackage.PATTERN__PREDICATE:
				getPredicate().clear();
				getPredicate().addAll((Collection<? extends Predicate>)newValue);
				return;
			case QVTBasePackage.PATTERN__BINDS_TO:
				getBindsTo().clear();
				getBindsTo().addAll((Collection<? extends Variable>)newValue);
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
			case QVTBasePackage.PATTERN__PREDICATE:
				getPredicate().clear();
				return;
			case QVTBasePackage.PATTERN__BINDS_TO:
				getBindsTo().clear();
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
			case QVTBasePackage.PATTERN__PREDICATE:
				return predicate != null && !predicate.isEmpty();
			case QVTBasePackage.PATTERN__BINDS_TO:
				return bindsTo != null && !bindsTo.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //PatternImpl
