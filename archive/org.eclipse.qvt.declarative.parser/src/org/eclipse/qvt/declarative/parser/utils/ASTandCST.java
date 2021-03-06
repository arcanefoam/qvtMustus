/*******************************************************************************
 * Copyright (c) 2008 E.D.Willink and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     E.D.Willink - initial API and implementation
 *******************************************************************************/
package org.eclipse.qvt.declarative.parser.utils;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ocl.cst.CSTNode;

/**
 * An ASTandCST supports passing both AST and CST representations
 * as 'the' AST for use by IMP-based editor components.
 */
public class ASTandCST
{
	private CSTNode cst = null;
	private Resource ast = null;

	public Resource getAST() {
		return ast;
	}
	
	public CSTNode getCST() {
		return cst;
	}

	public void reset() {
		this.ast = null;
		this.cst = null;
	}

	public void setAST(Resource ast) {
		this.ast = ast;
	}

	public void setCST(CSTNode cst) {
		this.cst = cst;
	}
}
