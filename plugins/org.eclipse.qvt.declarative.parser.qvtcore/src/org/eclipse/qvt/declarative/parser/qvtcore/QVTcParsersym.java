/**
* <copyright>
*
* Copyright (c) 2005, 2008 IBM Corporation and others.
* All rights reserved.   This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*   IBM - Initial API and implementation
*   E.D.Willink - Elimination of some shift-reduce conflicts
*   E.D.Willink - Remove unnecessary warning suppression
*   E.D.Willink - 225493 Need ability to set CSTNode offsets
*   E.D.Willink - Extended API and implementation for QVTc
*
* </copyright>
*
* $Id: QVTcParsersym.java,v 1.3 2008/08/14 07:57:56 ewillink Exp $
*/

package org.eclipse.qvt.declarative.parser.qvtcore;

public interface QVTcParsersym {
    public final static int
      TK_COLON_EQUALS = 93,
      TK_NUMERIC_OPERATION = 76,
      TK_STRING_LITERAL = 4,
      TK_INTEGER_LITERAL = 77,
      TK_REAL_LITERAL = 78,
      TK_PLUS = 43,
      TK_MINUS = 67,
      TK_MULTIPLY = 24,
      TK_DIVIDE = 25,
      TK_GREATER = 26,
      TK_LESS = 27,
      TK_EQUAL = 5,
      TK_GREATER_EQUAL = 28,
      TK_LESS_EQUAL = 29,
      TK_NOT_EQUAL = 16,
      TK_LPAREN = 1,
      TK_RPAREN = 2,
      TK_LBRACE = 83,
      TK_RBRACE = 90,
      TK_LBRACKET = 94,
      TK_RBRACKET = 92,
      TK_ARROW = 95,
      TK_BAR = 88,
      TK_COMMA = 30,
      TK_COLON = 84,
      TK_COLONCOLON = 85,
      TK_SEMICOLON = 89,
      TK_DOT = 96,
      TK_DOTDOT = 101,
      TK_ATPRE = 91,
      TK_CARET = 97,
      TK_CARETCARET = 98,
      TK_QUESTIONMARK = 102,
      TK_self = 44,
      TK_inv = 17,
      TK_pre = 18,
      TK_post = 19,
      TK_context = 109,
      TK_package = 110,
      TK_endpackage = 20,
      TK_def = 21,
      TK_if = 87,
      TK_then = 103,
      TK_else = 104,
      TK_endif = 105,
      TK_and = 31,
      TK_or = 32,
      TK_xor = 33,
      TK_not = 79,
      TK_implies = 106,
      TK_let = 86,
      TK_in = 99,
      TK_true = 80,
      TK_false = 81,
      TK_body = 50,
      TK_derive = 51,
      TK_init = 52,
      TK_null = 53,
      TK_attr = 111,
      TK_oper = 112,
      TK_Set = 45,
      TK_Bag = 46,
      TK_Sequence = 47,
      TK_Collection = 48,
      TK_OrderedSet = 49,
      TK_iterate = 54,
      TK_forAll = 55,
      TK_exists = 56,
      TK_isUnique = 57,
      TK_any = 58,
      TK_one = 59,
      TK_collect = 60,
      TK_select = 61,
      TK_reject = 62,
      TK_collectNested = 63,
      TK_sortedBy = 64,
      TK_closure = 65,
      TK_oclIsKindOf = 68,
      TK_oclIsTypeOf = 69,
      TK_oclAsType = 70,
      TK_oclIsNew = 71,
      TK_oclIsUndefined = 72,
      TK_oclIsInvalid = 73,
      TK_oclIsInState = 74,
      TK_allInstances = 66,
      TK_String = 34,
      TK_Integer = 35,
      TK_UnlimitedNatural = 36,
      TK_Real = 37,
      TK_Boolean = 38,
      TK_Tuple = 75,
      TK_OclAny = 39,
      TK_OclVoid = 40,
      TK_Invalid = 41,
      TK_OclMessage = 42,
      TK_OclInvalid = 82,
      TK_check = 9,
      TK_creation = 22,
      TK_default = 107,
      TK_deletion = 23,
      TK_enforce = 6,
      TK_imports = 10,
      TK_map = 7,
      TK_query = 11,
      TK_realize = 8,
      TK_refines = 12,
      TK_transformation = 13,
      TK_uses = 14,
      TK_where = 15,
      TK_EOF_TOKEN = 108,
      TK_IDENTIFIER = 3,
      TK_INTEGER_RANGE_START = 100,
      TK_ERROR_TOKEN = 113;

      public final static String orderedTerminalSymbols[] = {
                 "",
                 "LPAREN",
                 "RPAREN",
                 "IDENTIFIER",
                 "STRING_LITERAL",
                 "EQUAL",
                 "enforce",
                 "map",
                 "realize",
                 "check",
                 "imports",
                 "query",
                 "refines",
                 "transformation",
                 "uses",
                 "where",
                 "NOT_EQUAL",
                 "inv",
                 "pre",
                 "post",
                 "endpackage",
                 "def",
                 "creation",
                 "deletion",
                 "MULTIPLY",
                 "DIVIDE",
                 "GREATER",
                 "LESS",
                 "GREATER_EQUAL",
                 "LESS_EQUAL",
                 "COMMA",
                 "and",
                 "or",
                 "xor",
                 "String",
                 "Integer",
                 "UnlimitedNatural",
                 "Real",
                 "Boolean",
                 "OclAny",
                 "OclVoid",
                 "Invalid",
                 "OclMessage",
                 "PLUS",
                 "self",
                 "Set",
                 "Bag",
                 "Sequence",
                 "Collection",
                 "OrderedSet",
                 "body",
                 "derive",
                 "init",
                 "null",
                 "iterate",
                 "forAll",
                 "exists",
                 "isUnique",
                 "any",
                 "one",
                 "collect",
                 "select",
                 "reject",
                 "collectNested",
                 "sortedBy",
                 "closure",
                 "allInstances",
                 "MINUS",
                 "oclIsKindOf",
                 "oclIsTypeOf",
                 "oclAsType",
                 "oclIsNew",
                 "oclIsUndefined",
                 "oclIsInvalid",
                 "oclIsInState",
                 "Tuple",
                 "NUMERIC_OPERATION",
                 "INTEGER_LITERAL",
                 "REAL_LITERAL",
                 "not",
                 "true",
                 "false",
                 "OclInvalid",
                 "LBRACE",
                 "COLON",
                 "COLONCOLON",
                 "let",
                 "if",
                 "BAR",
                 "SEMICOLON",
                 "RBRACE",
                 "ATPRE",
                 "RBRACKET",
                 "COLON_EQUALS",
                 "LBRACKET",
                 "ARROW",
                 "DOT",
                 "CARET",
                 "CARETCARET",
                 "in",
                 "INTEGER_RANGE_START",
                 "DOTDOT",
                 "QUESTIONMARK",
                 "then",
                 "else",
                 "endif",
                 "implies",
                 "default",
                 "EOF_TOKEN",
                 "context",
                 "package",
                 "attr",
                 "oper",
                 "ERROR_TOKEN"
             };

    public final static boolean isValidForParser = true;
}
