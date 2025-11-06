package service;

import model.*;
import java.util.List;

public class Semantic {
    
    private SymbolTable symbolTable;
    private ASTNode ast;
    
    public Semantic(ASTNode ast) {
        this.ast = ast;
        this.symbolTable = new SymbolTable();
    }
    
    public void analyze() throws Exception {
        System.out.println("Iniciando analisis semantico...\n");
        analyzeNode(ast);
        System.out.println("\n*** Analisis semantico completado sin errores ***");
        symbolTable.printTable();
    }
    
    private String analyzeNode(ASTNode node) throws Exception {
        String nodeType = node.getType();
        
        switch (nodeType) {
            case "ASIGNACION":
                return analyzeAssignment(node);
            case "+":
            case "-":
            case "*":
            case "/":
                return analyzeOperation(node, nodeType);
            case "NUM":
                return "NUM";
            case "ID":
                return analyzeIdentifier(node);
            default:
                throw new Exception("Error interno: Tipo de nodo desconocido '" + nodeType + "'");
        }
    }
    
    private String analyzeAssignment(ASTNode node) throws Exception {
        List<ASTNode> children = node.getChildren();
        
        if (children.size() != 2) {
            throw new Exception("Error interno: Nodo de asignacion mal formado");
        }
        
        ASTNode leftNode = children.get(0);
        
        if (!leftNode.getType().equals("ID")) {
            throw new Exception("Error semantico: El lado izquierdo debe ser un identificador");
        }
        
        String variableName = leftNode.getValue();
        ASTNode rightNode = children.get(1);
        String expressionType = analyzeNode(rightNode);
        
        if (!expressionType.equals("NUM")) {
            throw new Exception("Error de tipos: Se esperaba tipo NUM pero se encontro " + expressionType);
        }
        
        if (!symbolTable.isDeclared(variableName)) {
            symbolTable.declare(variableName, "NUM");
            System.out.println("  -> Variable '" + variableName + "' declarada con tipo NUM");
        } else {
            String existingType = symbolTable.getType(variableName);
            if (!existingType.equals("NUM")) {
                throw new Exception("Error de tipos: Conflicto en variable '" + variableName + "'");
            }
            System.out.println("  -> Variable '" + variableName + "' actualizada");
        }
        
        return "NUM";
    }
    
    private String analyzeOperation(ASTNode node, String operator) throws Exception {
        List<ASTNode> children = node.getChildren();
        
        if (children.size() != 2) {
            throw new Exception("Error interno: Operacion '" + operator + "' debe tener 2 operandos");
        }
        
        ASTNode leftChild = children.get(0);
        String leftType = analyzeNode(leftChild);
        
        ASTNode rightChild = children.get(1);
        String rightType = analyzeNode(rightChild);
        
        if (!leftType.equals("NUM")) {
            throw new Exception("Error de tipos: Operando izquierdo de '" + operator + 
                              "' debe ser NUM, es " + leftType);
        }
        
        if (!rightType.equals("NUM")) {
            throw new Exception("Error de tipos: Operando derecho de '" + operator + 
                              "' debe ser NUM, es " + rightType);
        }
        
        return "NUM";
    }
    
    private String analyzeIdentifier(ASTNode node) throws Exception {
        String variableName = node.getValue();
        
        if (!symbolTable.isDeclared(variableName)) {
            symbolTable.declare(variableName, "NUM");
            System.out.println("  -> Variable '" + variableName + 
                             "' usada sin declaracion (declarada implicitamente como NUM)");
        }
        return symbolTable.getType(variableName);
    }
    
    public SymbolTable getSymbolTable() {
        return symbolTable;
    }
}
