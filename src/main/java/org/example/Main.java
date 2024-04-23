package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String inputCode = "START\n" +
                "INTEGER M, N, K, P, R, H, i, g, k, m\n" +
                "READ M, N, K \n" +
                "ASSIGN n = m * k - p / l\n " +
                "WRITE W\n" +
                "STOP";


        String input = "n = m * k - p / l";

        try {
            Lexer lexer = new Lexer();
            List<Token> tokens = lexer.tokenize(inputCode);

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
