package org.example;

public class TargetMachineCodeGenerator {

    public void generateMachineCode(String instruction) {
        for (int i = 0; i < instruction.length(); i++) {
            char c = instruction.charAt(i);
            if (Character.isLetter(c)) {
                // Convert letter to ASCII value and then to binary
                int asciiValue = (int) c;
                String binary = Integer.toBinaryString(asciiValue);
                System.out.print( binary);

                // Print machine code based on the instruction type
                if (instruction.contains("DIV")) {
                    System.out.print("01000100");
                } else if (instruction.contains("MUL")) {
                    System.out.print("01001101");
                } else if (instruction.contains("ADD")) {
                    System.out.print("01000001");
                } else if (instruction.contains("SUB")) {
                    System.out.print("01010011");
                }
            }

        }

        System.out.println();
    }
}
