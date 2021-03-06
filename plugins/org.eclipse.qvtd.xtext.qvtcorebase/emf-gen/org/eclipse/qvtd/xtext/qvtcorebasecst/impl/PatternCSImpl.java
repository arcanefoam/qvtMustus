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
package org.eclipse.qvtd.xtext.qvtcorebasecst.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ocl.examples.xtext.base.baseCST.impl.ModelElementCSImpl;
import org.eclipse.ocl.examples.xtext.base.util.BaseCSVisitor;
import org.eclipse.qvtd.xtext.qvtcorebasecst.AssignmentCS;
import org.eclipse.qvtd.xtext.qvtcorebasecst.PatternCS;
import org.eclipse.qvtd.xtext.qvtcorebasecst.QVTcoreBaseCSTPackage;
import org.eclipse.qvtd.xtext.qvtcorebasecst.UnrealizedVariableCS;
import org.eclipse.qvtd.xtext.qvtcorebasecst.util.QVTcoreBaseCSVisitor;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pattern CS</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.qvtd.xtext.qvtcorebasecst.impl.PatternCSImpl#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link org.eclipse.qvtd.xtext.qvtcorebasecst.impl.PatternCSImpl#getUnrealizedVariables <em>Unrealized Variables</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class PatternCSImpl extends ModelElementCSImpl implements PatternCS {
	/**
	 * The cached value of the '{@link #getConstraints() <em>Constraints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<AssignmentCS> constraints;

	/**
	 * The cached value of the '{@link #getUnrealizedVariables() <em>Unrealized Variables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnrealizedVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<UnrealizedVariableCS> unrealizedVariables;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PatternCSImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return QVTcoreBaseCSTPackage.Literals.PATTERN_CS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AssignmentCS> getConstraints() {
		if (constraints == null) {
			constraints = new EObjectContainmentEList<AssignmentCS>(AssignmentCS.class, this, QVTcoreBaseCSTPackage.PATTERN_CS__CONSTRAINTS);
		}
		return constraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<UnrealizedVariableCS> getUnrealizedVariables() {
		if (unrealizedVariables == null) {
			unrealizedVariables = new EObjectContainmentEList<UnrealizedVariableCS>(UnrealizedVariableCS.class, this, QVTcoreBaseCSTPackage.PATTERN_CS__UNREALIZED_VARIABLES);
		}
		return unrealizedVariables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case QVTcoreBaseCSTPackage.PATTERN_CS__CONSTRAINTS:
				return ((InternalEList<?>)getConstraints()).basicRemove(otherEnd, msgs);
			case QVTcoreBaseCSTPackage.PATTERN_CS__UNREALIZED_VARIABLES:
				return ((InternalEList<?>)getUnrealizedVariables()).basicRemove(otherEnd, msgs);
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
			case QVTcoreBaseCSTPackage.PATTERN_CS__CONSTRAINTS:
				return getConstraints();
			case QVTcoreBaseCSTPackage.PATTERN_CS__UNREALIZED_VARIABLES:
				return getUnrealizedVariables();
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
			case QVTcoreBaseCSTPackage.PATTERN_CS__CONSTRAINTS:
				getConstraints().clear();
				getConstraints().addAll((Collection<? extends AssignmentCS>)newValue);
				return;
			case QVTcoreBaseCSTPackage.PATTERN_CS__UNREALIZED_VARIABLES:
				getUnrealizedVariables().clear();
				getUnrealizedVariables().addAll((Collection<? extends UnrealizedVariableCS>)newValue);
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
			case QVTcoreBaseCSTPackage.PATTERN_CS__CONSTRAINTS:
				getConstraints().clear();
				return;
			case QVTcoreBaseCSTPackage.PATTERN_CS__UNREALIZED_VARIABLES:
				getUnrealizedVariables().clear();
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
			case QVTcoreBaseCSTPackage.PATTERN_CS__CONSTRAINTS:
				return constraints != null && !constraints.isEmpty();
			case QVTcoreBaseCSTPackage.PATTERN_CS__UNREALIZED_VARIABLES:
				return unrealizedVariables != null && !unrealizedVariables.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	@Override
	public <R> R accept(@NonNull BaseCSVisitor<R> visitor) {
		return ((QVTcoreBaseCSVisitor<R>)visitor).visitPatternCS(this);
	}
} //PatternCSImpl
