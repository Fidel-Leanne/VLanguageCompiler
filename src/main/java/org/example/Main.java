package org.example;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("The Compiler below is written in V language to check for errors");
        System.out.println("Grammar and Production Rules:");
        System.out.println("EE Rule1 (R1)");
        System.out.println("E> |E+E|E/E|E*E|E+E| R2 E> |E1]E2/E3]...|E26| R3 [E1|E2|E3|...|E26]J>{JA|B|C|...]Z|alb]c]...|z]} R4");
        System.out.println("Note the following additional conditions in the above program:");
        System.out.println("Words in capital letters are Keywords");
        System.out.println("Words in small letters are Identifiers. Words in single letters from A — Z and a to z are also Identifiers");
        System.out.println("Operators: +, -, /, *");
        System.out.println("Symbols: =, ;");
        System.out.println("Any string/line must contain: Keywords, Identifiers, Operators, or Symbols");
        System.out.println("Symbols such as: %,$, &, <, >,; not allowed and would give Semantic error");
        System.out.println("Two operators must not be combined such as: +* not allowed and would give Syntax error");
        System.out.println("Semi colon ; at the end of a line not allowed and would give Syntax error");
        System.out.println("Numbers 0,1 to 9 are not allowed and would give Syntax error");
        System.out.println("Acceptable keywords: INTEGER, ASSIGN, READ, WRITE, START, STOP.");
        System.out.println("Misspelling in the keywords such as: WRITEE not allowed and would give Lexical error");
        System.out.println("Any other character on the keyboard different from all the above will give Syntax error.\n");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Input Program code:");
            String inputCode = scanner.nextLine();

            if (inputCode.equals("quit")) {
                break;
            }

            System.out.println("Input expression for evaluation:");
            String input = scanner.nextLine();

            try {

                System.out.println("======STAGE1: COMPILER TECHNIQUES--> LEXICAL ANALYSER\n");

                Lexer lexer = new Lexer();
                List<Token> tokens = lexer.tokenize(inputCode);

                System.out.println("======STAGE1: SUCCESSFUL");
                System.out.println();

                System.out.println("======STAGE2: COMPILER TECHNIQUES--> PARSER\n");

                Parser parser = new Parser(tokens);
                parser.parse();

                System.out.println("Parsing successful. Input code is valid.");

                // Stage 3 of compiler
                System.out.println("\n" + "======STAGE3: COMPILER TECHNIQUES--> SEMANTIC ANALYSIS");
                System.out.println(
                        "CONCLUSION-->This expression: " + inputCode + " is Syntactically and Semantically correct" + "\n");
                // END of stage3

                // Stage 4 of compiler
                System.out.println("======STAGE4: COMPILER TECHNIQUES--> INTERMEDIATE CODE REPRESENTATION (ICR)");
                System.out.println("THE STRING ENTERED IS : " + input);
                System.out.println("The ICR is as follows: ");

                IntermediateCodeGenerator generator = new IntermediateCodeGenerator();
                List<String> intermediateCode = generator.generateIntermediateCode(input);
                System.out.println("Intermediate Code:");
                for (String code : intermediateCode) {
                    System.out.println(code);
                }

                // Stage 5 of compiler
                System.out.println("\n" + "======STAGE5: COMPILER TECHNIQUES--> CODE GENERATION");
                System.out.println("The generated assembly code is as follows:");

                CodeGenerator codeGenerator = new CodeGenerator();
                List<String> assemblyCode = codeGenerator.generateCode(intermediateCode);
                for (String assemblyInstruction : assemblyCode) {
                    System.out.println(assemblyInstruction);
                }

                // Stage 6: Code Optimization
                System.out.println("\n" + "======STAGE6: COMPILER TECHNIQUES--> CODE OPTIMIZATION");
                System.out.println("The optimized assembly code is as follows:");

                CodeOptimizer codeOptimizer = new CodeOptimizer();
                List<String> optimizedCode = codeOptimizer.optimizeCode(assemblyCode);
                for (String optimizedInstruction : optimizedCode) {
                    System.out.println(optimizedInstruction);
                }

                // Stage 7: Target Machine Code Generation
                System.out.println("\n" + "======STAGE7: COMPILER TECHNIQUES--> TARGET MACHINE CODE");
                System.out.println("The target machine code is as follows:");

                TargetMachineCodeGenerator machineCodeGenerator = new TargetMachineCodeGenerator();
                for (String assemblyInstruction : assemblyCode) {
                    machineCodeGenerator.generateMachineCode(assemblyInstruction);
                }

            } catch (LexicalException | ParseException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}
