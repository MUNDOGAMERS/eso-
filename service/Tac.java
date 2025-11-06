package service;

import model.*;
import java.util.ArrayList;
import java.util.List;

public class Tac {
    
    private ArrayList<TACInstruction> instructions;
    private int tempCounter;
    
    public Tac() {
        this.instructions = new ArrayList<>();
        this.tempCounter = 1;
    }
    
    private String newTemp() {
        return "t" + tempCounter++;
    }
    
    public void generate(ASTNode ast) {
        System.out.println("Generando codigo intermedio (TAC)...\n");
        generateCode(ast);
        printInstructions();
    }
    
    private String generateCode(ASTNode node) {
        String nodeType = node.getType();
        
        switch (nodeType) {
            case "ASIGNACION":
                return generateAssignment(node);
            case "+":
            case "-":
            case "*":
            case "/":
                return generateOperation(node, nodeType);
            case "NUM":
                return node.getValue();
            case "ID":
                return node.getValue();
            default:
                System.err.println("Advertencia: Tipo de nodo desconocido '" + nodeType + "'");
                return null;
        }
    }
    
    private String generateAssignment(ASTNode node) {
        List<ASTNode> children = node.getChildren();
        
        if (children.size() != 2) {
            System.err.println("Error: Asignacion mal formada");
            return null;
        }
        
        ASTNode leftNode = children.get(0);
        String leftVar = leftNode.getValue();
        
        ASTNode rightNode = children.get(1);
        String rightValue = generateCode(rightNode);
        
        TACInstruction instruction = new TACInstruction(leftVar, rightValue);
        instructions.add(instruction);
        
        return leftVar;
    }
    
    private String generateOperation(ASTNode node, String operator) {
        List<ASTNode> children = node.getChildren();
        
        if (children.size() != 2) {
            System.err.println("Error: Operacion mal formada");
            return null;
        }
        
        ASTNode leftNode = children.get(0);
        String leftOperand = generateCode(leftNode);
        
        ASTNode rightNode = children.get(1);
        String rightOperand = generateCode(rightNode);
        
        String temp = newTemp();
        
        TACInstruction instruction = new TACInstruction(temp, leftOperand, operator, rightOperand);
        instructions.add(instruction);
        
        return temp;
    }
    
    public void printInstructions() {
        if (instructions.isEmpty()) {
            System.out.println("No se generaron instrucciones TAC.");
            return;
        }
        
        System.out.println("+============================================+");
        System.out.println("|  CODIGO DE TRES DIRECCIONES (TAC)         |");
        System.out.println("+============================================+");
        
        for (int i = 0; i < instructions.size(); i++) {
            System.out.printf("|  (%d)  %-36s |%n", (i + 1), instructions.get(i).toString());
        }
        
        System.out.println("+============================================+");
    }
    
    public ArrayList<TACInstruction> getInstructions() {
        return instructions;
    }
    
    public int getInstructionCount() {
        return instructions.size();
    }
    
    public int getTempCount() {
        return tempCounter - 1;
    }
}
