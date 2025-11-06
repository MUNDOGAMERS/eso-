package service;

import model.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Simulate {
    
    private Map<String, Double> variables;
    private ArrayList<TACInstruction> instructions;
    
    public Simulate(ArrayList<TACInstruction> instructions) {
        this.instructions = instructions;
        this.variables = new HashMap<>();
    }
    
    public void execute() {
        System.out.println("Interpretando codigo de tres direcciones...\n");
        
        try {
            for (int i = 0; i < instructions.size(); i++) {
                TACInstruction instruction = instructions.get(i);
                executeInstruction(instruction, i + 1);
            }
            
            System.out.println("\n*** Simulacion completada exitosamente ***");
            printVariableState();
            
        } catch (Exception e) {
            System.err.println("*** Error durante simulacion: " + e.getMessage() + " ***");
        }
    }
    
    private void executeInstruction(TACInstruction instruction, int lineNumber) throws Exception {
        String result = instruction.getResult();
        String operator = instruction.getOperator();
        String operand1 = instruction.getOperand1();
        String operand2 = instruction.getOperand2();
        
        System.out.println("  Ejecutando (" + lineNumber + "): " + instruction);
        
        if (operand2 == null) {
            double value = getValue(operand1);
            variables.put(result, value);
            System.out.println("    -> " + result + " = " + value);
        } else {
            double leftValue = getValue(operand1);
            double rightValue = getValue(operand2);
            double resultValue = performOperation(leftValue, operator, rightValue);
            
            variables.put(result, resultValue);
            System.out.println("    -> " + result + " = " + leftValue + " " + operator + " " + 
                             rightValue + " = " + resultValue);
        }
    }
    
    private double getValue(String operand) throws Exception {
        try {
            return Double.parseDouble(operand);
        } catch (NumberFormatException e) {
            if (variables.containsKey(operand)) {
                return variables.get(operand);
            } else {
                throw new Exception("Variable '" + operand + "' no inicializada");
            }
        }
    }
    
    private double performOperation(double left, String operator, double right) throws Exception {
        switch (operator) {
            case "+": return left + right;
            case "-": return left - right;
            case "*": return left * right;
            case "/":
                if (right == 0) throw new Exception("Division por cero");
                return left / right;
            default:
                throw new Exception("Operador desconocido: " + operator);
        }
    }
    
    public void printVariableState() {
        System.out.println("\n+============================================+");
        System.out.println("|     ESTADO FINAL DE VARIABLES              |");
        System.out.println("+====================+=======================+");
        System.out.println("|    Variable        |       Valor           |");
        System.out.println("+====================+=======================+");
        
        if (variables.isEmpty()) {
            System.out.println("|   (sin variables)  |                       |");
        } else {
            variables.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    System.out.printf("|  %-17s |  %-20.2f |%n", 
                                    entry.getKey(), entry.getValue());
                });
        }
        
        System.out.println("+====================+=======================+");
    }
    
    public Map<String, Double> getVariables() {
        return new HashMap<>(variables);
    }
    
    public double getVariableValue(String varName) throws Exception {
        if (!variables.containsKey(varName)) {
            throw new Exception("Variable '" + varName + "' no existe");
        }
        return variables.get(varName);
    }
}
