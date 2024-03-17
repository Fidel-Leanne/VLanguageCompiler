package org.example;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class LexerTest {

    @Test
    public void testTokenizeValidCode() throws LexicalException {
        String inputCode = "BEGIN\n" +
                "INTEG A, B, C, D, E\n"
                ;

        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.tokenize(inputCode);

        System.out.println("Number of tokens: " + tokens.size());
        for (Token token : tokens) {
            System.out.println(token);
        }

        assertEquals(7, tokens.size()); // Check total number of tokens
        // You can add more assertions to check individual tokens if needed
    }

    @Test
    public void testTokenizeMoreValidCode() throws LexicalException {
        String inputCode = "BEGIN\n" +
                "INTEG A, B, C, D, E\n" +
                "LET B = A - C\n" +
                "INPUT A, B, C\n" +
                "D = A + B / C\n" +
                "E = G / H - I + a * B / c\n" +
                "WRITE D\n" +
                "END";

        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.tokenize(inputCode);



        System.out.println("Number of tokens: " + tokens.size());
        for (Token token : tokens) {
            System.out.println(token);
        }

        int expectedTokenCount = 40; // Adjust this according to your expected token count
        assertEquals(expectedTokenCount, tokens.size());
    }



    @Test
    public void testTokenizeInvalidOperator() {
        String inputCode = "BEGIN\n" +
                "INTEG A, B, C, D, E\n" +
                "LET B = A - / C\n" + // Invalid operator
                "INPUT A, B, C\n" +
                "D = A+B/C\n" +
                "E = G/H-I+a*B/c\n" +
                "WRITE D\n" +
                "END";

        Lexer lexer = new Lexer();

        assertThrows(LexicalException.class, () -> lexer.tokenize(inputCode));
    }

    @Test
    public void testTokenizeInvalidCharacter() {
        String inputCode = "BEGIN\n" +
                "INTEG A, B, C, D, E\n" +
                "LET B = A - C\n" +
                "INPUT A, B, C\n" +
                "temp = <s%*h - j / w - d +*$&;\n" + // Invalid character sequence
                "D = A+B/C\n" +
                "E = G/H-I+a*B/c\n" +
                "WRITE D\n" +
                "WRITEE F;\n" + // Misspelled keyword
                "END";

        Lexer lexer = new Lexer();

        assertThrows(LexicalException.class, () -> lexer.tokenize(inputCode));
    }

    @Test
    public void testTokenizeMisspelledKeyword() {
        String inputCode = "WRITEE F;\n" + // Misspelled keyword
                "END";

        Lexer lexer = new Lexer();

        assertThrows(LexicalException.class, () -> lexer.tokenize(inputCode));
    }

    @Test
    public void testTokenizeLineWithSemicolon() {
        String inputCode = "BEGIN\n" +
                "INTEG A, B, C, D, E\n" +
                "LET B = A - C;\n" +  // Line ends with a semicolon
                "INPUT A, B, C\n" +
                "D = A + B / C\n" +
                "E = G / H - I + a * B / c\n" +
                "WRITE D\n" +
                "END";

        Lexer lexer = new Lexer();

        assertThrows(LexicalException.class, () -> lexer.tokenize(inputCode));
    }

}
