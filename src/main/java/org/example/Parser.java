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

        if (currentTokenIndex < tokens.size()) {
            while (currentTokenIndex < tokens.size()) {
                System.out.println("Parsing statement...");
                statement();
            }
        } else {
            throw new ParseException("Syntax error: No statements found after START keyword");
        }
    }

    private void parseStop() throws ParseException {
        match(TokenType.KEYWORD_STOP);
        System.out.println("Program parsed successfully.");
    }


    private void statement() throws ParseException {


        if (currentTokenIndex >= tokens.size()) {
            return; // No more tokens to parse
        }

        System.out.println("Identifying statement type...");
        TokenType currentTokenType = tokens.get(currentTokenIndex).getType();
        System.out.println(currentTokenType);
        switch (currentTokenType) {
            case KEYWORD_INTEGER:
                System.out.println("Variable declaration statement found.");
                variableDeclaration();
                break;
            case IDENTIFIER:
                System.out.println("Assignment statement found.");
                assignIdentifier();
                break;
            case KEYWORD_READ:
                System.out.println("Read statement found.");
                readStatement();
                break;
            case KEYWORD_WRITE:
                System.out.println("Write statement found.");
                writeStatement();
                break;
            case KEYWORD_STOP:
                System.out.println("Stop keyword found.");
                parseStop();
                break;
            case KEYWORD_ASSIGN:
                System.out.println("Keyword Assign found");
                assignment();
                break;
            default:
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

        parseIdentifierList();

      statement();

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
        System.out.println("Parsing READ statement...");

        // Check if the current token is READ
        if (getCurrentToken().getType() == TokenType.KEYWORD_READ) {
            System.out.println("READ keyword found.");

            // Consume the READ keyword
            consume(TokenType.KEYWORD_READ);

            // Parse the list of identifiers
            System.out.println("Parsing list of identifiers...");
            parseIdentifierList();

            System.out.println("List of identifiers parsed successfully.");

            System.out.println("READ statement parsed successfully.");
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

        System.out.println("Identifier list parsed successfully.");

        statement();
    }



    private void parseIdentifier() throws ParseException {
        System.out.println("Parsing identifier...");

        // Check if the current token is an identifier
        System.out.println("Current token type: " + getCurrentToken().getType());
        if (getCurrentToken().getType() == TokenType.IDENTIFIER) {
            System.out.println("Let's find identifier");
            // Get the identifier value
            String identifier = getCurrentToken().getValue();

            // Print the identifier
            System.out.println("Found identifier: " + identifier);

            // Consume the identifier token
            consume(TokenType.IDENTIFIER);
        } else {
            // Throw an exception if the current token is not an identifier
            throw new ParseException("Expected identifier but found " + getCurrentToken().getType());
        }
    }


    private void assignIdentifier()throws ParseException{

        consume(TokenType.IDENTIFIER);

        consume(TokenType.SYMBOL_ASSIGNMENT);

        parseExpression();

        statement();

    }




    private void assignment() throws ParseException {
        System.out.println("Parsing assignment statement...");

        // Parse the left-hand side identifier
        consume(TokenType.KEYWORD_ASSIGN);
        parseIdentifier();

        // Consume the assignment symbol
        consume(TokenType.SYMBOL_ASSIGNMENT);
        System.out.println("Consumed = operator");

        // Parse the right-hand side expression
        parseExpression();

       statement();
    }


    private void variableDeclaration() throws ParseException {
        System.out.println("Parsing variable declaration...");

        // Consume the keyword "INTEGER"
        consume(TokenType.KEYWORD_INTEGER);
        System.out.println("Consumed 'INTEGER' keyword...");

        // Parse the list of identifiers
        parseIdentifierList();

        statement();
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
