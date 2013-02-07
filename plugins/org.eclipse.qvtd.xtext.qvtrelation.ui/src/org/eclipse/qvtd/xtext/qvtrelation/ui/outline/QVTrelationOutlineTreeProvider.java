/*
* generated by Xtext
*/
package org.eclipse.qvtd.xtext.qvtrelation.ui.outline;

import org.eclipse.ocl.examples.pivot.Element;
import org.eclipse.ocl.examples.xtext.base.baseCST.PathNameCS;
import org.eclipse.qvtd.pivot.qvtrelation.DomainPattern;
import org.eclipse.qvtd.xtext.qvtrelationcst.PredicateCS;
import org.eclipse.qvtd.xtext.qvtrelationcst.VarDeclarationCS;
import org.eclipse.qvtd.xtext.qvtrelationcst.VarDeclarationIdCS;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.impl.DefaultOutlineTreeProvider;

/**
 * customization of the default outline structure
 * 
 */
public class QVTrelationOutlineTreeProvider extends DefaultOutlineTreeProvider
{

	protected void _createNode(IOutlineNode parentNode, DomainPattern pattern) {
		createNode(parentNode, pattern.getTemplateExpression());
	}

	protected void _createNode(IOutlineNode parentNode, PathNameCS csPath) {
		Element element = csPath.getElement();
		if ((element != null) && !element.eIsProxy()) {
			createNode(parentNode, element);
		}
	}

	protected void _createNode(IOutlineNode parentNode, PredicateCS csPredicate) {
		createNode(parentNode, csPredicate.getExpr());
	}

	protected void _createNode(IOutlineNode parentNode, VarDeclarationCS csVars) {
		for (VarDeclarationIdCS var : csVars.getVarDeclarationIds()) {
			createNode(parentNode, var);
		}
	}
}
