package com.ayomideoyekanmi.explanck.interpreter;

import java.util.List;

/**
 * @author: aoyekanmi
 * @date: 11/03/2022
 */
public class Parser {
    private static class ParseError extends RuntimeException {
    }

    private final List<Token> tokens;
    int current = 0;

    Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    Expr parse() {
        try {
            return expression();
        } catch (ParseError error) {
            return null;
        }
    }

    private Expr expression() {
        return term();
    }

    private Expr term() {
        Expr expr = factor();

        // 2 + 3 + 3 + 4;
        while (match(TokenType.MINUS, TokenType.PLUS)) {
            Token operator = previous();
            Expr right = factor();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr factor() {
        Expr expr = power();

        while (match(TokenType.STAR, TokenType.SLASH)) {
            Token operator = previous();
            Expr right = power();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr power() {
        Expr expr = primary();

        while (match(TokenType.POWER)) {
            Token operator = previous();
            Expr right = primary();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr primary() {
        if (match(TokenType.NUMBER)) {
            return new Expr.Literal(previous().literal);
        }

        if (match(TokenType.LEFT_PAREN)) {
            Expr expr = expression();

            // Confirm the there's a right parenthesis or throw error
            if (peek().type != TokenType.RIGHT_PAREN) {
                throw error("Expect ')' after expression");
            }
            advance();

            return new Expr.Grouping(expr);
        }

        throw error("Expect expression.");
    }

    // Checks if the current character is one of the types
    private boolean match(TokenType... types) {
        if (isAtEnd()) return false;

        for (TokenType type : types) {
            if (peek().type == type) {
                advance();
                return true;
            }
        }

        return false;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private void advance() {
        if (!isAtEnd()) current++;
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private boolean isAtEnd() {
        return current >= tokens.size();
    }

    private ParseError error(String message) {
        Explanck.error(message);
        return new ParseError();
    }
}
