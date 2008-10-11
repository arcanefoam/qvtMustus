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
 * $Id: CommonBuilder.java,v 1.7 2008/10/11 15:37:19 ewillink Exp $
 */
package org.eclipse.qvt.declarative.editor.ui.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.imp.builder.BuilderBase;
import org.eclipse.imp.builder.BuilderUtils;
import org.eclipse.imp.language.Language;
import org.eclipse.imp.model.ISourceProject;
import org.eclipse.imp.model.ModelFactory;
import org.eclipse.imp.parser.IMessageHandler;
import org.eclipse.imp.runtime.PluginBase;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.ocl.lpg.ProblemHandler;
import org.eclipse.qvt.declarative.editor.ui.ICreationFactory;
import org.eclipse.qvt.declarative.editor.ui.imp.CommonParseController;

/**
 * A builder may be activated on a file containing language code every time it
 * has changed (when "Build automatically" is on), or when the programmer
 * chooses to "Build" a project.
 */
public abstract class CommonBuilder extends BuilderBase
{
	/**
	 * A BuilderListener can be notified at the start and/or end of a build.
	 * This is mainly intended for test harnesses that need to observe builders.
	 */
	public static interface BuilderListener {
		public void beginBuild(IFile file);
		public void endBuild(IFile file);
	}
	
	private static Map<String, Map<IFile, List<BuilderListener>>> builderListenerMap = null;
	
	public static void addBuilderListener(String builderId, IFile file, BuilderListener listener) {
		if (builderListenerMap == null)
			builderListenerMap = new HashMap<String, Map<IFile, List<BuilderListener>>>();
		Map<IFile, List<BuilderListener>> map = builderListenerMap.get(builderId);
		if (map == null) {
			map = new HashMap<IFile, List<BuilderListener>>();
			builderListenerMap.put(builderId, map);
		}
		List<BuilderListener> builderListeners = map.get(file);
		if (builderListeners == null) {
			builderListeners = new ArrayList<BuilderListener>();
			map.put(file, builderListeners);
		}
		builderListeners.add(listener);
	}
	
	public static void removeBuilderListener(String builderId, IFile file, BuilderListener listener) {
		if (builderListenerMap != null) {
			Map<IFile, List<BuilderListener>> map = builderListenerMap.get(builderId);
			if (map != null) {
				List<BuilderListener> builderListeners = map.get(file);
				if (builderListeners != null) {
					builderListeners.remove(listener);
				}
			}
		}
	}
	
	public static void reset() {
		builderListenerMap = null;
	}
	
	protected final ICreationFactory creationFactory;

	protected CommonBuilder(ICreationFactory creationFactory) {
		this.creationFactory = creationFactory;
	}

	/**
	 * Collects compilation-unit dependencies for the given file, and records
	 * them via calls to <code>fDependency.addDependency()</code>.
	 */
	@Override
	protected void collectDependencies(IFile file) {
//		String fromPath = file.getFullPath().toString();	
//		getPlugin().writeInfoMsg("Collecting dependencies from ${LANG_NAME} file: " + file.getName());	
// T O D O : implement dependency collector
// E.g. for each dependency:
// fDependencyInfo.addDependency(fromPath, uponPath);
	}

	/**
	 * Compile one language file.
	 */
	@Override
	protected void compile(final IFile inputFile, IProgressMonitor monitor) {
		List<BuilderListener> builderListeners = null;
		if (builderListenerMap != null) {
			Map<IFile, List<BuilderListener>> map = builderListenerMap.get(creationFactory.getBuilderId());
			if (map != null)
				builderListeners = map.get(inputFile);
		}
		if (builderListeners != null)
			for (BuilderListener builderListener : builderListeners)
				builderListener.beginBuild(inputFile);
		IPath projectRelativeInputPath = inputFile.getProjectRelativePath();
		IPath workspaceRelativeOutputPath = getWorkspaceRelativeOutputFilePath(inputFile);
		IFile outputFile = getProject().getFile(workspaceRelativeOutputPath.removeFirstSegments(1));
		getPlugin().writeInfoMsg("Building " + creationFactory.getLanguageName() + " input file: '" + inputFile.getName() + "', output file: '" + outputFile.getName() + "'");
		ProblemHandler problemHandler = creationFactory.createProblemHandler(inputFile);
		try {
			CommonParseController parseController = createParseController();
			parseController.getAnnotationTypeInfo().addProblemMarkerType(getErrorMarkerID());
			ISourceProject sourceProject = ModelFactory.open(inputFile.getProject());
			parseController.initialize(projectRelativeInputPath, sourceProject, (IMessageHandler) problemHandler);
			String contents = BuilderUtils.getFileContents(inputFile);
			CommonParseController.ParsedResult parsedResult = parseController.parse(contents, false, monitor);
			URI uri = URI.createPlatformResourceURI(workspaceRelativeOutputPath.toString(), true);
			Resource resource = parsedResult.getAST();
			resource.setURI(uri);
			parsedResult.getRootEnvironment().validate();
			resource.save(null);
			doRefresh(outputFile.getParent());
		} catch (Exception e) {
			getPlugin().logException("Failed to compile '" + inputFile.toString() + "'", e);
		} finally {
			if (builderListeners != null)
				for (BuilderListener builderListener : builderListeners)
					builderListener.endBuild(inputFile);
			problemHandler.flush(BasicMonitor.toMonitor(monitor));
		}
	}

	protected CommonParseController createParseController() {
		return creationFactory.createParseController();
	}

	/**
	 * Return the classpath entries applicable to the current project.
	 * Returns null if not a Java project.
	 */
	protected IClasspathEntry[] getClasspathEntries(IProject project) {
		IJavaProject javaProject = JavaCore.create(project);
		if (javaProject == null)
			return null;
		try {
			return javaProject.getResolvedClasspath(true);
		} catch (JavaModelException e) {
			return null;
		}
	}

	/**
	 * Return the classpath entry applicable to the file.
	 * Returns null if none available.
	 */
	protected IClasspathEntry getClasspathEntry(IFile file, IClasspathEntry[] resolvedClasspath) {
		IPath workspaceRelativeInputPath = file.getFullPath();
		for (IClasspathEntry resolvedClasspathEntry : resolvedClasspath) {
			if (resolvedClasspathEntry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
				IPath sourcePath = resolvedClasspathEntry.getPath();
				if (sourcePath.isPrefixOf(workspaceRelativeInputPath))
					return resolvedClasspathEntry;
			}
		}
		return null;
	}

	public ICreationFactory getCreationFactory() {
		return creationFactory;
	}

	@Override
	public String getErrorMarkerID() {
		return creationFactory.getErrorMarkerId();
	}

	@Override
	public String getInfoMarkerID() {
		return creationFactory.getInfoMarkerId();
	}

	public Language getLanguage() {
		return creationFactory.getLanguage();
	}

	public String getLanguageName() {
		return creationFactory.getLanguageName();
	}

	@Override
	public PluginBase getPlugin() {
		return (PluginBase) creationFactory.getPlugin();
	}

	@Override
	public String getWarningMarkerID() {
		return creationFactory.getWarningMarkerId();
	}

	protected IPath getWorkspaceRelativeOutputFilePath(final IFile inputFile) {
		IPath workspaceRelativeInputPath = inputFile.getFullPath();
		IPath workspaceRelativeOutputPath = workspaceRelativeInputPath;
		IClasspathEntry[] resolvedClasspath = getClasspathEntries(inputFile.getProject());		
		IClasspathEntry classpathEntry = resolvedClasspath != null ? getClasspathEntry(inputFile, resolvedClasspath) : null;
		if (classpathEntry != null) {
			IPath sourcePath = classpathEntry.getPath();
			IPath outputPath = classpathEntry.getOutputLocation();
			workspaceRelativeOutputPath = outputPath != null ? outputPath.append(workspaceRelativeInputPath.removeFirstSegments(sourcePath.segmentCount())) : workspaceRelativeInputPath;
		}
		if (hasTextExtension(inputFile))
			workspaceRelativeOutputPath = workspaceRelativeOutputPath.removeFileExtension();
		return workspaceRelativeOutputPath.addFileExtension(creationFactory.getXMLExtension());
	}
	
	/**
	 * Return true if file has one if the text extensions defined by the
	 * user preferences and consequently is to be treated as a source file.
	 */
	protected boolean hasTextExtension(IFile file) {
		IPath workspaceRelativeInputPath = file.getFullPath();
		String fileExtension = workspaceRelativeInputPath.getFileExtension();
		for (String textExtension : creationFactory.getTextExtensions()) {
			if (textExtension.equals(fileExtension))
				return true;
		}
		return false;
	}

	/**
	 * Decide whether or not to scan a file for dependencies. Note:
	 * <code>isNonRootSourceFile()</code> and <code>isSourceFile()</code>
	 * should never return true for the same file.
	 * 
	 * @return true iff the given file is a source file that this builder should
	 *         scan for dependencies, but not compile as a top-level compilation
	 *         unit.
	 * 
	 */
	@Override
	protected boolean isNonRootSourceFile(IFile resource) {
		return false;
	}

	/**
	 * @return true iff this resource identifies an output folder
	 */
	@Override
	protected boolean isOutputFolder(IResource resource) {
		IClasspathEntry[] resolvedClasspath = getClasspathEntries(resource.getProject());		
		if (resolvedClasspath == null)
			return false;		// FIXME ???
		IPath workspaceRelativePath = resource.getFullPath();
		for (IClasspathEntry resolvedClasspathEntry : resolvedClasspath) {
			if (resolvedClasspathEntry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
				IPath outputPath = resolvedClasspathEntry.getOutputLocation();
				if ((outputPath != null) && outputPath.isPrefixOf(workspaceRelativePath))
					return true;
			}
		}
		return false;
	}

	/**
	 * Decide whether a file needs to be build using this builder. Note that
	 * <code>isNonRootSourceFile()</code> and <code>isSourceFile()</code>
	 * should never return true for the same file.
	 * 
	 * @return true iff an arbitrary file is a source file for this language.
	 */
	@Override
	protected boolean isSourceFile(IFile file) {
		if (!hasTextExtension(file))
			return false;
		IClasspathEntry[] resolvedClasspath = getClasspathEntries(file.getProject());		
		if (resolvedClasspath == null)
			return true;			// No Classpath entries so don't restrict usage 
		if (getClasspathEntry(file, resolvedClasspath) == null)
			return false;
		return true;
	}
}
