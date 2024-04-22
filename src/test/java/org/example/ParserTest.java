package org.example;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void testValidProgram() throws ParseException, LexicalException {
        // Input code with a valid program
        String inputCode = "START\n" +
                "INTEGER M, N, K, P, R, H, i, g, k, m\n" +
                "READ M, N, K ASSIGN N = M - K \n " +
                "R = M + k / k"+
                "WRITE W\n" +
                "STOP";;
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.tokenize(inputCode);

        // Parse the tokens
        Parser parser = new Parser(tokens);
        assertDoesNotThrow(parser::parse, "Parsing should not throw an exception for valid program");
    }

    @Test
    public void testInvalidProgram() throws LexicalException {
        // Input code with an invalid program
        String inputCode = "START INTEGER M, N ASSIGN M  WRITE M "; // Missing STOP keyword
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.tokenize(inputCode);

        // Parse the tokens
        Parser parser = new Parser(tokens);
        ParseException exception = assertThrows(ParseException.class, parser::parse, "Parsing should throw ParseException for invalid program");
        assertEquals(null, exception.getMessage(), "ParseException message should match");
    }



    @Test
    public void testMissingStartKeyword() {
        String inputCode = "INTEGER N STOP";
        assertThrows(ParseException.class, () -> {
            Lexer lexer = new Lexer();
            List<Token> tokens = lexer.tokenize(inputCode);
            Parser parser = new Parser(tokens);
            parser.parse();
        });
    }

    @Test
    public void testInvalidAssignmentSyntax() {
        String inputCode = "START ASSIGN N = STOP";
        assertThrows(ParseException.class, () -> {
            Lexer lexer = new Lexer();
            List<Token> tokens = lexer.tokenize(inputCode);
            Parser parser = new Parser(tokens);
            parser.parse();
        });
    }





}
