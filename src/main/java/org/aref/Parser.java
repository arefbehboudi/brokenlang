package org.aref;


import java.util.List;

class Parser {
    private List<Token> tokens;
    private int pos = 0;
    private Token currentToken;

    Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.currentToken = tokens.get(pos);
    }

    private void advance() {
        pos++;
        if (pos < tokens.size()) {
            currentToken = tokens.get(pos);
        } else {
            currentToken = null;
        }
    }

    private ASTNode parseTerm() {
        ASTNode left = parseFactor();
        while (currentToken != null && (currentToken.type.equals("MULTIPLY") || currentToken.type.equals("DIVIDE"))) {
            String operator = currentToken.value;
            advance();
            ASTNode right = parseFactor();
            left = new BinaryOperation(left, operator, right);
        }
        return left;
    }

    private ASTNode parseFactor() {
        if (currentToken.type.equals("NUMBER")) {
            String value = currentToken.value;
            advance();
            return new NumberNode(value);
        } else if (currentToken.type.equals("IDENTIFIER")) {
            String name = currentToken.value;
            advance();
            return new VariableNode(name);
        } else {
            throw new RuntimeException("Unexpected token: " + currentToken);
        }
    }

    private ASTNode parseCondition() {
        ASTNode left = parseTerm();
        String operator = currentToken.value;
        advance();
        ASTNode right = parseTerm();
        return new BinaryOperation(left, operator, right);
    }

    private ASTNode parseBlock() {
        return new NumberNode("0");
    }

    public ASTNode parse() {
        if (currentToken.type.equals("IF")) {
            advance();
            ASTNode condition = parseCondition();
            ASTNode ifBlock = parseBlock();
            ASTNode elseBlock = null;
            if (currentToken != null && currentToken.type.equals("ELSE")) {
                advance();
                elseBlock = parseBlock();
            }
            return new IfElseNode(condition, ifBlock, elseBlock);
        } else {
            return parseTerm();
        }
    }
}
