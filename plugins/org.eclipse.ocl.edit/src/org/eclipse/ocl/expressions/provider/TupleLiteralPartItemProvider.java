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
 * $Id: TupleLiteralPartItemProvider.java,v 1.1 2008/07/23 09:26:18 qglineur Exp $
 */
package org.eclipse.ocl.expressions.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.ocl.edit.internal.OCLEditPlugin;

import org.eclipse.ocl.expressions.ExpressionsFactory;
import org.eclipse.ocl.expressions.ExpressionsPackage;
import org.eclipse.ocl.expressions.TupleLiteralPart;

import org.eclipse.ocl.types.TypesFactory;

import org.eclipse.ocl.utilities.UtilitiesPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.ocl.expressions.TupleLiteralPart} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class TupleLiteralPartItemProvider
	extends ItemProviderAdapter
	implements	
		IEditingDomainItemProvider,	
		IStructuredItemContentProvider,	
		ITreeItemContentProvider,	
		IItemLabelProvider,	
		IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TupleLiteralPartItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addStartPositionPropertyDescriptor(object);
			addEndPositionPropertyDescriptor(object);
			addTypeStartPositionPropertyDescriptor(object);
			addTypeEndPositionPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Start Position feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addStartPositionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ASTNode_startPosition_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_ASTNode_startPosition_feature", "_UI_ASTNode_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UtilitiesPackage.Literals.AST_NODE__START_POSITION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the End Position feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addEndPositionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ASTNode_endPosition_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_ASTNode_endPosition_feature", "_UI_ASTNode_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UtilitiesPackage.Literals.AST_NODE__END_POSITION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Type Start Position feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addTypeStartPositionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TypedASTNode_typeStartPosition_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_TypedASTNode_typeStartPosition_feature", "_UI_TypedASTNode_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UtilitiesPackage.Literals.TYPED_AST_NODE__TYPE_START_POSITION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Type End Position feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addTypeEndPositionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TypedASTNode_typeEndPosition_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_TypedASTNode_typeEndPosition_feature", "_UI_TypedASTNode_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UtilitiesPackage.Literals.TYPED_AST_NODE__TYPE_END_POSITION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__VALUE);
			childrenFeatures.add(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns TupleLiteralPart.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/TupleLiteralPart")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		TupleLiteralPart<?, ?> tupleLiteralPart = (TupleLiteralPart<?, ?>)object;
		return getString("_UI_TupleLiteralPart_type") + " " + tupleLiteralPart.getStartPosition(); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(TupleLiteralPart.class)) {
			case ExpressionsPackage.TUPLE_LITERAL_PART__START_POSITION:
			case ExpressionsPackage.TUPLE_LITERAL_PART__END_POSITION:
			case ExpressionsPackage.TUPLE_LITERAL_PART__TYPE_START_POSITION:
			case ExpressionsPackage.TUPLE_LITERAL_PART__TYPE_END_POSITION:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case ExpressionsPackage.TUPLE_LITERAL_PART__VALUE:
			case ExpressionsPackage.TUPLE_LITERAL_PART__ATTRIBUTE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__VALUE,
				 ExpressionsFactory.eINSTANCE.createAssociationClassCallExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__VALUE,
				 ExpressionsFactory.eINSTANCE.createBooleanLiteralExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__VALUE,
				 ExpressionsFactory.eINSTANCE.createCollectionLiteralExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__VALUE,
				 ExpressionsFactory.eINSTANCE.createEnumLiteralExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__VALUE,
				 ExpressionsFactory.eINSTANCE.createIfExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__VALUE,
				 ExpressionsFactory.eINSTANCE.createIntegerLiteralExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__VALUE,
				 ExpressionsFactory.eINSTANCE.createUnlimitedNaturalLiteralExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__VALUE,
				 ExpressionsFactory.eINSTANCE.createInvalidLiteralExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__VALUE,
				 ExpressionsFactory.eINSTANCE.createIterateExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__VALUE,
				 ExpressionsFactory.eINSTANCE.createIteratorExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__VALUE,
				 ExpressionsFactory.eINSTANCE.createLetExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__VALUE,
				 ExpressionsFactory.eINSTANCE.createMessageExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__VALUE,
				 ExpressionsFactory.eINSTANCE.createNullLiteralExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__VALUE,
				 ExpressionsFactory.eINSTANCE.createOperationCallExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__VALUE,
				 ExpressionsFactory.eINSTANCE.createPropertyCallExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__VALUE,
				 ExpressionsFactory.eINSTANCE.createRealLiteralExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__VALUE,
				 ExpressionsFactory.eINSTANCE.createStateExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__VALUE,
				 ExpressionsFactory.eINSTANCE.createStringLiteralExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__VALUE,
				 ExpressionsFactory.eINSTANCE.createTupleLiteralExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__VALUE,
				 ExpressionsFactory.eINSTANCE.createTypeExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__VALUE,
				 ExpressionsFactory.eINSTANCE.createUnspecifiedValueExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__VALUE,
				 ExpressionsFactory.eINSTANCE.createVariableExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 ExpressionsFactory.eINSTANCE.createAssociationClassCallExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 ExpressionsFactory.eINSTANCE.createBooleanLiteralExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 ExpressionsFactory.eINSTANCE.createCollectionItem()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 ExpressionsFactory.eINSTANCE.createCollectionLiteralExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 ExpressionsFactory.eINSTANCE.createCollectionRange()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 ExpressionsFactory.eINSTANCE.createEnumLiteralExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 ExpressionsFactory.eINSTANCE.createIfExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 ExpressionsFactory.eINSTANCE.createIntegerLiteralExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 ExpressionsFactory.eINSTANCE.createUnlimitedNaturalLiteralExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 ExpressionsFactory.eINSTANCE.createInvalidLiteralExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 ExpressionsFactory.eINSTANCE.createIterateExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 ExpressionsFactory.eINSTANCE.createIteratorExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 ExpressionsFactory.eINSTANCE.createLetExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 ExpressionsFactory.eINSTANCE.createMessageExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 ExpressionsFactory.eINSTANCE.createNullLiteralExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 ExpressionsFactory.eINSTANCE.createOperationCallExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 ExpressionsFactory.eINSTANCE.createPropertyCallExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 ExpressionsFactory.eINSTANCE.createRealLiteralExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 ExpressionsFactory.eINSTANCE.createStateExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 ExpressionsFactory.eINSTANCE.createStringLiteralExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 ExpressionsFactory.eINSTANCE.createTupleLiteralExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 ExpressionsFactory.eINSTANCE.createTupleLiteralPart()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 ExpressionsFactory.eINSTANCE.createTypeExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 ExpressionsFactory.eINSTANCE.createUnspecifiedValueExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 ExpressionsFactory.eINSTANCE.createVariable()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 ExpressionsFactory.eINSTANCE.createVariableExp()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 TypesFactory.eINSTANCE.createAnyType()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 TypesFactory.eINSTANCE.createCollectionType()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 TypesFactory.eINSTANCE.createBagType()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 TypesFactory.eINSTANCE.createElementType()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 TypesFactory.eINSTANCE.createInvalidType()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 TypesFactory.eINSTANCE.createMessageType()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 TypesFactory.eINSTANCE.createOrderedSetType()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 TypesFactory.eINSTANCE.createPrimitiveType()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 TypesFactory.eINSTANCE.createSequenceType()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 TypesFactory.eINSTANCE.createSetType()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 TypesFactory.eINSTANCE.createTupleType()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 TypesFactory.eINSTANCE.createTypeType()));

		newChildDescriptors.add
			(createChildParameter
				(ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE,
				 TypesFactory.eINSTANCE.createVoidType()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify =
			childFeature == ExpressionsPackage.Literals.TUPLE_LITERAL_PART__VALUE ||
			childFeature == ExpressionsPackage.Literals.TUPLE_LITERAL_PART__ATTRIBUTE;

		if (qualify) {
			return getString
				("_UI_CreateChild_text2", //$NON-NLS-1$
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return OCLEditPlugin.INSTANCE;
	}

}
