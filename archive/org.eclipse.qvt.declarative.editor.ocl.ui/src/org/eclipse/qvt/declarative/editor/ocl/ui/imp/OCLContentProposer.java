/**
 * <copyright>
 * 
 * Copyright (c) 2008 E.D.Willink and others.
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
 * $Id: OCLContentProposer.java,v 1.5 2009/08/20 20:22:31 ewillink Exp $
 */
package org.eclipse.qvt.declarative.editor.ocl.ui.imp;

import org.eclipse.qvt.declarative.editor.ui.imp.CommonContentProposer;
import org.eclipse.qvt.declarative.editor.ui.imp.ICommonParseResult;

public class OCLContentProposer extends CommonContentProposer
{
	@Override
	protected OCLContentProposals createProposals(ICommonParseResult ast, int offset) {
		return new OCLContentProposals(ast, offset);
	}
}
