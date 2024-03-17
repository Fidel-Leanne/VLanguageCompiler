package org.example;


// Class representing a single token in the input code
public class Token {
    // Instance variables
    private TokenType type; // Type of the token (e.g., keyword, identifier, operator)
    private String value; // Value of the token (actual text of the token)

    // Constructor to initialize a Token object with type and value
    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    // Getter method to retrieve the type of the token
    public TokenType getType() {
        return type;
    }

    // Getter method to retrieve the value of the token
    public String getValue() {
        return value;
    }

    // Override toString() method to provide a string representation of the Token
    // object
    @Override
    public String toString() {
        // Return a string containing the token's type and value enclosed in square
        // brackets
        return "[" + type + ", " + value + "]";
    }
}
