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
 * $Id: QVTrLabelProvider.java,v 1.1 2008/08/10 13:51:13 ewillink Exp $
 */
package org.eclipse.qvt.declarative.editor.qvtrelation.ui.imp;

import java.util.List;

import org.eclipse.ocl.cst.OCLExpressionCS;
import org.eclipse.ocl.cst.TypeCS;
import org.eclipse.qvt.declarative.editor.qvtrelation.ui.QVTrPlugin;
import org.eclipse.qvt.declarative.editor.ui.imp.CommonLabelProvider;
import org.eclipse.qvt.declarative.editor.ui.imp.ICommonPlugin;

public class QVTrLabelProvider extends CommonLabelProvider
{
	@Override
	protected String formatObject(Object object, Object node, int index) {
		if (object instanceof List) {
			StringBuffer s = new StringBuffer();
			for (Object o : (List<?>)object) {
				if (s.length() > 0)
					s.append("::");
				s.append(formatObject(o, node, index));
			}
			return s.toString();
		}
		else if (object instanceof TypeCS) {
			return formatType((TypeCS)object);
		}
		else if (object instanceof OCLExpressionCS) {
			return formatExpression((OCLExpressionCS)object);
		}
		else
			return super.formatObject(object, node, index);
	}
	
	@Override
	protected ICommonPlugin getPlugin() {
		return QVTrPlugin.getInstance();
	}
	
	public String getText(Object element) {
		String text = super.getText(element);
		return (text != null) ? text : "<???>";
	}
}