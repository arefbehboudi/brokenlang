package org.aref;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String input = "if 3 == 3 else 4";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);
        ASTNode ast = parser.parse();

        CodeGenerator codeGenerator = new CodeGenerator();
        List<String> assemblyCode = codeGenerator.generate(ast);

        System.out.println("Generated Assembly Code:");
        for (String instruction : assemblyCode) {
            System.out.println(instruction);
        }
    }
}