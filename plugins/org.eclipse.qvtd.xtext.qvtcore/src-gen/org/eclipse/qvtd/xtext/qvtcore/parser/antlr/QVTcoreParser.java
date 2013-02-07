/*
* generated by Xtext
*/
package org.eclipse.qvtd.xtext.qvtcore.parser.antlr;

import com.google.inject.Inject;

import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.qvtd.xtext.qvtcore.services.QVTcoreGrammarAccess;

public class QVTcoreParser extends org.eclipse.xtext.parser.antlr.AbstractAntlrParser {
	
	@Inject
	private QVTcoreGrammarAccess grammarAccess;
	
	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	
	@Override
	protected org.eclipse.qvtd.xtext.qvtcore.parser.antlr.internal.InternalQVTcoreParser createParser(XtextTokenStream stream) {
		return new org.eclipse.qvtd.xtext.qvtcore.parser.antlr.internal.InternalQVTcoreParser(stream, getGrammarAccess());
	}
	
	@Override 
	protected String getDefaultRuleName() {
		return "TopLevelCS";
	}
	
	public QVTcoreGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(QVTcoreGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
	
}
