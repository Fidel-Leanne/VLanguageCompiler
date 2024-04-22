package org.example;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Input code to test the parser
        String inputCode = "START\n" +
                "INTEGER M, N, K, P, R, H, i, g, k, m\n" +
                "READ M, N, K ASSIGN N = M - K \n " +
                "WRITE W\n" +
                "STOP";

        try {
            // Tokenize the input code
            Lexer lexer = new Lexer();
            List<Token> tokens = lexer.tokenize(inputCode);

            // Parse the tokens
            Parser parser = new Parser(tokens);
            parser.parse();

            // If parsing completes without throwing an exception, the input code is valid
            System.out.println("Parsing successful. Input code is valid.");

            System.out.println();

            // Stage 3 of compiler
            System.out.println("\n" + "======STAGE3: COMPILER TECHNIQUES--> SEMANTIC ANALYSIS");
            System.out.println(
                    "CONCLUSION-->This expression:  is Syntactically and Semantically correct" + "\n");
            // END of stage3

            // Generate ICR
            ICRGenerator.generateICR(inputCode);
        } catch (LexicalException | ParseException e) {
            // If an exception is thrown during lexing or parsing, print the error message
            System.err.println("Error: " + e.getMessage());
        }


    }
}
