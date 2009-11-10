/**
* Essential OCL Grammar
* <copyright>
*
* Copyright (c) 2005, 2009 IBM Corporation and others.
* All rights reserved.   This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*   IBM - Initial API and implementation
*   E.D.Willink - Elimination of some shift-reduce conflicts
*   E.D.Willink - Remove unnecessary warning suppression
*   E.D.Willink - Bugs 184048, 225493, 243976, 259818, 282882, 287993, 288040, 292112
*   Borland - Bug 242880
*   E.D.Willink - Extended API and implementation for QVTr
*
* </copyright>
*
* $Id: QVTrParsersym.java,v 1.13 2009/11/10 06:12:52 ewillink Exp $
*/

package org.eclipse.qvt.declarative.parser.qvtrelation;

public interface QVTrParsersym {
    public final static int
      TK_PLUS_PLUS = 74,
      TK_INTEGER_LITERAL = 29,
      TK_REAL_LITERAL = 30,
      TK_STRING_LITERAL = 26,
      TK_PLUS = 37,
      TK_MINUS = 5,
      TK_MULTIPLY = 6,
      TK_DIVIDE = 40,
      TK_GREATER = 45,
      TK_LESS = 46,
      TK_EQUAL = 38,
      TK_GREATER_EQUAL = 47,
      TK_LESS_EQUAL = 48,
      TK_NOT_EQUAL = 64,
      TK_LPAREN = 2,
      TK_RPAREN = 7,
      TK_LBRACE = 11,
      TK_RBRACE = 9,
      TK_LBRACKET = 68,
      TK_RBRACKET = 72,
      TK_ARROW = 43,
      TK_BAR = 65,
      TK_COMMA = 10,
      TK_COLON = 4,
      TK_COLONCOLON = 27,
      TK_SEMICOLON = 41,
      TK_DOT = 42,
      TK_DOTDOT = 75,
      TK_self = 8,
      TK_if = 31,
      TK_then = 76,
      TK_else = 77,
      TK_endif = 73,
      TK_and = 66,
      TK_or = 67,
      TK_xor = 69,
      TK_not = 32,
      TK_implies = 70,
      TK_let = 39,
      TK_in = 78,
      TK_true = 33,
      TK_false = 34,
      TK_null = 35,
      TK_invalid = 36,
      TK_Set = 12,
      TK_Bag = 13,
      TK_Sequence = 14,
      TK_Collection = 15,
      TK_OrderedSet = 16,
      TK_String = 17,
      TK_Integer = 18,
      TK_UnlimitedNatural = 19,
      TK_Real = 20,
      TK_Boolean = 21,
      TK_Tuple = 22,
      TK_OclAny = 23,
      TK_OclVoid = 24,
      TK_OclInvalid = 25,
      TK_checkonly = 51,
      TK_default_values = 49,
      TK_domain = 28,
      TK_enforce = 52,
      TK_extends = 53,
      TK_implementedby = 54,
      TK_import = 55,
      TK_key = 56,
      TK_overrides = 57,
      TK_primitive = 58,
      TK_query = 59,
      TK_relation = 50,
      TK_replace = 60,
      TK_top = 61,
      TK_transformation = 44,
      TK_when = 62,
      TK_where = 63,
      TK_EOF_TOKEN = 71,
      TK_IDENTIFIER = 3,
      TK_SINGLE_LINE_COMMENT = 79,
      TK_MULTI_LINE_COMMENT = 80,
      TK_ERROR_TOKEN = 1;

      public final static String orderedTerminalSymbols[] = {
                 "",
                 "ERROR_TOKEN",
                 "LPAREN",
                 "IDENTIFIER",
                 "COLON",
                 "MINUS",
                 "MULTIPLY",
                 "RPAREN",
                 "self",
                 "RBRACE",
                 "COMMA",
                 "LBRACE",
                 "Set",
                 "Bag",
                 "Sequence",
                 "Collection",
                 "OrderedSet",
                 "String",
                 "Integer",
                 "UnlimitedNatural",
                 "Real",
                 "Boolean",
                 "Tuple",
                 "OclAny",
                 "OclVoid",
                 "OclInvalid",
                 "STRING_LITERAL",
                 "COLONCOLON",
                 "domain",
                 "INTEGER_LITERAL",
                 "REAL_LITERAL",
                 "if",
                 "not",
                 "true",
                 "false",
                 "null",
                 "invalid",
                 "PLUS",
                 "EQUAL",
                 "let",
                 "DIVIDE",
                 "SEMICOLON",
                 "DOT",
                 "ARROW",
                 "transformation",
                 "GREATER",
                 "LESS",
                 "GREATER_EQUAL",
                 "LESS_EQUAL",
                 "default_values",
                 "relation",
                 "checkonly",
                 "enforce",
                 "extends",
                 "implementedby",
                 "import",
                 "key",
                 "overrides",
                 "primitive",
                 "query",
                 "replace",
                 "top",
                 "when",
                 "where",
                 "NOT_EQUAL",
                 "BAR",
                 "and",
                 "or",
                 "LBRACKET",
                 "xor",
                 "implies",
                 "EOF_TOKEN",
                 "RBRACKET",
                 "endif",
                 "PLUS_PLUS",
                 "DOTDOT",
                 "then",
                 "else",
                 "in",
                 "SINGLE_LINE_COMMENT",
                 "MULTI_LINE_COMMENT"
             };

    public final static boolean isValidForParser = true;
}
