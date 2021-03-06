/**
 * <copyright>
 * 
 * Copyright (c) 2007,2008,2009 E.D.Willink and others.
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
 * $Id: ICreationFactory.java,v 1.10 2010/07/10 09:35:42 ewillink Exp $
 */
package org.eclipse.qvt.declarative.editor.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.imp.language.Language;
import org.eclipse.imp.parser.ISourcePositionLocator;
import org.eclipse.ocl.examples.modelregistry.environment.FileHandle;
import org.eclipse.ocl.lpg.ProblemHandler;
import org.eclipse.qvt.declarative.ecore.mappings.IMappingMetaData;
import org.eclipse.qvt.declarative.editor.ui.builder.CommonNature;
import org.eclipse.qvt.declarative.editor.ui.imp.CommonEditorDefinition;
import org.eclipse.qvt.declarative.editor.ui.imp.CommonTreeModelBuilder;
import org.eclipse.qvt.declarative.editor.ui.imp.ICommonParseController;
import org.eclipse.qvt.declarative.editor.ui.imp.ICommonPlugin;
import org.eclipse.qvt.declarative.editor.ui.text.ITextEditorWithUndoContext;
import org.eclipse.qvt.declarative.parser.environment.ICSTFileEnvironment;
import org.eclipse.qvt.declarative.parser.environment.ICSTRootEnvironment;
import org.eclipse.qvt.declarative.parser.unparser.IUnparser;

public interface ICreationFactory
{
	/**
	 * Assign an xmi:id to each element of resource.
	 * 
	 * @param resource
	 */
	public void assignXmiIds(XMLResource resource);

	/**
	 * Create the IFileEnvironment for parsing fileHandle within resourceSet to produce ecoreURI.
	 */
	public ICSTFileEnvironment createFileEnvironment(FileHandle fileHandle, ResourceSet resourceSet, URI astURI);

	public CommonNature createNature();

	@Deprecated // Use createSourcePositionLocator
	public ISourcePositionLocator createNodeLocator(ICSTRootEnvironment environment);

	public ICommonParseController createParseController();

	/**
	 * Create the problem handler and associated marker creation support.
	 */
	public ProblemHandler createProblemHandler(IFile file);

	public ISourcePositionLocator createSourcePositionLocator(ICSTRootEnvironment environment);

	public ITextEditorWithUndoContext createTextEditor(IPageManager editorPageManager);

	public CommonTreeModelBuilder createTreeModelBuilder(boolean showAST);
	
	public IUnparser createUnparser(Resource resource);	

	/**
	 * Return an adapter that enables this to behave as key, or null if no adapter available. 
	 */
	public <T> T getAdapter(Class<T> key);
	
	/**
	 * Return the ID of the builder.
	 */
	public String getBuilderId();

	public String getEMOFExtension();
	
	public String[] getEMOFExtensions();

	public String getEcoreExtension();

	public String[] getEcoreExtensions();
	
	public CommonEditorDefinition getEditorDefinition();

	/**
	 * Return a name to be used to describe the associated type of editor.
	 */
	public String getEditorName();

	/**
	 * Return the Marker ID for errors.
	 */
	public String getErrorMarkerId();

	/**
	 * Return the Marker ID for infos.
	 */
	public String getInfoMarkerId();

	public Language getLanguage();

	public String getLanguageID();
	@Deprecated // Use getLanguageID()
	public String getLanguageName();
	
	/**
	 * Return the EMOF to/from Ecore mapping description.
	 */
	public IMappingMetaData getMappingMetaData();

	/**
	 * Return the ID of the nature.
	 */
	public String getNatureId();

	/**
	 * Return the plugin that defines the language.
	 */
	public ICommonPlugin getPlugin();

	/**
	 * Return the Marker ID for problems.
	 */
	public String getProblemMarkerId();
	
	public String getTextExtension();

	/**
	 * Return the extensions for which checkResource should invoke compile.
	 */
	public String[] getTextExtensions();

	/**
	 * Return the Marker ID for warnings.
	 */
	public String getWarningMarkerId();

	public String getXMLExtension();
}
