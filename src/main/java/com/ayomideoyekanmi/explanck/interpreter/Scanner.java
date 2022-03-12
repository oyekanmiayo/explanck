package com.ayomideoyekanmi.explanck.interpreter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: aoyekanmi
 * @date: 11/03/2022
 */
public class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;

    Scanner(String source) {
        this.source = source;
    }

    List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }

        return tokens;
    }


    private void scanToken() {
        char c = peek();
        advance();

        switch (c) {
            // @formatter:off
            case '(': add(TokenType.LEFT_PAREN); break;
            case ')': add(TokenType.RIGHT_PAREN); break;
            case '*': add(TokenType.STAR); break;
            case '/': add(TokenType.SLASH); break;
            case '+': add(TokenType.PLUS); break;
            case '-': add(TokenType.MINUS); break;
            case '^': add(TokenType.POWER); break;
            // @formatter:on

            case ' ':
            case '\r':
            case '\t':
                break;

            default:
                if (isDigit(c)) {
                    addNumberToken();
                } else {
                    Explanck.error("Unexpected character: " + c);
                }
        }
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private void advance() {
        current++;
    }

    private void add(TokenType type) {
        add(type, null);
    }

    private void add(TokenType type, Object literal) {
        tokens.add(new Token(type, literal));
    }

    private void addNumberToken() {
        while (isDigit(peek())) advance();

        // Look for fractional part
        if (peek() == '.' && isDigit(peekNext())) {
            // Consume the '.'
            advance();

            while (isDigit(peek())) advance();
        }

        add(TokenType.NUMBER, Double.parseDouble(source.substring(start, current)));
    }
}
