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
package org.eclipse.qvtd.xtext.qvtimperativecst.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ocl.examples.xtext.base.baseCST.impl.RootPackageCSImpl;
import org.eclipse.ocl.examples.xtext.base.util.BaseCSVisitor;
import org.eclipse.qvtd.xtext.qvtcorebasecst.QueryCS;
import org.eclipse.qvtd.xtext.qvtcorebasecst.TransformationCS;
import org.eclipse.qvtd.xtext.qvtimperativecst.MappingCS;
import org.eclipse.qvtd.xtext.qvtimperativecst.QVTimperativeCSTPackage;
import org.eclipse.qvtd.xtext.qvtimperativecst.TopLevelCS;
import org.eclipse.qvtd.xtext.qvtimperativecst.util.QVTimperativeCSVisitor;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Top Level CS</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.qvtd.xtext.qvtimperativecst.impl.TopLevelCSImpl#getTransformations <em>Transformations</em>}</li>
 *   <li>{@link org.eclipse.qvtd.xtext.qvtimperativecst.impl.TopLevelCSImpl#getQueries <em>Queries</em>}</li>
 *   <li>{@link org.eclipse.qvtd.xtext.qvtimperativecst.impl.TopLevelCSImpl#getMappings <em>Mappings</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TopLevelCSImpl extends RootPackageCSImpl implements TopLevelCS {
	/**
	 * The cached value of the '{@link #getTransformations() <em>Transformations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransformations()
	 * @generated
	 * @ordered
	 */
	protected EList<TransformationCS> transformations;

	/**
	 * The cached value of the '{@link #getQueries() <em>Queries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueries()
	 * @generated
	 * @ordered
	 */
	protected EList<QueryCS> queries;

	/**
	 * The cached value of the '{@link #getMappings() <em>Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<MappingCS> mappings;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TopLevelCSImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return QVTimperativeCSTPackage.Literals.TOP_LEVEL_CS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TransformationCS> getTransformations() {
		if (transformations == null) {
			transformations = new EObjectContainmentEList<TransformationCS>(TransformationCS.class, this, QVTimperativeCSTPackage.TOP_LEVEL_CS__TRANSFORMATIONS);
		}
		return transformations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<QueryCS> getQueries() {
		if (queries == null) {
			queries = new EObjectContainmentEList<QueryCS>(QueryCS.class, this, QVTimperativeCSTPackage.TOP_LEVEL_CS__QUERIES);
		}
		return queries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MappingCS> getMappings() {
		if (mappings == null) {
			mappings = new EObjectContainmentEList<MappingCS>(MappingCS.class, this, QVTimperativeCSTPackage.TOP_LEVEL_CS__MAPPINGS);
		}
		return mappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case QVTimperativeCSTPackage.TOP_LEVEL_CS__TRANSFORMATIONS:
				return ((InternalEList<?>)getTransformations()).basicRemove(otherEnd, msgs);
			case QVTimperativeCSTPackage.TOP_LEVEL_CS__QUERIES:
				return ((InternalEList<?>)getQueries()).basicRemove(otherEnd, msgs);
			case QVTimperativeCSTPackage.TOP_LEVEL_CS__MAPPINGS:
				return ((InternalEList<?>)getMappings()).basicRemove(otherEnd, msgs);
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
			case QVTimperativeCSTPackage.TOP_LEVEL_CS__TRANSFORMATIONS:
				return getTransformations();
			case QVTimperativeCSTPackage.TOP_LEVEL_CS__QUERIES:
				return getQueries();
			case QVTimperativeCSTPackage.TOP_LEVEL_CS__MAPPINGS:
				return getMappings();
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
			case QVTimperativeCSTPackage.TOP_LEVEL_CS__TRANSFORMATIONS:
				getTransformations().clear();
				getTransformations().addAll((Collection<? extends TransformationCS>)newValue);
				return;
			case QVTimperativeCSTPackage.TOP_LEVEL_CS__QUERIES:
				getQueries().clear();
				getQueries().addAll((Collection<? extends QueryCS>)newValue);
				return;
			case QVTimperativeCSTPackage.TOP_LEVEL_CS__MAPPINGS:
				getMappings().clear();
				getMappings().addAll((Collection<? extends MappingCS>)newValue);
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
			case QVTimperativeCSTPackage.TOP_LEVEL_CS__TRANSFORMATIONS:
				getTransformations().clear();
				return;
			case QVTimperativeCSTPackage.TOP_LEVEL_CS__QUERIES:
				getQueries().clear();
				return;
			case QVTimperativeCSTPackage.TOP_LEVEL_CS__MAPPINGS:
				getMappings().clear();
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
			case QVTimperativeCSTPackage.TOP_LEVEL_CS__TRANSFORMATIONS:
				return transformations != null && !transformations.isEmpty();
			case QVTimperativeCSTPackage.TOP_LEVEL_CS__QUERIES:
				return queries != null && !queries.isEmpty();
			case QVTimperativeCSTPackage.TOP_LEVEL_CS__MAPPINGS:
				return mappings != null && !mappings.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	@Override
	public <R> R accept(@NonNull BaseCSVisitor<R> visitor) {
		return ((QVTimperativeCSVisitor<R>)visitor).visitTopLevelCS(this);
	}
} //TopLevelCSImpl
