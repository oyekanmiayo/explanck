package com.ayomideoyekanmi.explanck.interpreter;

/**
 * @author: aoyekanmi
 * @date: 11/03/2022
 */
public class Token {
    final TokenType type;
    final Object literal;

    Token(TokenType type, Object literal) {
        this.type = type;
        this.literal = literal;
    }

    public String toString() {
        if (type == null) {
            return literal.toString();
        }

        if (literal == null) {
            return type.toString();
        }

        return type + " " + literal;
    }
}
