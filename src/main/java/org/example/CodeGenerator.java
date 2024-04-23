package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeGenerator {
    private static final Map<String, String> operationToInstruction = new HashMap<>();

    static {
        // Define the mapping of arithmetic operations to assembly instructions
        operationToInstruction.put("+", "ADD");
        operationToInstruction.put("-", "SUB");
        operationToInstruction.put("*", "MUL");
        operationToInstruction.put("/", "DIV");
    }

    public List<String> generateCode(List<String> intermediateCode) {
        List<String> assemblyCode = new ArrayList<>();

        for (String code : intermediateCode) {
            String[] tokens = code.split("\\s+");
            if (tokens.length == 5) {
                String operation = tokens[3];
                if (operationToInstruction.containsKey(operation)) {
                    String l1 = "LDA " + tokens[2];
                    String l2 = operationToInstruction.get(operation) + " " + tokens[4];
                    String l3 = "STR T" + (assemblyCode.size() + 1);
                    assemblyCode.add(l1);
                    assemblyCode.add(l2);
                    assemblyCode.add(l3);
                }
            }
        }

        return assemblyCode;
    }
}

