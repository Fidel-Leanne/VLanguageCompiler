package org.example;

import java.util.List;

public class Parser {
    private List<Token> tokens;
    private int currentTokenIndex;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
    }

    public void parse() throws ParseException {
        System.out.println("Starting parsing...");
        program();
        System.out.println("Parsing complete.");
    }

    private void program() throws ParseException {
        System.out.println("Parsing program...");
        match(TokenType.KEYWORD_START);

        while (currentTokenIndex < tokens.size()) {
            System.out.println("Parsing statement...");
            statement();
        }

        match(TokenType.KEYWORD_STOP);
        System.out.println("Program parsed successfully.");
    }


    private void statement() throws ParseException {
        System.out.println("Identifying statement type...");
        if (tokens.get(currentTokenIndex).getType() == TokenType.KEYWORD_INTEGER) {
            System.out.println("Variable declaration statement found.");
            variableDeclaration();
        } else if (tokens.get(currentTokenIndex).getType() == TokenType.IDENTIFIER) {
            System.out.println("Assignment statement found.");
            assignment();
        } else if (tokens.get(currentTokenIndex).getType() == TokenType.KEYWORD_READ) {
            System.out.println("Read statement found.");
            readStatement();
        } else if (tokens.get(currentTokenIndex).getType() == TokenType.KEYWORD_WRITE) {
            System.out.println("Write statement found.");
            writeStatement();
        } else {
            throw new ParseException("Syntax error: Invalid statement at token " + currentTokenIndex);
        }
    }

    private void writeStatement() throws ParseException {
        System.out.println("Parsing write statement...");

        // Consume the "WRITE" keyword token
        System.out.println("Consuming WRITE keyword token...");
        consume(TokenType.KEYWORD_WRITE);
        System.out.println("WRITE keyword token consumed successfully.");

        // Parse the expression to be written
        System.out.println("Parsing expression...");
        parseExpression();
        System.out.println("Expression parsed successfully.");

        // Note: No need to consume semicolon token since it's not expected
    }


    private void parseExpression() throws ParseException {
        System.out.println("Parsing expression...");

        // Parse the first term
        parseTerm();

        // Loop to handle additional terms and operators
        while (isAdditionOperator() || isSubtractionOperator()) {
            // Consume the operator
            TokenType operator = getCurrentToken().getType();
            System.out.println("Encountered operator: " + operator);
            consume(operator);

            // Parse the next term
            System.out.println("Parsing next term...");
            parseTerm();
            System.out.println("Term parsed successfully.");
        }

        System.out.println("Expression parsed successfully.");
    }

    private void parseTerm() throws ParseException {
        System.out.println("Parsing term...");

        // Parse the first factor
        parseFactor();

        // Loop to handle additional factors and operators
        while (isMultiplicationOperator() || isDivisionOperator()) {
            // Consume the operator
            TokenType operator = getCurrentToken().getType();
            System.out.println("Encountered operator: " + operator);
            consume(operator);

            // Parse the next factor
            System.out.println("Parsing next factor...");
            parseFactor();
            System.out.println("Factor parsed successfully.");
        }

        System.out.println("Term parsed successfully.");
    }


    private void parseFactor() throws ParseException {
        System.out.println("Parsing factor...");

        // Check if the current token is an identifier
        if (getCurrentToken().getType() == TokenType.IDENTIFIER) {
            // Consume the identifier
            Token identifierToken = getCurrentToken();
            System.out.println("Consumed identifier: " + identifierToken.getValue());
            consume(TokenType.IDENTIFIER);
        } else {
            // If it's not an identifier, it's a syntax error
            throw new ParseException("Expected identifier");
        }

        System.out.println("Factor parsed successfully.");
    }



    private boolean isAdditionOperator() throws ParseException {
        return getCurrentToken().getType() == TokenType.OPERATOR_ADD;
    }

    private boolean isSubtractionOperator() throws ParseException {
        return getCurrentToken().getType() == TokenType.OPERATOR_SUBTRACT;
    }

    private boolean isMultiplicationOperator() throws ParseException {
        return getCurrentToken().getType() == TokenType.OPERATOR_MULTIPLY;
    }

    private boolean isDivisionOperator() throws ParseException {
        return getCurrentToken().getType() == TokenType.OPERATOR_DIVIDE;
    }

    private Token getCurrentToken() throws ParseException {
        if (currentTokenIndex < tokens.size()) {
            return tokens.get(currentTokenIndex);
        } else {
            throw new ParseException("Unexpected end of input");
        }
    }



    private void consume(TokenType tokenType) throws ParseException {
        // Check if there are any tokens left
        if (currentTokenIndex >= tokens.size()) {
            throw new ParseException("Unexpected end of input");
        }

        // Get the next token
        Token nextToken = tokens.get(currentTokenIndex);

        // Check if the token type matches the expected type
        if (nextToken.getType() != tokenType) {
            throw new ParseException("Unexpected token '" + nextToken.getValue() + "' of type " +
                    nextToken.getType() + ", expected " + tokenType);
        }

        // If the token type matches, consume the token by moving to the next one
        currentTokenIndex++;
    }



    private void readStatement() throws ParseException {
        // Check if the current token is READ
        if (getCurrentToken().getType() == TokenType.KEYWORD_READ) {
            // Consume the READ keyword
            consume(TokenType.KEYWORD_READ);

            // Parse the list of identifiers
            parseIdentifierList();
        } else {
            // If it's not READ, it's a syntax error
            throw new ParseException("Expected READ keyword");
        }
    }


    private void parseIdentifierList() throws ParseException {
        System.out.println("Parsing identifier list...");
        // Parse the first identifier
        parseIdentifier();

        // Parse additional identifiers if there are any
        while (getCurrentToken().getType() == TokenType.IDENTIFIER) {
            System.out.println("Parsing next identifier...");
            // Parse the next identifier
            parseIdentifier();
        }
    }


    private void parseIdentifier() throws ParseException {
        System.out.println("Parsing identifier...");

        // Check if the current token is an identifier
        if (getCurrentToken().getType() == TokenType.IDENTIFIER) {
            // Consume the identifier token
            consume(TokenType.IDENTIFIER);
        } else {
            // Throw an exception if the current token is not an identifier
            throw new ParseException("Expected identifier but found " + getCurrentToken().getType());
        }
    }




    private void assignment() throws ParseException {
        System.out.println("Parsing assignment...");

        // Parse the left-hand side identifier
        System.out.println("Parsing left-hand side identifier...");
        parseIdentifier();

        // Consume the assignment symbol
        System.out.println("Consuming assignment symbol...");
        consume(TokenType.SYMBOL_ASSIGNMENT);

        // Parse the right-hand side expression
        System.out.println("Parsing right-hand side expression...");
        parseExpression();
    }



    private void variableDeclaration() throws ParseException {
        System.out.println("Parsing variable declaration...");

        // Consume the keyword "INTEGER"
        consume(TokenType.KEYWORD_INTEGER);
        System.out.println("Consumed 'INTEGER' keyword...");

        // Parse the list of identifiers
        parseIdentifierList();
    }



    private void match(TokenType expectedTokenType) throws ParseException {
        System.out.println("Matching tokens: Expected " + expectedTokenType);

        // Check if there are tokens left to match
        if (currentTokenIndex < tokens.size()) {
            Token currentToken = tokens.get(currentTokenIndex);
            TokenType currentTokenType = currentToken.getType();

            // Compare the current token type with the expected type
            if (currentTokenType == expectedTokenType) {
                System.out.println("Matched: " + currentTokenType);
                // Move to the next token
                currentTokenIndex++;
            } else {
                // Throw an exception if the token types don't match
                throw new ParseException("Syntax error: Expected " + expectedTokenType + " but found " + currentTokenType);
            }
        } else {
            // Throw an exception if there are no more tokens
            throw new ParseException("Unexpected end of input");
        }
    }

}
