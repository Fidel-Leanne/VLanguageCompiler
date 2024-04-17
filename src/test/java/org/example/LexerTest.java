package org.example;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LexerTest {

    @Test
    public void testTokenize() throws LexicalException {
        String inputCode = "START INTEGER M, N, K, P, R STOP ";
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.tokenize(inputCode);


        // Expected tokens
        Token[] expectedTokens = {
                new Token(TokenType.KEYWORD_START, "START"),
                new Token(TokenType.KEYWORD_INTEGER, "INTEGER"),
                new Token(TokenType.IDENTIFIER, "M"),
                new Token(TokenType.IDENTIFIER, "N"),
                new Token(TokenType.IDENTIFIER, "K"),
                new Token(TokenType.IDENTIFIER, "P"),
                new Token(TokenType.IDENTIFIER, "R"),

                new Token(TokenType.KEYWORD_STOP, "STOP"),

        };


        // Assert the size of the tokens list
        assertEquals(expectedTokens.length, tokens.size(), "Number of tokens does not match");

        // Assert each token individually
        for (int i = 0; i < expectedTokens.length; i++) {
            Token expectedToken = expectedTokens[i];
            Token actualToken = tokens.get(i);
            assertEquals(expectedToken.getType(), actualToken.getType(), "Token type mismatch at index " + i);
            assertEquals(expectedToken.getValue(), actualToken.getValue(), "Token value mismatch at index " + i);
        }
    }


    @Test
    public void testTokenizeMisspelledKeyword() {
        String inputCode = "INT F;\n" + // Misspelled keyword
                "STOP";

        Lexer lexer = new Lexer();

        assertThrows(LexicalException.class, () -> lexer.tokenize(inputCode));
    }

    @Test
    public void CombinedOperatorCheck() throws LexicalException{
       String input = "START INTEGER A , B , C WRITE 1 + + 4";

       Lexer lexer = new Lexer();

       assertThrows(LexicalException.class, ()->lexer.tokenize(input));
   }

   @Test
   public void LineEndsWithSemiColon() throws LexicalException{
        String input= "START INTEGER A , B V ;\n "+"STOP";

        Lexer lexer= new Lexer();

        assertThrows(LexicalException.class, ()->lexer.tokenize(input));
   }

   @Test
    public void InvalidCharacterCheck() throws LexicalException{
        String input= "START INTEGER A , B , V\n" +
                "ASSIGN A * B & C" +
                "STOP ";

        Lexer lexer =new Lexer();

        assertThrows(LexicalException.class, ()->lexer.tokenize(input));
   }
}
