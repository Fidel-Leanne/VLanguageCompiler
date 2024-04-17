package org.example;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void testValidProgram() throws ParseException, LexicalException {
        // Input code with a valid program
        String inputCode = "START READ M, N ASSIGN M WRITE M STOP";
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.tokenize(inputCode);

        // Parse the tokens
        Parser parser = new Parser(tokens);
        assertDoesNotThrow(parser::parse, "Parsing should not throw an exception for valid program");
    }

    @Test
    public void testInvalidProgram() throws LexicalException {
        // Input code with an invalid program
        String inputCode = "START INTEGER M, N ASSIGN M  WRITE M"; // Missing STOP keyword
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.tokenize(inputCode);

        // Parse the tokens
        Parser parser = new Parser(tokens);
        ParseException exception = assertThrows(ParseException.class, parser::parse, "Parsing should throw ParseException for invalid program");
        assertEquals("Syntax error: Expected KEYWORD_STOP ", exception.getMessage(), "ParseException message should match");
    }
}
