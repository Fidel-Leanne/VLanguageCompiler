package org.example;

import java.util.ArrayList;
import java.util.List;

public class ICRGenerator {

    public static void generateICR(String inputCode) {
        // Tokenize the input code
        String[] lines = inputCode.split("\\n");
        List<String[]> tokensList = new ArrayList<>();
        for (String line : lines) {
            String[] tokens = line.split("\\s*,\\s*|\\s+");
            tokensList.add(tokens);
        }

        // Parse and generate intermediate code for each statement
        for (String[] tokens : tokensList) {
            if (tokens.length == 0) {
                System.err.println("Error: Empty statement");
                continue;
            }
            switch (tokens[0]) {
                case "INTEGER":
                    generateVariableDeclarationICR(tokens);
                    break;
                case "READ":
                    generateReadICR(tokens);
                    break;
                case "WRITE":
                    generateWriteICR(tokens);
                    break;
                case "STOP":
                    generateStopICR(tokens);
                    break;
                default:
                    System.err.println("Warning: Unknown statement type: " + tokens[0]);
            }
        }
    }

    private static void generateVariableDeclarationICR(String[] tokens) {
        for (int i = 1; i < tokens.length; i++) {
            System.out.println("Allocate memory for " + tokens[i]);
        }
    }

    private static void generateReadICR(String[] tokens) {
        for (int i = 1; i < tokens.length; i++) {
            System.out.println("Read " + tokens[i]);
        }
    }

    private static void generateWriteICR(String[] tokens) {
        for (int i = 1; i < tokens.length; i++) {
            System.out.println("Write " + tokens[i]);
        }
    }

    private static void generateStopICR(String[] tokens) {
        System.out.println("Stop program execution");
    }
}
