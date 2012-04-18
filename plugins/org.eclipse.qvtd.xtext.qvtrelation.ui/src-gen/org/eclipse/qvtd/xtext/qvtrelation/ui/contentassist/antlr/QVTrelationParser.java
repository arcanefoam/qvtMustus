/*
* generated by Xtext
*/
package org.eclipse.qvtd.xtext.qvtrelation.ui.contentassist.antlr;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.RecognitionException;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.AbstractContentAssistParser;
import org.eclipse.xtext.ui.editor.contentassist.antlr.FollowElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;

import com.google.inject.Inject;

import org.eclipse.qvtd.xtext.qvtrelation.services.QVTrelationGrammarAccess;

public class QVTrelationParser extends AbstractContentAssistParser {
	
	@Inject
	private QVTrelationGrammarAccess grammarAccess;
	
	private Map<AbstractElement, String> nameMappings;
	
	@Override
	protected org.eclipse.qvtd.xtext.qvtrelation.ui.contentassist.antlr.internal.InternalQVTrelationParser createParser() {
		org.eclipse.qvtd.xtext.qvtrelation.ui.contentassist.antlr.internal.InternalQVTrelationParser result = new org.eclipse.qvtd.xtext.qvtrelation.ui.contentassist.antlr.internal.InternalQVTrelationParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}
	
	@Override
	protected String getRuleName(AbstractElement element) {
		if (nameMappings == null) {
			nameMappings = new HashMap<AbstractElement, String>() {
				private static final long serialVersionUID = 1L;
				{
					put(grammarAccess.getTransformationCSAccess().getAlternatives_9(), "rule__TransformationCS__Alternatives_9");
					put(grammarAccess.getModelDeclCSAccess().getAlternatives_2(), "rule__ModelDeclCS__Alternatives_2");
					put(grammarAccess.getRelationCSAccess().getAlternatives_6(), "rule__RelationCS__Alternatives_6");
					put(grammarAccess.getDomainCSAccess().getAlternatives_0(), "rule__DomainCS__Alternatives_0");
					put(grammarAccess.getTemplateCSAccess().getAlternatives_0(), "rule__TemplateCS__Alternatives_0");
					put(grammarAccess.getCollectionTemplateCSAccess().getMemberIdentifierAlternatives_4_0_0(), "rule__CollectionTemplateCS__MemberIdentifierAlternatives_4_0_0");
					put(grammarAccess.getCollectionTemplateCSAccess().getMemberIdentifierAlternatives_4_1_1_0(), "rule__CollectionTemplateCS__MemberIdentifierAlternatives_4_1_1_0");
					put(grammarAccess.getCollectionTemplateCSAccess().getRestIdentifierAlternatives_4_3_0(), "rule__CollectionTemplateCS__RestIdentifierAlternatives_4_3_0");
					put(grammarAccess.getQueryCSAccess().getAlternatives_7(), "rule__QueryCS__Alternatives_7");
					put(grammarAccess.getUnrestrictedNameAccess().getAlternatives(), "rule__UnrestrictedName__Alternatives");
					put(grammarAccess.getIDAccess().getAlternatives(), "rule__ID__Alternatives");
					put(grammarAccess.getUPPERAccess().getAlternatives(), "rule__UPPER__Alternatives");
					put(grammarAccess.getNUMBER_LITERALAccess().getAlternatives_2_0(), "rule__NUMBER_LITERAL__Alternatives_2_0");
					put(grammarAccess.getNUMBER_LITERALAccess().getAlternatives_2_1(), "rule__NUMBER_LITERAL__Alternatives_2_1");
					put(grammarAccess.getEssentialOCLReservedKeywordAccess().getAlternatives(), "rule__EssentialOCLReservedKeyword__Alternatives");
					put(grammarAccess.getEssentialOCLUnrestrictedIdentifierAccess().getAlternatives(), "rule__EssentialOCLUnrestrictedIdentifier__Alternatives");
					put(grammarAccess.getEssentialOCLPrefixOperatorAccess().getAlternatives(), "rule__EssentialOCLPrefixOperator__Alternatives");
					put(grammarAccess.getEssentialOCLInfixOperatorAccess().getAlternatives(), "rule__EssentialOCLInfixOperator__Alternatives");
					put(grammarAccess.getEssentialOCLNavigationOperatorAccess().getAlternatives(), "rule__EssentialOCLNavigationOperator__Alternatives");
					put(grammarAccess.getIdentifierAccess().getAlternatives(), "rule__Identifier__Alternatives");
					put(grammarAccess.getEssentialOCLUnreservedNameAccess().getAlternatives(), "rule__EssentialOCLUnreservedName__Alternatives");
					put(grammarAccess.getPrimitiveTypeIdentifierAccess().getAlternatives(), "rule__PrimitiveTypeIdentifier__Alternatives");
					put(grammarAccess.getCollectionTypeIdentifierAccess().getAlternatives(), "rule__CollectionTypeIdentifier__Alternatives");
					put(grammarAccess.getMultiplicityCSAccess().getAlternatives_1(), "rule__MultiplicityCS__Alternatives_1");
					put(grammarAccess.getMultiplicityStringCSAccess().getStringBoundsAlternatives_0(), "rule__MultiplicityStringCS__StringBoundsAlternatives_0");
					put(grammarAccess.getPrimitiveLiteralExpCSAccess().getAlternatives(), "rule__PrimitiveLiteralExpCS__Alternatives");
					put(grammarAccess.getBooleanLiteralExpCSAccess().getAlternatives(), "rule__BooleanLiteralExpCS__Alternatives");
					put(grammarAccess.getTypeLiteralCSAccess().getAlternatives(), "rule__TypeLiteralCS__Alternatives");
					put(grammarAccess.getTypeExpCSAccess().getAlternatives_0(), "rule__TypeExpCS__Alternatives_0");
					put(grammarAccess.getExpCSAccess().getAlternatives(), "rule__ExpCS__Alternatives");
					put(grammarAccess.getBinaryOperatorCSAccess().getAlternatives(), "rule__BinaryOperatorCS__Alternatives");
					put(grammarAccess.getPrefixedExpCSAccess().getAlternatives(), "rule__PrefixedExpCS__Alternatives");
					put(grammarAccess.getPrefixedExpOrLetExpCSAccess().getAlternatives(), "rule__PrefixedExpOrLetExpCS__Alternatives");
					put(grammarAccess.getPrimaryExpCSAccess().getAlternatives(), "rule__PrimaryExpCS__Alternatives");
					put(grammarAccess.getPrimaryExpOrLetExpCSAccess().getAlternatives(), "rule__PrimaryExpOrLetExpCS__Alternatives");
					put(grammarAccess.getTopLevelCSAccess().getGroup(), "rule__TopLevelCS__Group__0");
					put(grammarAccess.getTopLevelCSAccess().getGroup_0(), "rule__TopLevelCS__Group_0__0");
					put(grammarAccess.getUnitCSAccess().getGroup(), "rule__UnitCS__Group__0");
					put(grammarAccess.getUnitCSAccess().getGroup_1(), "rule__UnitCS__Group_1__0");
					put(grammarAccess.getTransformationCSAccess().getGroup(), "rule__TransformationCS__Group__0");
					put(grammarAccess.getTransformationCSAccess().getGroup_4(), "rule__TransformationCS__Group_4__0");
					put(grammarAccess.getTransformationCSAccess().getGroup_6(), "rule__TransformationCS__Group_6__0");
					put(grammarAccess.getTransformationCSAccess().getGroup_6_2(), "rule__TransformationCS__Group_6_2__0");
					put(grammarAccess.getModelDeclCSAccess().getGroup(), "rule__ModelDeclCS__Group__0");
					put(grammarAccess.getModelDeclCSAccess().getGroup_2_1(), "rule__ModelDeclCS__Group_2_1__0");
					put(grammarAccess.getModelDeclCSAccess().getGroup_2_1_2(), "rule__ModelDeclCS__Group_2_1_2__0");
					put(grammarAccess.getKeyDeclCSAccess().getGroup(), "rule__KeyDeclCS__Group__0");
					put(grammarAccess.getKeyDeclCSAccess().getGroup_4(), "rule__KeyDeclCS__Group_4__0");
					put(grammarAccess.getRelationCSAccess().getGroup(), "rule__RelationCS__Group__0");
					put(grammarAccess.getRelationCSAccess().getGroup_3(), "rule__RelationCS__Group_3__0");
					put(grammarAccess.getWhenCSAccess().getGroup(), "rule__WhenCS__Group__0");
					put(grammarAccess.getWhenCSAccess().getGroup_3(), "rule__WhenCS__Group_3__0");
					put(grammarAccess.getWhereCSAccess().getGroup(), "rule__WhereCS__Group__0");
					put(grammarAccess.getWhereCSAccess().getGroup_3(), "rule__WhereCS__Group_3__0");
					put(grammarAccess.getVarDeclarationCSAccess().getGroup(), "rule__VarDeclarationCS__Group__0");
					put(grammarAccess.getVarDeclarationCSAccess().getGroup_1(), "rule__VarDeclarationCS__Group_1__0");
					put(grammarAccess.getDomainCSAccess().getGroup(), "rule__DomainCS__Group__0");
					put(grammarAccess.getDomainCSAccess().getGroup_4(), "rule__DomainCS__Group_4__0");
					put(grammarAccess.getDomainCSAccess().getGroup_5(), "rule__DomainCS__Group_5__0");
					put(grammarAccess.getPrimitiveTypeDomainCSAccess().getGroup(), "rule__PrimitiveTypeDomainCS__Group__0");
					put(grammarAccess.getTemplateCSAccess().getGroup(), "rule__TemplateCS__Group__0");
					put(grammarAccess.getTemplateCSAccess().getGroup_1(), "rule__TemplateCS__Group_1__0");
					put(grammarAccess.getObjectTemplateCSAccess().getGroup(), "rule__ObjectTemplateCS__Group__0");
					put(grammarAccess.getObjectTemplateCSAccess().getGroup_4(), "rule__ObjectTemplateCS__Group_4__0");
					put(grammarAccess.getObjectTemplateCSAccess().getGroup_4_1(), "rule__ObjectTemplateCS__Group_4_1__0");
					put(grammarAccess.getPropertyTemplateCSAccess().getGroup(), "rule__PropertyTemplateCS__Group__0");
					put(grammarAccess.getCollectionTemplateCSAccess().getGroup(), "rule__CollectionTemplateCS__Group__0");
					put(grammarAccess.getCollectionTemplateCSAccess().getGroup_4(), "rule__CollectionTemplateCS__Group_4__0");
					put(grammarAccess.getCollectionTemplateCSAccess().getGroup_4_1(), "rule__CollectionTemplateCS__Group_4_1__0");
					put(grammarAccess.getAnyElementCSAccess().getGroup(), "rule__AnyElementCS__Group__0");
					put(grammarAccess.getDefaultValueCSAccess().getGroup(), "rule__DefaultValueCS__Group__0");
					put(grammarAccess.getQueryCSAccess().getGroup(), "rule__QueryCS__Group__0");
					put(grammarAccess.getQueryCSAccess().getGroup_3(), "rule__QueryCS__Group_3__0");
					put(grammarAccess.getQueryCSAccess().getGroup_3_1(), "rule__QueryCS__Group_3_1__0");
					put(grammarAccess.getQueryCSAccess().getGroup_7_1(), "rule__QueryCS__Group_7_1__0");
					put(grammarAccess.getParamDeclarationCSAccess().getGroup(), "rule__ParamDeclarationCS__Group__0");
					put(grammarAccess.getNUMBER_LITERALAccess().getGroup(), "rule__NUMBER_LITERAL__Group__0");
					put(grammarAccess.getNUMBER_LITERALAccess().getGroup_1(), "rule__NUMBER_LITERAL__Group_1__0");
					put(grammarAccess.getNUMBER_LITERALAccess().getGroup_2(), "rule__NUMBER_LITERAL__Group_2__0");
					put(grammarAccess.getPathNameCSAccess().getGroup(), "rule__PathNameCS__Group__0");
					put(grammarAccess.getPathNameCSAccess().getGroup_1(), "rule__PathNameCS__Group_1__0");
					put(grammarAccess.getCollectionTypeCSAccess().getGroup(), "rule__CollectionTypeCS__Group__0");
					put(grammarAccess.getCollectionTypeCSAccess().getGroup_1(), "rule__CollectionTypeCS__Group_1__0");
					put(grammarAccess.getMultiplicityBoundsCSAccess().getGroup(), "rule__MultiplicityBoundsCS__Group__0");
					put(grammarAccess.getMultiplicityBoundsCSAccess().getGroup_1(), "rule__MultiplicityBoundsCS__Group_1__0");
					put(grammarAccess.getMultiplicityCSAccess().getGroup(), "rule__MultiplicityCS__Group__0");
					put(grammarAccess.getTupleTypeCSAccess().getGroup(), "rule__TupleTypeCS__Group__0");
					put(grammarAccess.getTupleTypeCSAccess().getGroup_1(), "rule__TupleTypeCS__Group_1__0");
					put(grammarAccess.getTupleTypeCSAccess().getGroup_1_1(), "rule__TupleTypeCS__Group_1_1__0");
					put(grammarAccess.getTupleTypeCSAccess().getGroup_1_1_1(), "rule__TupleTypeCS__Group_1_1_1__0");
					put(grammarAccess.getTuplePartCSAccess().getGroup(), "rule__TuplePartCS__Group__0");
					put(grammarAccess.getCollectionLiteralExpCSAccess().getGroup(), "rule__CollectionLiteralExpCS__Group__0");
					put(grammarAccess.getCollectionLiteralExpCSAccess().getGroup_2(), "rule__CollectionLiteralExpCS__Group_2__0");
					put(grammarAccess.getCollectionLiteralExpCSAccess().getGroup_2_1(), "rule__CollectionLiteralExpCS__Group_2_1__0");
					put(grammarAccess.getCollectionLiteralPartCSAccess().getGroup(), "rule__CollectionLiteralPartCS__Group__0");
					put(grammarAccess.getCollectionLiteralPartCSAccess().getGroup_1(), "rule__CollectionLiteralPartCS__Group_1__0");
					put(grammarAccess.getConstructorPartCSAccess().getGroup(), "rule__ConstructorPartCS__Group__0");
					put(grammarAccess.getTupleLiteralExpCSAccess().getGroup(), "rule__TupleLiteralExpCS__Group__0");
					put(grammarAccess.getTupleLiteralExpCSAccess().getGroup_3(), "rule__TupleLiteralExpCS__Group_3__0");
					put(grammarAccess.getTupleLiteralPartCSAccess().getGroup(), "rule__TupleLiteralPartCS__Group__0");
					put(grammarAccess.getTupleLiteralPartCSAccess().getGroup_1(), "rule__TupleLiteralPartCS__Group_1__0");
					put(grammarAccess.getUnlimitedNaturalLiteralExpCSAccess().getGroup(), "rule__UnlimitedNaturalLiteralExpCS__Group__0");
					put(grammarAccess.getInvalidLiteralExpCSAccess().getGroup(), "rule__InvalidLiteralExpCS__Group__0");
					put(grammarAccess.getNullLiteralExpCSAccess().getGroup(), "rule__NullLiteralExpCS__Group__0");
					put(grammarAccess.getTypeExpCSAccess().getGroup(), "rule__TypeExpCS__Group__0");
					put(grammarAccess.getExpCSAccess().getGroup_0(), "rule__ExpCS__Group_0__0");
					put(grammarAccess.getExpCSAccess().getGroup_0_1(), "rule__ExpCS__Group_0_1__0");
					put(grammarAccess.getExpCSAccess().getGroup_1(), "rule__ExpCS__Group_1__0");
					put(grammarAccess.getPrefixedExpCSAccess().getGroup_1(), "rule__PrefixedExpCS__Group_1__0");
					put(grammarAccess.getPrefixedExpOrLetExpCSAccess().getGroup_1(), "rule__PrefixedExpOrLetExpCS__Group_1__0");
					put(grammarAccess.getPrimaryExpCSAccess().getGroup_0(), "rule__PrimaryExpCS__Group_0__0");
					put(grammarAccess.getPrimaryExpCSAccess().getGroup_0_4(), "rule__PrimaryExpCS__Group_0_4__0");
					put(grammarAccess.getPrimaryExpCSAccess().getGroup_0_6(), "rule__PrimaryExpCS__Group_0_6__0");
					put(grammarAccess.getPrimaryExpCSAccess().getGroup_0_6_2(), "rule__PrimaryExpCS__Group_0_6_2__0");
					put(grammarAccess.getPrimaryExpCSAccess().getGroup_0_7(), "rule__PrimaryExpCS__Group_0_7__0");
					put(grammarAccess.getPrimaryExpCSAccess().getGroup_1(), "rule__PrimaryExpCS__Group_1__0");
					put(grammarAccess.getPrimaryExpCSAccess().getGroup_1_4(), "rule__PrimaryExpCS__Group_1_4__0");
					put(grammarAccess.getPrimaryExpCSAccess().getGroup_2(), "rule__PrimaryExpCS__Group_2__0");
					put(grammarAccess.getPrimaryExpCSAccess().getGroup_2_2(), "rule__PrimaryExpCS__Group_2_2__0");
					put(grammarAccess.getPrimaryExpCSAccess().getGroup_2_4(), "rule__PrimaryExpCS__Group_2_4__0");
					put(grammarAccess.getPrimaryExpCSAccess().getGroup_2_4_2(), "rule__PrimaryExpCS__Group_2_4_2__0");
					put(grammarAccess.getPrimaryExpCSAccess().getGroup_2_4_3(), "rule__PrimaryExpCS__Group_2_4_3__0");
					put(grammarAccess.getPrimaryExpCSAccess().getGroup_3(), "rule__PrimaryExpCS__Group_3__0");
					put(grammarAccess.getPrimaryExpCSAccess().getGroup_3_2(), "rule__PrimaryExpCS__Group_3_2__0");
					put(grammarAccess.getNavigatingArgCSAccess().getGroup(), "rule__NavigatingArgCS__Group__0");
					put(grammarAccess.getNavigatingArgCSAccess().getGroup_1(), "rule__NavigatingArgCS__Group_1__0");
					put(grammarAccess.getNavigatingArgCSAccess().getGroup_1_2(), "rule__NavigatingArgCS__Group_1_2__0");
					put(grammarAccess.getNavigatingBarArgCSAccess().getGroup(), "rule__NavigatingBarArgCS__Group__0");
					put(grammarAccess.getNavigatingBarArgCSAccess().getGroup_2(), "rule__NavigatingBarArgCS__Group_2__0");
					put(grammarAccess.getNavigatingBarArgCSAccess().getGroup_2_2(), "rule__NavigatingBarArgCS__Group_2_2__0");
					put(grammarAccess.getNavigatingCommaArgCSAccess().getGroup(), "rule__NavigatingCommaArgCS__Group__0");
					put(grammarAccess.getNavigatingCommaArgCSAccess().getGroup_2(), "rule__NavigatingCommaArgCS__Group_2__0");
					put(grammarAccess.getNavigatingCommaArgCSAccess().getGroup_2_2(), "rule__NavigatingCommaArgCS__Group_2_2__0");
					put(grammarAccess.getNavigatingSemiArgCSAccess().getGroup(), "rule__NavigatingSemiArgCS__Group__0");
					put(grammarAccess.getNavigatingSemiArgCSAccess().getGroup_2(), "rule__NavigatingSemiArgCS__Group_2__0");
					put(grammarAccess.getNavigatingSemiArgCSAccess().getGroup_2_2(), "rule__NavigatingSemiArgCS__Group_2_2__0");
					put(grammarAccess.getIfExpCSAccess().getGroup(), "rule__IfExpCS__Group__0");
					put(grammarAccess.getLetExpCSAccess().getGroup(), "rule__LetExpCS__Group__0");
					put(grammarAccess.getLetExpCSAccess().getGroup_2(), "rule__LetExpCS__Group_2__0");
					put(grammarAccess.getLetVariableCSAccess().getGroup(), "rule__LetVariableCS__Group__0");
					put(grammarAccess.getLetVariableCSAccess().getGroup_1(), "rule__LetVariableCS__Group_1__0");
					put(grammarAccess.getNestedExpCSAccess().getGroup(), "rule__NestedExpCS__Group__0");
					put(grammarAccess.getSelfExpCSAccess().getGroup(), "rule__SelfExpCS__Group__0");
					put(grammarAccess.getTopLevelCSAccess().getImportClauseAssignment_0_1(), "rule__TopLevelCS__ImportClauseAssignment_0_1");
					put(grammarAccess.getTopLevelCSAccess().getTransformationAssignment_1(), "rule__TopLevelCS__TransformationAssignment_1");
					put(grammarAccess.getUnitCSAccess().getIdentifierAssignment_0(), "rule__UnitCS__IdentifierAssignment_0");
					put(grammarAccess.getUnitCSAccess().getIdentifierAssignment_1_1(), "rule__UnitCS__IdentifierAssignment_1_1");
					put(grammarAccess.getTransformationCSAccess().getNameAssignment_1(), "rule__TransformationCS__NameAssignment_1");
					put(grammarAccess.getTransformationCSAccess().getModelDeclAssignment_3(), "rule__TransformationCS__ModelDeclAssignment_3");
					put(grammarAccess.getTransformationCSAccess().getModelDeclAssignment_4_1(), "rule__TransformationCS__ModelDeclAssignment_4_1");
					put(grammarAccess.getTransformationCSAccess().getExtendsAssignment_6_1(), "rule__TransformationCS__ExtendsAssignment_6_1");
					put(grammarAccess.getTransformationCSAccess().getExtendsAssignment_6_2_1(), "rule__TransformationCS__ExtendsAssignment_6_2_1");
					put(grammarAccess.getTransformationCSAccess().getKeyDeclAssignment_8(), "rule__TransformationCS__KeyDeclAssignment_8");
					put(grammarAccess.getTransformationCSAccess().getRelationAssignment_9_0(), "rule__TransformationCS__RelationAssignment_9_0");
					put(grammarAccess.getTransformationCSAccess().getQueryAssignment_9_1(), "rule__TransformationCS__QueryAssignment_9_1");
					put(grammarAccess.getModelDeclCSAccess().getNameAssignment_0(), "rule__ModelDeclCS__NameAssignment_0");
					put(grammarAccess.getModelDeclCSAccess().getMetaModelIdAssignment_2_0(), "rule__ModelDeclCS__MetaModelIdAssignment_2_0");
					put(grammarAccess.getModelDeclCSAccess().getMetaModelIdAssignment_2_1_1(), "rule__ModelDeclCS__MetaModelIdAssignment_2_1_1");
					put(grammarAccess.getModelDeclCSAccess().getMetaModelIdAssignment_2_1_2_1(), "rule__ModelDeclCS__MetaModelIdAssignment_2_1_2_1");
					put(grammarAccess.getKeyDeclCSAccess().getPathNameAssignment_1(), "rule__KeyDeclCS__PathNameAssignment_1");
					put(grammarAccess.getKeyDeclCSAccess().getPropertyIdAssignment_3(), "rule__KeyDeclCS__PropertyIdAssignment_3");
					put(grammarAccess.getKeyDeclCSAccess().getPropertyIdAssignment_4_1(), "rule__KeyDeclCS__PropertyIdAssignment_4_1");
					put(grammarAccess.getRelationCSAccess().getTopAssignment_0(), "rule__RelationCS__TopAssignment_0");
					put(grammarAccess.getRelationCSAccess().getNameAssignment_2(), "rule__RelationCS__NameAssignment_2");
					put(grammarAccess.getRelationCSAccess().getOverridesAssignment_3_1(), "rule__RelationCS__OverridesAssignment_3_1");
					put(grammarAccess.getRelationCSAccess().getVarDeclarationAssignment_5(), "rule__RelationCS__VarDeclarationAssignment_5");
					put(grammarAccess.getRelationCSAccess().getDomainAssignment_6_0(), "rule__RelationCS__DomainAssignment_6_0");
					put(grammarAccess.getRelationCSAccess().getDomainAssignment_6_1(), "rule__RelationCS__DomainAssignment_6_1");
					put(grammarAccess.getRelationCSAccess().getWhenAssignment_7(), "rule__RelationCS__WhenAssignment_7");
					put(grammarAccess.getRelationCSAccess().getWhereAssignment_8(), "rule__RelationCS__WhereAssignment_8");
					put(grammarAccess.getWhenCSAccess().getExprAssignment_3_0(), "rule__WhenCS__ExprAssignment_3_0");
					put(grammarAccess.getWhereCSAccess().getExprAssignment_3_0(), "rule__WhereCS__ExprAssignment_3_0");
					put(grammarAccess.getVarDeclarationCSAccess().getVarDeclarationIdAssignment_0(), "rule__VarDeclarationCS__VarDeclarationIdAssignment_0");
					put(grammarAccess.getVarDeclarationCSAccess().getVarDeclarationIdAssignment_1_1(), "rule__VarDeclarationCS__VarDeclarationIdAssignment_1_1");
					put(grammarAccess.getVarDeclarationCSAccess().getTypeAssignment_3(), "rule__VarDeclarationCS__TypeAssignment_3");
					put(grammarAccess.getDomainCSAccess().getCheckonlyAssignment_0_0(), "rule__DomainCS__CheckonlyAssignment_0_0");
					put(grammarAccess.getDomainCSAccess().getEnforceAssignment_0_1(), "rule__DomainCS__EnforceAssignment_0_1");
					put(grammarAccess.getDomainCSAccess().getModelIdAssignment_2(), "rule__DomainCS__ModelIdAssignment_2");
					put(grammarAccess.getDomainCSAccess().getTemplateAssignment_3(), "rule__DomainCS__TemplateAssignment_3");
					put(grammarAccess.getDomainCSAccess().getImplementedByAssignment_4_1(), "rule__DomainCS__ImplementedByAssignment_4_1");
					put(grammarAccess.getDomainCSAccess().getDefaultValueAssignment_5_2(), "rule__DomainCS__DefaultValueAssignment_5_2");
					put(grammarAccess.getPrimitiveTypeDomainCSAccess().getNameAssignment_2(), "rule__PrimitiveTypeDomainCS__NameAssignment_2");
					put(grammarAccess.getPrimitiveTypeDomainCSAccess().getTypeAssignment_4(), "rule__PrimitiveTypeDomainCS__TypeAssignment_4");
					put(grammarAccess.getTemplateCSAccess().getGuardExpressionAssignment_1_1(), "rule__TemplateCS__GuardExpressionAssignment_1_1");
					put(grammarAccess.getObjectTemplateCSAccess().getPropertyIdAssignment_0(), "rule__ObjectTemplateCS__PropertyIdAssignment_0");
					put(grammarAccess.getObjectTemplateCSAccess().getTypeAssignment_2(), "rule__ObjectTemplateCS__TypeAssignment_2");
					put(grammarAccess.getObjectTemplateCSAccess().getPropertyTemplateAssignment_4_0(), "rule__ObjectTemplateCS__PropertyTemplateAssignment_4_0");
					put(grammarAccess.getObjectTemplateCSAccess().getPropertyTemplateAssignment_4_1_1(), "rule__ObjectTemplateCS__PropertyTemplateAssignment_4_1_1");
					put(grammarAccess.getPropertyTemplateCSAccess().getPropertyIdAssignment_0(), "rule__PropertyTemplateCS__PropertyIdAssignment_0");
					put(grammarAccess.getPropertyTemplateCSAccess().getOclExpressionAssignment_2(), "rule__PropertyTemplateCS__OclExpressionAssignment_2");
					put(grammarAccess.getCollectionTemplateCSAccess().getPropertyIdAssignment_0(), "rule__CollectionTemplateCS__PropertyIdAssignment_0");
					put(grammarAccess.getCollectionTemplateCSAccess().getTypeAssignment_2(), "rule__CollectionTemplateCS__TypeAssignment_2");
					put(grammarAccess.getCollectionTemplateCSAccess().getMemberIdentifierAssignment_4_0(), "rule__CollectionTemplateCS__MemberIdentifierAssignment_4_0");
					put(grammarAccess.getCollectionTemplateCSAccess().getMemberIdentifierAssignment_4_1_1(), "rule__CollectionTemplateCS__MemberIdentifierAssignment_4_1_1");
					put(grammarAccess.getCollectionTemplateCSAccess().getRestIdentifierAssignment_4_3(), "rule__CollectionTemplateCS__RestIdentifierAssignment_4_3");
					put(grammarAccess.getNameExpCSAccess().getPathNameAssignment(), "rule__NameExpCS__PathNameAssignment");
					put(grammarAccess.getDefaultValueCSAccess().getPropertyIdAssignment_0(), "rule__DefaultValueCS__PropertyIdAssignment_0");
					put(grammarAccess.getDefaultValueCSAccess().getInitialiserAssignment_2(), "rule__DefaultValueCS__InitialiserAssignment_2");
					put(grammarAccess.getQueryCSAccess().getPathNameAssignment_1(), "rule__QueryCS__PathNameAssignment_1");
					put(grammarAccess.getQueryCSAccess().getInputParamDeclarationAssignment_3_0(), "rule__QueryCS__InputParamDeclarationAssignment_3_0");
					put(grammarAccess.getQueryCSAccess().getInputParamDeclarationAssignment_3_1_1(), "rule__QueryCS__InputParamDeclarationAssignment_3_1_1");
					put(grammarAccess.getQueryCSAccess().getTypeAssignment_6(), "rule__QueryCS__TypeAssignment_6");
					put(grammarAccess.getQueryCSAccess().getOclExpressionAssignment_7_1_1(), "rule__QueryCS__OclExpressionAssignment_7_1_1");
					put(grammarAccess.getParamDeclarationCSAccess().getNameAssignment_0(), "rule__ParamDeclarationCS__NameAssignment_0");
					put(grammarAccess.getParamDeclarationCSAccess().getTypeAssignment_2(), "rule__ParamDeclarationCS__TypeAssignment_2");
					put(grammarAccess.getCollectionTypedRefCSAccess().getPathNameAssignment(), "rule__CollectionTypedRefCS__PathNameAssignment");
					put(grammarAccess.getTypedRefCSAccess().getPathNameAssignment(), "rule__TypedRefCS__PathNameAssignment");
					put(grammarAccess.getModelAccess().getOwnedExpressionAssignment(), "rule__Model__OwnedExpressionAssignment");
					put(grammarAccess.getPathNameCSAccess().getPathAssignment_0(), "rule__PathNameCS__PathAssignment_0");
					put(grammarAccess.getPathNameCSAccess().getPathAssignment_1_1(), "rule__PathNameCS__PathAssignment_1_1");
					put(grammarAccess.getFirstPathElementCSAccess().getElementAssignment(), "rule__FirstPathElementCS__ElementAssignment");
					put(grammarAccess.getNextPathElementCSAccess().getElementAssignment(), "rule__NextPathElementCS__ElementAssignment");
					put(grammarAccess.getPrimitiveTypeCSAccess().getNameAssignment(), "rule__PrimitiveTypeCS__NameAssignment");
					put(grammarAccess.getCollectionTypeCSAccess().getNameAssignment_0(), "rule__CollectionTypeCS__NameAssignment_0");
					put(grammarAccess.getCollectionTypeCSAccess().getOwnedTypeAssignment_1_1(), "rule__CollectionTypeCS__OwnedTypeAssignment_1_1");
					put(grammarAccess.getMultiplicityBoundsCSAccess().getLowerBoundAssignment_0(), "rule__MultiplicityBoundsCS__LowerBoundAssignment_0");
					put(grammarAccess.getMultiplicityBoundsCSAccess().getUpperBoundAssignment_1_1(), "rule__MultiplicityBoundsCS__UpperBoundAssignment_1_1");
					put(grammarAccess.getMultiplicityStringCSAccess().getStringBoundsAssignment(), "rule__MultiplicityStringCS__StringBoundsAssignment");
					put(grammarAccess.getTupleTypeCSAccess().getNameAssignment_0(), "rule__TupleTypeCS__NameAssignment_0");
					put(grammarAccess.getTupleTypeCSAccess().getOwnedPartsAssignment_1_1_0(), "rule__TupleTypeCS__OwnedPartsAssignment_1_1_0");
					put(grammarAccess.getTupleTypeCSAccess().getOwnedPartsAssignment_1_1_1_1(), "rule__TupleTypeCS__OwnedPartsAssignment_1_1_1_1");
					put(grammarAccess.getTuplePartCSAccess().getNameAssignment_0(), "rule__TuplePartCS__NameAssignment_0");
					put(grammarAccess.getTuplePartCSAccess().getOwnedTypeAssignment_2(), "rule__TuplePartCS__OwnedTypeAssignment_2");
					put(grammarAccess.getCollectionLiteralExpCSAccess().getOwnedTypeAssignment_0(), "rule__CollectionLiteralExpCS__OwnedTypeAssignment_0");
					put(grammarAccess.getCollectionLiteralExpCSAccess().getOwnedPartsAssignment_2_0(), "rule__CollectionLiteralExpCS__OwnedPartsAssignment_2_0");
					put(grammarAccess.getCollectionLiteralExpCSAccess().getOwnedPartsAssignment_2_1_1(), "rule__CollectionLiteralExpCS__OwnedPartsAssignment_2_1_1");
					put(grammarAccess.getCollectionLiteralPartCSAccess().getExpressionCSAssignment_0(), "rule__CollectionLiteralPartCS__ExpressionCSAssignment_0");
					put(grammarAccess.getCollectionLiteralPartCSAccess().getLastExpressionCSAssignment_1_1(), "rule__CollectionLiteralPartCS__LastExpressionCSAssignment_1_1");
					put(grammarAccess.getConstructorPartCSAccess().getPropertyAssignment_0(), "rule__ConstructorPartCS__PropertyAssignment_0");
					put(grammarAccess.getConstructorPartCSAccess().getInitExpressionAssignment_2(), "rule__ConstructorPartCS__InitExpressionAssignment_2");
					put(grammarAccess.getTupleLiteralExpCSAccess().getOwnedPartsAssignment_2(), "rule__TupleLiteralExpCS__OwnedPartsAssignment_2");
					put(grammarAccess.getTupleLiteralExpCSAccess().getOwnedPartsAssignment_3_1(), "rule__TupleLiteralExpCS__OwnedPartsAssignment_3_1");
					put(grammarAccess.getTupleLiteralPartCSAccess().getNameAssignment_0(), "rule__TupleLiteralPartCS__NameAssignment_0");
					put(grammarAccess.getTupleLiteralPartCSAccess().getOwnedTypeAssignment_1_1(), "rule__TupleLiteralPartCS__OwnedTypeAssignment_1_1");
					put(grammarAccess.getTupleLiteralPartCSAccess().getInitExpressionAssignment_3(), "rule__TupleLiteralPartCS__InitExpressionAssignment_3");
					put(grammarAccess.getNumberLiteralExpCSAccess().getNameAssignment(), "rule__NumberLiteralExpCS__NameAssignment");
					put(grammarAccess.getStringLiteralExpCSAccess().getNameAssignment(), "rule__StringLiteralExpCS__NameAssignment");
					put(grammarAccess.getBooleanLiteralExpCSAccess().getNameAssignment_0(), "rule__BooleanLiteralExpCS__NameAssignment_0");
					put(grammarAccess.getBooleanLiteralExpCSAccess().getNameAssignment_1(), "rule__BooleanLiteralExpCS__NameAssignment_1");
					put(grammarAccess.getTypeLiteralExpCSAccess().getOwnedTypeAssignment(), "rule__TypeLiteralExpCS__OwnedTypeAssignment");
					put(grammarAccess.getTypeNameExpCSAccess().getPathNameAssignment(), "rule__TypeNameExpCS__PathNameAssignment");
					put(grammarAccess.getTypeExpCSAccess().getMultiplicityAssignment_1(), "rule__TypeExpCS__MultiplicityAssignment_1");
					put(grammarAccess.getExpCSAccess().getOwnedExpressionAssignment_0_1_0(), "rule__ExpCS__OwnedExpressionAssignment_0_1_0");
					put(grammarAccess.getExpCSAccess().getOwnedOperatorAssignment_0_1_1(), "rule__ExpCS__OwnedOperatorAssignment_0_1_1");
					put(grammarAccess.getExpCSAccess().getOwnedExpressionAssignment_0_2(), "rule__ExpCS__OwnedExpressionAssignment_0_2");
					put(grammarAccess.getExpCSAccess().getOwnedOperatorAssignment_1_1(), "rule__ExpCS__OwnedOperatorAssignment_1_1");
					put(grammarAccess.getExpCSAccess().getOwnedExpressionAssignment_1_2(), "rule__ExpCS__OwnedExpressionAssignment_1_2");
					put(grammarAccess.getInfixOperatorCSAccess().getNameAssignment(), "rule__InfixOperatorCS__NameAssignment");
					put(grammarAccess.getNavigationOperatorCSAccess().getNameAssignment(), "rule__NavigationOperatorCS__NameAssignment");
					put(grammarAccess.getPrefixedExpCSAccess().getOwnedOperatorAssignment_1_1(), "rule__PrefixedExpCS__OwnedOperatorAssignment_1_1");
					put(grammarAccess.getPrefixedExpCSAccess().getOwnedExpressionAssignment_1_2(), "rule__PrefixedExpCS__OwnedExpressionAssignment_1_2");
					put(grammarAccess.getPrefixedExpOrLetExpCSAccess().getOwnedOperatorAssignment_1_1(), "rule__PrefixedExpOrLetExpCS__OwnedOperatorAssignment_1_1");
					put(grammarAccess.getPrefixedExpOrLetExpCSAccess().getOwnedExpressionAssignment_1_2(), "rule__PrefixedExpOrLetExpCS__OwnedExpressionAssignment_1_2");
					put(grammarAccess.getUnaryOperatorCSAccess().getNameAssignment(), "rule__UnaryOperatorCS__NameAssignment");
					put(grammarAccess.getPrimaryExpCSAccess().getPathNameAssignment_0_1(), "rule__PrimaryExpCS__PathNameAssignment_0_1");
					put(grammarAccess.getPrimaryExpCSAccess().getFirstIndexesAssignment_0_3(), "rule__PrimaryExpCS__FirstIndexesAssignment_0_3");
					put(grammarAccess.getPrimaryExpCSAccess().getFirstIndexesAssignment_0_4_1(), "rule__PrimaryExpCS__FirstIndexesAssignment_0_4_1");
					put(grammarAccess.getPrimaryExpCSAccess().getSecondIndexesAssignment_0_6_1(), "rule__PrimaryExpCS__SecondIndexesAssignment_0_6_1");
					put(grammarAccess.getPrimaryExpCSAccess().getSecondIndexesAssignment_0_6_2_1(), "rule__PrimaryExpCS__SecondIndexesAssignment_0_6_2_1");
					put(grammarAccess.getPrimaryExpCSAccess().getAtPreAssignment_0_7_0(), "rule__PrimaryExpCS__AtPreAssignment_0_7_0");
					put(grammarAccess.getPrimaryExpCSAccess().getPathNameAssignment_1_1(), "rule__PrimaryExpCS__PathNameAssignment_1_1");
					put(grammarAccess.getPrimaryExpCSAccess().getOwnedPartsAssignment_1_3(), "rule__PrimaryExpCS__OwnedPartsAssignment_1_3");
					put(grammarAccess.getPrimaryExpCSAccess().getOwnedPartsAssignment_1_4_1(), "rule__PrimaryExpCS__OwnedPartsAssignment_1_4_1");
					put(grammarAccess.getPrimaryExpCSAccess().getPathNameAssignment_2_1(), "rule__PrimaryExpCS__PathNameAssignment_2_1");
					put(grammarAccess.getPrimaryExpCSAccess().getAtPreAssignment_2_2_0(), "rule__PrimaryExpCS__AtPreAssignment_2_2_0");
					put(grammarAccess.getPrimaryExpCSAccess().getArgumentAssignment_2_4_0(), "rule__PrimaryExpCS__ArgumentAssignment_2_4_0");
					put(grammarAccess.getPrimaryExpCSAccess().getArgumentAssignment_2_4_1(), "rule__PrimaryExpCS__ArgumentAssignment_2_4_1");
					put(grammarAccess.getPrimaryExpCSAccess().getArgumentAssignment_2_4_2_0(), "rule__PrimaryExpCS__ArgumentAssignment_2_4_2_0");
					put(grammarAccess.getPrimaryExpCSAccess().getArgumentAssignment_2_4_2_1(), "rule__PrimaryExpCS__ArgumentAssignment_2_4_2_1");
					put(grammarAccess.getPrimaryExpCSAccess().getArgumentAssignment_2_4_3_0(), "rule__PrimaryExpCS__ArgumentAssignment_2_4_3_0");
					put(grammarAccess.getPrimaryExpCSAccess().getArgumentAssignment_2_4_3_1(), "rule__PrimaryExpCS__ArgumentAssignment_2_4_3_1");
					put(grammarAccess.getPrimaryExpCSAccess().getPathNameAssignment_3_1(), "rule__PrimaryExpCS__PathNameAssignment_3_1");
					put(grammarAccess.getPrimaryExpCSAccess().getAtPreAssignment_3_2_0(), "rule__PrimaryExpCS__AtPreAssignment_3_2_0");
					put(grammarAccess.getNavigatingArgCSAccess().getNameAssignment_0(), "rule__NavigatingArgCS__NameAssignment_0");
					put(grammarAccess.getNavigatingArgCSAccess().getOwnedTypeAssignment_1_1(), "rule__NavigatingArgCS__OwnedTypeAssignment_1_1");
					put(grammarAccess.getNavigatingArgCSAccess().getInitAssignment_1_2_1(), "rule__NavigatingArgCS__InitAssignment_1_2_1");
					put(grammarAccess.getNavigatingBarArgCSAccess().getPrefixAssignment_0(), "rule__NavigatingBarArgCS__PrefixAssignment_0");
					put(grammarAccess.getNavigatingBarArgCSAccess().getNameAssignment_1(), "rule__NavigatingBarArgCS__NameAssignment_1");
					put(grammarAccess.getNavigatingBarArgCSAccess().getOwnedTypeAssignment_2_1(), "rule__NavigatingBarArgCS__OwnedTypeAssignment_2_1");
					put(grammarAccess.getNavigatingBarArgCSAccess().getInitAssignment_2_2_1(), "rule__NavigatingBarArgCS__InitAssignment_2_2_1");
					put(grammarAccess.getNavigatingCommaArgCSAccess().getPrefixAssignment_0(), "rule__NavigatingCommaArgCS__PrefixAssignment_0");
					put(grammarAccess.getNavigatingCommaArgCSAccess().getNameAssignment_1(), "rule__NavigatingCommaArgCS__NameAssignment_1");
					put(grammarAccess.getNavigatingCommaArgCSAccess().getOwnedTypeAssignment_2_1(), "rule__NavigatingCommaArgCS__OwnedTypeAssignment_2_1");
					put(grammarAccess.getNavigatingCommaArgCSAccess().getInitAssignment_2_2_1(), "rule__NavigatingCommaArgCS__InitAssignment_2_2_1");
					put(grammarAccess.getNavigatingSemiArgCSAccess().getPrefixAssignment_0(), "rule__NavigatingSemiArgCS__PrefixAssignment_0");
					put(grammarAccess.getNavigatingSemiArgCSAccess().getNameAssignment_1(), "rule__NavigatingSemiArgCS__NameAssignment_1");
					put(grammarAccess.getNavigatingSemiArgCSAccess().getOwnedTypeAssignment_2_1(), "rule__NavigatingSemiArgCS__OwnedTypeAssignment_2_1");
					put(grammarAccess.getNavigatingSemiArgCSAccess().getInitAssignment_2_2_1(), "rule__NavigatingSemiArgCS__InitAssignment_2_2_1");
					put(grammarAccess.getIfExpCSAccess().getConditionAssignment_1(), "rule__IfExpCS__ConditionAssignment_1");
					put(grammarAccess.getIfExpCSAccess().getThenExpressionAssignment_3(), "rule__IfExpCS__ThenExpressionAssignment_3");
					put(grammarAccess.getIfExpCSAccess().getElseExpressionAssignment_5(), "rule__IfExpCS__ElseExpressionAssignment_5");
					put(grammarAccess.getLetExpCSAccess().getVariableAssignment_1(), "rule__LetExpCS__VariableAssignment_1");
					put(grammarAccess.getLetExpCSAccess().getVariableAssignment_2_1(), "rule__LetExpCS__VariableAssignment_2_1");
					put(grammarAccess.getLetExpCSAccess().getInAssignment_4(), "rule__LetExpCS__InAssignment_4");
					put(grammarAccess.getLetVariableCSAccess().getNameAssignment_0(), "rule__LetVariableCS__NameAssignment_0");
					put(grammarAccess.getLetVariableCSAccess().getOwnedTypeAssignment_1_1(), "rule__LetVariableCS__OwnedTypeAssignment_1_1");
					put(grammarAccess.getLetVariableCSAccess().getInitExpressionAssignment_3(), "rule__LetVariableCS__InitExpressionAssignment_3");
					put(grammarAccess.getNestedExpCSAccess().getSourceAssignment_1(), "rule__NestedExpCS__SourceAssignment_1");
				}
			};
		}
		return nameMappings.get(element);
	}
	
	@Override
	protected Collection<FollowElement> getFollowElements(AbstractInternalContentAssistParser parser) {
		try {
			org.eclipse.qvtd.xtext.qvtrelation.ui.contentassist.antlr.internal.InternalQVTrelationParser typedParser = (org.eclipse.qvtd.xtext.qvtrelation.ui.contentassist.antlr.internal.InternalQVTrelationParser) parser;
			typedParser.entryRuleTopLevelCS();
			return typedParser.getFollowElements();
		} catch(RecognitionException ex) {
			throw new RuntimeException(ex);
		}		
	}
	
	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT" };
	}
	
	public QVTrelationGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(QVTrelationGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}