package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String inputCode = "BEGIN\n" +
                "INTEG A, B, C, D, E\n" +
                "LET B = A - / C\n" +
                "INPUT A, B, C\n" +
                "temp = <s%*h - j / w - d +*$&;\n" +
                "D = A+B/C\n" +
                "E = G/H-I+a*B/c\n" +
                "WRITE D\n" +
                "WRITEE F;\n" +
                "END";

        Lexer lexer = new Lexer();

        try {
            List<Token> tokens = lexer.tokenize(inputCode);
            // Print tokens
            for (Token token : tokens) {
                System.out.println(token);
            }
        } catch (LexicalException e) {
            System.out.println("Lexical error: " + e.getMessage());
        }
    }
}
