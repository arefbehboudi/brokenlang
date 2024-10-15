package org.aref;

abstract class ASTNode {}

class NumberNode extends ASTNode {
    String value;

    NumberNode(String value) {
        this.value = value;
    }
}

class VariableNode extends ASTNode {
    String name;

    VariableNode(String name) {
        this.name = name;
    }
}

class BinaryOperation extends ASTNode {
    String operator;
    ASTNode left;
    ASTNode right;

    BinaryOperation(ASTNode left, String operator, ASTNode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
}

class IfElseNode extends ASTNode {
    ASTNode condition;
    ASTNode ifBlock;
    ASTNode elseBlock;

    IfElseNode(ASTNode condition, ASTNode ifBlock, ASTNode elseBlock) {
        this.condition = condition;
        this.ifBlock = ifBlock;
        this.elseBlock = elseBlock;
    }
}



