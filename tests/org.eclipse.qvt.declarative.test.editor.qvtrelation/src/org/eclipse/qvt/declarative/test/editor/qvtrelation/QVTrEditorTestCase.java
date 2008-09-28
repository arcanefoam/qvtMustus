package org.eclipse.qvt.declarative.test.editor.qvtrelation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.qvt.declarative.editor.qvtrelation.ui.QVTrCreationFactory;
import org.eclipse.qvt.declarative.modelregistry.environment.AbstractProjectHandle;
import org.eclipse.qvt.declarative.test.editor.EditorTestCase;

public class QVTrEditorTestCase extends EditorTestCase
{
	protected IFile createModelRegistryFile() throws CoreException {
		final String contents = 
			"<?xml version='1.0' encoding='ASCII'?>\n" +
			"<mreg:ModelRegistrySettings xmi:version='2.0' xmlns:xmi='http://www.omg.org/XMI' xmlns:mreg='http://www.eclipse.org/gmt/umlx/2007/ModelRegistry'>\n" +
			"  <resource name='.'>\n" +
			"    <entry accessor='Ecore' kind='Model Name' uri='platform:/plugin/org.eclipse.emf.ecore/model/Ecore.ecore'/>\n" +
			"  </resource>\n" +
			"</mreg:ModelRegistrySettings>\n";
		final String testFileName = AbstractProjectHandle.DEFAULT_MODEL_REGISTRY_PATH;
		IFile file = createFile(testFileName, contents);
		return file;
	}

	protected IFile createMinimalTestFile() throws CoreException {
		final String contents = "transformation tx0(ecore:Ecore) {}\n";
		final String testFileName = getName() + " .qvtr";
		IFile file = createFile(testFileName, contents);
		return file;
	}

	@Override
	protected String getEditorId() {
		return QVTrCreationFactory.EDITOR_ID;
	}

	@Override
	protected String getMultiEditorId() {
		return QVTrCreationFactory.MULTI_EDITOR_ID;
	}
}
