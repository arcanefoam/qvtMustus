/**
 * <copyright>
 * 
 * Copyright (c) 2007,2008 E.D.Willink and others.
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
 * $Id: OCLFileEnvironment.java,v 1.1 2008/10/11 15:27:53 ewillink Exp $
 */
package org.eclipse.qvt.declarative.parser.ocl.environment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lpg.lpgjavaruntime.Monitor;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.ocl.Environment;
import org.eclipse.ocl.EnvironmentFactory;
import org.eclipse.ocl.LookupException;
import org.eclipse.ocl.cst.CSTNode;
import org.eclipse.ocl.cst.PackageDeclarationCS;
import org.eclipse.ocl.ecore.CallOperationAction;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.ecore.SendSignalAction;
import org.eclipse.qvt.declarative.emof.EssentialOCL.util.EssentialOCLMappingMetaData;
import org.eclipse.qvt.declarative.modelregistry.environment.AbstractFileHandle;
import org.eclipse.qvt.declarative.parser.environment.CSTFileEnvironment;
import org.eclipse.qvt.declarative.parser.environment.ICSTFileEnvironment;
import org.eclipse.qvt.declarative.parser.ocl.OCLFileAnalyzer;
import org.eclipse.qvt.declarative.parser.ocl.OCLParsingError;

public class OCLFileEnvironment extends CSTFileEnvironment<OCLTopLevelEnvironment,OCLEnvironment<?,?,?>,PackageDeclarationCS> implements ICSTFileEnvironment
{
	protected class OCLEnvironmentFactory extends EcoreEnvironmentFactory implements EcoreEnvironmentFactory.Lookup<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter,
	EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject>
	{
		@Override public Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> createEnvironment(
				Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> parent) {
			if (!(parent instanceof IOCLEnvironment)) {
				throw new IllegalArgumentException(
					"Parent environment must be an OCLFileEnvironment: " + parent); //$NON-NLS-1$
			}
			return new OCLEnvironment<IOCLEnvironment,Notifier,CSTNode>((IOCLEnvironment)parent, null, ((IOCLEnvironment)parent).getCSTNode());
		}

		@Override public Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> createClassifierContext(
				Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> parent,
				EClassifier context) {
			if (context == null)
				throw new OCLParsingError("Missing classifier context");
			return super.createClassifierContext(parent, context);
		}

		@Override public Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> createOperationContext(
				Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> parent,
				EOperation operation) {
			if (operation == null)
				throw new OCLParsingError("Missing operation context");
			return super.createOperationContext(parent, operation);
		}

		@Deprecated @Override
		public final Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> createPackageContext(
				Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> parent,
				List<String> pathname) {
			return super.createPackageContext(parent, pathname);
		}


		@Deprecated
		@Override protected final EPackage lookupPackage(List<String> pathname) {
			return OCLFileEnvironment.this.lookupPackage(pathname);
		}
		
		public Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> tryCreatePackageContext(
				Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> parent,
				List<String> pathname) throws LookupException {
			EPackage contextPackage = ((IOCLEnvironment) parent).tryLookupPackage(pathname);        
	        return (contextPackage == null)? null : super.createPackageContext(parent, contextPackage);
		}

		public EPackage tryLookupPackage(List<String> names) throws LookupException {
			return OCLFileEnvironment.this.tryLookupPackage(names);
		}
	}

	protected EcoreEnvironmentFactory derivedFactory;

	public OCLFileEnvironment(AbstractFileHandle file, ResourceSet resourceSet, XMIResource astResource) {
		super(file, resourceSet, astResource);
	}

	@Override
	public OCLFileAnalyzer createAnalyzer(Monitor monitor) {
		return new OCLFileAnalyzer(this, monitor);
	}

	@Override
	protected OCLTopLevelEnvironment createRootEnvironment(XMIResource ast, PackageDeclarationCS cst) {
		return new OCLTopLevelEnvironment(this, ast, cst);
	}

	@Override
	protected String getContentTypeIdentifier() {
		return EssentialOCLMappingMetaData.INSTANCE.getEcoreContentTypeIdentifier();	// FIXME Use an MDT-OCL defined value once available
//		return EcorePackage.eCONTENT_TYPE;
	}

	@Override public EnvironmentFactory<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> getFactory() {
		if (derivedFactory == null)
			derivedFactory = new OCLEnvironmentFactory();
		return derivedFactory;
	}

	public Collection<Resource> getResourcesVisibleAt(EObject astNode) {
		List<Resource> resources = new ArrayList<Resource>();
		resources.add(astNode.eResource());
		resources.addAll(getResourceSet().getResources());
		return resources;
	}
}