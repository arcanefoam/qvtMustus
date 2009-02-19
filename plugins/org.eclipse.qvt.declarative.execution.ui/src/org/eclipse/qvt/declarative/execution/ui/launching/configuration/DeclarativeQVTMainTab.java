/**
 * <copyright>
 * 
 * Copyright (c) 2007, 2008 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * </copyright>
 * 
 * Contributors:
 *     Quentin Glineur - initial API and implementation
 *
 * $Id: DeclarativeQVTMainTab.java,v 1.4 2009/02/19 18:28:56 qglineur Exp $
 */
package org.eclipse.qvt.declarative.execution.ui.launching.configuration;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.emf.common.ui.dialogs.ResourceDialog;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.qvt.declarative.ecore.QVTBase.TypedModel;
import org.eclipse.qvt.declarative.ecore.QVTRelation.RelationalTransformation;
import org.eclipse.qvt.declarative.editor.qvtcore.ui.QVTcCreationFactory;
import org.eclipse.qvt.declarative.editor.qvtrelation.ui.QVTrCreationFactory;
import org.eclipse.qvt.declarative.execution.ide.DeclarativeQVTLaunchDelegate;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

public class DeclarativeQVTMainTab extends AbstractLaunchConfigurationTab {

	public static String TAB_NAME = "Main";

	private Text projectText;

	private Button projectButton;

	private Text transformationText;

	private Button transformationButton;

	private Composite modelsComposite;

	private Map<String, List<Widget>> dynamicModelsWidget = new HashMap<String, List<Widget>>();

	private IFile currentTransformationFile;

	private IProject currentProject;

	private RelationalTransformation currentRelationalTransformation;

	private class TransformationSelectionListener implements SelectionListener {

		Composite parent = null;

		public TransformationSelectionListener(Composite parent) {
			this.parent = parent;
		}

		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void widgetSelected(SelectionEvent e) {
			currentRelationalTransformation = handleSearchButtonSelected();
			if (currentRelationalTransformation != null) {
				createAllModelEditors(parent, currentRelationalTransformation);
			}
			updateLaunchConfigurationDialog();
		}
	}

	private class ModelButtonSelectionListener implements SelectionListener {

		Text relatedText;
		int style;

		public ModelButtonSelectionListener(Text relatedText, int style) {
			this.relatedText = relatedText;
			this.style = style;
		}

		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void widgetSelected(SelectionEvent e) {
			ResourceDialog rd = new ResourceDialog(getShell(),
					"Model Selection", style | SWT.SINGLE);
			rd.open();
			if (rd.getReturnCode() == Window.OK) {
				relatedText.setText(rd.getURIText());
			}
			updateLaunchConfigurationDialog();
		}
	}

	private class TargetButtonSelectionListener implements SelectionListener {

		public TargetButtonSelectionListener() {
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub

		}

		public void widgetSelected(SelectionEvent e) {
			for (Map.Entry<String, List<Widget>> entry : dynamicModelsWidget
					.entrySet()) {
				List<Widget> widgetList = entry.getValue();
				Widget widget = widgetList.get(3);
				if (widget instanceof Button) {
					Button newModelButton = (Button) widget;
					if (e.getSource() == widgetList.get(0)) {
						newModelButton.setEnabled(true);
					} else {
						newModelButton.setEnabled(false);
					}
				}
			}
		}
	}

	private SelectionListener targetButtonsSelectionListener = new TargetButtonSelectionListener();

	public void createControl(Composite parent) {
		Composite comp = createMainComposite(parent);
		createProjectEditor(comp);
		createVerticalSpacer(comp, 1);
		createTransformationEditor(comp, "Transformation");
		setControl(comp);
	}

	private static Composite createComposite(Composite parent, Font font,
			int columns, int hspan, int fill) {
		Composite g = new Composite(parent, SWT.NONE);
		g.setLayout(new GridLayout(columns, false));
		g.setFont(font);
		GridData gd = new GridData(fill);
		gd.horizontalSpan = hspan;
		g.setLayoutData(gd);
		return g;
	}

	private static Group createGroup(Composite parent, String text,
			int columns, int hspan, int fill) {
		Group g = new Group(parent, SWT.NONE);
		g.setLayout(new GridLayout(columns, false));
		g.setText(text);
		g.setFont(parent.getFont());
		GridData gd = new GridData(fill);
		gd.horizontalSpan = hspan;
		g.setLayoutData(gd);
		return g;
	}

	private static void createLabel(Composite parent, String text,
			int layoutStyle) {
		Label tempLabel;
		tempLabel = new Label(parent, SWT.CENTER);
		tempLabel.setText(text);
		tempLabel.setLayoutData(new GridData(layoutStyle));
	}

	private static Composite createMainComposite(Composite parent) {
		Composite comp = createComposite(parent, parent.getFont(), 2, 1,
				GridData.FILL_HORIZONTAL);
		((GridLayout) comp.getLayout()).verticalSpacing = 0;
		return comp;
	}

	private static Text createSingleText(Composite parent, int hspan) {
		Text t = new Text(parent, SWT.SINGLE | SWT.BORDER);
		t.setFont(parent.getFont());
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = hspan;
		t.setLayoutData(gd);
		return t;
	}

	private static IWorkspaceRoot getWorkspaceRoot() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}

	private void createProjectEditor(Composite parent) {
		Font font = parent.getFont();
		Group group = new Group(parent, SWT.NONE);
		group.setText("Project");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayoutData(gd);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		group.setLayout(layout);
		group.setFont(font);
		projectText = new Text(group, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		projectText.setLayoutData(gd);
		projectText.setFont(font);
		projectText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}

		});

		projectButton = createPushButton(group, "Browse...", null);
		projectButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				handleProjectButtonSelected();
				updateLaunchConfigurationDialog();
			}
		});
	}

	private void createTransformationEditor(Composite parent, String text) {
		Group mainGroup = createGroup(parent, text, 2, 1,
				GridData.FILL_HORIZONTAL);
		Composite comp = createComposite(mainGroup, parent.getFont(), 2, 2,
				GridData.FILL_BOTH);
		removeCompositeMargin(comp);
		transformationText = createSingleText(comp, 1);
		transformationText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
		transformationButton = createPushButton(comp, "Search...", null);
		transformationButton
				.addSelectionListener(new TransformationSelectionListener(comp));
	}

	private RelationalTransformation handleSearchButtonSelected() {
		IProject project = getCurrentProject();
		IContainer searchScope = null;
		if (project != null && project.exists()) {
			searchScope = getCurrentProject();
		} else {
			searchScope = getWorkspaceRoot();
		}
		if (searchScope != null) {
			DeclarativeQVTFileFetcher fetcher = new DeclarativeQVTFileFetcher(
					searchScope);
			Set<IFile> qvtFiles = fetcher.getQVTfiles();
			TransformationSelectionDialog tsd = new TransformationSelectionDialog(
					getShell(), qvtFiles);
			tsd.setListLabelProvider(new DeclarativeQVTElementLabelProvider());
			if (tsd.open() == Window.OK) {
				Object selection = tsd.getFirstResult();
				if (selection instanceof IFile) {
					IFile file = (IFile) selection;
					currentTransformationFile = file;
					currentProject = file.getProject();
					transformationText.setText(file.getName());
					projectText.setText(file.getProject().getName());
					Resource modelResource = getModelResource(file);
					return getTransformation(modelResource);
				}
			}
		}
		return null;
	}

	private Resource getModelResource(IFile file) {
		IFile astFile = getASTFile(file);
		URI modelURI = URI.createPlatformResourceURI(astFile.getFullPath()
				.toString(), true);
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource modelResource = resourceSet.getResource(modelURI, true);
		return modelResource;
	}

	private RelationalTransformation getTransformation(Resource modelResource) {
		EObject object = modelResource.getContents().get(0);
		if (object instanceof RelationalTransformation) {
			return (RelationalTransformation) object;
		}
		return null;
	}

	private void createAllModelEditors(Composite parent,
			RelationalTransformation transformation) {
		if (modelsComposite != null) {
			modelsComposite.dispose();
		}
		modelsComposite = createComposite(parent, parent.getFont(), 3, 2,
				GridData.FILL_BOTH);
		((GridLayout) modelsComposite.getLayout()).verticalSpacing = 0;
		createLabel(modelsComposite, "Model", SWT.NONE);
		createLabel(modelsComposite, "Target", SWT.NONE);
		createLabel(modelsComposite, "Location", GridData.FILL_HORIZONTAL);
		dynamicModelsWidget.clear();

		int count = 0;
		List<TypedModel> models = transformation.getModelParameter();
		int lastModelIndex = models.size() - 1;
		for (TypedModel typedModel : models) {
			createModelEditor(modelsComposite, typedModel,
					count == lastModelIndex);
			count++;
		}
		updateLaunchConfigurationDialog();
	}

	private void removeCompositeMargin(Composite composite) {
		GridLayout gl = (GridLayout) composite.getLayout();
		gl.marginWidth = 0;
		gl.marginHeight = 0;
	}

	private void createModelEditor(Composite parent, TypedModel typedModel,
			boolean isTarget) {
		Label label = new Label(parent, SWT.CENTER);
		String modelName = typedModel.getName();
		label.setText(modelName);
		Button targetButton = new Button(parent, SWT.RADIO);
		targetButton.setSelection(isTarget);
		Composite subComposite = createComposite(parent, parent.getFont(), 4,
				1, GridData.FILL_BOTH);
		removeCompositeMargin(subComposite);
		Text text = createSingleText(subComposite, 1);
		text.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}

		});
		Button searchModelButton = createPushButton(subComposite, "Search",
				null);
		searchModelButton
				.addSelectionListener(new ModelButtonSelectionListener(text,
						SWT.OPEN));
		Button newModelButton = createPushButton(subComposite, "New...", null);
		newModelButton.addSelectionListener(new ModelButtonSelectionListener(
				text, SWT.SAVE));
		newModelButton.setEnabled(isTarget);
		targetButton.addSelectionListener(targetButtonsSelectionListener);
		List<Widget> modelWidgets = new ArrayList<Widget>();
		modelWidgets.add(targetButton);
		modelWidgets.add(text);
		modelWidgets.add(searchModelButton);
		modelWidgets.add(newModelButton);
		dynamicModelsWidget.put(modelName, modelWidgets);
	}

	private void handleProjectButtonSelected() {
		ILabelProvider labelProvider = new DeclarativeQVTElementLabelProvider();
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(
				getShell(), labelProvider);
		dialog.setTitle("Project Selection");
		dialog.setMessage("Select a project to constrain your search.");
		IProject[] declarativeQVTProjects = getDeclarativeQVTProjects();
		dialog.setElements(declarativeQVTProjects);
		IProject currentProject = null;
		boolean projectFound = false;
		for (int i = 0; i < declarativeQVTProjects.length && !projectFound; i++) {
			IProject project = declarativeQVTProjects[i];
			if (project.getName().equals(projectText.getText().trim())) {
				currentProject = project;
				projectFound = true;
			}
		}
		if (projectFound) {
			dialog.setInitialSelections(new Object[] { currentProject });
		}
		IProject selectedProject = null;
		if (dialog.open() == Window.OK) {
			selectedProject = (IProject) dialog.getFirstResult();
		}
		if (selectedProject != null) {
			projectText.setText(selectedProject.getName());
		}
	}

	private IProject getCurrentProject() {
		String currentProjectName = projectText.getText().trim();
		for (IProject project : getWorkspaceRoot().getProjects()) {
			if (project.getName().equals(currentProjectName)) {
				return project;
			}
		}
		return null;
	}

	private IProject[] getDeclarativeQVTProjects() {
		List<IProject> qvtProjectList = new ArrayList<IProject>();
		for (IProject project : getWorkspaceRoot().getProjects()) {
			try {
				if (project.getNature(QVTrCreationFactory.NATURE_ID) != null
						|| project.getNature(QVTcCreationFactory.NATURE_ID) != null) {
					qvtProjectList.add(project);
				}
			} catch (CoreException e) {

			}
		}
		return qvtProjectList.toArray(new IProject[] {});
	}

	public String getName() {
		return TAB_NAME;
	}

	private static IFile getFileFromURI(String uriString)
			throws URISyntaxException {
		java.net.URI uri = new java.net.URI(uriString);
		String path = uri.toString();
		if (uri.getScheme().equals("file")) {
			path = new File(uri).toString();
		}
		IFile iFile = getWorkspaceRoot().getFileForLocation(new Path(path));
		return iFile;
	}

	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			String executablePath = configuration
					.getAttribute(
							DeclarativeQVTLaunchDelegate.EXECUTABLE_PATH_ATTRIBUTE_NAME,
							DeclarativeQVTLaunchDelegate.EMPTY_STRING);
			List<?> modelNameList = configuration
					.getAttribute(
							DeclarativeQVTLaunchDelegate.MODEL_NAME_LIST_ATTRIBUTE_NAME,
							Collections.EMPTY_LIST);
			List<?> modelPathList = configuration
					.getAttribute(
							DeclarativeQVTLaunchDelegate.MODEL_PATH_LIST_ATTRIBUTE_NAME,
							Collections.EMPTY_LIST);
			String directionModel = configuration
					.getAttribute(
							DeclarativeQVTLaunchDelegate.DIRECTION_MODEL_PATH_ATTRIBUTE_NAME,
							DeclarativeQVTLaunchDelegate.EMPTY_STRING);

			if (executablePath == null
					|| executablePath == DeclarativeQVTLaunchDelegate.EMPTY_STRING)
				return;

			IFile sourceFile = getSourceFile(executablePath);

			if (sourceFile == null)
				return;

			currentProject = sourceFile.getProject();
			projectText.setText(currentProject.getName());
			currentTransformationFile = sourceFile;
			currentRelationalTransformation = getTransformation(getModelResource(currentTransformationFile));
			transformationText.setText(currentTransformationFile.getName());
			createAllModelEditors(
					transformationText.getParent(),
					getTransformation(getModelResource(currentTransformationFile)));

			for (Object modelNameObject : modelNameList) {
				int index = modelNameList.indexOf(modelNameObject);
				String modelname = (String) modelNameObject;
				List<Widget> widgetList = dynamicModelsWidget.get(modelname);
				getTargetWiget(widgetList).setSelection(
						modelname.equals(directionModel));
				String modelPath = (String) modelPathList.get(index);
				IFile modelFile = getFileFromURI(modelPath);
				if (modelFile != null) {
					String relativePath = URI.createPlatformPluginURI(
							modelFile.getFullPath().toString(), true)
							.toString();
					getModelPathWiget(widgetList).setText(relativePath);
				}
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Button getTargetWiget(List<Widget> widgetList) {
		Widget widget = widgetList.get(0);
		if (widget instanceof Button) {
			return (Button) widget;
		}
		return null;
	}

	private Text getModelPathWiget(List<Widget> widgetList) {
		Widget widget = widgetList.get(1);
		if (widget instanceof Text) {
			return (Text) widget;
		}
		return null;
	}

	private boolean isTargetModel(List<Widget> widgetList) {
		Button button = getTargetWiget(widgetList);
		if (button != null) {
			return button.getSelection();
		}
		return false;
	}

	private String getModelURIString(List<Widget> widgetList) {
		Text text = getModelPathWiget(widgetList);
		if (text != null) {
			return text.getText();
		}
		return null;
	}

	private static IFile getASTFile(IFile sourceFile) {
		IJavaProject javaProject = JavaCore.create(sourceFile.getProject());
		IClasspathEntry srcContainer = null;
		IPath currentTransformationPath = sourceFile.getFullPath();
		try {
			for (IClasspathEntry classpathEntry : javaProject.getRawClasspath()) {
				if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
					IPath classPathEntryPath = classpathEntry.getPath();
					if (classPathEntryPath
							.isPrefixOf(currentTransformationPath)) {
						srcContainer = classpathEntry;
					}
				}
			}
			if (srcContainer != null) {
				IPath relativeTransformationPath = currentTransformationPath
						.removeFirstSegments(srcContainer.getPath()
								.segmentCount());
				IPath binPath = srcContainer.getOutputLocation();
				IPath ASTPath = binPath.append(relativeTransformationPath)
						.removeFileExtension().addFileExtension("eqvtrelation");
				IFile ASTFile = getWorkspaceRoot().getFile(ASTPath);
				return ASTFile;
			}

		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static IFile getSourceFile(String executablePath) {
		try {
			IFile executableFile = getFileFromURI(executablePath);
			IJavaProject javaProject = JavaCore.create(executableFile
					.getProject());
			IPath executableRelativePath = executableFile.getFullPath()
					.removeFirstSegments(
							javaProject.getOutputLocation().segmentCount());
			IPath sourceRelativePath = executableRelativePath
					.removeFileExtension().removeFileExtension()
					.addFileExtension("qvtr");
			for (IClasspathEntry classpathEntry : javaProject.getRawClasspath()) {
				if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
					IPath classPathPath = classpathEntry.getPath();
					IFolder classPathFolder = getWorkspaceRoot().getFolder(
							classPathPath);
					IFile searchedFile = classPathFolder
							.getFile(sourceRelativePath);
					if (searchedFile.exists()) {
						return searchedFile;
					}
				}
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		if (currentProject != null && currentTransformationFile != null) {

			String direction = null;
			List<String> modelNameList = new ArrayList<String>();
			List<String> modelPathList = new ArrayList<String>();
			List<String> metamodelNameList = new ArrayList<String>();
			List<String> metamodelPathList = new ArrayList<String>();

			for (Map.Entry<String, List<Widget>> entry : dynamicModelsWidget
					.entrySet()) {
				List<Widget> widgetList = entry.getValue();

				String modelName = entry.getKey();
				modelNameList.add(modelName);

				String modelURI = getModelURIString(widgetList);
				modelPathList.add(modelURI);

				if (isTargetModel(widgetList)) {
					direction = modelName;
				}

				if (currentRelationalTransformation != null) {

					TypedModel typedModel = currentRelationalTransformation
							.getModelParameter(modelName);
					EPackage ePackage = typedModel.getUsedPackage().get(0);
					URI metamodelURI = ePackage.eResource().getURI()
							.isPlatformPlugin() ? URI.createURI(ePackage
							.getNsURI()) : ePackage.eResource().getURI();
					metamodelNameList.add(modelName + "MM");
					metamodelPathList.add(metamodelURI.toString());

				}
			}
			String absoluteExecutablePath = currentTransformationFile
					.getFullPath().toString();

			configuration
					.setAttribute(
							DeclarativeQVTLaunchDelegate.EXECUTABLE_PATH_ATTRIBUTE_NAME,
							absoluteExecutablePath);
			configuration
					.setAttribute(
							DeclarativeQVTLaunchDelegate.MODEL_NAME_LIST_ATTRIBUTE_NAME,
							modelNameList);
			configuration
					.setAttribute(
							DeclarativeQVTLaunchDelegate.MODEL_PATH_LIST_ATTRIBUTE_NAME,
							modelPathList);
			configuration
					.setAttribute(
							DeclarativeQVTLaunchDelegate.METAMODEL_NAME_LIST_ATTRIBUTE_NAME,
							metamodelNameList);
			configuration
					.setAttribute(
							DeclarativeQVTLaunchDelegate.METAMODEL_PATH_LIST_ATTRIBUTE_NAME,
							metamodelPathList);
			configuration
					.setAttribute(
							DeclarativeQVTLaunchDelegate.DIRECTION_MODEL_PATH_ATTRIBUTE_NAME,
							direction);
			configuration.setAttribute(
					DeclarativeQVTLaunchDelegate.EXECUTION_MODE_ATTRIBUTE_NAME,
					"enforcement");
		}

	}

	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		System.out.println("setDefaults");

	}

	private boolean isProjectValid() {
		String projectTextValue = projectText.getText().trim();
		if (projectTextValue.length() == 0) {
			setErrorMessage("No project specified.");
			return false;
		} else {
			if (getCurrentProject() == null) {
				setErrorMessage("Project " + projectTextValue
						+ " does not exit.");
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		if (!isProjectValid()) {
			return false;
		}
		String transformationTextValue = transformationText.getText().trim();
		if (transformationTextValue.length() == 0) {
			setErrorMessage("No transformation specified");
			return false;
		}
		for (Map.Entry<String, List<Widget>> modelEditor : dynamicModelsWidget
				.entrySet()) {
			List<Widget> wigetList = modelEditor.getValue();
			if (getModelPathWiget(wigetList).getText().trim().length() == 0) {
				setErrorMessage("Path to model " + modelEditor.getKey()
						+ " not specified");
				return false;
			}
		}
		setErrorMessage(null);
		setMessage("Launch Declarative QVT Transformation");
		return true;
	}

}
