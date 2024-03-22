package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.StringTokenizer;

public class Lexer {

    private Map<String, TokenType> keywords;
    private Map<String, TokenType> operators;
    private Map<String, TokenType> symbols;

    public Lexer() {
        initializeKeywords();
        initializeOperators();
        initializeSymbols();
    }

    private void initializeKeywords() {
        keywords = new HashMap<>();
        // Populate the hashtable with keywords and their corresponding token types
        keywords.put("BEGIN", TokenType.KEYWORD_BEGIN);
        keywords.put("LET", TokenType.KEYWORD_LET);
        keywords.put("INTEG", TokenType.KEYWORD_INTEG);
        keywords.put("REAL", TokenType.KEYWORD_REAL);
        keywords.put("INPUT", TokenType.KEYWORD_INPUT);
        keywords.put("WRITE", TokenType.KEYWORD_WRITE);
        keywords.put("END", TokenType.KEYWORD_END);
    }

    private void initializeOperators() {
        operators = new HashMap<>();
        // Populate the hashtable with operators and their corresponding token types
        operators.put("+", TokenType.OPERATOR_ADD);
        operators.put("/", TokenType.OPERATOR_DIVIDE);
        operators.put("*", TokenType.OPERATOR_MULTIPLY);
        operators.put("-", TokenType.OPERATOR_SUBTRACT);
    }

    private void initializeSymbols() {
        symbols = new HashMap<>();
        // Populate the hashtable with symbols and their corresponding token types
        symbols.put("=", TokenType.SYMBOL_ASSIGNMENT);
        symbols.put(",", TokenType.SYMBOL_COMMA);
    }

    public List<Token> tokenize(String inputCode) throws LexicalException {
        List<Token> tokens = new ArrayList<>();
        String[] lines = inputCode.split("\\r?\\n");

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            List<Token> lineTokens = tokenizeLine(line, i + 1); // Pass line number for error reporting
            tokens.addAll(lineTokens);
        }

        return tokens;
    }

    private List<Token> tokenizeLine(String line, int lineNumber) throws LexicalException {
        List<Token> tokens = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(line, " \t\n\r\f,;");

        boolean containsKeyword = false;
        boolean containsIdentifier = false;
        boolean containsOperator = false;
        boolean containsSymbol = false;
        boolean containsCombinedOperator = false;

        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken();

            // Check for numbers
            if (word.matches("[0-9]")) {
                throw new LexicalException("Syntax error: Number '" + word + "' not allowed at line " + lineNumber);
            }

            // Check for valid characters
            if (!word.matches("[A-Za-z+=,/*;-]+")) {
                throw new LexicalException("Syntax error: Invalid character '" + word + "' at line " + lineNumber);
            }

            // Check for keywords
            if (keywords.containsKey(word)) {
                tokens.add(new Token(keywords.get(word), word));
                containsKeyword = true;
            }
            // Check for identifiers
            else if (word.matches("[a-zA-Z]")) {
                tokens.add(new Token(TokenType.IDENTIFIER, word));
                containsIdentifier = true;
            }
            // Check for operators
            else if (operators.containsKey(word)) {
                tokens.add(new Token(operators.get(word), word));
                containsOperator = true;
            }
            // Check for symbols
            else if (symbols.containsKey(word)) {
                tokens.add(new Token(symbols.get(word), word));
                containsSymbol = true;
            }
        }

        // Check if line contains required components
        if (!containsKeyword && !containsIdentifier && !containsOperator && !containsSymbol) {
            throw new LexicalException("Syntax error: Line does not contain any keywords, identifiers, operators, or symbols at line " + lineNumber);
        }

        // Check for combined operators
        tokenizer = new StringTokenizer(line);
        String prevToken = null;
        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken();
            if (prevToken != null && operators.containsKey(prevToken) && operators.containsKey(word)) {
                containsCombinedOperator = true;
                break;
            }
            prevToken = word;
        }

        if (containsCombinedOperator) {
            throw new LexicalException("Syntax error: Combined operators not allowed at line " + lineNumber);
        }

        // Check for semicolon at the end of the line
        if (line.endsWith(";")) {
            throw new LexicalException("Syntax error: Semicolon at the end of the line not allowed at line " + lineNumber);
        }

        return tokens;
    }
}