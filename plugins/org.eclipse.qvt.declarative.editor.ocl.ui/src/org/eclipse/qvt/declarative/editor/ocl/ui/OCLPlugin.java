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
 * $Id: OCLPlugin.java,v 1.2 2008/08/18 07:43:58 ewillink Exp $
 */
package org.eclipse.qvt.declarative.editor.ocl.ui;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.imp.preferences.PreferencesService;
import org.eclipse.imp.runtime.PluginBase;
import org.eclipse.qvt.declarative.editor.ui.imp.CommonEditorDefinition;
import org.eclipse.qvt.declarative.editor.ui.imp.ICommonPlugin;

public class OCLPlugin extends PluginBase implements ICommonPlugin
{
	public static final String kPluginID = "org.eclipse.qvt.declarative.editor.ocl.ui";
	public static final String kLanguageName = "ocl";

	/**
	 * The unique instance of this plugin class
	 */
	protected static OCLPlugin sPlugin;

	public static OCLPlugin getInstance() {
		// SMS 11 Jul 2007
		// Added conditional call to constructor in case the plugin
		// class has not been auto-started
		if (sPlugin == null)
			new OCLPlugin();
		return sPlugin;
	}

	public static PreferencesService getPreferencesService() {
		if (preferencesService == null) {
			preferencesService = new PreferencesService(ResourcesPlugin.getWorkspace().getRoot().getProject());
			preferencesService.setLanguageName(kLanguageName);
			// To trigger the invocation of the preferences initializer:
			try {
				new DefaultScope().getNode(kPluginID);
			} catch (Exception e) {
				// If this ever happens, it will probably be because the preferences
				// and their initializer haven't been defined yet.  In that situation
				// there's not really anything to do--you can't initialize preferences
				// that don't exist.  So swallow the exception and continue ...
			}
		}
		return preferencesService;
	}
	
	public CommonEditorDefinition editorDefinition = null;

	public OCLPlugin() {
		super();
		sPlugin = this;
	}

	public CommonEditorDefinition getEditorDefinition() {
		if (editorDefinition == null)
			editorDefinition = CommonEditorDefinition.create(this, "model/" + kLanguageName + ".editor");
		return editorDefinition;
	}

	@Override
	public String getID() {
		return kPluginID;
	}

	@Override
	public String getLanguageName() {
		return kLanguageName;
	}
}