
/**
 * <copyright>
 *
 * Copyright (c) 2012 E.D.Willink and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     E.D.Willink - initial API and implementation
 *
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.qvtd.xtext.qvtrelation.ui;

import org.eclipse.xtext.ui.DefaultUiModule;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * Manual modifications go to {org.eclipse.qvtd.xtext.qvtrelation.ui.QVTrelationUiModule}
 */
@SuppressWarnings("all")
public abstract class AbstractQVTrelationUiModule extends DefaultUiModule {
	
	public AbstractQVTrelationUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}
	
	
	// contributed by org.eclipse.xtext.ui.generator.ImplicitUiFragment
	public com.google.inject.Provider<org.eclipse.xtext.resource.containers.IAllContainersState> provideIAllContainersState() {
		return org.eclipse.xtext.ui.shared.Access.getJavaProjectsState();
	}

	// contributed by org.eclipse.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment
	public Class<? extends org.eclipse.xtext.ui.editor.contentassist.IProposalConflictHelper> bindIProposalConflictHelper() {
		return org.eclipse.xtext.ui.editor.contentassist.antlr.AntlrProposalConflictHelper.class;
	}

	// contributed by org.eclipse.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment
	public void configureHighlightingLexer(com.google.inject.Binder binder) {
		binder.bind(org.eclipse.xtext.parser.antlr.Lexer.class).annotatedWith(com.google.inject.name.Names.named(org.eclipse.xtext.ui.LexerUIBindings.HIGHLIGHTING)).to(org.eclipse.qvtd.xtext.qvtrelation.parser.antlr.internal.InternalQVTrelationLexer.class);
	}

	// contributed by org.eclipse.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment
	public void configureHighlightingTokenDefProvider(com.google.inject.Binder binder) {
		binder.bind(org.eclipse.xtext.parser.antlr.ITokenDefProvider.class).annotatedWith(com.google.inject.name.Names.named(org.eclipse.xtext.ui.LexerUIBindings.HIGHLIGHTING)).to(org.eclipse.xtext.parser.antlr.AntlrTokenDefProvider.class);
	}

	// contributed by org.eclipse.xtext.generator.exporting.QualifiedNamesFragment
	public Class<? extends org.eclipse.xtext.ui.editor.contentassist.PrefixMatcher> bindPrefixMatcher() {
		return org.eclipse.xtext.ui.editor.contentassist.FQNPrefixMatcher.class;
	}

	// contributed by org.eclipse.xtext.generator.exporting.QualifiedNamesFragment
	public Class<? extends org.eclipse.xtext.ui.refactoring.IDependentElementsCalculator> bindIDependentElementsCalculator() {
		return org.eclipse.xtext.ui.refactoring.impl.DefaultDependentElementsCalculator.class;
	}

	// contributed by org.eclipse.xtext.ui.generator.labeling.LabelProviderFragment
	public Class<? extends org.eclipse.jface.viewers.ILabelProvider> bindILabelProvider() {
		return org.eclipse.qvtd.xtext.qvtrelation.ui.labeling.QVTrelationLabelProvider.class;
	}

	// contributed by org.eclipse.xtext.ui.generator.labeling.LabelProviderFragment
	public void configureResourceUIServiceLabelProvider(com.google.inject.Binder binder) {
		binder.bind(org.eclipse.jface.viewers.ILabelProvider.class).annotatedWith(org.eclipse.xtext.ui.resource.ResourceServiceDescriptionLabelProvider.class).to(org.eclipse.qvtd.xtext.qvtrelation.ui.labeling.QVTrelationDescriptionLabelProvider.class);
	}

	// contributed by org.eclipse.xtext.ui.generator.outline.OutlineTreeProviderFragment
	public Class<? extends org.eclipse.xtext.ui.editor.outline.IOutlineTreeProvider> bindIOutlineTreeProvider() {
		return org.eclipse.qvtd.xtext.qvtrelation.ui.outline.QVTrelationOutlineTreeProvider.class;
	}

	// contributed by org.eclipse.xtext.ui.generator.outline.OutlineTreeProviderFragment
	public Class<? extends org.eclipse.xtext.ui.editor.outline.impl.IOutlineTreeStructureProvider> bindIOutlineTreeStructureProvider() {
		return org.eclipse.qvtd.xtext.qvtrelation.ui.outline.QVTrelationOutlineTreeProvider.class;
	}

	// contributed by org.eclipse.xtext.ui.generator.contentAssist.JavaBasedContentAssistFragment
	public Class<? extends org.eclipse.xtext.ui.editor.contentassist.IContentProposalProvider> bindIContentProposalProvider() {
		return org.eclipse.qvtd.xtext.qvtrelation.ui.contentassist.QVTrelationProposalProvider.class;
	}

	// contributed by org.eclipse.xtext.generator.parser.antlr.XtextAntlrUiGeneratorFragment
	public Class<? extends org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext.Factory> bindContentAssistContext$Factory() {
		return org.eclipse.xtext.ui.editor.contentassist.antlr.ParserBasedContentAssistContextFactory.class;
	}

	// contributed by org.eclipse.xtext.generator.parser.antlr.XtextAntlrUiGeneratorFragment
	public Class<? extends org.eclipse.xtext.ui.editor.contentassist.antlr.IContentAssistParser> bindIContentAssistParser() {
		return org.eclipse.qvtd.xtext.qvtrelation.ui.contentassist.antlr.QVTrelationParser.class;
	}

	// contributed by org.eclipse.xtext.generator.parser.antlr.XtextAntlrUiGeneratorFragment
	public void configureContentAssistLexerProvider(com.google.inject.Binder binder) {
		binder.bind(org.eclipse.qvtd.xtext.qvtrelation.ui.contentassist.antlr.internal.InternalQVTrelationLexer.class).toProvider(org.eclipse.xtext.parser.antlr.LexerProvider.create(org.eclipse.qvtd.xtext.qvtrelation.ui.contentassist.antlr.internal.InternalQVTrelationLexer.class));
	}

	// contributed by org.eclipse.xtext.generator.parser.antlr.XtextAntlrUiGeneratorFragment
	public void configureContentAssistLexer(com.google.inject.Binder binder) {
		binder.bind(org.eclipse.xtext.ui.editor.contentassist.antlr.internal.Lexer.class).annotatedWith(com.google.inject.name.Names.named(org.eclipse.xtext.ui.LexerUIBindings.CONTENT_ASSIST)).to(org.eclipse.qvtd.xtext.qvtrelation.ui.contentassist.antlr.internal.InternalQVTrelationLexer.class);
	}

	// contributed by org.eclipse.xtext.generator.builder.BuilderIntegrationFragment
	public void configureIResourceDescriptionsBuilderScope(com.google.inject.Binder binder) {
		binder.bind(org.eclipse.xtext.resource.IResourceDescriptions.class).annotatedWith(com.google.inject.name.Names.named(org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider.NAMED_BUILDER_SCOPE)).to(org.eclipse.xtext.builder.clustering.CurrentDescriptions.ResourceSetAware.class);
	}

	// contributed by org.eclipse.xtext.generator.builder.BuilderIntegrationFragment
	public void configureIResourceDescriptionsPersisted(com.google.inject.Binder binder) {
		binder.bind(org.eclipse.xtext.resource.IResourceDescriptions.class).annotatedWith(com.google.inject.name.Names.named(org.eclipse.xtext.builder.impl.PersistentDataAwareDirtyResource.PERSISTED_DESCRIPTIONS)).to(org.eclipse.xtext.builder.builderState.IBuilderState.class);
	}

	// contributed by org.eclipse.xtext.generator.builder.BuilderIntegrationFragment
	public Class<? extends org.eclipse.xtext.ui.editor.DocumentBasedDirtyResource> bindDocumentBasedDirtyResource() {
		return org.eclipse.xtext.builder.impl.PersistentDataAwareDirtyResource.class;
	}

	// contributed by org.eclipse.xtext.ui.generator.quickfix.QuickfixProviderFragment
	public Class<? extends org.eclipse.xtext.ui.editor.quickfix.IssueResolutionProvider> bindIssueResolutionProvider() {
		return org.eclipse.qvtd.xtext.qvtrelation.ui.quickfix.QVTrelationQuickfixProvider.class;
	}

	// contributed by org.eclipse.ocl.examples.build.fragments.EssentialOCLFragment
	public Class<? extends org.eclipse.xtext.ui.editor.syntaxcoloring.AbstractAntlrTokenToAttributeIdMapper> bindAbstractAntlrTokenToAttributeIdMapper() {
		return org.eclipse.ocl.examples.xtext.essentialocl.ui.syntaxcoloring.BaseAntlrTokenToAttributeIdMapper.class;
	}

	// contributed by org.eclipse.ocl.examples.build.fragments.EssentialOCLFragment
	public Class<? extends org.eclipse.xtext.ui.editor.autoedit.AbstractEditStrategyProvider> bindAbstractEditStrategyProvider() {
		return org.eclipse.ocl.examples.xtext.essentialocl.ui.autoedit.BaseAutoEditStrategyProvider.class;
	}

	// contributed by org.eclipse.ocl.examples.build.fragments.EssentialOCLFragment
	public Class<? extends org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration> bindIHighlightingConfiguration() {
		return org.eclipse.ocl.examples.xtext.essentialocl.ui.syntaxcoloring.EssentialOCLHighlightingConfiguration.class;
	}

	// contributed by org.eclipse.ocl.examples.build.fragments.EssentialOCLFragment
	public Class<? extends org.eclipse.xtext.resource.ILocationInFileProvider> bindILocationInFileProvider() {
		return org.eclipse.ocl.examples.xtext.base.pivot2cs.BaseLocationInFileProvider.class;
	}

	// contributed by org.eclipse.ocl.examples.build.fragments.EssentialOCLFragment
	public Class<? extends org.eclipse.xtext.ui.editor.syntaxcoloring.ISemanticHighlightingCalculator> bindISemanticHighlightingCalculator() {
		return org.eclipse.ocl.examples.xtext.essentialocl.ui.syntaxcoloring.EssentialOCLSemanticHighlightingCalculator.class;
	}

	// contributed by org.eclipse.ocl.examples.build.fragments.EssentialOCLFragment
	public Class<? extends org.eclipse.xtext.ui.editor.model.ITokenTypeToPartitionTypeMapper> bindITokenTypeToPartitionTypeMapper() {
		return org.eclipse.ocl.examples.xtext.essentialocl.ui.model.BaseTerminalsTokenTypeToPartitionMapper.class;
	}

	// contributed by org.eclipse.ocl.examples.build.fragments.EssentialOCLFragment
	public Class<? extends org.eclipse.xtext.ui.editor.IURIEditorOpener> bindIURIEditorOpener() {
		return org.eclipse.ocl.examples.xtext.essentialocl.ui.model.BaseURIEditorOpener.class;
	}

	// contributed by org.eclipse.ocl.examples.build.fragments.EssentialOCLFragment
	public Class<? extends org.eclipse.xtext.ui.editor.IXtextEditorCallback> bindIXtextEditorCallback() {
		return org.eclipse.xtext.ui.editor.validation.ValidatingEditorCallback.class;
	}

	// contributed by org.eclipse.ocl.examples.build.fragments.EssentialOCLFragment
	public Class<? extends org.eclipse.xtext.ui.editor.model.XtextDocument> bindXtextDocument() {
		return org.eclipse.ocl.examples.xtext.essentialocl.ui.model.BaseDocument.class;
	}

	// contributed by org.eclipse.ocl.examples.build.fragments.MarkupHoverFragment
	public Class<? extends org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider> bindIEObjectHoverProvider() {
		return org.eclipse.ocl.examples.xtext.markup.ui.hover.MarkupHoverProvider.class;
	}

	// contributed by org.eclipse.ocl.examples.build.fragments.MarkupHoverFragment
	public Class<? extends org.eclipse.xtext.documentation.IEObjectDocumentationProvider> bindIEObjectDocumentationProvider() {
		return org.eclipse.ocl.examples.xtext.essentialocl.ui.model.BaseDocumentationProvider.class;
	}


}