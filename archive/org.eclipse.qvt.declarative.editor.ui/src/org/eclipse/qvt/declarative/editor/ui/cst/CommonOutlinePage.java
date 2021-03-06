/**
 * <copyright>
 * 
 * Copyright (c) 2008,2009 E.D.Willink and others.
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
 * $Id: CommonOutlinePage.java,v 1.6 2010/03/13 19:03:06 ewillink Exp $
 */
package org.eclipse.qvt.declarative.editor.ui.cst;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.imp.editor.IMPOutlinePage;
import org.eclipse.imp.editor.ModelTreeNode;
import org.eclipse.imp.parser.IParseController;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ocl.cst.CSTNode;
import org.eclipse.qvt.declarative.editor.ui.QVTEditorPlugin;
import org.eclipse.qvt.declarative.editor.ui.format.DefaultFormatManager;
import org.eclipse.qvt.declarative.editor.ui.imp.CommonTextEditor;
import org.eclipse.qvt.declarative.editor.ui.imp.CommonTreeModelBuilder;
import org.eclipse.qvt.declarative.editor.ui.imp.ICommonParseResult;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.IPageSite;

public abstract class CommonOutlinePage extends IMPOutlinePage implements ICSTOutlinePage, ISelectionListener
{
	protected final CommonTextEditor editor;
	protected final CommonTreeModelBuilder treeModelBuilder;
	private boolean changingSelection = false;	// Set true while selection is changing to inhibit recursive call-backs

	public CommonOutlinePage(CommonTextEditor editor, CommonTreeModelBuilder treeModelBuilder) {
		super(editor.getParseController(), treeModelBuilder,
			editor.getLanguageServiceManager().getLabelProvider(),
			editor.getLanguageServiceManager().getImageProvider(),
			null, null);
		this.editor = editor;
		this.treeModelBuilder = treeModelBuilder;
	}

	@Override
	public void dispose() {
		super.dispose();
		getSite().getPage().removePostSelectionListener(this);
		getSite().getPage().removeSelectionListener(this);
		editor.removeModelListener(this);
	}

	protected ISelection getItemSelection(ISelection selection) {
		if ((selection instanceof IStructuredSelection) && !selection.isEmpty()) {
			Object[] selections = ((IStructuredSelection)selection).toArray();
			Object[] unwrappedSelections = new Object[selections.length];
			for (int i = 0; i < selections.length; i++) {
				for (Object n = selections[i]; n != null; n = (n instanceof EObject) ? ((EObject)n).eContainer() : null) {
					ModelTreeNode item = treeModelBuilder.getItem(n);
					if (item != null) {
						unwrappedSelections[i] = item;
						break;
					}
				}
			}
			selection = new StructuredSelection(unwrappedSelections);
			
		}
		return selection;
	}

	@Override
	public void init(IPageSite pageSite) {
		editor.addModelListener(this);
		pageSite.getPage().addSelectionListener(this);			// Outline Click and Text Double Click, Text Single Click after Double Click
		pageSite.getPage().addPostSelectionListener(this);		// Text Single Click after Single Click	
		super.init(pageSite);
	}
	
	protected boolean isChangingSelection() {
		return changingSelection;
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
        if (!changingSelection) {
            if (QVTEditorPlugin.SELECTION_OUTER.isActive())
    			QVTEditorPlugin.SELECTION_OUTER.println(getClass(), "selectionChanged1 " + DefaultFormatManager.formatDebug(event.getSelection()));
//    		System.out.println(getClass().getSimpleName() + ".selectionChanged");
        	try {
        		changingSelection = true;
            	ISelection selection = event.getSelection();
				fireSelectionChanged(selection);
        		if ((selection instanceof IStructuredSelection) && (((IStructuredSelection)selection).size() == 1)) {
        			Object object = ((IStructuredSelection)selection).getFirstElement();
        			ICommonParseResult currentResult = editor.getParseController().getCurrentResult();
					CSTNode cstNode = currentResult != null ? currentResult.getCSTNode(object) : null;
        			if (cstNode != null)
        				editor.selectAndReveal(cstNode.getStartOffset(), cstNode.getEndOffset() - cstNode.getStartOffset() + 1);
        		}	      	
        	}
        	finally {
        		changingSelection = false;
        	}
        }      
        else if (QVTEditorPlugin.SELECTION_INNER.isActive())
			QVTEditorPlugin.SELECTION_INNER.println(getClass(), "selectionChanged1 " + DefaultFormatManager.formatDebug(event.getSelection()));
	}

	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (!isChangingSelection())
			setSelection(selection);
        else if (QVTEditorPlugin.SELECTION_INNER.isActive())
			QVTEditorPlugin.SELECTION_INNER.println(getClass(), "selectionChanged2 " + DefaultFormatManager.formatDebug(selection));
	}

	@Override
	public void setSelection(ISelection selection) {
        if (!changingSelection) {
//    		System.out.println(getClass().getSimpleName() + ".setSelection");
        	try {
    			if (QVTEditorPlugin.SELECTION_OUTER.isActive())
    				QVTEditorPlugin.SELECTION_OUTER.println(getClass(), "setSelection " + DefaultFormatManager.formatDebug(selection));
        		changingSelection = true;
    			ISelection itemSelection = getItemSelection(selection);
        		super.setSelection(itemSelection);
        	}
        	finally {
        		changingSelection = false;
        	}
        }
	}

	@Override
	public void update(IParseController parseController, IProgressMonitor monitor) {
	    if ((getTreeViewer() != null) && (getTreeViewer().getContentProvider() != null))
	    	super.update(parseController, monitor);
	}
}
