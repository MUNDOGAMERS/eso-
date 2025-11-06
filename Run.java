import service.*;
import model.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Run {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        
        System.out.println("========================================================");
        System.out.println("  COMPILADOR DE UN LENGUAJE ARITMETICO SIMPLE          ");
        System.out.println("                5 FASES COMPLETAS                       ");
        System.out.println("========================================================\n");
        
        while (continuar) {
            System.out.println("\n+--------------------------------------------------+");
            System.out.println("|                   MENU PRINCIPAL                 |");
            System.out.println("+--------------------------------------------------+");
            System.out.println("| 1. Ingresar expresion manualmente                |");
            System.out.println("| 2. Ejecutar tests predefinidos                   |");
            System.out.println("| 3. Salir                                         |");
            System.out.println("+--------------------------------------------------+");
            System.out.print("Seleccione una opcion: ");
            
            String opcion = scanner.nextLine().trim();
            
            switch (opcion) {
                case "1":
                    ejecutarManual(scanner);
                    break;
                case "2":
                    ejecutarTests();
                    break;
                case "3":
                    continuar = false;
                    System.out.println("\n*** Saliendo del compilador. Hasta luego! ***\n");
                    break;
                default:
                    System.out.println("\n*** Opcion invalida. Intente de nuevo. ***");
            }
        }
        
        scanner.close();
    }
    
    private static void ejecutarManual(Scanner scanner) {
        boolean continuarIngreso = true;
        
        while (continuarIngreso) {
            System.out.println("\n========================================================");
            System.out.println("           MODO DE ENTRADA MANUAL");
            System.out.println("========================================================");
            System.out.println("Ejemplos validos:");
            System.out.println("  - x = 5 + 3;");
            System.out.println("  - result = 10 * 2 + 5;");
            System.out.println("  - z = a + b * 2;");
            System.out.println("\nNOTA: Use solo numeros en las expresiones para evitar");
            System.out.println("      errores de variables no inicializadas.");
            System.out.println("========================================================\n");
            
            System.out.print("Ingrese su expresion (o 'salir' para volver): ");
            String codigoFuente = scanner.nextLine().trim();
            
            if (codigoFuente.equalsIgnoreCase("salir") || codigoFuente.equalsIgnoreCase("exit")) {
                continuarIngreso = false;
                continue;
            }
            
            if (codigoFuente.isEmpty()) {
                System.out.println("\n*** Error: La expresion no puede estar vacia ***");
                continue;
            }
            
            // Agregar punto y coma si no lo tiene
            if (!codigoFuente.endsWith(";")) {
                codigoFuente += ";";
            }
            
            System.out.println("\nCodigo ingresado: " + codigoFuente);
            System.out.println("========================================================\n");
            
            compilarExpresion(codigoFuente);
            
            System.out.print("\n¿Desea ingresar otra expresion? (s/n): ");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            if (!respuesta.equals("s") && !respuesta.equals("si") && !respuesta.equals("yes")) {
                continuarIngreso = false;
            }
        }
    }
    
    private static void ejecutarTests() {
        System.out.println("\n========================================================");
        System.out.println("           EJECUTANDO TESTS PREDEFINIDOS");
        System.out.println("========================================================\n");
        
        String[] tests = {
            "z = 5 + 3;",
            "result = 10 * 2;",
            "x = 1 + 2 * 3;",
            "y = 15 - 5 + 2;",
            "w = 100 / 4 + 10;"
        };
        
        for (String test : tests) {
            testCompiler(test);
        }
        
        System.out.println("\n*** Todos los tests completados ***");
    }
    
    private static void compilarExpresion(String codigoFuente) {
        try {
            // FASE 1: ANALISIS LEXICO
            System.out.println("+--------------------------------------------------+");
            System.out.println("|  FASE 1: ANALISIS LEXICO (Lexer)                |");
            System.out.println("+--------------------------------------------------+");
            
            Lexer lexer = new Lexer();
            ArrayList<Token> tokens = lexer.lexicalAnalysis(codigoFuente);
            
            System.out.print("Tokens: ");
            lexer.printTokens(tokens);
            System.out.println();
            
            // FASE 2: ANALISIS SINTACTICO
            System.out.println("+--------------------------------------------------+");
            System.out.println("|  FASE 2: ANALISIS SINTACTICO (Parser)           |");
            System.out.println("+--------------------------------------------------+");
            
            Parser parser = new Parser(tokens);
            ASTNode ast = parser.parse();
            
            System.out.println("AST:");
            ast.printTree("", true);
            System.out.println();
            
            // FASE 3: ANALISIS SEMANTICO
            System.out.println("+--------------------------------------------------+");
            System.out.println("|  FASE 3: ANALISIS SEMANTICO                     |");
            System.out.println("+--------------------------------------------------+");
            
            Semantic semantic = new Semantic(ast);
            semantic.analyze();
            System.out.println();
            
            // FASE 4: GENERACION DE CODIGO INTERMEDIO
            System.out.println("+--------------------------------------------------+");
            System.out.println("|  FASE 4: GENERACION DE CODIGO INTERMEDIO (TAC)  |");
            System.out.println("+--------------------------------------------------+");
            
            Tac tac = new Tac();
            tac.generate(ast);
            System.out.println();
            
            // FASE 5: SIMULACION DE EJECUCION
            System.out.println("+--------------------------------------------------+");
            System.out.println("|  FASE 5: SIMULACION DE EJECUCION                |");
            System.out.println("+--------------------------------------------------+");
            
            Simulate simulator = new Simulate(tac.getInstructions());
            simulator.execute();
            
            System.out.println("\n========================================================");
            System.out.println("         *** COMPILACION EXITOSA ***                   ");
            System.out.println("========================================================");
            
        } catch (Exception e) {
            System.err.println("\n========================================================");
            System.err.println("                    *** ERROR ***                       ");
            System.err.println("========================================================");
            System.err.println("Detalle: " + e.getMessage());
            System.err.println("========================================================");
        }
    }
    
    private static void testCompiler(String codigo) {
        System.out.println("\n--------------------------------------------------");
        System.out.println("  TEST: " + codigo);
        System.out.println("--------------------------------------------------");
        
        try {
            Lexer lexer = new Lexer();
            ArrayList<Token> tokens = lexer.lexicalAnalysis(codigo);
            System.out.print("\nTokens: ");
            lexer.printTokens(tokens);
            
            Parser parser = new Parser(tokens);
            ASTNode ast = parser.parse();
            System.out.println("\nAST:");
            ast.printTree("", true);
            
            Semantic semantic = new Semantic(ast);
            semantic.analyze();
            
            Tac tac = new Tac();
            tac.generate(ast);
            
            Simulate simulator = new Simulate(tac.getInstructions());
            simulator.execute();
            
            System.out.println("\n*** Test completado exitosamente ***");
            System.out.println("--------------------------------------------------");
            
        } catch (Exception e) {
            System.err.println("\n*** Error: " + e.getMessage() + " ***");
            System.out.println("--------------------------------------------------");
        }
    }
}
