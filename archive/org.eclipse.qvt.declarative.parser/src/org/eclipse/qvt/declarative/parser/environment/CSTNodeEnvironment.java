/******************************************************************************
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.LookupException;
import org.eclipse.ocl.cst.CSTNode;
import org.eclipse.ocl.ecore.InvalidLiteralExp;
import org.eclipse.qvt.declarative.parser.qvt.cst.IdentifierCS;

/**
 * CSTNodeEnvironment<E,AST,CST> revises the inherited EcoreEnvironment behaviour
 * to support operation appropriate to the construct defined by a CST node and
 * realised by an AST node. The environment is installed as an adapter on the AST
 * node so that the environment may be regarded as an extension of the AST model
 * providing state and functionality that are inappropriate for direct inclusion
 * within the AST. A derived CSTRootEnvironment<E,CST> defines the rrot of an
 * environment hierarchy comprising an AST XMIResource and a top level CST node.
 * Further derived CSTChildEnvironment<E,P,AST,CST> define more specific nested
 * environments. A CSTFileEnvironment is the parent of the CSTRootEnvironment
 * and supervises lexing, parsing and analysis.
 * <p>
 * Environments may be used to capture context during a two-phase declare/define
 * CST to AST conversion, in which the first declare phase creates the AST
 * hierarchy from the CST to ensure that all declarations are available for
 * resolution during a second definition phase. Thereafter the environments
 * could be discarded. Alternatively they may be preserved or indeed created
 * to support validation of either a parsed AST or an externally supplied AST.
 * <p>
 * The environment is an adapter on the AST and may be obtained from the
 * AST node by CSTNodeEnvironment.getEnvironmentFromAST(astNode, environmentClass)
 * or the CST by CSTNodeEnvironment.getEnvironmentFromCST(cstNode, environmentClass).
 * This assumes that the AST /CST has an associated environment. 
 * <p>
 * The AST node may be obtained from the cst node by cstNode.getAst().
 * <p>
 * The AST or CST may be obtained from the environment by env.getAst() or env.getCST().
 * <p>
 * The CST may always be obtained from the AST by env.getASTtoCSTMapping().get(astNode)
 * where env is any environment in the hierarchy. There is no need for AST or CST to have
 * an associated environment. 
 * <p>
 * CSTChildEnvironment<E,P,AST,CST> defines hierarchy of environments in which all
 * environments implement E and for which the parent environment implements P. 
 * Lookups are re-implemented to traverse the environment hierarchy.
 * <p>
 * lookupXXX methods are therefore deprecated in favour of tryLookupXXX methods
 * that support a LookupException to report ambiguous lookups.
 * <p>
 * Look-ups are performed first using the derived implementation assuming
 * that the derived application is OCL-like. A failed derived look-up
 * is then delegated to the parent environment.
 * <p>
 * This provides a default semantics that may often be useful. It is expected
 * that derived environments will re-implement whenever another semantics is needed.
 * Total re-implementation of semantics by a derived environment is supported by
 * the ability to use the lookupOCLXXX method to access the underyling OCL functionality.
 *
 * @param <E> The derived ICSTEnvironment from which all environments derive
 * @param <P> The derived E of the parent environment
 * @param <AST> The type of the associated AST node
 * @param <CST> The type of the associated CST node
 */
public abstract class CSTNodeEnvironment<E extends ICSTNodeEnvironment, AST extends Notifier, CST extends CSTNode> extends CSTEnvironment<E> implements ICSTNodeEnvironment, Adapter
{	
	public static <E extends ICSTNodeEnvironment> E getEnvironmentFromAST(Notifier ast, Class<E> envClass) {
		if (ast != null) {
			@SuppressWarnings("unchecked")
			E adapter = (E) EcoreUtil.getAdapter(ast.eAdapters(), envClass);
			return adapter;
		} else
			return null;
	}

	public static <E extends ICSTNodeEnvironment> E getEnvironmentFromCST(CSTNode cst, Class<E> envClass) {
		Object ast = cst.getAst();
		if (ast instanceof Notifier) {
			@SuppressWarnings("unchecked")
			E adapter = (E) EcoreUtil.getAdapter(((Notifier)ast).eAdapters(), envClass);
			return adapter;
		} else
			return null;
	}

	protected AST ast;	
	protected CST cst;
	
	protected CSTNodeEnvironment(ICSTEnvironment parent, AST astNode, CST cstNode) {
		super(parent);
//		assert (astNode != null);
		assert (cstNode != null);
		this.ast = astNode;
		this.cst = cstNode;
		if (astNode != null)
			internalSetAdapter();
	}

	public InvalidLiteralExp createInvalidLiteralExp(CSTNode cstNode) {
		InvalidLiteralExp invalidLiteralExp = org.eclipse.ocl.ecore.EcoreFactory.eINSTANCE.createInvalidLiteralExp();
		initASTMapping(invalidLiteralExp, cstNode);
		return invalidLiteralExp;
	}

	public String formatLookupException(LookupException e) {
		List<?> matches = e.getAmbiguousMatches();
		List<String> matchNames = new ArrayList<String>();
		for (Object match : matches)
			matchNames.add(formatQualifiedName(match));				
		Collections.sort(matchNames);
		StringBuilder s = new StringBuilder();
		s.append(e.getMessage());
		String separator = " { ";
		for (String matchName : matchNames) {
			s.append(separator);
			s.append(matchName);
			separator = ", ";
		}
		s.append(" }");
		return s.toString();
	}
	
	public String formatName(Object object) {
		return getFormatter().formatName(object);
	}

	public String formatPath(List<String> names) {
		return getFormatter().formatPath(names);
	}

	@Deprecated
	public String formatPathName(Object object) {
		return formatQualifiedName(object);
	}

	public String formatQualifiedName(Object object) {
		return getFormatter().formatQualifiedName(object);
	}

	public String formatString(String string) {
		return getFormatter().formatString(string);
	}

	public String formatType(Object type) {
		return getFormatter().formatType(type);
	}

	public AST getASTNode() {
		return ast;
	}	

	public CST getCSTNode() {
		return cst;
	}	

	public Notifier getTarget() {
		return ast;
	}

	protected void internalSetAST(AST astNode) {
		assert (ast == null) == (astNode != null);
		ast = astNode;
	}

	protected void internalSetAdapter() {
		ast.eAdapters().add(this);
	}

	public boolean isAdapterForType(Object type) {
		return (type instanceof Class<?>) && ((Class<?>)type).isAssignableFrom(getClass());
	}

	public void notifyChanged(Notification notification) {}

	@SuppressWarnings("unchecked")
	@Deprecated // Use createNestedEnvironment
	public CSTNode setCSTNode(CSTNode cstNode) {
		CSTNode oldCSTNode = this.cst;
		this.cst = (CST)cstNode;
		return oldCSTNode;
	}

	public void setNameFromIdentifier(ENamedElement astNode, IdentifierCS identifier) {
		if (identifier != null) {
			astNode.setName(identifier.getValue());
			identifier.setAst(astNode);
		}
	}

	public void setNameFromIdentifier(ENamedElement fromAstNode, IdentifierCS identifier, Object toAstNode) {
		if (identifier != null) {
			fromAstNode.setName(identifier.getValue());
			if (toAstNode != null)
				identifier.setAst(toAstNode);
		}
	}

	/**
	 * Implementation of the Adapter method that imposes the set once policy of setASTNode.
	 */
	public final void setTarget(Notifier newTarget) {
		if (newTarget == null) 					// removing adapter
			internalSetAST(null);		
		else if (newTarget == ast) 				// adding adapter
			;		
		else 									// something unexpected
			throw new UnsupportedOperationException(getClass().getName() + ".setTarget");		
	}
	
	@Override
	public String toString() {
		String parentString = getInternalParent() != null ? (getInternalParent().toString() + ".") : "";
		CSTNode cstNode = getCSTNode();
		String childString = cstNode instanceof IHasName ? ((IHasName)cstNode).getName() : getClass().getSimpleName();
		return parentString + childString;
	}
}
