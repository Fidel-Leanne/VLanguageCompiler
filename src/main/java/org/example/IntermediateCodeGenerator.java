package org.example;
import java.util.ArrayList;
import java.util.List;

public class IntermediateCodeGenerator {
    private List<String> intermediateCode;

    public IntermediateCodeGenerator() {
        intermediateCode = new ArrayList<>();
    }

    public List<String> generateIntermediateCode(String input) {
        String[] tokens = input.split("\\s+");
        List<String> intermediateCode = new ArrayList<>();
        String previousResult = null;
        char previousOperator = ' ';

        for (String token : tokens) {
            if (isKeyword(token)) {
                if (previousResult != null) {
                    String operation = "T" + (intermediateCode.size() + 1) + " = " + previousResult + " " + token;
                    intermediateCode.add(operation);
                    previousResult = "T" + (intermediateCode.size());
                } else {
                    previousResult = token;
                }
            } else if (isIdentifier(token)) {
                if (previousResult != null) {
                    String operation;
                    if (previousOperator == '*' || previousOperator == '/') {
                        operation = "T" + (intermediateCode.size() + 1) + " = " + previousResult + " " + previousOperator + " " + token;
                    } else {
                        operation = "T" + (intermediateCode.size() + 1) + " = " + previousResult + " + " + token;
                    }
                    intermediateCode.add(operation);
                    previousResult = "T" + (intermediateCode.size());
                } else {
                    previousResult = token;
                }
            } else if (isOperator(token)) {
                previousOperator = token.charAt(0);
            }
        }

        return intermediateCode;
    }

    private boolean isKeyword(String token) {
        return token.equals("START") || token.equals("STOP") || token.equals("READ") ||
                token.equals("WRITE") || token.equals("ASSIGN");
    }


    private boolean isIdentifier(String token) {
        return token.matches("[A-Za-z]+");
    }

    private boolean isOperator(String token) {
        return token.matches("[+\\-*/]");
    }


}
