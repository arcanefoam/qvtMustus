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
 * $Id: CommonFoldingUpdater.java,v 1.6 2009/08/20 20:17:34 ewillink Exp $
 */
package org.eclipse.qvt.declarative.editor.ui.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lpg.lpgjavaruntime.Adjunct;
import lpg.lpgjavaruntime.IToken;
import lpg.lpgjavaruntime.LexStream;
import lpg.lpgjavaruntime.PrsStream;

import org.eclipse.imp.services.base.FolderBase;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.ocl.cst.CSTNode;
import org.eclipse.qvt.declarative.editor.FoldingBehavior;
import org.eclipse.qvt.declarative.parser.utils.CommonASTVisitor;

/**
 * CommonFoldingUpdater provides the FoldingUpdater functionality for
 * an IMP editor, using the EditorDefinition model to configure behavior.
 */
public abstract class CommonFoldingUpdater extends FolderBase
{
	/*
	 * A visitor for ASTs.  Its purpose is to create ProjectionAnnotations
	 * for regions of text corresponding to various types of AST node or to
	 * text ranges computed from AST nodes.  Projection annotations appear
	 * in the editor as the widgets that control folding.
	 */
	protected class FoldingCSTVisitor extends CommonASTVisitor<Object, CSTNode>
	{
		protected final CommonEditorDefinition editorDefinition;
		
		public FoldingCSTVisitor(CommonEditorDefinition editorDefinition) {
			super(CSTNode.class);
			this.editorDefinition = editorDefinition;
		}

		@Override
		public boolean preVisit(CSTNode cstNode) {
			FoldingBehavior behavior = editorDefinition.getBehavior(cstNode, FoldingBehavior.class);
			if (behavior != null)
				makeAnnotation(cstNode);
			return true;
		}
	};

	protected PrsStream prsStream = null;

	protected abstract ICommonPlugin getPlugin();

	protected void makeAdjunctAnnotations(CSTNode theAST) {
		LexStream lexStream = prsStream.getLexStream();
		if (lexStream == null)
			return;
		@SuppressWarnings("unchecked")
		ArrayList<Adjunct> adjuncts = prsStream.getAdjuncts();
		for (int i = 0; i < adjuncts.size();) {
			Adjunct adjunct = adjuncts.get(i);
			IToken previous_token = prsStream.getIToken(adjunct.getTokenIndex());
			IToken next_token = prsStream.getIToken(prsStream.getNext(previous_token.getTokenIndex()));
			IToken comments[] = previous_token.getFollowingAdjuncts();
			for (int k = 0; k < comments.length; k++) {
				Adjunct comment = (Adjunct) comments[k];
				if (comment.getEndLine() > comment.getLine()) {
					IToken gate_token = k + 1 < comments.length ? comments[k + 1]
							: next_token;
					makeAnnotationWithOffsets(
							comment.getStartOffset(),
							gate_token.getLine() > comment.getEndLine() ? lexStream
									.getLineOffset(gate_token.getLine() - 1)
									: comment.getEndOffset());
				}
			}

			i += comments.length;
		}
	}

	//
	// Use this version of makeAnnotation when you have a range of 
	// tokens to fold.
	//
	private void makeAnnotation(IToken first_token, IToken last_token) {
		if (last_token.getEndLine() > first_token.getLine()) {
			IToken next_token = prsStream.getIToken(prsStream
					.getNext(last_token.getTokenIndex()));
			IToken[] adjuncts = next_token.getPrecedingAdjuncts();
			IToken gate_token = adjuncts.length == 0 ? next_token : adjuncts[0];
			makeAnnotationWithOffsets(first_token.getStartOffset(), gate_token
					.getLine() > last_token.getEndLine() ? prsStream
					.getLexStream().getLineOffset(gate_token.getLine() - 1)
					: last_token.getEndOffset());
		}
	}

	protected void makeAnnotation(CSTNode n) {
		makeAnnotation(n.getStartToken(), n.getEndToken());
	}

	public void makeAnnotationWithOffsets(int first_offset, int last_offset) {
		super.makeAnnotation(first_offset, last_offset - first_offset + 1);
	}

	// When instantiated will provide a concrete implementation of an abstract method
	// defined in FolderBase
	@Override
	public void sendVisitorToAST(HashMap<Annotation,Position> newAnnotations, List<Annotation> annotations, Object ast) {
		CSTNode theCST = ((ICommonParseResult) ast).getCST();
		if (theCST != null) {
			IToken startToken = theCST.getStartToken();
			if (startToken != null) {
				prsStream = startToken.getPrsStream();
				FoldingCSTVisitor visitor = new FoldingCSTVisitor(getPlugin().getEditorDefinition());
				visitor.enter(theCST);
				makeAdjunctAnnotations(theCST);
			}
		}
	}
}
