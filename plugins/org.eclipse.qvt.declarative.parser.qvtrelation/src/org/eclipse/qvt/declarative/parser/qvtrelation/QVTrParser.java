/**
* <copyright>
*
* Copyright (c) 2005, 2008 IBM Corporation, Zeligsoft Inc., and others.
* All rights reserved.   This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*   IBM - Initial API and implementation
*   E.D.Willink - Elimination of some shift-reduce conflicts
*   E.D.Willink - Remove unnecessary warning suppression
*   E.D.Willink - Bugs 225493, 243976
*   Zeligsoft - Bug 243976
*   E.D.Willink - Extended API and implementation for QVTr
*
* </copyright>
*
* $Id: QVTrParser.java,v 1.6 2008/10/11 15:17:31 ewillink Exp $
*/

package org.eclipse.qvt.declarative.parser.qvtrelation;

import org.eclipse.qvt.declarative.parser.qvt.cst.*;
import org.eclipse.qvt.declarative.parser.qvtrelation.cst.*;
import org.eclipse.qvt.declarative.parser.environment.ICSTFileEnvironment;
import org.eclipse.ocl.cst.CollectionTypeCS;
import org.eclipse.ocl.parser.AbstractOCLParser;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.ocl.cst.CSTNode;
import org.eclipse.ocl.cst.CallExpCS;
import org.eclipse.ocl.cst.CollectionTypeIdentifierEnum;
import org.eclipse.ocl.cst.DotOrArrowEnum;
import org.eclipse.ocl.cst.IntegerLiteralExpCS;
import org.eclipse.ocl.cst.IsMarkedPreCS;
import org.eclipse.ocl.cst.MessageExpCS;
import org.eclipse.ocl.cst.OCLExpressionCS;
import org.eclipse.ocl.cst.OCLMessageArgCS;
import org.eclipse.ocl.cst.OperationCallExpCS;
import org.eclipse.ocl.cst.PathNameCS;
import org.eclipse.ocl.cst.SimpleNameCS;
import org.eclipse.ocl.cst.SimpleTypeEnum;
import org.eclipse.ocl.cst.StateExpCS;
import org.eclipse.ocl.cst.TypeCS;
import org.eclipse.ocl.cst.VariableCS;
import org.eclipse.ocl.util.OCLStandardLibraryUtil;
import org.eclipse.ocl.utilities.PredefinedType;

import lpg.lpgjavaruntime.BadParseException;
import lpg.lpgjavaruntime.BadParseSymFileException;
import lpg.lpgjavaruntime.DeterministicParser;
import lpg.lpgjavaruntime.DiagnoseParser;
import lpg.lpgjavaruntime.IToken;
import lpg.lpgjavaruntime.Monitor;
import lpg.lpgjavaruntime.NotDeterministicParseTableException;
import lpg.lpgjavaruntime.ParseTable;
import lpg.lpgjavaruntime.RuleAction;

public class QVTrParser extends AbstractOCLParser implements RuleAction
{
	protected static ParseTable prs = new QVTrParserprs();
	private DeterministicParser dtParser;

	public QVTrParser(QVTrLexer lexer) {
		super(lexer);
	}

	public int getEOFTokenKind() { return QVTrParserprs.EOFT_SYMBOL; }

	public ICSTFileEnvironment getOCLEnvironment() {
		return getLexer().getOCLEnvironment();
	}
	
	@Override 
	public QVTrLexer getLexer() {
		return (QVTrLexer)super.getLexer();
	}

	public String getTokenKindName(int kind) { return QVTrParsersym.orderedTerminalSymbols[kind]; }			

	@Override
	public String[] orderedTerminalSymbols() { return QVTrParsersym.orderedTerminalSymbols; }
		
	@SuppressWarnings("nls")
	@Override
	public CSTNode parseTokensToCST(Monitor monitor, int error_repair_count) {
		ParseTable prsTable = new QVTrParserprs();

		try {
			dtParser = new DeterministicParser(monitor, this, prsTable, this);
		}
		catch (NotDeterministicParseTableException e) {
			throw new RuntimeException("****Error: Regenerate QVTrParserprs.java with -NOBACKTRACK option");
		}
		catch (BadParseSymFileException e) {
			throw new RuntimeException("****Error: Bad Parser Symbol File -- QVTrParsersym.java. Regenerate QVTrParserprs.java");
		}

		try {
			return (CSTNode) dtParser.parse();
		}
		catch (BadParseException e) {
			reset(e.error_token); // point to error token

			DiagnoseParser diagnoseParser = new DiagnoseParser(this, prsTable);
			diagnoseParser.diagnose(e.error_token);
		}

		return null;
	}

    /**
     * Initializes a concrete-syntax node's start and end offsets from the
     * current token in the parser stream.
     * 
     * @param cstNode a concrete-syntax node
     * 
     * @since 1.2
     */
	protected void setOffsets(CSTNode cstNode) {
		IToken firstToken = getIToken(dtParser.getToken(1));
		cstNode.setStartToken(firstToken);
		cstNode.setEndToken(firstToken);
		cstNode.setStartOffset(firstToken.getStartOffset());
		cstNode.setEndOffset(firstToken.getEndOffset()-1);
	}


		
	protected IdentifierCS createIdentifierCS(int token) {
		IdentifierCS result = QVTCSTFactory.eINSTANCE.createIdentifierCS();
		result.setValue(getTokenText(token));
		setOffsets(result, getIToken(token));
		return result;
	}

	protected String createIdentifierOrUnderscore(int token) {
		String text = getTokenText(token);
		if ("_".equals(text))
			return createUniqueIdentifier();
		else
			return text;
	}

	protected IdentifierCS createIdentifierOrUnderscoreCS(int token) {
		String text = getTokenText(token);
		if ("_".equals(text))
			return createUniqueIdentifierCS(token);
		else
			return createIdentifierCS(token);
	}
	
	@Override protected SimpleNameCS createSimpleNameCS(SimpleTypeEnum type, String value) {
		return super.createSimpleNameCS(type, "_".equals(value) ? createUniqueIdentifier() : value);
	}

	private int _uniqueNameCount = 0;

	protected String createUniqueIdentifier() {
		return "_unique" + _uniqueNameCount++;
	}

	protected IdentifierCS createUniqueIdentifierCS(int token) {
		IdentifierCS result = QVTCSTFactory.eINSTANCE.createIdentifierCS();
		result.setValue(createUniqueIdentifier());
		setOffsets(result, getIToken(token));
		return result;
	}

	@SuppressWarnings("unchecked")
	public void ruleAction(int ruleNumber)
	{
		switch (ruleNumber) {
		
 
			//
			// Rule 26:  operationCS1 ::= IDENTIFIER ( parametersCSopt ) : typeCSopt
			//
			case 26: {
				
				CSTNode result = createOperationCS(
						getTokenText(dtParser.getToken(1)),
						(EList)dtParser.getSym(3),
						(TypeCS)dtParser.getSym(6)
					);
				if (dtParser.getSym(6) != null) {
					setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(6));
				} else {
					setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(5)));
				}
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 27:  operationCS2 ::= pathNameCS :: simpleNameCS ( parametersCSopt ) : typeCSopt
			//
			case 27: {
				
				CSTNode result = createOperationCS(
						(PathNameCS)dtParser.getSym(1),
						(SimpleNameCS)dtParser.getSym(3),
						(EList)dtParser.getSym(5),
						(TypeCS)dtParser.getSym(8)
					);
				if (dtParser.getSym(8) != null) {
					setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(8));
				} else {
					setOffsets(result, (CSTNode)dtParser.getSym(1), getIToken(dtParser.getToken(7)));
				}
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 28:  parametersCSopt ::= $Empty
			//
			case 28:
				dtParser.setSym1(new BasicEList());
				break;
 
			//
			// Rule 30:  parametersCS ::= variableCS
			//
			case 30: {
				
				EList result = new BasicEList();
				result.add(dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 31:  parametersCS ::= parametersCS , variableCS
			//
			case 31: {
				
				EList result = (EList)dtParser.getSym(1);
				result.add(dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 32:  simpleNameCSopt ::= $Empty
			//
			case 32:
				dtParser.setSym1(null);
				break;
 
			//
			// Rule 38:  impliesExpCS ::= impliesExpCS implies andOrXorExpCS
			//
			case 38:
 
			//
			// Rule 39:  impliesWithLet ::= impliesExpCS implies andOrXorWithLet
			//
			case 39:
 
			//
			// Rule 42:  andOrXorExpCS ::= andOrXorExpCS and equalityExpCS
			//
			case 42:
 
			//
			// Rule 43:  andOrXorExpCS ::= andOrXorExpCS or equalityExpCS
			//
			case 43:
 
			//
			// Rule 44:  andOrXorExpCS ::= andOrXorExpCS xor equalityExpCS
			//
			case 44:
 
			//
			// Rule 45:  andOrXorWithLet ::= andOrXorExpCS and equalityWithLet
			//
			case 45:
 
			//
			// Rule 46:  andOrXorWithLet ::= andOrXorExpCS or equalityWithLet
			//
			case 46:
 
			//
			// Rule 47:  andOrXorWithLet ::= andOrXorExpCS xor equalityWithLet
			//
			case 47: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.STRING_LITERAL,
							getTokenText(dtParser.getToken(2))
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(2)));
				EList args = new BasicEList();
				args.add(dtParser.getSym(3));
				CSTNode result = createOperationCallExpCS(
						(OCLExpressionCS)dtParser.getSym(1),
						simpleNameCS,
						args
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 50:  equalityExpCS ::= equalityExpCS = relationalExpCS
			//
			case 50:
 
			//
			// Rule 51:  equalityWithLet ::= equalityExpCS = relationalWithLet
			//
			case 51: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.STRING_LITERAL,
							OCLStandardLibraryUtil.getOperationName(PredefinedType.EQUAL)
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(2)));
				EList args = new BasicEList();
				args.add(dtParser.getSym(3));
				CSTNode result = createOperationCallExpCS(
						(OCLExpressionCS)dtParser.getSym(1),
						simpleNameCS,
						args
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 52:  equalityExpCS ::= equalityExpCS <> relationalExpCS
			//
			case 52:
 
			//
			// Rule 53:  equalityWithLet ::= equalityExpCS <> relationalWithLet
			//
			case 53: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.STRING_LITERAL,
							OCLStandardLibraryUtil.getOperationName(PredefinedType.NOT_EQUAL)
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(2)));
				EList args = new BasicEList();
				args.add(dtParser.getSym(3));
				CSTNode result = createOperationCallExpCS(
						(OCLExpressionCS)dtParser.getSym(1),
						simpleNameCS,
						args
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 56:  relationalExpCS ::= relationalExpCS > ifExpCSPrec
			//
			case 56:
 
			//
			// Rule 57:  relationalWithLet ::= relationalExpCS > additiveWithLet
			//
			case 57: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.STRING_LITERAL,
							OCLStandardLibraryUtil.getOperationName(PredefinedType.GREATER_THAN)
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(2)));
				EList args = new BasicEList();
				args.add(dtParser.getSym(3));
				CSTNode result = createOperationCallExpCS(
						(OCLExpressionCS)dtParser.getSym(1),
						simpleNameCS,
						args
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 58:  relationalExpCS ::= relationalExpCS < ifExpCSPrec
			//
			case 58:
 
			//
			// Rule 59:  relationalWithLet ::= relationalExpCS < additiveWithLet
			//
			case 59: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.STRING_LITERAL,
							OCLStandardLibraryUtil.getOperationName(PredefinedType.LESS_THAN)
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(2)));
				EList args = new BasicEList();
				args.add(dtParser.getSym(3));
				CSTNode result = createOperationCallExpCS(
						(OCLExpressionCS)dtParser.getSym(1),
						simpleNameCS,
						args
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 60:  relationalExpCS ::= relationalExpCS >= ifExpCSPrec
			//
			case 60:
 
			//
			// Rule 61:  relationalWithLet ::= relationalExpCS >= additiveWithLet
			//
			case 61: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.STRING_LITERAL,
							OCLStandardLibraryUtil.getOperationName(PredefinedType.GREATER_THAN_EQUAL)
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(2)));
				EList args = new BasicEList();
				args.add(dtParser.getSym(3));
				CSTNode result = createOperationCallExpCS(
						(OCLExpressionCS)dtParser.getSym(1),
						simpleNameCS,
						args
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 62:  relationalExpCS ::= relationalExpCS <= ifExpCSPrec
			//
			case 62:
 
			//
			// Rule 63:  relationalWithLet ::= relationalExpCS <= additiveWithLet
			//
			case 63: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.STRING_LITERAL,
							OCLStandardLibraryUtil.getOperationName(PredefinedType.LESS_THAN_EQUAL)
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(2)));
				EList args = new BasicEList();
				args.add(dtParser.getSym(3));
				CSTNode result = createOperationCallExpCS(
						(OCLExpressionCS)dtParser.getSym(1),
						simpleNameCS,
						args
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 68:  additiveExpCS ::= additiveExpCS + multiplicativeExpCS
			//
			case 68:
 
			//
			// Rule 69:  additiveWithLet ::= additiveExpCS + multiplicativeWithLet
			//
			case 69: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.STRING_LITERAL,
							OCLStandardLibraryUtil.getOperationName(PredefinedType.PLUS)
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(2)));
				EList args = new BasicEList();
				args.add(dtParser.getSym(3));
				CSTNode result = createOperationCallExpCS(
						(OCLExpressionCS)dtParser.getSym(1),
						simpleNameCS,
						args
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 70:  additiveExpCS ::= additiveExpCS - multiplicativeExpCS
			//
			case 70:
 
			//
			// Rule 71:  additiveWithLet ::= additiveExpCS - multiplicativeWithLet
			//
			case 71: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.STRING_LITERAL,
							OCLStandardLibraryUtil.getOperationName(PredefinedType.MINUS)
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(2)));
				EList args = new BasicEList();
				args.add(dtParser.getSym(3));
				CSTNode result = createOperationCallExpCS(
						(OCLExpressionCS)dtParser.getSym(1),
						simpleNameCS,
						args
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 74:  multiplicativeExpCS ::= multiplicativeExpCS * unaryExpCS
			//
			case 74:
 
			//
			// Rule 75:  multiplicativeWithLet ::= multiplicativeExpCS * unaryWithLet
			//
			case 75: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.STRING_LITERAL,
							OCLStandardLibraryUtil.getOperationName(PredefinedType.TIMES)
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(2)));
				EList args = new BasicEList();
				args.add(dtParser.getSym(3));
				CSTNode result = createOperationCallExpCS(
						(OCLExpressionCS)dtParser.getSym(1),
						simpleNameCS,
						args
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 76:  multiplicativeExpCS ::= multiplicativeExpCS / unaryExpCS
			//
			case 76:
 
			//
			// Rule 77:  multiplicativeWithLet ::= multiplicativeExpCS / unaryWithLet
			//
			case 77: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.STRING_LITERAL,
							OCLStandardLibraryUtil.getOperationName(PredefinedType.DIVIDE)
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(2)));
				EList args = new BasicEList();
				args.add(dtParser.getSym(3));
				CSTNode result = createOperationCallExpCS(
						(OCLExpressionCS)dtParser.getSym(1),
						simpleNameCS,
						args
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 80:  unaryExpCS ::= - unaryExpCS
			//
			case 80: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.STRING_LITERAL,
							OCLStandardLibraryUtil.getOperationName(PredefinedType.MINUS)
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(1)));
				CSTNode result = createOperationCallExpCS(
						(OCLExpressionCS)dtParser.getSym(2),
						simpleNameCS,
						new BasicEList()
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(2));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 81:  unaryExpCS ::= not unaryExpCS
			//
			case 81: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.STRING_LITERAL,
							getTokenText(dtParser.getToken(1))
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(1)));
				CSTNode result = createOperationCallExpCS(
						(OCLExpressionCS)dtParser.getSym(2),
						simpleNameCS,
						new BasicEList()
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(2));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 83:  dotArrowExpCS ::= dotArrowExpCS callExpCS
			//
			case 83: {
				
				CallExpCS result = (CallExpCS)dtParser.getSym(2);
				result.setSource((OCLExpressionCS)dtParser.getSym(1));
				setOffsets(result, (CSTNode)dtParser.getSym(1), result);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 84:  dotArrowExpCS ::= dotArrowExpCS messageExpCS
			//
			case 84: {
				
				MessageExpCS result = (MessageExpCS)dtParser.getSym(2);
				result.setTarget((OCLExpressionCS)dtParser.getSym(1));
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(2));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 85:  dotArrowExpCS ::= NUMERIC_OPERATION ( argumentsCSopt )
			//
			case 85: {
				
				// NUMERIC_OPERATION -> Integer '.' Identifier
				String text = getTokenText(dtParser.getToken(1));
				int index = text.indexOf('.');
				String integer = text.substring(0, index);
				String simpleName = text.substring(index + 1);

				// create the IntegerLiteralExpCS
				IToken numericToken = getIToken(dtParser.getToken(1));
				int startOffset = numericToken.getStartOffset();
				int endOffset = startOffset + integer.length() - 1; // inclusive

				IntegerLiteralExpCS integerLiteralExpCS = createIntegerLiteralExpCS(integer);
				integerLiteralExpCS.setStartOffset(startOffset);
				integerLiteralExpCS.setEndOffset(endOffset);
				integerLiteralExpCS.setStartToken(numericToken);
				integerLiteralExpCS.setEndToken(numericToken);

				startOffset = endOffset + 2; // end of integerLiteral + 1('.') + 1(start of simpleName)
				endOffset = getIToken(dtParser.getToken(1)).getEndOffset();

				// create the SimpleNameCS
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.IDENTIFIER_LITERAL,
							simpleName
						);
				simpleNameCS.setStartOffset(startOffset);
				simpleNameCS.setEndOffset(endOffset);
				simpleNameCS.setStartToken(numericToken);
				simpleNameCS.setEndToken(numericToken);

				// create the OperationCallExpCS
				CSTNode result = createOperationCallExpCS(
						integerLiteralExpCS,
						simpleNameCS,
						(EList)dtParser.getSym(3)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 86:  dotArrowExpCS ::= pathNameCS :: simpleNameCS ( argumentsCSopt )
			//
			case 86: {
				
				OperationCallExpCS result = createOperationCallExpCS(
						(PathNameCS)dtParser.getSym(1),
						(SimpleNameCS)dtParser.getSym(3),
						(EList)dtParser.getSym(5)
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), getIToken(dtParser.getToken(6)));
				result.setAccessor(DotOrArrowEnum.DOT_LITERAL);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 91:  oclExpCS ::= ( oclExpressionCS )
			//
			case 91: {
				
				CSTNode result = (CSTNode)dtParser.getSym(2);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(3)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 92:  variableExpCS ::= simpleNameCS isMarkedPreCS
			//
			case 92: {
				
				IsMarkedPreCS isMarkedPreCS = (IsMarkedPreCS)dtParser.getSym(2);
				CSTNode result = createVariableExpCS(
						(SimpleNameCS)dtParser.getSym(1),
						new BasicEList(),
						isMarkedPreCS
					);
				if (isMarkedPreCS.isPre()) {
					setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(2));
				} else {
					setOffsets(result, (CSTNode)dtParser.getSym(1));
				}
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 93:  variableExpCS ::= keywordAsIdentifier1 isMarkedPreCS
			//
			case 93: {
				
				IsMarkedPreCS isMarkedPreCS = (IsMarkedPreCS)dtParser.getSym(2);
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.IDENTIFIER_LITERAL,
							getTokenText(dtParser.getToken(1))
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(1)));
				CSTNode result = createVariableExpCS(
						simpleNameCS,
						new BasicEList(),
						isMarkedPreCS
					);
				if (isMarkedPreCS.isPre()) {
					setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(2));
				} else {
					setOffsets(result, getIToken(dtParser.getToken(1)));
				}
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 94:  variableExpCS ::= simpleNameCS [ argumentsCS ] isMarkedPreCS
			//
			case 94: {
				
				IsMarkedPreCS isMarkedPreCS = (IsMarkedPreCS)dtParser.getSym(5);
				CSTNode result = createVariableExpCS(
						(SimpleNameCS)dtParser.getSym(1),
						(EList)dtParser.getSym(3),
						isMarkedPreCS
					);
				if (isMarkedPreCS.isPre()) {
					setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(5));
				} else {
					setOffsets(result, (CSTNode)dtParser.getSym(1), getIToken(dtParser.getToken(4)));
				}
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 95:  variableExpCS ::= keywordAsIdentifier1 [ argumentsCS ] isMarkedPreCS
			//
			case 95: {
				
				IsMarkedPreCS isMarkedPreCS = (IsMarkedPreCS)dtParser.getSym(5);
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.IDENTIFIER_LITERAL,
							getTokenText(dtParser.getToken(1))
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(1)));
				CSTNode result = createVariableExpCS(
						(SimpleNameCS)dtParser.getSym(1),
						(EList)dtParser.getSym(3),
						isMarkedPreCS
					);
				if (isMarkedPreCS.isPre()) {
					setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(5));
				} else {
					setOffsets(result, (CSTNode)dtParser.getSym(1), getIToken(dtParser.getToken(4)));
				}
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 97:  simpleNameCS ::= self
			//
			case 97: {
				
				CSTNode result = createSimpleNameCS(
						SimpleTypeEnum.SELF_LITERAL,
						getTokenText(dtParser.getToken(1))
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 98:  simpleNameCS ::= IDENTIFIER
			//
			case 98: {
				
				CSTNode result = createSimpleNameCS(
						SimpleTypeEnum.IDENTIFIER_LITERAL,
						getTokenText(dtParser.getToken(1))
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 99:  primitiveTypeCS ::= Integer
			//
			case 99: {
				
				CSTNode result = createPrimitiveTypeCS(
						SimpleTypeEnum.INTEGER_LITERAL,
						getTokenText(dtParser.getToken(1))
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 100:  primitiveTypeCS ::= UnlimitedNatural
			//
			case 100: {
				
				CSTNode result = createPrimitiveTypeCS(
						SimpleTypeEnum.UNLIMITED_NATURAL_LITERAL,
						getTokenText(dtParser.getToken(1))
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 101:  primitiveTypeCS ::= String
			//
			case 101: {
				
				CSTNode result = createPrimitiveTypeCS(
						SimpleTypeEnum.STRING_LITERAL,
						getTokenText(dtParser.getToken(1))
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 102:  primitiveTypeCS ::= Real
			//
			case 102: {
				
				CSTNode result = createPrimitiveTypeCS(
						SimpleTypeEnum.REAL_LITERAL,
						getTokenText(dtParser.getToken(1))
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 103:  primitiveTypeCS ::= Boolean
			//
			case 103: {
				
				CSTNode result = createPrimitiveTypeCS(
						SimpleTypeEnum.BOOLEAN_LITERAL,
						getTokenText(dtParser.getToken(1))
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 104:  primitiveTypeCS ::= OclAny
			//
			case 104: {
				
				CSTNode result = createPrimitiveTypeCS(
						SimpleTypeEnum.OCL_ANY_LITERAL,
						getTokenText(dtParser.getToken(1))
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 105:  primitiveTypeCS ::= OclVoid
			//
			case 105: {
				
				CSTNode result = createPrimitiveTypeCS(
						SimpleTypeEnum.OCL_VOID_LITERAL,
						getTokenText(dtParser.getToken(1))
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 106:  primitiveTypeCS ::= Invalid
			//
			case 106: {
				
				CSTNode result = createPrimitiveTypeCS(
						SimpleTypeEnum.INVALID_LITERAL,
						getTokenText(dtParser.getToken(1))
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 107:  primitiveTypeCS ::= OclMessage
			//
			case 107: {
				
				CSTNode result = createPrimitiveTypeCS(
						SimpleTypeEnum.OCL_MESSAGE_LITERAL,
						getTokenText(dtParser.getToken(1))
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 108:  pathNameCS ::= IDENTIFIER
			//
			case 108: {
				
				CSTNode result = createPathNameCS(getTokenText(dtParser.getToken(1)));
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 109:  pathNameCS ::= pathNameCS :: simpleNameCS
			//
			case 109: {
				
				PathNameCS result = (PathNameCS)dtParser.getSym(1);
				result = extendPathNameCS(result, getTokenText(dtParser.getToken(3)));
				setOffsets(result, result, (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 110:  pathNameCSOpt ::= $Empty
			//
			case 110: {
				
				CSTNode result = createPathNameCS();
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 118:  enumLiteralExpCS ::= pathNameCS :: keywordAsIdentifier
			//
			case 118: {
				
				CSTNode result = createEnumLiteralExpCS(
						(PathNameCS)dtParser.getSym(1),
						getTokenText(dtParser.getToken(3))
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), getIToken(dtParser.getToken(3)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 119:  enumLiteralExpCS ::= pathNameCS :: simpleNameCS
			//
			case 119: {
				
				CSTNode result = createEnumLiteralExpCS(
						(PathNameCS)dtParser.getSym(1),
						(SimpleNameCS)dtParser.getSym(3)
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 120:  collectionLiteralExpCS ::= collectionTypeIdentifierCS { collectionLiteralPartsCSopt }
			//
			case 120: {
				
				Object[] objs = (Object[])dtParser.getSym(1);
				CSTNode result = createCollectionLiteralExpCS(
						(CollectionTypeIdentifierEnum)objs[1],
						(EList)dtParser.getSym(3)
					);
				setOffsets(result, (IToken)objs[0], getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 121:  collectionTypeIdentifierCS ::= Set
			//
			case 121: {
				
				dtParser.setSym1(new Object[]{getIToken(dtParser.getToken(1)), CollectionTypeIdentifierEnum.SET_LITERAL});
	  		  break;
			}
	 
			//
			// Rule 122:  collectionTypeIdentifierCS ::= Bag
			//
			case 122: {
				
				dtParser.setSym1(new Object[]{getIToken(dtParser.getToken(1)), CollectionTypeIdentifierEnum.BAG_LITERAL});
	  		  break;
			}
	 
			//
			// Rule 123:  collectionTypeIdentifierCS ::= Sequence
			//
			case 123: {
				
				dtParser.setSym1(new Object[]{getIToken(dtParser.getToken(1)), CollectionTypeIdentifierEnum.SEQUENCE_LITERAL});
	  		  break;
			}
	 
			//
			// Rule 124:  collectionTypeIdentifierCS ::= Collection
			//
			case 124: {
				
				dtParser.setSym1(new Object[]{getIToken(dtParser.getToken(1)), CollectionTypeIdentifierEnum.COLLECTION_LITERAL});
	  		  break;
			}
	 
			//
			// Rule 125:  collectionTypeIdentifierCS ::= OrderedSet
			//
			case 125: {
				
				dtParser.setSym1(new Object[]{getIToken(dtParser.getToken(1)), CollectionTypeIdentifierEnum.ORDERED_SET_LITERAL});
	  		  break;
			}
	 
			//
			// Rule 126:  collectionLiteralPartsCSopt ::= $Empty
			//
			case 126:
				dtParser.setSym1(new BasicEList());
				break;
 
			//
			// Rule 128:  collectionLiteralPartsCS ::= collectionLiteralPartCS
			//
			case 128: {
				
				EList result = new BasicEList();
				result.add(dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 129:  collectionLiteralPartsCS ::= collectionLiteralPartsCS , collectionLiteralPartCS
			//
			case 129: {
				
				EList result = (EList)dtParser.getSym(1);
				result.add(dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 131:  collectionLiteralPartCS ::= oclExpressionCS
			//
			case 131: {
				
				CSTNode result = createCollectionLiteralPartCS(
						(OCLExpressionCS)dtParser.getSym(1)
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 132:  collectionRangeCS ::= - INTEGER_RANGE_START oclExpressionCS
			//
			case 132: {
				
				OCLExpressionCS rangeStart = createRangeStart(
						getTokenText(dtParser.getToken(2)), true);
				CSTNode result = createCollectionRangeCS(
						rangeStart,
						(OCLExpressionCS)dtParser.getSym(3)
					);
				setOffsets(result, rangeStart, (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 133:  collectionRangeCS ::= INTEGER_RANGE_START oclExpressionCS
			//
			case 133: {
				
				OCLExpressionCS rangeStart = createRangeStart(
						getTokenText(dtParser.getToken(1)), false);
				CSTNode result = createCollectionRangeCS(
						rangeStart,
						(OCLExpressionCS)dtParser.getSym(2)
					);
				setOffsets(result, rangeStart, (CSTNode)dtParser.getSym(2));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 134:  collectionRangeCS ::= oclExpressionCS .. oclExpressionCS
			//
			case 134: {
				
				CSTNode result = createCollectionRangeCS(
						(OCLExpressionCS)dtParser.getSym(1),
						(OCLExpressionCS)dtParser.getSym(3)
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 140:  tupleLiteralExpCS ::= Tuple { variableListCS2 }
			//
			case 140: {
				
				CSTNode result = createTupleLiteralExpCS((EList)dtParser.getSym(3));
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 141:  integerLiteralExpCS ::= INTEGER_LITERAL
			//
			case 141: {
				
				CSTNode result = createIntegerLiteralExpCS(getTokenText(dtParser.getToken(1)));
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 142:  unlimitedNaturalLiteralExpCS ::= *
			//
			case 142: {
				
				CSTNode result = createUnlimitedNaturalLiteralExpCS(getTokenText(dtParser.getToken(1)));
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 143:  realLiteralExpCS ::= REAL_LITERAL
			//
			case 143: {
				
				CSTNode result = createRealLiteralExpCS(getTokenText(dtParser.getToken(1)));
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 144:  stringLiteralExpCS ::= STRING_LITERAL
			//
			case 144: {
				
				CSTNode result = createStringLiteralExpCS(getTokenText(dtParser.getToken(1)));
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 145:  booleanLiteralExpCS ::= true
			//
			case 145: {
				
				CSTNode result = createBooleanLiteralExpCS(getTokenText(dtParser.getToken(1)));
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 146:  booleanLiteralExpCS ::= false
			//
			case 146: {
				
				CSTNode result = createBooleanLiteralExpCS(getTokenText(dtParser.getToken(1)));
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 147:  nullLiteralExpCS ::= null
			//
			case 147: {
				
				CSTNode result = createNullLiteralExpCS(getTokenText(dtParser.getToken(1)));
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 148:  invalidLiteralExpCS ::= OclInvalid
			//
			case 148: {
				
				CSTNode result = createInvalidLiteralExpCS(getTokenText(dtParser.getToken(1)));
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 149:  callExpCS ::= -> featureCallExpCS
			//
			case 149:
 
			//
			// Rule 150:  callExpCS ::= -> loopExpCS
			//
			case 150: {
				
				CallExpCS result = (CallExpCS)dtParser.getSym(2);
				result.setAccessor(DotOrArrowEnum.ARROW_LITERAL);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 151:  callExpCS ::= . keywordOperationCallExpCS
			//
			case 151:
 
			//
			// Rule 152:  callExpCS ::= . featureCallExpCS
			//
			case 152: {
				
				CallExpCS result = (CallExpCS)dtParser.getSym(2);
				result.setAccessor(DotOrArrowEnum.DOT_LITERAL);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 155:  iteratorExpCS ::= forAll ( iterContents )
			//
			case 155:
 
			//
			// Rule 156:  iteratorExpCS ::= exists ( iterContents )
			//
			case 156:
 
			//
			// Rule 157:  iteratorExpCS ::= isUnique ( iterContents )
			//
			case 157:
 
			//
			// Rule 158:  iteratorExpCS ::= one ( iterContents )
			//
			case 158:
 
			//
			// Rule 159:  iteratorExpCS ::= any ( iterContents )
			//
			case 159:
 
			//
			// Rule 160:  iteratorExpCS ::= collect ( iterContents )
			//
			case 160:
 
			//
			// Rule 161:  iteratorExpCS ::= select ( iterContents )
			//
			case 161:
 
			//
			// Rule 162:  iteratorExpCS ::= reject ( iterContents )
			//
			case 162:
 
			//
			// Rule 163:  iteratorExpCS ::= collectNested ( iterContents )
			//
			case 163:
 
			//
			// Rule 164:  iteratorExpCS ::= sortedBy ( iterContents )
			//
			case 164:
 
			//
			// Rule 165:  iteratorExpCS ::= closure ( iterContents )
			//
			case 165: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.KEYWORD_LITERAL,
							getTokenText(dtParser.getToken(1))
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(1)));
				Object[] iterContents = (Object[])dtParser.getSym(3);
				CSTNode result = createIteratorExpCS(
						simpleNameCS,
						(VariableCS)iterContents[0],
						(VariableCS)iterContents[1],
						(OCLExpressionCS)iterContents[2]
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 166:  iterContents ::= oclExpressionCS
			//
			case 166: {
				
				dtParser.setSym1(new Object[] {
						null,
						null,
						dtParser.getSym(1)
					});
	  		  break;
			}
	 
			//
			// Rule 167:  iterContents ::= variableCS | oclExpressionCS
			//
			case 167: {
				
				dtParser.setSym1(new Object[] {
						dtParser.getSym(1),
						null,
						dtParser.getSym(3)
					});
	  		  break;
			}
	 
			//
			// Rule 168:  iterContents ::= variableCS , variableCS | oclExpressionCS
			//
			case 168: {
				
				dtParser.setSym1(new Object[] {
						dtParser.getSym(1),
						dtParser.getSym(3),
						dtParser.getSym(5)
					});
	  		  break;
			}
	 
			//
			// Rule 169:  iterateExpCS ::= iterate ( variableCS | oclExpressionCS )
			//
			case 169: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.KEYWORD_LITERAL,
							getTokenText(dtParser.getToken(1))
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(1)));
				CSTNode result = createIterateExpCS(
						simpleNameCS,
						(VariableCS)dtParser.getSym(3),
						null,
						(OCLExpressionCS)dtParser.getSym(5)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(6)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 170:  iterateExpCS ::= iterate ( variableCS ; variableCS | oclExpressionCS )
			//
			case 170: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.KEYWORD_LITERAL,
							getTokenText(dtParser.getToken(1))
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(1)));
				CSTNode result = createIterateExpCS(
						simpleNameCS,
						(VariableCS)dtParser.getSym(3),
						(VariableCS)dtParser.getSym(5),
						(OCLExpressionCS)dtParser.getSym(7)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(8)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 171:  variableCS ::= IDENTIFIER
			//
			case 171: {
				
				CSTNode result = createVariableCS(
						getTokenText(dtParser.getToken(1)),
						null,
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 172:  variableCS ::= IDENTIFIER : typeCS
			//
			case 172: {
				
				CSTNode result = createVariableCS(
						getTokenText(dtParser.getToken(1)),
						(TypeCS)dtParser.getSym(3),
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 173:  variableCS ::= IDENTIFIER : typeCS = oclExpressionCS
			//
			case 173: {
				
				CSTNode result = createVariableCS(
						getTokenText(dtParser.getToken(1)),
						(TypeCS)dtParser.getSym(3),
						(OCLExpressionCS)dtParser.getSym(5)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(5));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 174:  variableCS2 ::= IDENTIFIER = oclExpressionCS
			//
			case 174: {
				
				CSTNode result = createVariableCS(
						getTokenText(dtParser.getToken(1)),
						null,
						(OCLExpressionCS)dtParser.getSym(3)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 175:  typeCSopt ::= $Empty
			//
			case 175:
				dtParser.setSym1(null);
				break;
 
			//
			// Rule 181:  collectionTypeCS ::= collectionTypeIdentifierCS ( typeCS )
			//
			case 181: {
				
				Object[] objs = (Object[])dtParser.getSym(1);
				CSTNode result = createCollectionTypeCS(
						(CollectionTypeIdentifierEnum)objs[1],
						(TypeCS)dtParser.getSym(3)
					);
				setOffsets(result, (IToken)objs[0], getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 182:  tupleTypeCS ::= Tuple ( variableListCSopt )
			//
			case 182: {
				
				CSTNode result = createTupleTypeCS((EList)dtParser.getSym(3));
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 183:  variableListCSopt ::= $Empty
			//
			case 183:
				dtParser.setSym1(new BasicEList());
				break;
 
			//
			// Rule 185:  variableListCS ::= variableCS
			//
			case 185: {
				
				EList result = new BasicEList();
				result.add(dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 186:  variableListCS ::= variableListCS , variableCS
			//
			case 186: {
				
				EList result = (EList)dtParser.getSym(1);
				result.add(dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 187:  variableListCS2 ::= variableCS2
			//
			case 187:
 
			//
			// Rule 188:  variableListCS2 ::= variableCS
			//
			case 188: {
				
				EList result = new BasicEList();
				result.add(dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 189:  variableListCS2 ::= variableListCS2 , variableCS2
			//
			case 189:
 
			//
			// Rule 190:  variableListCS2 ::= variableListCS2 , variableCS
			//
			case 190: {
				
				EList result = (EList)dtParser.getSym(1);
				result.add(dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 193:  featureCallExpCS ::= MINUS isMarkedPreCS ( argumentsCSopt )
			//
			case 193:
 
			//
			// Rule 194:  featureCallExpCS ::= not isMarkedPreCS ( argumentsCSopt )
			//
			case 194: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.IDENTIFIER_LITERAL,
							getTokenText(dtParser.getToken(1))
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(1)));
				CSTNode result = createOperationCallExpCS(
						simpleNameCS,
						(IsMarkedPreCS)dtParser.getSym(2),
						(EList)dtParser.getSym(4)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(5)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 195:  operationCallExpCS ::= simpleNameCS isMarkedPreCS ( argumentsCSopt )
			//
			case 195: {
				
				CSTNode result = createOperationCallExpCS(
						(SimpleNameCS)dtParser.getSym(1),
						(IsMarkedPreCS)dtParser.getSym(2),
						(EList)dtParser.getSym(4)
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), getIToken(dtParser.getToken(5)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 196:  operationCallExpCS ::= oclIsUndefined isMarkedPreCS ( argumentsCSopt )
			//
			case 196:
 
			//
			// Rule 197:  operationCallExpCS ::= oclIsInvalid isMarkedPreCS ( argumentsCSopt )
			//
			case 197:
 
			//
			// Rule 198:  operationCallExpCS ::= oclIsNew isMarkedPreCS ( argumentsCSopt )
			//
			case 198:
 
			//
			// Rule 199:  operationCallExpCS ::= oclAsType isMarkedPreCS ( argumentsCSopt )
			//
			case 199:
 
			//
			// Rule 200:  operationCallExpCS ::= oclIsKindOf isMarkedPreCS ( argumentsCSopt )
			//
			case 200:
 
			//
			// Rule 201:  operationCallExpCS ::= oclIsTypeOf isMarkedPreCS ( argumentsCSopt )
			//
			case 201:
 
			//
			// Rule 202:  operationCallExpCS ::= EQUAL isMarkedPreCS ( argumentsCSopt )
			//
			case 202:
 
			//
			// Rule 203:  operationCallExpCS ::= NOT_EQUAL isMarkedPreCS ( argumentsCSopt )
			//
			case 203:
 
			//
			// Rule 204:  operationCallExpCS ::= PLUS isMarkedPreCS ( argumentsCSopt )
			//
			case 204:
 
			//
			// Rule 205:  operationCallExpCS ::= MULTIPLY isMarkedPreCS ( argumentsCSopt )
			//
			case 205:
 
			//
			// Rule 206:  operationCallExpCS ::= DIVIDE isMarkedPreCS ( argumentsCSopt )
			//
			case 206:
 
			//
			// Rule 207:  operationCallExpCS ::= GREATER isMarkedPreCS ( argumentsCSopt )
			//
			case 207:
 
			//
			// Rule 208:  operationCallExpCS ::= LESS isMarkedPreCS ( argumentsCSopt )
			//
			case 208:
 
			//
			// Rule 209:  operationCallExpCS ::= GREATER_EQUAL isMarkedPreCS ( argumentsCSopt )
			//
			case 209:
 
			//
			// Rule 210:  operationCallExpCS ::= LESS_EQUAL isMarkedPreCS ( argumentsCSopt )
			//
			case 210:
 
			//
			// Rule 211:  operationCallExpCS ::= and isMarkedPreCS ( argumentsCSopt )
			//
			case 211:
 
			//
			// Rule 212:  operationCallExpCS ::= or isMarkedPreCS ( argumentsCSopt )
			//
			case 212:
 
			//
			// Rule 213:  operationCallExpCS ::= xor isMarkedPreCS ( argumentsCSopt )
			//
			case 213:
 
			//
			// Rule 214:  keywordOperationCallExpCS ::= keywordAsIdentifier isMarkedPreCS ( argumentsCSopt )
			//
			case 214: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.IDENTIFIER_LITERAL,
							getTokenText(dtParser.getToken(1))
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(1)));
				CSTNode result = createOperationCallExpCS(
						simpleNameCS,
						(IsMarkedPreCS)dtParser.getSym(2),
						(EList)dtParser.getSym(4)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(5)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 215:  operationCallExpCS ::= oclIsInState isMarkedPreCS ( pathNameCSOpt )
			//
			case 215: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.KEYWORD_LITERAL,
							getTokenText(dtParser.getToken(1))
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(1)));

				PathNameCS pathNameCS = (PathNameCS) dtParser.getSym(4);
				StateExpCS stateExpCS = createStateExpCS(pathNameCS);
				setOffsets(stateExpCS, pathNameCS);
			
				CSTNode result = createOperationCallExpCS(
						simpleNameCS,
						(IsMarkedPreCS)dtParser.getSym(2),
						stateExpCS
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(5)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 216:  attrOrNavCallExpCS ::= simpleNameCS isMarkedPreCS
			//
			case 216: {
				
				IsMarkedPreCS isMarkedPreCS = (IsMarkedPreCS)dtParser.getSym(2);
				CSTNode result = createFeatureCallExpCS(
						(SimpleNameCS)dtParser.getSym(1),
						new BasicEList(),
						isMarkedPreCS
					);
				if (isMarkedPreCS.isPre()) {
					setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(2));
				} else {
					setOffsets(result, (CSTNode)dtParser.getSym(1));
				}
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 217:  attrOrNavCallExpCS ::= keywordAsIdentifier isMarkedPreCS
			//
			case 217: {
				
				IsMarkedPreCS isMarkedPreCS = (IsMarkedPreCS)dtParser.getSym(2);
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.IDENTIFIER_LITERAL,
							getTokenText(dtParser.getToken(1))
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(1)));
				CSTNode result = createFeatureCallExpCS(
						simpleNameCS,
						new BasicEList(),
						isMarkedPreCS
					);
				if (isMarkedPreCS.isPre()) {
					setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(2));
				} else {
					setOffsets(result, getIToken(dtParser.getToken(1)));
				}
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 218:  attrOrNavCallExpCS ::= simpleNameCS [ argumentsCS ] isMarkedPreCS
			//
			case 218: {
				
				IsMarkedPreCS isMarkedPreCS = (IsMarkedPreCS)dtParser.getSym(5);
				CSTNode result = createFeatureCallExpCS(
						(SimpleNameCS)dtParser.getSym(1),
						(EList)dtParser.getSym(3),
						isMarkedPreCS
					);
				if (isMarkedPreCS.isPre()) {
					setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(5));
				} else {
					setOffsets(result, (CSTNode)dtParser.getSym(1), getIToken(dtParser.getToken(4)));
				}
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 219:  isMarkedPreCS ::= $Empty
			//
			case 219: {
				
				CSTNode result = createIsMarkedPreCS(false);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 220:  isMarkedPreCS ::= @pre
			//
			case 220: {
				
				CSTNode result = createIsMarkedPreCS(true);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 221:  argumentsCSopt ::= $Empty
			//
			case 221:
				dtParser.setSym1(new BasicEList());
				break;
 
			//
			// Rule 223:  argumentsCS ::= oclExpressionCS
			//
			case 223: {
				
				EList result = new BasicEList();
				result.add(dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 224:  argumentsCS ::= argumentsCS , oclExpressionCS
			//
			case 224: {
				
				EList result = (EList)dtParser.getSym(1);
				result.add(dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 225:  letExpCS ::= let variableCS letExpSubCSopt in oclExpressionCS
			//
			case 225: {
				
				EList variables = (EList)dtParser.getSym(3);
				variables.add(0, dtParser.getSym(2));
				CSTNode result = createLetExpCS(
						variables,
						(OCLExpressionCS)dtParser.getSym(5)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(5));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 226:  letExpSubCSopt ::= $Empty
			//
			case 226:
				dtParser.setSym1(new BasicEList());
				break;
 
			//
			// Rule 228:  letExpSubCS ::= , variableCS
			//
			case 228: {
				
				EList result = new BasicEList();
				result.add(dtParser.getSym(2));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 229:  letExpSubCS ::= letExpSubCS , variableCS
			//
			case 229: {
				
				EList result = (EList)dtParser.getSym(1);
				result.add(dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 230:  ifExpCS ::= if oclExpressionCS then oclExpressionCS else oclExpressionCS endif
			//
			case 230: {
				
				CSTNode result = createIfExpCS(
						(OCLExpressionCS)dtParser.getSym(2),
						(OCLExpressionCS)dtParser.getSym(4),
						(OCLExpressionCS)dtParser.getSym(6)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(7)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 231:  messageExpCS ::= ^ simpleNameCS ( oclMessageArgumentsCSopt )
			//
			case 231:
 
			//
			// Rule 232:  messageExpCS ::= ^^ simpleNameCS ( oclMessageArgumentsCSopt )
			//
			case 232: {
				
				CSTNode result = createMessageExpCS(
						getIToken(dtParser.getToken(1)).getKind() == QVTrParsersym.TK_CARET,
						(SimpleNameCS)dtParser.getSym(2),
						(EList<OCLMessageArgCS>)dtParser.getSym(4)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(5)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 233:  oclMessageArgumentsCSopt ::= $Empty
			//
			case 233:
				dtParser.setSym1(new BasicEList());
				break;
 
			//
			// Rule 235:  oclMessageArgumentsCS ::= oclMessageArgCS
			//
			case 235: {
				
				EList result = new BasicEList();
				result.add(dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 236:  oclMessageArgumentsCS ::= oclMessageArgumentsCS , oclMessageArgCS
			//
			case 236: {
				
				EList result = (EList)dtParser.getSym(1);
				result.add(dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 237:  oclMessageArgCS ::= oclExpressionCS
			//
			case 237: {
				
				CSTNode result = createOCLMessageArgCS(
						null,
						(OCLExpressionCS)dtParser.getSym(1)
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 238:  oclMessageArgCS ::= ?
			//
			case 238: {
				
				CSTNode result = createOCLMessageArgCS(
						null,
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 239:  oclMessageArgCS ::= ? : typeCS
			//
			case 239: {
				
				CSTNode result = createOCLMessageArgCS(
						(TypeCS)dtParser.getSym(3),
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 240:  topLevelCS_0_ ::= $Empty
			//
			case 240: {
				
				TopLevelCS result = QVTrCSTFactory.eINSTANCE.createTopLevelCS();
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 241:  topLevelCS_0_ ::= topLevelCS_0_ import unitCS ;
			//
			case 241: {
				
				TopLevelCS result = (TopLevelCS)dtParser.getSym(1);
				result.getImportClause().add((UnitCS)dtParser.getSym(3));
				setOffsets(result, result, getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 243:  topLevelCS ::= topLevelCS transformationCS
			//
			case 243: {
				
				TransformationCS transformationCS = (TransformationCS)dtParser.getSym(2);
				TopLevelCS result = (TopLevelCS)dtParser.getSym(1);
				result.getTransformation().add(transformationCS);
				setOffsets(result, result, transformationCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 244:  unitCS ::= identifierCS
			//
			case 244: {
				
				IdentifierCS identifierCS = (IdentifierCS)dtParser.getSym(1);
				UnitCS result = QVTrCSTFactory.eINSTANCE.createUnitCS();
				result.getIdentifier().add(identifierCS);
				setOffsets(result, identifierCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 245:  unitCS ::= unitCS . identifierCS
			//
			case 245: {
				
				IdentifierCS identifierCS = (IdentifierCS)dtParser.getSym(3);
				UnitCS result = (UnitCS)dtParser.getSym(1);
				result.getIdentifier().add(identifierCS);
				setOffsets(result, result, identifierCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 246:  transformationCS_0_ ::= transformation identifierCS ( modelDeclCS
			//
			case 246: {
				
				IdentifierCS identifierCS = (IdentifierCS)dtParser.getSym(2);
				ModelDeclCS modelDeclCS = (ModelDeclCS)dtParser.getSym(4);
				TransformationCS result = QVTrCSTFactory.eINSTANCE.createTransformationCS();
				result.setIdentifier(identifierCS);
				result.getModelDecl().add(modelDeclCS);
				setOffsets(result, getIToken(dtParser.getToken(1)), modelDeclCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 247:  transformationCS_0_ ::= transformationCS_0_ , modelDeclCS
			//
			case 247: {
				
				ModelDeclCS modelDeclCS = (ModelDeclCS)dtParser.getSym(3);
				TransformationCS result = (TransformationCS)dtParser.getSym(1);
				result.getModelDecl().add(modelDeclCS);
				setOffsets(result, result, modelDeclCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 248:  transformationCS_1_ ::= transformationCS_0_ )
			//
			case 248: {
				
				TransformationCS result = (TransformationCS)dtParser.getSym(1);
				setOffsets(result, result, getIToken(dtParser.getToken(3)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 250:  transformationCS_2_ ::= transformationCS_1_ extends identifierCS
			//
			case 250: {
				
				IdentifierCS identifierCS = (IdentifierCS)dtParser.getSym(3);
				TransformationCS result = (TransformationCS)dtParser.getSym(1);
				result.setExtends(identifierCS);
				setOffsets(result, result, identifierCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 252:  transformationCS_3_ ::= transformationCS_3_ keyDeclCS
			//
			case 252: {
				
				KeyDeclCS keyDeclCS = (KeyDeclCS)dtParser.getSym(2);
				TransformationCS result = (TransformationCS)dtParser.getSym(1);
				result.getKeyDecl().add(keyDeclCS);
				setOffsets(result, result, keyDeclCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 254:  transformationCS_4_ ::= transformationCS_4_ queryCS
			//
			case 254: {
				
				QueryCS queryCS =(QueryCS)dtParser.getSym(2);
				TransformationCS result = (TransformationCS)dtParser.getSym(1);
				result.getQuery().add(queryCS);
				setOffsets(result, result, queryCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 255:  transformationCS_4_ ::= transformationCS_4_ relationCS
			//
			case 255: {
				
				RelationCS relationCS = (RelationCS)dtParser.getSym(2);
				TransformationCS result = (TransformationCS)dtParser.getSym(1);
				result.getRelation().add(relationCS);
				setOffsets(result, result, relationCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 256:  transformationCS ::= transformationCS_4_ }
			//
			case 256: {
				
				TransformationCS result = (TransformationCS)dtParser.getSym(1);
				setOffsets(result, result, getIToken(dtParser.getToken(2)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 257:  modelDeclCS_0_ ::= modelIdCS :
			//
			case 257: {
				
				IdentifierCS modelIdCS = (IdentifierCS)dtParser.getSym(1);
				ModelDeclCS result = QVTrCSTFactory.eINSTANCE.createModelDeclCS();
				result.setModelId(modelIdCS);
				setOffsets(result, modelIdCS, getIToken(dtParser.getToken(2)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 260:  modelDeclCS_2_ ::= modelDeclCS_1_ metaModelIdCS
			//
			case 260: 
 
			//
			// Rule 261:  modelDeclCS ::= modelDeclCS_0_ metaModelIdCS
			//
			case 261: {
				
				IdentifierCS metaModelIdCS = (IdentifierCS)dtParser.getSym(2);
				ModelDeclCS result = (ModelDeclCS)dtParser.getSym(1);
				result.getMetaModelId().add(metaModelIdCS);
				setOffsets(result, result, metaModelIdCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 262:  modelDeclCS ::= modelDeclCS_2_ }
			//
			case 262: {
				
				ModelDeclCS result = (ModelDeclCS)dtParser.getSym(1);
				setOffsets(result, result, getIToken(dtParser.getToken(2)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 265:  keyDeclCS_0_ ::= key classIdCS {
			//
			case 265: {
				
				PathNameCS classIdCS = (PathNameCS)dtParser.getSym(2);
				KeyDeclCS result = QVTrCSTFactory.eINSTANCE.createKeyDeclCS();
				result.setClassId(classIdCS);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(3)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 267:  keyDeclCS_1_ ::= keyDeclCS_0_ propertyIdCS
			//
			case 267: {
				
				IdentifiedCS propertyIdCS = (IdentifiedCS)dtParser.getSym(2);
				KeyDeclCS result = (KeyDeclCS)dtParser.getSym(1);
				result.getPropertyId().add(propertyIdCS);
				setOffsets(result, result, propertyIdCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 268:  keyDeclCS ::= keyDeclCS_1_ } ;
			//
			case 268: {
				
				KeyDeclCS result = (KeyDeclCS)dtParser.getSym(1);
				setOffsets(result, result, getIToken(dtParser.getToken(3)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 270:  propertyIdCS ::= identifierCS
			//
			case 270: {
				
				IdentifierCS identifierCS = (IdentifierCS)dtParser.getSym(1);
				IdentifiedCS result = QVTCSTFactory.eINSTANCE.createIdentifiedCS();
				result.setIdentifier(identifierCS);
				setOffsets(result, identifierCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 271:  relationCS_0_ ::= relation identifierCS
			//
			case 271: {
				
				IdentifierCS identifierCS = (IdentifierCS)dtParser.getSym(2);
				RelationCS result = QVTrCSTFactory.eINSTANCE.createRelationCS();
				result.setIdentifier(identifierCS);
				setOffsets(result, getIToken(dtParser.getToken(1)), identifierCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 273:  relationCS_1_ ::= top relationCS_0_
			//
			case 273: {
				
				RelationCS result = (RelationCS)dtParser.getSym(2);
				result.setTop(true);
				setOffsets(result, getIToken(dtParser.getToken(1)), result);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 275:  relationCS_2_ ::= relationCS_1_ overrides identifierCS
			//
			case 275: {
				
				IdentifierCS identifierCS = (IdentifierCS)dtParser.getSym(3);
				RelationCS result = (RelationCS)dtParser.getSym(1);
				result.setOverrides(identifierCS);
				setOffsets(result, result, identifierCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 277:  relationCS_3_ ::= relationCS_3_ varDeclarationCS
			//
			case 277: {
				
				VarDeclarationCS varDeclarationCS = (VarDeclarationCS)dtParser.getSym(2);
				RelationCS result = (RelationCS)dtParser.getSym(1);
				result.getVarDeclaration().add(varDeclarationCS);
				setOffsets(result, result, varDeclarationCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 280:  relationCS_postDomain ::= relationCS_preDomain domainCS
			//
			case 280: 
 
			//
			// Rule 281:  relationCS_postDomain ::= relationCS_preDomain primitiveTypeDomainCS
			//
			case 281: {
				
				AbstractDomainCS domainCS = (AbstractDomainCS)dtParser.getSym(2);
				RelationCS result = (RelationCS)dtParser.getSym(1);
				result.getDomain().add(domainCS);
				setOffsets(result, result, domainCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 283:  relationCS_postWhen ::= relationCS_postDomain whenCS
			//
			case 283: {
				
				RelationCS result = (RelationCS)dtParser.getSym(1);
				WhenCS whenCS = (WhenCS)dtParser.getSym(2);
				result.setWhen(whenCS);
				setOffsets(result, result, whenCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 285:  relationCS_postWhere ::= relationCS_postWhen whereCS
			//
			case 285: {
				
				RelationCS result = (RelationCS)dtParser.getSym(1);
				WhereCS whereCS = (WhereCS)dtParser.getSym(2);
				result.setWhere(whereCS);
				setOffsets(result, result, whereCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 286:  relationCS ::= relationCS_postWhere }
			//
			case 286: {
				
				RelationCS result = (RelationCS)dtParser.getSym(1);
				setOffsets(result, result, getIToken(dtParser.getToken(2)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 287:  whenCS_0 ::= when {
			//
			case 287: {
				
				WhenCS result = QVTrCSTFactory.eINSTANCE.createWhenCS();
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(2)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 289:  whenCS_1 ::= whenCS_1 oclExpressionCS ;
			//
			case 289: {
				
				WhenCS result = (WhenCS)dtParser.getSym(1);
				OCLExpressionCS oclExpressionCS = (OCLExpressionCS)dtParser.getSym(2);
				result.getExpr().add(oclExpressionCS);
				setOffsets(result, result, getIToken(dtParser.getToken(3)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 290:  whenCS ::= whenCS_1 }
			//
			case 290: {
				
				WhenCS result = (WhenCS)dtParser.getSym(1);
				setOffsets(result, result, getIToken(dtParser.getToken(2)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 291:  whereCS_0 ::= where {
			//
			case 291: {
				
				WhereCS result = QVTrCSTFactory.eINSTANCE.createWhereCS();
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(2)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 293:  whereCS_1 ::= whereCS_1 oclExpressionCS ;
			//
			case 293: {
				
				WhereCS result = (WhereCS)dtParser.getSym(1);
				OCLExpressionCS oclExpressionCS = (OCLExpressionCS)dtParser.getSym(2);
				result.getExpr().add(oclExpressionCS);
				setOffsets(result, result, getIToken(dtParser.getToken(3)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 294:  whereCS ::= whereCS_1 }
			//
			case 294: {
				
				WhereCS result = (WhereCS)dtParser.getSym(1);
				setOffsets(result, result, getIToken(dtParser.getToken(2)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 295:  varDeclarationCS_0 ::= identifierCS
			//
			case 295: {
				
				IdentifierCS identifierCS = (IdentifierCS)dtParser.getSym(1);
				VarDeclarationCS result = QVTrCSTFactory.eINSTANCE.createVarDeclarationCS();
				result.getVarDeclarationId().add(identifierCS);
				setOffsets(result, identifierCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 296:  varDeclarationCS_0 ::= varDeclarationCS_0 , identifierCS
			//
			case 296: {
				
				IdentifierCS identifierCS = (IdentifierCS)dtParser.getSym(3);
				VarDeclarationCS result = (VarDeclarationCS)dtParser.getSym(1);
				result.getVarDeclarationId().add(identifierCS);
				setOffsets(result, result, identifierCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 297:  varDeclarationCS ::= varDeclarationCS_0 : typeCS ;
			//
			case 297: {
				
				VarDeclarationCS result = (VarDeclarationCS)dtParser.getSym(1);
				result.setType((TypeCS)dtParser.getSym(3));
				setOffsets(result, result, getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 298:  domainCS_0_ ::= domain modelIdCS templateCS
			//
			case 298: {
				
				IdentifierCS identifierCS = (IdentifierCS)dtParser.getSym(2);
				TemplateCS templateCS = (TemplateCS)dtParser.getSym(3);
				DomainCS result = QVTrCSTFactory.eINSTANCE.createDomainCS();
				result.setModelId(identifierCS);
				result.setTemplate(templateCS);
				setOffsets(result, getIToken(dtParser.getToken(1)), templateCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 300:  domainCS_1_ ::= checkonly domainCS_0_
			//
			case 300: {
				
				DomainCS result = (DomainCS)dtParser.getSym(2);
				result.setCheckonly(true);
				setOffsets(result, getIToken(dtParser.getToken(1)), result);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 301:  domainCS_1_ ::= enforce domainCS_0_
			//
			case 301: {
				
				DomainCS result = (DomainCS)dtParser.getSym(2);
				result.setEnforce(true);
				setOffsets(result, getIToken(dtParser.getToken(1)), result);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 302:  domainCS_1_ ::= replace domainCS_0_
			//
			case 302: {
				
				DomainCS result = (DomainCS)dtParser.getSym(2);
				result.setReplace(true);
				setOffsets(result, getIToken(dtParser.getToken(1)), result);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 304:  domainCS_postImplementedby ::= domainCS_1_ implementedby operationCallExpCS
			//
			case 304: {
				
				DomainCS result = (DomainCS)dtParser.getSym(1);
				OperationCallExpCS operationCallExpCS =(OperationCallExpCS)dtParser.getSym(3);
				result.setImplementedBy(operationCallExpCS);
				setOffsets(result, result, operationCallExpCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 306:  domainCS_preDefaultValue ::= domainCS_preDefaultValue defaultValueCS
			//
			case 306: {
				
				DomainCS result = (DomainCS)dtParser.getSym(1);
				DefaultValueCS defaultValueCS = (DefaultValueCS)dtParser.getSym(2);
				result.getDefaultValue().add(defaultValueCS);
				setOffsets(result, result, defaultValueCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 308:  domainCS_postDefaultValues ::= domainCS_preDefaultValue }
			//
			case 308: {
				
				DomainCS result = (DomainCS)dtParser.getSym(1);
				setOffsets(result, result, getIToken(dtParser.getToken(2)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 309:  domainCS ::= domainCS_postDefaultValues ;
			//
			case 309: {
				
				DomainCS result = (DomainCS)dtParser.getSym(1);
				setOffsets(result, result, getIToken(dtParser.getToken(2)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 310:  primitiveTypeDomainCS ::= primitive domain identifierCS : typeCS ;
			//
			case 310: {
				
				IdentifierCS identifierCS = (IdentifierCS)dtParser.getSym(3);
				TypeCS typeCS = (TypeCS)dtParser.getSym(5);
				PrimitiveTypeDomainCS result = QVTrCSTFactory.eINSTANCE.createPrimitiveTypeDomainCS();
				result.setIdentifier(identifierCS);
				result.setType(typeCS);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(6)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 314:  templateCS ::= templateCS_0_ { oclExpressionCS }
			//
			case 314: {
				
				TemplateCS result = (TemplateCS)dtParser.getSym(1);
				OCLExpressionCS oclExpressionCS = (OCLExpressionCS)dtParser.getSym(3);
				result.setGuardExpression(oclExpressionCS);
				setOffsets(result, result, getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 315:  objectTemplateCS_prePropertyTemplates ::= IDENTIFIER : pathNameCS {
			//
			case 315:
 
			//
			// Rule 316:  objectTemplateCS_prePropertyTemplates ::= relationIdentifier : pathNameCS {
			//
			case 316:
 
			//
			// Rule 317:  objectTemplateCS_prePropertyTemplates ::= self : pathNameCS {
			//
			case 317: {
				
				IdentifierCS identifierCS = createIdentifierOrUnderscoreCS(dtParser.getToken(1));
				TypeCS typeCS = (TypeCS)dtParser.getSym(3);
				ObjectTemplateCS result = QVTrCSTFactory.eINSTANCE.createObjectTemplateCS();
				result.setType(typeCS);
				result.setIdentifier(identifierCS);
				setOffsets(result, identifierCS, getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 318:  objectTemplateCS_prePropertyTemplates ::= : pathNameCS {
			//
			case 318: {
				
				ObjectTemplateCS result = QVTrCSTFactory.eINSTANCE.createObjectTemplateCS();
				result.setIdentifier(createUniqueIdentifierCS(dtParser.getToken(1)));
				result.setType((TypeCS)dtParser.getSym(2));
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(3)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 321:  objectTemplateCS_postPropertyTemplate ::= objectTemplateCS_prePropertyTemplate propertyTemplateCS
			//
			case 321: {
				
				ObjectTemplateCS result = (ObjectTemplateCS)dtParser.getSym(1);
				PropertyTemplateCS propertyTemplateCS = (PropertyTemplateCS)dtParser.getSym(2);
				result.getPropertyTemplate().add(propertyTemplateCS);
				setOffsets(result, result, propertyTemplateCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 322:  objectTemplateCS ::= objectTemplateCS_prePropertyTemplates }
			//
			case 322:
 
			//
			// Rule 323:  objectTemplateCS ::= objectTemplateCS_postPropertyTemplate }
			//
			case 323: {
				
				ObjectTemplateCS result = (ObjectTemplateCS)dtParser.getSym(1);
				setOffsets(result, result, getIToken(dtParser.getToken(2)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 324:  propertyTemplateCS ::= propertyIdCS = oclExpressionCS
			//
			case 324: {
				
				IdentifiedCS propertyIdCS = (IdentifiedCS)dtParser.getSym(1);
				PropertyTemplateCS result = QVTrCSTFactory.eINSTANCE.createPropertyTemplateCS();
				OCLExpressionCS oclExpressionCS = (OCLExpressionCS)dtParser.getSym(3);
				result.setPropertyId(propertyIdCS);
				result.setOclExpression(oclExpressionCS);
				setOffsets(result, propertyIdCS, oclExpressionCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 325:  collectionTemplateCS_1_ ::= : collectionTypeCS
			//
			case 325: {
				
				CollectionTypeCS collectionTypeCS = (CollectionTypeCS)dtParser.getSym(2);
				CollectionTemplateCS result = QVTrCSTFactory.eINSTANCE.createCollectionTemplateCS();
				result.setType(collectionTypeCS);
				setOffsets(result, getIToken(dtParser.getToken(1)), collectionTypeCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 326:  collectionTemplateCS_1_ ::= IDENTIFIER : collectionTypeCS
			//
			case 326:
 
			//
			// Rule 327:  collectionTemplateCS_1_ ::= relationIdentifier : collectionTypeCS
			//
			case 327:
 
			//
			// Rule 328:  collectionTemplateCS_1_ ::= self : collectionTypeCS
			//
			case 328: {
				
				IdentifierCS identifierCS = createIdentifierOrUnderscoreCS(dtParser.getToken(1));
				CollectionTypeCS collectionTypeCS = (CollectionTypeCS)dtParser.getSym(3);
				CollectionTemplateCS result = QVTrCSTFactory.eINSTANCE.createCollectionTemplateCS();
				result.setType(collectionTypeCS);
				result.setIdentifier(identifierCS);
				setOffsets(result, identifierCS, collectionTypeCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 332:  collectionTemplateCS_postMemberSelection ::= collectionTemplateCS_preMemberSelection memberSelectorCS
			//
			case 332: {
				
				IdentifiedCS memberSelectorCS = (IdentifiedCS)dtParser.getSym(2);
				CollectionTemplateCS result = (CollectionTemplateCS)dtParser.getSym(1);
				result.getMemberIdentifier().add(memberSelectorCS);
				setOffsets(result, result, memberSelectorCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 333:  collectionTemplateCS ::= collectionTemplateCS_postMemberSelection PLUS_PLUS identifierCS }
			//
			case 333: {
				
				IdentifierCS restIdentifier = (IdentifierCS)dtParser.getSym(3);
				CollectionTemplateCS result = (CollectionTemplateCS)dtParser.getSym(1);
				result.setRestIdentifier(restIdentifier);
				setOffsets(result, result, getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 334:  collectionTemplateCS ::= collectionTemplateCS_preMemberSelections }
			//
			case 334: {
				
				CollectionTemplateCS result = (CollectionTemplateCS)dtParser.getSym(1);
				setOffsets(result, result, getIToken(dtParser.getToken(2)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 335:  memberSelectorCS ::= identifierCS
			//
			case 335: {
				
				IdentifierCS identifierCS = (IdentifierCS)dtParser.getSym(1);
				IdentifiedCS result = QVTCSTFactory.eINSTANCE.createIdentifiedCS();
				result.setIdentifier(identifierCS);
				setOffsets(result, identifierCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 337:  defaultValueCS ::= identifierCS = oclExpressionCS ;
			//
			case 337: {
				
				IdentifierCS identifierCS = (IdentifierCS)dtParser.getSym(1);
				OCLExpressionCS oclExpressionCS = (OCLExpressionCS)dtParser.getSym(3);
				DefaultValueCS result = QVTrCSTFactory.eINSTANCE.createDefaultValueCS();
				result.setIdentifier(identifierCS);
				result.setInitialiser(oclExpressionCS);
				setOffsets(result, identifierCS, getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 338:  queryCS_preParamDeclaration ::= query pathNameCS (
			//
			case 338: {
				
				QueryCS result = QVTrCSTFactory.eINSTANCE.createQueryCS();
				result.setPathName((PathNameCS)dtParser.getSym(2));
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(3)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 340:  queryCS_postParamDeclaration ::= queryCS_preParamDeclaration paramDeclarationCS
			//
			case 340: {
				
				ParamDeclarationCS paramDeclarationCS = (ParamDeclarationCS)dtParser.getSym(2);
				QueryCS result = (QueryCS)dtParser.getSym(1);
				result.getInputParamDeclaration().add(paramDeclarationCS);
				setOffsets(result, result, paramDeclarationCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 341:  queryCS_postType ::= queryCS_postParamDeclaration ) : typeCS
			//
			case 341: {
				
				TypeCS typeCS = (TypeCS)dtParser.getSym(4);
				QueryCS result = (QueryCS)dtParser.getSym(1);
				result.setType(typeCS);
				setOffsets(result, result, typeCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 342:  queryCS ::= queryCS_postType ;
			//
			case 342: {
				
				QueryCS result = (QueryCS)dtParser.getSym(1);
				setOffsets(result, result, getIToken(dtParser.getToken(3)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 343:  queryCS ::= queryCS_postType { oclExpressionCS }
			//
			case 343: {
				
				QueryCS result = (QueryCS)dtParser.getSym(1);
				result.setOclExpression((OCLExpressionCS)dtParser.getSym(3));
				setOffsets(result, result, getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 344:  paramDeclarationCS ::= identifierCS : typeCS
			//
			case 344: {
				
				IdentifierCS identifierCS = (IdentifierCS)dtParser.getSym(1);
				TypeCS typeCS = (TypeCS)dtParser.getSym(3);
				ParamDeclarationCS result = QVTrCSTFactory.eINSTANCE.createParamDeclarationCS();
				result.setIdentifier(identifierCS);
				result.setType(typeCS);
				setOffsets(result, identifierCS, typeCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 368:  pathNameCS ::= relationIdentifier
			//
			case 368: {
				
				PathNameCS result = createPathNameCS();
				result.getSequenceOfNames().add(createIdentifierOrUnderscore(dtParser.getToken(1)));
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 369:  simpleNameCS ::= relationIdentifier
			//
			case 369: {
				
				SimpleNameCS result = createSimpleNameCS(
							SimpleTypeEnum.IDENTIFIER_LITERAL,
							createIdentifierOrUnderscore(dtParser.getToken(1))
						);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 370:  identifierCS ::= IDENTIFIER
			//
			case 370: 
 
			//
			// Rule 371:  identifierCS ::= relationIdentifier
			//
			case 371: 
 
			//
			// Rule 372:  identifierCS ::= self
			//
			case 372: {
				
				IdentifierCS result = createIdentifierOrUnderscoreCS(dtParser.getToken(1));
				dtParser.setSym1(result);
	  		  break;
			}
	
	
			default:
				break;
		}
		return;
	}
}

