package org.aref;


import java.util.ArrayList;
import java.util.List;

class CodeGenerator {
    private List<String> instructions = new ArrayList<>();

    private void generateOptimizedBinaryOperation(BinaryOperation operation) {
        if (operation.left instanceof NumberNode && operation.right instanceof NumberNode) {
            String leftValue = ((NumberNode) operation.left).value;
            String rightValue = ((NumberNode) operation.right).value;

            if (operation.operator.equals("+")) {
                instructions.add("mov eax, " + leftValue);
                instructions.add("add eax, " + rightValue);
            } else if (operation.operator.equals("-")) {
                instructions.add("mov eax, " + leftValue);
                instructions.add("sub eax, " + rightValue);
            } else if (operation.operator.equals("*")) {
                instructions.add("mov eax, " + leftValue);
                instructions.add("imul eax, " + rightValue);
            } else if (operation.operator.equals("/")) {
                instructions.add("mov eax, " + leftValue);
                instructions.add("mov ebx, " + rightValue);
                instructions.add("idiv ebx");
            }
        }
    }

    private void generateIfElse(IfElseNode ifElseNode) {
        String trueLabel = generateLabel();
        String falseLabel = generateLabel();
        String endLabel = generateLabel();

        if (ifElseNode.condition instanceof BinaryOperation) {
            BinaryOperation condition = (BinaryOperation) ifElseNode.condition;
            generateOptimizedBinaryOperation(condition);
            instructions.add("cmp eax, 0");
            instructions.add("je " + falseLabel);
        }

        instructions.add(trueLabel + ":");
        generateBlock(ifElseNode.ifBlock);
        instructions.add("jmp " + endLabel);

        instructions.add(falseLabel + ":");
        if (ifElseNode.elseBlock != null) {
            generateBlock(ifElseNode.elseBlock);
        }

        instructions.add(endLabel + ":");
    }

    private void generateBlock(ASTNode block) {
        if (block instanceof NumberNode) {
            instructions.add("mov eax, " + ((NumberNode) block).value);
        } else if (block instanceof BinaryOperation) {
            generateOptimizedBinaryOperation((BinaryOperation) block);
        }
    }

    private String generateLabel() {
        return "label" + (instructions.size() + 1);
    }

    public List<String> generate(ASTNode ast) {
        if (ast instanceof BinaryOperation) {
            generateOptimizedBinaryOperation((BinaryOperation) ast);
        } else if (ast instanceof IfElseNode) {
            generateIfElse((IfElseNode) ast);
        }
        return instructions;
    }
}