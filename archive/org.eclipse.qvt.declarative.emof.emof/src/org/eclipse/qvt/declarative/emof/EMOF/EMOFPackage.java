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
 * $Id: EMOFPackage.java,v 1.2 2008/09/21 12:30:24 ewillink Exp $
 */
package org.eclipse.qvt.declarative.emof.EMOF;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.qvt.declarative.emof.EMOF.EMOFFactory
 * @model kind="package"
 * @generated
 */
public interface EMOFPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "EMOF";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://schema.omg.org/spec/MOF/2.0/emof.xml";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "emof";

	/**
	 * The package content type ID.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eCONTENT_TYPE = "org.eclipse.qvt.declarative.emof.emof";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EMOFPackage eINSTANCE = org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.ObjectImpl <em>Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.ObjectImpl
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getObject()
	 * @generated
	 */
	int OBJECT = 10;

	/**
	 * The number of structural features of the '<em>Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.ElementImpl <em>Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.ElementImpl
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getElement()
	 * @generated
	 */
	int ELEMENT = 3;

	/**
	 * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT__OWNED_COMMENT = OBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_FEATURE_COUNT = OBJECT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.NamedElementImpl <em>Named Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.NamedElementImpl
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getNamedElement()
	 * @generated
	 */
	int NAMED_ELEMENT = 9;

	/**
	 * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__OWNED_COMMENT = ELEMENT__OWNED_COMMENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__NAME = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Named Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.TypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.TypeImpl
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getType()
	 * @generated
	 */
	int TYPE = 19;

	/**
	 * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__OWNED_COMMENT = NAMED_ELEMENT__OWNED_COMMENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Package</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__PACKAGE = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.ClassImpl <em>Class</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.ClassImpl
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getClass_()
	 * @generated
	 */
	int CLASS = 0;

	/**
	 * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS__OWNED_COMMENT = TYPE__OWNED_COMMENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS__NAME = TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Package</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS__PACKAGE = TYPE__PACKAGE;

	/**
	 * The feature id for the '<em><b>Is Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS__IS_ABSTRACT = TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Owned Attribute</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS__OWNED_ATTRIBUTE = TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Owned Operation</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS__OWNED_OPERATION = TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Super Class</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS__SUPER_CLASS = TYPE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Class</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_FEATURE_COUNT = TYPE_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.CommentImpl <em>Comment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.CommentImpl
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getComment()
	 * @generated
	 */
	int COMMENT = 1;

	/**
	 * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__OWNED_COMMENT = ELEMENT__OWNED_COMMENT;

	/**
	 * The feature id for the '<em><b>Annotated Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__ANNOTATED_ELEMENT = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__BODY = ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Comment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.DataTypeImpl <em>Data Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.DataTypeImpl
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getDataType()
	 * @generated
	 */
	int DATA_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__OWNED_COMMENT = TYPE__OWNED_COMMENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__NAME = TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Package</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__PACKAGE = TYPE__PACKAGE;

	/**
	 * The number of structural features of the '<em>Data Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE_FEATURE_COUNT = TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.EnumerationImpl <em>Enumeration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EnumerationImpl
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getEnumeration()
	 * @generated
	 */
	int ENUMERATION = 4;

	/**
	 * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION__OWNED_COMMENT = DATA_TYPE__OWNED_COMMENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION__NAME = DATA_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Package</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION__PACKAGE = DATA_TYPE__PACKAGE;

	/**
	 * The feature id for the '<em><b>Owned Literal</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION__OWNED_LITERAL = DATA_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Enumeration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION_FEATURE_COUNT = DATA_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.EnumerationLiteralImpl <em>Enumeration Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EnumerationLiteralImpl
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getEnumerationLiteral()
	 * @generated
	 */
	int ENUMERATION_LITERAL = 5;

	/**
	 * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION_LITERAL__OWNED_COMMENT = NAMED_ELEMENT__OWNED_COMMENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION_LITERAL__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Enumeration</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION_LITERAL__ENUMERATION = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Enumeration Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION_LITERAL_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.ExtentImpl <em>Extent</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.ExtentImpl
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getExtent()
	 * @generated
	 */
	int EXTENT = 6;

	/**
	 * The number of structural features of the '<em>Extent</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENT_FEATURE_COUNT = OBJECT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.FactoryImpl <em>Factory</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.FactoryImpl
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getFactory()
	 * @generated
	 */
	int FACTORY = 7;

	/**
	 * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FACTORY__OWNED_COMMENT = ELEMENT__OWNED_COMMENT;

	/**
	 * The feature id for the '<em><b>Package</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FACTORY__PACKAGE = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Factory</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FACTORY_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.MultiplicityElementImpl <em>Multiplicity Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.MultiplicityElementImpl
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getMultiplicityElement()
	 * @generated
	 */
	int MULTIPLICITY_ELEMENT = 8;

	/**
	 * The feature id for the '<em><b>Is Ordered</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLICITY_ELEMENT__IS_ORDERED = 0;

	/**
	 * The feature id for the '<em><b>Is Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLICITY_ELEMENT__IS_UNIQUE = 1;

	/**
	 * The feature id for the '<em><b>Lower</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLICITY_ELEMENT__LOWER = 2;

	/**
	 * The feature id for the '<em><b>Upper</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLICITY_ELEMENT__UPPER = 3;

	/**
	 * The number of structural features of the '<em>Multiplicity Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLICITY_ELEMENT_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.TypedElementImpl <em>Typed Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.TypedElementImpl
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getTypedElement()
	 * @generated
	 */
	int TYPED_ELEMENT = 20;

	/**
	 * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT__OWNED_COMMENT = NAMED_ELEMENT__OWNED_COMMENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT__TYPE = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Typed Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.OperationImpl <em>Operation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.OperationImpl
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getOperation()
	 * @generated
	 */
	int OPERATION = 11;

	/**
	 * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__OWNED_COMMENT = TYPED_ELEMENT__OWNED_COMMENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__NAME = TYPED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__TYPE = TYPED_ELEMENT__TYPE;

	/**
	 * The feature id for the '<em><b>Is Ordered</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__IS_ORDERED = TYPED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Is Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__IS_UNIQUE = TYPED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Lower</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__LOWER = TYPED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Upper</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__UPPER = TYPED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Class</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__CLASS = TYPED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Owned Parameter</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__OWNED_PARAMETER = TYPED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Raised Exception</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__RAISED_EXCEPTION = TYPED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Operation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_FEATURE_COUNT = TYPED_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.PackageImpl <em>Package</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.PackageImpl
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getPackage()
	 * @generated
	 */
	int PACKAGE = 12;

	/**
	 * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE__OWNED_COMMENT = NAMED_ELEMENT__OWNED_COMMENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Nested Package</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE__NESTED_PACKAGE = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Nesting Package</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE__NESTING_PACKAGE = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Owned Type</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE__OWNED_TYPE = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE__URI = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Package</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.ParameterImpl
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 13;

	/**
	 * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__OWNED_COMMENT = TYPED_ELEMENT__OWNED_COMMENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__NAME = TYPED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__TYPE = TYPED_ELEMENT__TYPE;

	/**
	 * The feature id for the '<em><b>Is Ordered</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__IS_ORDERED = TYPED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Is Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__IS_UNIQUE = TYPED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Lower</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__LOWER = TYPED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Upper</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__UPPER = TYPED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Operation</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__OPERATION = TYPED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_FEATURE_COUNT = TYPED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.PrimitiveTypeImpl <em>Primitive Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.PrimitiveTypeImpl
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getPrimitiveType()
	 * @generated
	 */
	int PRIMITIVE_TYPE = 14;

	/**
	 * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMITIVE_TYPE__OWNED_COMMENT = DATA_TYPE__OWNED_COMMENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMITIVE_TYPE__NAME = DATA_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Package</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMITIVE_TYPE__PACKAGE = DATA_TYPE__PACKAGE;

	/**
	 * The number of structural features of the '<em>Primitive Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMITIVE_TYPE_FEATURE_COUNT = DATA_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.PropertyImpl <em>Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.PropertyImpl
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getProperty()
	 * @generated
	 */
	int PROPERTY = 15;

	/**
	 * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__OWNED_COMMENT = TYPED_ELEMENT__OWNED_COMMENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__NAME = TYPED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__TYPE = TYPED_ELEMENT__TYPE;

	/**
	 * The feature id for the '<em><b>Is Ordered</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__IS_ORDERED = TYPED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Is Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__IS_UNIQUE = TYPED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Lower</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__LOWER = TYPED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Upper</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__UPPER = TYPED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Class</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__CLASS = TYPED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__DEFAULT = TYPED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Is Composite</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__IS_COMPOSITE = TYPED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Is Derived</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__IS_DERIVED = TYPED_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Is ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__IS_ID = TYPED_ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Is Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__IS_READ_ONLY = TYPED_ELEMENT_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Opposite</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__OPPOSITE = TYPED_ELEMENT_FEATURE_COUNT + 10;

	/**
	 * The number of structural features of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FEATURE_COUNT = TYPED_ELEMENT_FEATURE_COUNT + 11;

	/**
	 * The meta object id for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.ReflectiveCollectionImpl <em>Reflective Collection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.ReflectiveCollectionImpl
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getReflectiveCollection()
	 * @generated
	 */
	int REFLECTIVE_COLLECTION = 16;

	/**
	 * The number of structural features of the '<em>Reflective Collection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFLECTIVE_COLLECTION_FEATURE_COUNT = OBJECT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.ReflectiveSequenceImpl <em>Reflective Sequence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.ReflectiveSequenceImpl
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getReflectiveSequence()
	 * @generated
	 */
	int REFLECTIVE_SEQUENCE = 17;

	/**
	 * The number of structural features of the '<em>Reflective Sequence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFLECTIVE_SEQUENCE_FEATURE_COUNT = REFLECTIVE_COLLECTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.TagImpl <em>Tag</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.TagImpl
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getTag()
	 * @generated
	 */
	int TAG = 18;

	/**
	 * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG__OWNED_COMMENT = ELEMENT__OWNED_COMMENT;

	/**
	 * The feature id for the '<em><b>Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG__ELEMENT = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG__NAME = ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG__VALUE = ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Tag</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.URIExtentImpl <em>URI Extent</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.URIExtentImpl
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getURIExtent()
	 * @generated
	 */
	int URI_EXTENT = 21;

	/**
	 * The number of structural features of the '<em>URI Extent</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URI_EXTENT_FEATURE_COUNT = EXTENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '<em>Boolean</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.Boolean
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getBoolean()
	 * @generated
	 */
	int BOOLEAN = 22;

	/**
	 * The meta object id for the '<em>Integer</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.Integer
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getInteger()
	 * @generated
	 */
	int INTEGER = 23;

	/**
	 * The meta object id for the '<em>Real</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.Float
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getReal()
	 * @generated
	 */
	int REAL = 24;

	/**
	 * The meta object id for the '<em>String</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getString()
	 * @generated
	 */
	int STRING = 25;

	/**
	 * The meta object id for the '<em>Unlimited Natural</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.Integer
	 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getUnlimitedNatural()
	 * @generated
	 */
	int UNLIMITED_NATURAL = 26;


	/**
	 * Returns the meta object for class '{@link org.eclipse.qvt.declarative.emof.EMOF.Class <em>Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Class</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Class
	 * @generated
	 */
	EClass getClass_();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.qvt.declarative.emof.EMOF.Class#getIsAbstract <em>Is Abstract</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Abstract</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Class#getIsAbstract()
	 * @see #getClass_()
	 * @generated
	 */
	EAttribute getClass_IsAbstract();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.qvt.declarative.emof.EMOF.Class#getOwnedAttribute <em>Owned Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Owned Attribute</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Class#getOwnedAttribute()
	 * @see #getClass_()
	 * @generated
	 */
	EReference getClass_OwnedAttribute();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.qvt.declarative.emof.EMOF.Class#getOwnedOperation <em>Owned Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Owned Operation</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Class#getOwnedOperation()
	 * @see #getClass_()
	 * @generated
	 */
	EReference getClass_OwnedOperation();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.qvt.declarative.emof.EMOF.Class#getSuperClass <em>Super Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Super Class</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Class#getSuperClass()
	 * @see #getClass_()
	 * @generated
	 */
	EReference getClass_SuperClass();

	/**
	 * Returns the meta object for class '{@link org.eclipse.qvt.declarative.emof.EMOF.Comment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Comment</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Comment
	 * @generated
	 */
	EClass getComment();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.qvt.declarative.emof.EMOF.Comment#getAnnotatedElement <em>Annotated Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Annotated Element</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Comment#getAnnotatedElement()
	 * @see #getComment()
	 * @generated
	 */
	EReference getComment_AnnotatedElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.qvt.declarative.emof.EMOF.Comment#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Body</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Comment#getBody()
	 * @see #getComment()
	 * @generated
	 */
	EAttribute getComment_Body();

	/**
	 * Returns the meta object for class '{@link org.eclipse.qvt.declarative.emof.EMOF.DataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Type</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.DataType
	 * @generated
	 */
	EClass getDataType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.qvt.declarative.emof.EMOF.Element <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Element
	 * @generated
	 */
	EClass getElement();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.qvt.declarative.emof.EMOF.Element#getOwnedComment <em>Owned Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Owned Comment</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Element#getOwnedComment()
	 * @see #getElement()
	 * @generated
	 */
	EReference getElement_OwnedComment();

	/**
	 * Returns the meta object for class '{@link org.eclipse.qvt.declarative.emof.EMOF.Enumeration <em>Enumeration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enumeration</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Enumeration
	 * @generated
	 */
	EClass getEnumeration();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.qvt.declarative.emof.EMOF.Enumeration#getOwnedLiteral <em>Owned Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Owned Literal</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Enumeration#getOwnedLiteral()
	 * @see #getEnumeration()
	 * @generated
	 */
	EReference getEnumeration_OwnedLiteral();

	/**
	 * Returns the meta object for class '{@link org.eclipse.qvt.declarative.emof.EMOF.EnumerationLiteral <em>Enumeration Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enumeration Literal</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.EnumerationLiteral
	 * @generated
	 */
	EClass getEnumerationLiteral();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.qvt.declarative.emof.EMOF.EnumerationLiteral#getEnumeration <em>Enumeration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Enumeration</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.EnumerationLiteral#getEnumeration()
	 * @see #getEnumerationLiteral()
	 * @generated
	 */
	EReference getEnumerationLiteral_Enumeration();

	/**
	 * Returns the meta object for class '{@link org.eclipse.qvt.declarative.emof.EMOF.Extent <em>Extent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Extent</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Extent
	 * @generated
	 */
	EClass getExtent();

	/**
	 * Returns the meta object for class '{@link org.eclipse.qvt.declarative.emof.EMOF.Factory <em>Factory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Factory</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Factory
	 * @generated
	 */
	EClass getFactory();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.qvt.declarative.emof.EMOF.Factory#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Package</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Factory#getPackage()
	 * @see #getFactory()
	 * @generated
	 */
	EReference getFactory_Package();

	/**
	 * Returns the meta object for class '{@link org.eclipse.qvt.declarative.emof.EMOF.MultiplicityElement <em>Multiplicity Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Multiplicity Element</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.MultiplicityElement
	 * @generated
	 */
	EClass getMultiplicityElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.qvt.declarative.emof.EMOF.MultiplicityElement#getIsOrdered <em>Is Ordered</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Ordered</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.MultiplicityElement#getIsOrdered()
	 * @see #getMultiplicityElement()
	 * @generated
	 */
	EAttribute getMultiplicityElement_IsOrdered();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.qvt.declarative.emof.EMOF.MultiplicityElement#getIsUnique <em>Is Unique</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Unique</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.MultiplicityElement#getIsUnique()
	 * @see #getMultiplicityElement()
	 * @generated
	 */
	EAttribute getMultiplicityElement_IsUnique();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.qvt.declarative.emof.EMOF.MultiplicityElement#getLower <em>Lower</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lower</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.MultiplicityElement#getLower()
	 * @see #getMultiplicityElement()
	 * @generated
	 */
	EAttribute getMultiplicityElement_Lower();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.qvt.declarative.emof.EMOF.MultiplicityElement#getUpper <em>Upper</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Upper</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.MultiplicityElement#getUpper()
	 * @see #getMultiplicityElement()
	 * @generated
	 */
	EAttribute getMultiplicityElement_Upper();

	/**
	 * Returns the meta object for class '{@link org.eclipse.qvt.declarative.emof.EMOF.NamedElement <em>Named Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named Element</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.NamedElement
	 * @generated
	 */
	EClass getNamedElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.qvt.declarative.emof.EMOF.NamedElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.NamedElement#getName()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.qvt.declarative.emof.EMOF.Object <em>Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Object</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Object
	 * @generated
	 */
	EClass getObject();

	/**
	 * Returns the meta object for class '{@link org.eclipse.qvt.declarative.emof.EMOF.Operation <em>Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operation</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Operation
	 * @generated
	 */
	EClass getOperation();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.qvt.declarative.emof.EMOF.Operation#getClass_ <em>Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Class</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Operation#getClass_()
	 * @see #getOperation()
	 * @generated
	 */
	EReference getOperation_Class();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.qvt.declarative.emof.EMOF.Operation#getOwnedParameter <em>Owned Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Owned Parameter</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Operation#getOwnedParameter()
	 * @see #getOperation()
	 * @generated
	 */
	EReference getOperation_OwnedParameter();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.qvt.declarative.emof.EMOF.Operation#getRaisedException <em>Raised Exception</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Raised Exception</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Operation#getRaisedException()
	 * @see #getOperation()
	 * @generated
	 */
	EReference getOperation_RaisedException();

	/**
	 * Returns the meta object for class '{@link org.eclipse.qvt.declarative.emof.EMOF.Package <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Package</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Package
	 * @generated
	 */
	EClass getPackage();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.qvt.declarative.emof.EMOF.Package#getNestedPackage <em>Nested Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nested Package</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Package#getNestedPackage()
	 * @see #getPackage()
	 * @generated
	 */
	EReference getPackage_NestedPackage();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.qvt.declarative.emof.EMOF.Package#getNestingPackage <em>Nesting Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Nesting Package</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Package#getNestingPackage()
	 * @see #getPackage()
	 * @generated
	 */
	EReference getPackage_NestingPackage();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.qvt.declarative.emof.EMOF.Package#getOwnedType <em>Owned Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Owned Type</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Package#getOwnedType()
	 * @see #getPackage()
	 * @generated
	 */
	EReference getPackage_OwnedType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.qvt.declarative.emof.EMOF.Package#getUri <em>Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Uri</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Package#getUri()
	 * @see #getPackage()
	 * @generated
	 */
	EAttribute getPackage_Uri();

	/**
	 * Returns the meta object for class '{@link org.eclipse.qvt.declarative.emof.EMOF.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Parameter
	 * @generated
	 */
	EClass getParameter();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.qvt.declarative.emof.EMOF.Parameter#getOperation <em>Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Operation</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Parameter#getOperation()
	 * @see #getParameter()
	 * @generated
	 */
	EReference getParameter_Operation();

	/**
	 * Returns the meta object for class '{@link org.eclipse.qvt.declarative.emof.EMOF.PrimitiveType <em>Primitive Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Primitive Type</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.PrimitiveType
	 * @generated
	 */
	EClass getPrimitiveType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.qvt.declarative.emof.EMOF.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Property
	 * @generated
	 */
	EClass getProperty();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.qvt.declarative.emof.EMOF.Property#getClass_ <em>Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Class</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Property#getClass_()
	 * @see #getProperty()
	 * @generated
	 */
	EReference getProperty_Class();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.qvt.declarative.emof.EMOF.Property#getDefault <em>Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Property#getDefault()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Default();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.qvt.declarative.emof.EMOF.Property#getIsComposite <em>Is Composite</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Composite</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Property#getIsComposite()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_IsComposite();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.qvt.declarative.emof.EMOF.Property#getIsDerived <em>Is Derived</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Derived</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Property#getIsDerived()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_IsDerived();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.qvt.declarative.emof.EMOF.Property#getIsID <em>Is ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is ID</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Property#getIsID()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_IsID();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.qvt.declarative.emof.EMOF.Property#getIsReadOnly <em>Is Read Only</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Read Only</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Property#getIsReadOnly()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_IsReadOnly();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.qvt.declarative.emof.EMOF.Property#getOpposite <em>Opposite</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Opposite</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Property#getOpposite()
	 * @see #getProperty()
	 * @generated
	 */
	EReference getProperty_Opposite();

	/**
	 * Returns the meta object for class '{@link org.eclipse.qvt.declarative.emof.EMOF.ReflectiveCollection <em>Reflective Collection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Reflective Collection</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.ReflectiveCollection
	 * @generated
	 */
	EClass getReflectiveCollection();

	/**
	 * Returns the meta object for class '{@link org.eclipse.qvt.declarative.emof.EMOF.ReflectiveSequence <em>Reflective Sequence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Reflective Sequence</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.ReflectiveSequence
	 * @generated
	 */
	EClass getReflectiveSequence();

	/**
	 * Returns the meta object for class '{@link org.eclipse.qvt.declarative.emof.EMOF.Tag <em>Tag</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tag</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Tag
	 * @generated
	 */
	EClass getTag();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.qvt.declarative.emof.EMOF.Tag#getElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Element</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Tag#getElement()
	 * @see #getTag()
	 * @generated
	 */
	EReference getTag_Element();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.qvt.declarative.emof.EMOF.Tag#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Tag#getName()
	 * @see #getTag()
	 * @generated
	 */
	EAttribute getTag_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.qvt.declarative.emof.EMOF.Tag#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Tag#getValue()
	 * @see #getTag()
	 * @generated
	 */
	EAttribute getTag_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.qvt.declarative.emof.EMOF.Type <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Type
	 * @generated
	 */
	EClass getType();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.qvt.declarative.emof.EMOF.Type#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Package</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.Type#getPackage()
	 * @see #getType()
	 * @generated
	 */
	EReference getType_Package();

	/**
	 * Returns the meta object for class '{@link org.eclipse.qvt.declarative.emof.EMOF.TypedElement <em>Typed Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Typed Element</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.TypedElement
	 * @generated
	 */
	EClass getTypedElement();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.qvt.declarative.emof.EMOF.TypedElement#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.TypedElement#getType()
	 * @see #getTypedElement()
	 * @generated
	 */
	EReference getTypedElement_Type();

	/**
	 * Returns the meta object for class '{@link org.eclipse.qvt.declarative.emof.EMOF.URIExtent <em>URI Extent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>URI Extent</em>'.
	 * @see org.eclipse.qvt.declarative.emof.EMOF.URIExtent
	 * @generated
	 */
	EClass getURIExtent();

	/**
	 * Returns the meta object for data type '{@link java.lang.Boolean <em>Boolean</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Boolean</em>'.
	 * @see java.lang.Boolean
	 * @model instanceClass="java.lang.Boolean"
	 * @generated
	 */
	EDataType getBoolean();

	/**
	 * Returns the meta object for data type '{@link java.lang.Integer <em>Integer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Integer</em>'.
	 * @see java.lang.Integer
	 * @model instanceClass="java.lang.Integer"
	 * @generated
	 */
	EDataType getInteger();

	/**
	 * Returns the meta object for data type '{@link java.lang.Float <em>Real</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Real</em>'.
	 * @see java.lang.Float
	 * @model instanceClass="java.lang.Float"
	 * @generated
	 */
	EDataType getReal();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>String</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 * @generated
	 */
	EDataType getString();

	/**
	 * Returns the meta object for data type '{@link java.lang.Integer <em>Unlimited Natural</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Unlimited Natural</em>'.
	 * @see java.lang.Integer
	 * @model instanceClass="java.lang.Integer"
	 * @generated
	 */
	EDataType getUnlimitedNatural();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EMOFFactory getEMOFFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.ClassImpl <em>Class</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.ClassImpl
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getClass_()
		 * @generated
		 */
		EClass CLASS = eINSTANCE.getClass_();

		/**
		 * The meta object literal for the '<em><b>Is Abstract</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLASS__IS_ABSTRACT = eINSTANCE.getClass_IsAbstract();

		/**
		 * The meta object literal for the '<em><b>Owned Attribute</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLASS__OWNED_ATTRIBUTE = eINSTANCE.getClass_OwnedAttribute();

		/**
		 * The meta object literal for the '<em><b>Owned Operation</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLASS__OWNED_OPERATION = eINSTANCE.getClass_OwnedOperation();

		/**
		 * The meta object literal for the '<em><b>Super Class</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLASS__SUPER_CLASS = eINSTANCE.getClass_SuperClass();

		/**
		 * The meta object literal for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.CommentImpl <em>Comment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.CommentImpl
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getComment()
		 * @generated
		 */
		EClass COMMENT = eINSTANCE.getComment();

		/**
		 * The meta object literal for the '<em><b>Annotated Element</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMENT__ANNOTATED_ELEMENT = eINSTANCE.getComment_AnnotatedElement();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT__BODY = eINSTANCE.getComment_Body();

		/**
		 * The meta object literal for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.DataTypeImpl <em>Data Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.DataTypeImpl
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getDataType()
		 * @generated
		 */
		EClass DATA_TYPE = eINSTANCE.getDataType();

		/**
		 * The meta object literal for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.ElementImpl <em>Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.ElementImpl
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getElement()
		 * @generated
		 */
		EClass ELEMENT = eINSTANCE.getElement();

		/**
		 * The meta object literal for the '<em><b>Owned Comment</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT__OWNED_COMMENT = eINSTANCE.getElement_OwnedComment();

		/**
		 * The meta object literal for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.EnumerationImpl <em>Enumeration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EnumerationImpl
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getEnumeration()
		 * @generated
		 */
		EClass ENUMERATION = eINSTANCE.getEnumeration();

		/**
		 * The meta object literal for the '<em><b>Owned Literal</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENUMERATION__OWNED_LITERAL = eINSTANCE.getEnumeration_OwnedLiteral();

		/**
		 * The meta object literal for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.EnumerationLiteralImpl <em>Enumeration Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EnumerationLiteralImpl
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getEnumerationLiteral()
		 * @generated
		 */
		EClass ENUMERATION_LITERAL = eINSTANCE.getEnumerationLiteral();

		/**
		 * The meta object literal for the '<em><b>Enumeration</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENUMERATION_LITERAL__ENUMERATION = eINSTANCE.getEnumerationLiteral_Enumeration();

		/**
		 * The meta object literal for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.ExtentImpl <em>Extent</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.ExtentImpl
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getExtent()
		 * @generated
		 */
		EClass EXTENT = eINSTANCE.getExtent();

		/**
		 * The meta object literal for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.FactoryImpl <em>Factory</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.FactoryImpl
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getFactory()
		 * @generated
		 */
		EClass FACTORY = eINSTANCE.getFactory();

		/**
		 * The meta object literal for the '<em><b>Package</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FACTORY__PACKAGE = eINSTANCE.getFactory_Package();

		/**
		 * The meta object literal for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.MultiplicityElementImpl <em>Multiplicity Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.MultiplicityElementImpl
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getMultiplicityElement()
		 * @generated
		 */
		EClass MULTIPLICITY_ELEMENT = eINSTANCE.getMultiplicityElement();

		/**
		 * The meta object literal for the '<em><b>Is Ordered</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MULTIPLICITY_ELEMENT__IS_ORDERED = eINSTANCE.getMultiplicityElement_IsOrdered();

		/**
		 * The meta object literal for the '<em><b>Is Unique</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MULTIPLICITY_ELEMENT__IS_UNIQUE = eINSTANCE.getMultiplicityElement_IsUnique();

		/**
		 * The meta object literal for the '<em><b>Lower</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MULTIPLICITY_ELEMENT__LOWER = eINSTANCE.getMultiplicityElement_Lower();

		/**
		 * The meta object literal for the '<em><b>Upper</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MULTIPLICITY_ELEMENT__UPPER = eINSTANCE.getMultiplicityElement_Upper();

		/**
		 * The meta object literal for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.NamedElementImpl <em>Named Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.NamedElementImpl
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getNamedElement()
		 * @generated
		 */
		EClass NAMED_ELEMENT = eINSTANCE.getNamedElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__NAME = eINSTANCE.getNamedElement_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.ObjectImpl <em>Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.ObjectImpl
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getObject()
		 * @generated
		 */
		EClass OBJECT = eINSTANCE.getObject();

		/**
		 * The meta object literal for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.OperationImpl <em>Operation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.OperationImpl
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getOperation()
		 * @generated
		 */
		EClass OPERATION = eINSTANCE.getOperation();

		/**
		 * The meta object literal for the '<em><b>Class</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATION__CLASS = eINSTANCE.getOperation_Class();

		/**
		 * The meta object literal for the '<em><b>Owned Parameter</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATION__OWNED_PARAMETER = eINSTANCE.getOperation_OwnedParameter();

		/**
		 * The meta object literal for the '<em><b>Raised Exception</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATION__RAISED_EXCEPTION = eINSTANCE.getOperation_RaisedException();

		/**
		 * The meta object literal for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.PackageImpl <em>Package</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.PackageImpl
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getPackage()
		 * @generated
		 */
		EClass PACKAGE = eINSTANCE.getPackage();

		/**
		 * The meta object literal for the '<em><b>Nested Package</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PACKAGE__NESTED_PACKAGE = eINSTANCE.getPackage_NestedPackage();

		/**
		 * The meta object literal for the '<em><b>Nesting Package</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PACKAGE__NESTING_PACKAGE = eINSTANCE.getPackage_NestingPackage();

		/**
		 * The meta object literal for the '<em><b>Owned Type</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PACKAGE__OWNED_TYPE = eINSTANCE.getPackage_OwnedType();

		/**
		 * The meta object literal for the '<em><b>Uri</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PACKAGE__URI = eINSTANCE.getPackage_Uri();

		/**
		 * The meta object literal for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.ParameterImpl
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getParameter()
		 * @generated
		 */
		EClass PARAMETER = eINSTANCE.getParameter();

		/**
		 * The meta object literal for the '<em><b>Operation</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETER__OPERATION = eINSTANCE.getParameter_Operation();

		/**
		 * The meta object literal for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.PrimitiveTypeImpl <em>Primitive Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.PrimitiveTypeImpl
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getPrimitiveType()
		 * @generated
		 */
		EClass PRIMITIVE_TYPE = eINSTANCE.getPrimitiveType();

		/**
		 * The meta object literal for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.PropertyImpl <em>Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.PropertyImpl
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getProperty()
		 * @generated
		 */
		EClass PROPERTY = eINSTANCE.getProperty();

		/**
		 * The meta object literal for the '<em><b>Class</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY__CLASS = eINSTANCE.getProperty_Class();

		/**
		 * The meta object literal for the '<em><b>Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__DEFAULT = eINSTANCE.getProperty_Default();

		/**
		 * The meta object literal for the '<em><b>Is Composite</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__IS_COMPOSITE = eINSTANCE.getProperty_IsComposite();

		/**
		 * The meta object literal for the '<em><b>Is Derived</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__IS_DERIVED = eINSTANCE.getProperty_IsDerived();

		/**
		 * The meta object literal for the '<em><b>Is ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__IS_ID = eINSTANCE.getProperty_IsID();

		/**
		 * The meta object literal for the '<em><b>Is Read Only</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__IS_READ_ONLY = eINSTANCE.getProperty_IsReadOnly();

		/**
		 * The meta object literal for the '<em><b>Opposite</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY__OPPOSITE = eINSTANCE.getProperty_Opposite();

		/**
		 * The meta object literal for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.ReflectiveCollectionImpl <em>Reflective Collection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.ReflectiveCollectionImpl
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getReflectiveCollection()
		 * @generated
		 */
		EClass REFLECTIVE_COLLECTION = eINSTANCE.getReflectiveCollection();

		/**
		 * The meta object literal for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.ReflectiveSequenceImpl <em>Reflective Sequence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.ReflectiveSequenceImpl
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getReflectiveSequence()
		 * @generated
		 */
		EClass REFLECTIVE_SEQUENCE = eINSTANCE.getReflectiveSequence();

		/**
		 * The meta object literal for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.TagImpl <em>Tag</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.TagImpl
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getTag()
		 * @generated
		 */
		EClass TAG = eINSTANCE.getTag();

		/**
		 * The meta object literal for the '<em><b>Element</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TAG__ELEMENT = eINSTANCE.getTag_Element();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TAG__NAME = eINSTANCE.getTag_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TAG__VALUE = eINSTANCE.getTag_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.TypeImpl <em>Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.TypeImpl
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getType()
		 * @generated
		 */
		EClass TYPE = eINSTANCE.getType();

		/**
		 * The meta object literal for the '<em><b>Package</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE__PACKAGE = eINSTANCE.getType_Package();

		/**
		 * The meta object literal for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.TypedElementImpl <em>Typed Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.TypedElementImpl
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getTypedElement()
		 * @generated
		 */
		EClass TYPED_ELEMENT = eINSTANCE.getTypedElement();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPED_ELEMENT__TYPE = eINSTANCE.getTypedElement_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.qvt.declarative.emof.EMOF.impl.URIExtentImpl <em>URI Extent</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.URIExtentImpl
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getURIExtent()
		 * @generated
		 */
		EClass URI_EXTENT = eINSTANCE.getURIExtent();

		/**
		 * The meta object literal for the '<em>Boolean</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.Boolean
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getBoolean()
		 * @generated
		 */
		EDataType BOOLEAN = eINSTANCE.getBoolean();

		/**
		 * The meta object literal for the '<em>Integer</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.Integer
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getInteger()
		 * @generated
		 */
		EDataType INTEGER = eINSTANCE.getInteger();

		/**
		 * The meta object literal for the '<em>Real</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.Float
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getReal()
		 * @generated
		 */
		EDataType REAL = eINSTANCE.getReal();

		/**
		 * The meta object literal for the '<em>String</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getString()
		 * @generated
		 */
		EDataType STRING = eINSTANCE.getString();

		/**
		 * The meta object literal for the '<em>Unlimited Natural</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.Integer
		 * @see org.eclipse.qvt.declarative.emof.EMOF.impl.EMOFPackageImpl#getUnlimitedNatural()
		 * @generated
		 */
		EDataType UNLIMITED_NATURAL = eINSTANCE.getUnlimitedNatural();

	}

} //EMOFPackage
