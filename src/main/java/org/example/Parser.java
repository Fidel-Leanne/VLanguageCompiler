package org.example;

import java.util.List;

public class Parser {
    private List<Token> tokens;
    private int currentTokenIndex;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
    }

    public void parse() {
        // Start parsing the program
        parseProgram();

        // Check if there are remaining tokens
        if (currentTokenIndex < tokens.size()) {
            // Report syntax error: unexpected tokens after the end of the program
            System.out.println("Syntax error: Unexpected tokens after the end of the program");
        } else {
            // Parsing successful
            System.out.println("Parsing successful");
        }
    }

    private void parseProgram() {
        // Parse the program: statementList
        parseStatementList();
    }

    private void parseStatementList() {
        // Parse the statement list: statement statementList | ε
        while (currentTokenIndex < tokens.size()) {
            // Parse a single statement
            parseStatement();
        }
    }

    private void parseStatement() {
        // Check the current token to determine the type of statement
        if (currentTokenIndex < tokens.size()) {
            Token currentToken = tokens.get(currentTokenIndex);

            // Check if the token corresponds to a specific statement type
            switch (currentToken.getType()) {
                case KEYWORD_BEGIN:
                    // Parse block statement
                    parseBlockStatement();
                    break;
                case KEYWORD_LET:
                    // Parse variable declaration statement
                    parseVariableDeclaration();
                    break;
                case KEYWORD_INPUT:
                    // Parse input statement
                    parseInputStatement();
                    break;
                case KEYWORD_OUTPUT:
                    // Parse output statement
                    parseOutputStatement();
                    break;
                case IDENTIFIER:
                    // Parse assignment statement or function call
                    parseAssignmentOrFunctionCall();
                    break;
                // Add cases for other statement types as needed
                default:
                    // Report syntax error: unexpected token for statement
                    System.out.println("Syntax error: Unexpected token for statement " + currentToken);
                    break;
            }
        } else {
            // Report syntax error: unexpected end of input
            System.out.println("Syntax error: Unexpected end of input");
        }
    }

    private void parseBlockStatement() {
        // Match the BEGIN keyword
        match(TokenType.KEYWORD_BEGIN, "");

        // Parse statements within the block
        while (currentTokenIndex < tokens.size()) {
            // Check if the current token is the END keyword
            if (tokens.get(currentTokenIndex).getType() == TokenType.KEYWORD_END) {
                // Move to the next token to exit the loop
                currentTokenIndex++;
                return;
            }

            // Parse individual statements within the block
            parseStatement();
        }

        // If the loop exits without encountering the END keyword, report a syntax error
        System.out.println("Syntax error: Expected END keyword");
    }



    private void parseVariableDeclaration() {
        // Parse variable declaration: INTEG/REAL identifier [, identifier] ;

        // Match the INTEG or REAL keyword
        if (tokens.get(currentTokenIndex).getType() == TokenType.KEYWORD_INTEG ||
                tokens.get(currentTokenIndex).getType() == TokenType.KEYWORD_REAL) {
            // Move to the next token
            currentTokenIndex++;
        } else {
            // Report syntax error: Expected INTEG or REAL keyword
            System.out.println("Syntax error: Expected INTEG  keyword");
            return;
        }

        // Parse one or more identifiers separated by commas
        while (true) {
            // Parse an identifier
            parseIdentifier();

            // Check if there's a comma to parse more identifiers
            if (currentTokenIndex < tokens.size()) {
                Token currentToken = tokens.get(currentTokenIndex);
                if (currentToken.getType() == TokenType.SYMBOL && currentToken.getValue().equals(",")) {
                    // Move to the next token
                    currentTokenIndex++;
                } else {
                    // No more identifiers to parse
                    break;
                }
            } else {
                // Syntax error: Unexpected end of input
                System.out.println("Syntax error: Unexpected end of input");
                return;
            }
        }

        // Expect a semicolon at the end of the variable declaration
        match(TokenType.SYMBOL, ";");
    }


    private void parseIdentifier() {
        if (currentTokenIndex < tokens.size()) {
            Token currentToken = tokens.get(currentTokenIndex);
            if (currentToken.getType() == TokenType.IDENTIFIER) {
                // Process the identifier token (e.g., store it, perform further validation)
                // For now, you can simply move to the next token
                currentTokenIndex++;
            } else {
                // Report syntax error: Expected an identifier token
                System.out.println("Syntax error: Expected an identifier but found " + currentToken);
            }
        } else {
            // Report syntax error: Unexpected end of input
            System.out.println("Syntax error: Unexpected end of input");
        }
    }



    private void parseInputStatement() {
        // Parse input statement: INPUT identifier [, identifier] ;

        // Match the INPUT keyword
        match(TokenType.KEYWORD_INPUT, "");

        // Parse one or more identifiers separated by commas
        while (true) {
            // Parse an identifier
            parseIdentifier();

            // Check if there's a comma to parse more identifiers
            if (currentTokenIndex < tokens.size()) {
                Token currentToken = tokens.get(currentTokenIndex);
                if (currentToken.getType() == TokenType.SYMBOL && currentToken.getValue().equals(",")) {
                    // Move to the next token
                    currentTokenIndex++;
                } else {
                    // No more identifiers to parse
                    break;
                }
            } else {
                // End of input statement
                return;
            }
        }
    }


    private void parseOutputStatement() {
        // Parse output statement: WRITE expression [, expression] ;

        // Match the WRITE keyword
        match(TokenType.KEYWORD_WRITE, "");

        // Parse one or more expressions or identifiers separated by commas
        while (true) {
            // Parse an expression or identifier
            parseExpression();

            // Check if there's a comma to parse more expressions or identifiers
            if (currentTokenIndex < tokens.size()) {
                Token currentToken = tokens.get(currentTokenIndex);
                if (currentToken.getType() == TokenType.SYMBOL && currentToken.getValue().equals(",")) {
                    // Move to the next token
                    currentTokenIndex++;
                } else {
                    // No more expressions or identifiers to parse
                    break;
                }
            } else {
                // End of output statement
                return;
            }
        }
    }

    private void parseExpression() {
        // Implement parsing of expressions

        // Check the current token to determine the type of expression
        if (currentTokenIndex < tokens.size()) {
            Token currentToken = tokens.get(currentTokenIndex);

            // Check if the token corresponds to a specific type of expression
            switch (currentToken.getType()) {
                case IDENTIFIER:
                    // Parse an identifier
                    parseIdentifier();
                    break;
                case SYMBOL:
                    // Check for parentheses to parse a parenthesized expression
                    if (currentToken.getValue().equals("(")) {
                        parseParenthesizedExpression();
                    } else {
                        // Check if the symbol is an allowed arithmetic operator
                        if (isArithmeticOperator(currentToken.getValue())) {
                            // Move to the next token
                            currentTokenIndex++;
                        } else {
                            // Syntax error: Unexpected symbol in expression
                            System.out.println("Syntax error: Unexpected symbol in expression " + currentToken);
                        }
                    }
                    break;
                // Add cases for other types of expressions as needed
                default:
                    // Syntax error: Unexpected token for expression
                    System.out.println("Syntax error: Unexpected token for expression " + currentToken);
                    break;
            }
        } else {
            // Syntax error: Unexpected end of input
            System.out.println("Syntax error: Unexpected end of input");
        }
    }

    private void parseParenthesizedExpression() {
        // Match the opening parenthesis "("
        match(TokenType.SYMBOL, "(");

        // Parse the expression inside the parentheses
        parseExpression();

        // Match the closing parenthesis ")"
        match(TokenType.SYMBOL, ")");
    }


    // Helper method to check if a symbol is an allowed arithmetic operator
    private boolean isArithmeticOperator(String symbol) {
        return symbol.equals("+") || symbol.equals("-") || symbol.equals("*") || symbol.equals("/");
    }



    private void parseAssignmentOrFunctionCall() {
        // Parse assignment statement or function call

        // Parse the identifier
        parseIdentifier();

        // Check the next token
        if (currentTokenIndex < tokens.size()) {
            Token currentToken = tokens.get(currentTokenIndex);

            // If the next token is an assignment operator "=", parse an assignment statement
            if (currentToken.getType() == TokenType.SYMBOL && currentToken.getValue().equals("=")) {
                // Move to the next token
                currentTokenIndex++;

                // Parse the expression on the right-hand side of the assignment
                parseExpression();

                // No more tokens expected after the expression in an assignment statement
            }
            // If the next token is an opening parenthesis "(", parse a function call
            else if (currentToken.getType() == TokenType.SYMBOL && currentToken.getValue().equals("(")) {
                // Move to the next token
                currentTokenIndex++;

                // Parse the list of arguments (if any) enclosed in parentheses
                parseArgumentList();

                // Check for closing parenthesis
                match(TokenType.SYMBOL, ")");
            } else {
                // Syntax error: Unexpected token after identifier
                System.out.println("Syntax error: Unexpected token after identifier " + currentToken);
            }
        } else {
            // Syntax error: Unexpected end of input
            System.out.println("Syntax error: Unexpected end of input");
        }
    }

    // Helper method to parse a list of function call arguments
    private void parseArgumentList() {
        // Parse a list of arguments: expression [, expression]*
        parseExpression();

        // Parse additional expressions if there are commas
        while (currentTokenIndex < tokens.size()) {
            Token currentToken = tokens.get(currentTokenIndex);
            if (currentToken.getType() == TokenType.SYMBOL && currentToken.getValue().equals(",")) {
                // Move to the next token
                currentTokenIndex++;

                // Parse the next expression
                parseExpression();
            } else {
                // No more arguments to parse
                break;
            }
        }
    }



    // Implement other parsing methods for specific types of statements, expressions, etc.

    private void match(TokenType expectedType, String expectedValue) {
        // Utility method to match the current token with an expected type and value
        if (currentTokenIndex < tokens.size()) {
            Token currentToken = tokens.get(currentTokenIndex);
            if (currentToken.getType() == expectedType &&
                    (expectedValue.isEmpty() || currentToken.getValue().equals(expectedValue))) {
                // Move to the next token
                currentTokenIndex++;
            } else {
                // Report syntax error: unexpected token
                System.out.println("Syntax error: Unexpected token " + currentToken);
            }
        } else {
            // Report syntax error: unexpected end of input
            System.out.println("Syntax error: Unexpected end of input");
        }
    }
}
