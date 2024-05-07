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

    // Initialize keywords, operators, and symbols

    private void initializeKeywords() {
        keywords = new HashMap<>();
        keywords.put("INTEGER", TokenType.KEYWORD_INTEGER);
        keywords.put("READ", TokenType.KEYWORD_READ);
        keywords.put("WRITE", TokenType.KEYWORD_WRITE);
        keywords.put("STOP", TokenType.KEYWORD_STOP);
        keywords.put("START", TokenType.KEYWORD_START);
        keywords.put("ASSIGN", TokenType.KEYWORD_ASSIGN);
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
        symbols.put(";", TokenType.SYMBOL_SEMICOLON);
    }

    // Add method for spell checking misspelled keywords
    private String suggestCorrectKeyword(String misspelledKeyword) {
        // Here, we simply return the closest matching keyword based on Levenshtein distance
        String suggestedKeyword = null;
        int minDistance = Integer.MAX_VALUE;
        for (String keyword : keywords.keySet()) {
            int distance = calculateLevenshteinDistance(misspelledKeyword, keyword);
            if (distance < minDistance) {
                minDistance = distance;
                suggestedKeyword = keyword;
            }
        }
        return suggestedKeyword;
    }

    private int calculateLevenshteinDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        // Initialize first row and column
        for (int i = 0; i <= s1.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= s2.length(); j++) {
            dp[0][j] = j;
        }

        // Fill the DP table
        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(dp[i - 1][j] + 1,             // Deletion
                        Math.min(dp[i][j - 1] + 1,     // Insertion
                                dp[i - 1][j - 1] + cost)); // Substitution
            }
        }

        // Return the Levenshtein distance
        return dp[s1.length()][s2.length()];
    }


    public List<Token> tokenize(String inputCode) throws LexicalException {
        // Tokenize input code into lines
        List<Token> tokens = new ArrayList<>();
        String[] lines = inputCode.split("\\r?\\n");

        // Tokenize each line
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
            // Handle misspelled keywords
            else {
                String suggestedKeyword = suggestCorrectKeyword(word);
                if (suggestedKeyword != null) {
                    throw new LexicalException("Syntax error: Misspelled keyword '" + word + "' at line " + lineNumber
                            + ". Did you mean '" + suggestedKeyword + "'?");
                } else {
                    throw new LexicalException("Syntax error: Unknown keyword '" + word + "' at line " + lineNumber);
                }
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

        // Print out the tokens
        System.out.println("Tokens:");
        for (Token token : tokens) {
            System.out.println(token);
        }

        return tokens;
    }

}