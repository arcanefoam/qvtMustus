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
 * $Id: CSTOutlineMessages.java,v 1.1 2008/08/24 19:03:19 ewillink Exp $
 */
package org.eclipse.qvt.declarative.editor.ui.cst;

import org.eclipse.osgi.util.NLS;

/**
 * CSTOutlineMessages is the message class for the messages used in the CST outline.
 *
 */
public class CSTOutlineMessages extends NLS
{
	private static final String BUNDLE_NAME = "org.eclipse.qvt.declarative.editor.ui.cst.messages";//$NON-NLS-1$

	// ==============================================================================
	// Outline View
	// ==============================================================================
	/** */
	public static String CSTOutline_noOutline;

	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, CSTOutlineMessages.class);
	}
}