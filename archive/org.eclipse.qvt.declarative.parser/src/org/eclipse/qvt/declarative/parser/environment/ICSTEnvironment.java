/*******************************************************************************
 * Copyright (c) 2007,2008 E.D.Willink and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     E.D.Willink - initial API and implementation
 *******************************************************************************/
package org.eclipse.qvt.declarative.parser.environment;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.ocl.Environment;
import org.eclipse.ocl.cst.CSTNode;
import org.eclipse.ocl.ecore.CallOperationAction;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.SendSignalAction;
import org.eclipse.ocl.examples.modelregistry.environment.AbstractModelResolver;
import org.eclipse.ocl.lpg.BasicEnvironment2;

/**
 * ICSTEnvironment provides the enhanced interface for the CSTEnvironment hierarchy
 * comprising a ICSTFileEnvironment supervising ICSTNodeEnvironments, of which
 * there is an ICSTRootEnvironment for the AST resource and top level CST node.
 * Further ICSTChildEnvironments define nested AST, CST paired scopes.
 */
public interface ICSTEnvironment extends Environment.Internal<
	EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter,
	EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject>,
	Environment.Lookup<EPackage, EClassifier, EOperation, EStructuralFeature>, BasicEnvironment2
{	
	/**
	 * Return map of AST node to defining CST node. Note that these mappings are not exactly
	 * equivalent to the set of cstNode to cstNode.getAst() mappings since AST and CST do
	 * not always form a 1:1 relation.
	 * <p>
	 * For instance construct identifiers are usually distinct CST nodes realised by AST properties,
	 * so the AST node has a symmetric mapping to the CST node defining the construct. The
	 * identifier has a unidirectional mapping to the AST. Multiple AST nodes are very occasionally
	 * associated with a single CST node.
	 */
	public Map<Object, CSTNode> getASTNodeToCSTNodeMap();
	
	/**
	 * Return the file environment at the root of the environment hierarchy.
	 */
	public ICSTFileEnvironment getFileEnvironment();

	/**
	 * Return the model resolver for model references. 
	 */
	public AbstractModelResolver getResolver();
}
