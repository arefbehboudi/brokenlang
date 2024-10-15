package org.aref;

import java.util.ArrayList;
import java.util.List;

class Token {
    String type;
    String value;

    Token(String type, String value) {
        this.type = type;
        this.value = value;
    }
}

class Lexer {
    private final String input;
    private int pos = 0;
    private char currentChar;

    Lexer(String input) {
        this.input = input;
        this.currentChar = input.charAt(pos);
    }

    private void advance() {
        pos++;
        if (pos >= input.length()) {
            currentChar = '\0'; // End of input
        } else {
            currentChar = input.charAt(pos);
        }
    }

    private void advance(int steps) {
        for (int i = 0; i < steps; i++) {
            advance();
        }
    }

    private void skipWhitespace() {
        while (currentChar != '\0' && Character.isWhitespace(currentChar)) {
            advance();
        }
    }

    private String collectIdentifier() {
        StringBuilder result = new StringBuilder();
        while (currentChar != '\0' && Character.isLetter(currentChar)) {
            result.append(currentChar);
            advance();
        }
        return result.toString();
    }

    private String collectNumber() {
        StringBuilder result = new StringBuilder();
        while (currentChar != '\0' && Character.isDigit(currentChar)) {
            result.append(currentChar);
            advance();
        }
        return result.toString();
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (currentChar != '\0') {
            if (Character.isWhitespace(currentChar)) {
                skipWhitespace();
            } else if (Character.isLetter(currentChar)) {
                String identifier = collectIdentifier();
                tokens.add(new Token("IDENTIFIER", identifier));
            } else if (Character.isDigit(currentChar)) {
                String number = collectNumber();
                tokens.add(new Token("NUMBER", number));
            } else if (currentChar == '+') {
                tokens.add(new Token("PLUS", "+"));
                advance();
            } else if (currentChar == '-') {
                tokens.add(new Token("MINUS", "-"));
                advance();
            } else if (currentChar == '*') {
                tokens.add(new Token("MULTIPLY", "*"));
                advance();
            } else if (currentChar == '/') {
                tokens.add(new Token("DIVIDE", "/"));
                advance();
            } else if (currentChar == '=') {
                tokens.add(new Token("ASSIGN", "="));
                advance();
            } else if (currentChar == 'i' && peek("if")) {
                tokens.add(new Token("IF", "if"));
                advance(2); // پیشروی برای خواندن "if"
            } else if (currentChar == 'e' && peek("else")) {
                tokens.add(new Token("ELSE", "else"));
                advance(4); // پیشروی برای خواندن "else"
            } else {
                throw new RuntimeException("Unexpected character: " + currentChar);
            }
        }
        return tokens;
    }

    private boolean peek(String keyword) {
        return input.startsWith(keyword, pos);
    }
}
