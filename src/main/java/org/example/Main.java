package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String inputCode = "BEGIN\n"+"INTEG A\n"
                +"END";

        Lexer lexer = new Lexer();

        try {
            // Tokenize the input code
            List<Token> tokens = lexer.tokenize(inputCode);

            // Create a Parser object with the list of tokens
            Parser parser = new Parser(tokens);

            // Parse the tokens
            parser.parse();
        } catch (LexicalException e) {
            System.out.println("Lexical error: " + e.getMessage());
        }
    }
}
