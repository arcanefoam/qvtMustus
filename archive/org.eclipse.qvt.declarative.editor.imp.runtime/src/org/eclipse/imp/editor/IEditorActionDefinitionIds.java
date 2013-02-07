package org.eclipse.imp.editor;

/**
 * This interface houses action definition IDs, which match command IDs, so that they
 * can be bound to keystrokes in the UI.
 * @author rfuhrer@watson.ibm.com
 */
public interface IEditorActionDefinitionIds {
    /**
     * Action definition ID of the navigate -> Show Outline action
     * (value <code>"org.eclipse.imp.runtime.editor.showOutline"</code>).
     * 
     * @since 0.1
     */
    public static final String SHOW_OUTLINE= "org.eclipse.imp.runtime.editor.showOutline"; //$NON-NLS-1$
    
    /**
     * Action definition id of the collapse members action
     * (value: <code>"org.eclipse.imp.runtime.editor.folding.collapseMembers"</code>).
     * @since 0.1
     */
    public static final String FOLDING_COLLAPSE_MEMBERS= "org.eclipse.imp.runtime.editor.folding.collapseMembers"; //$NON-NLS-1$

    /**
     * Action definition id of the collapse comments action
     * (value: <code>"org.eclipse.imp.runtime.editor.folding.collapseComments"</code>).
     * @since 0.1
     */
    public static final String FOLDING_COLLAPSE_COMMENTS= "org.eclipse.imp.runtime.editor.folding.collapseComments"; //$NON-NLS-1$

    /**
     * Source menu: id of standard Format global action
     * (value <code>"org.eclipse.imp.runtime.editor.formatSource"</code>).
     */
    public static final String FORMAT= "org.eclipse.imp.runtime.editor.formatSource"; //$NON-NLS-1$
}
