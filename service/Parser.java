package service;

import model.*;
import java.util.ArrayList;

public class Parser {
    
    private ArrayList<Token> tokens;
    private int position;
    private Token currentToken;
    
    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.position = 0;
        this.currentToken = tokens.size() > 0 ? tokens.get(0) : null;
    }
    
    // Avanzar al siguiente token
    private void advance() {
        position++;
        if (position < tokens.size()) {
            currentToken = tokens.get(position);
        } else {
            currentToken = null;
        }
    }
    
    // Verificar si el token actual coincide con el tipo esperado
    private boolean match(String expectedType) {
        return currentToken != null && currentToken.getKey().equals(expectedType);
    }
    
    // Consumir un token esperado
    private Token consume(String expectedType) throws Exception {
        if (!match(expectedType)) {
            throw new Exception("Error de sintaxis: Se esperaba " + expectedType + 
                              " pero se encontró " + (currentToken != null ? currentToken.getKey() : "EOF"));
        }
        Token token = currentToken;
        advance();
        return token;
    }
    
    // Método principal de análisis sintáctico
    public ASTNode parse() throws Exception {
        ASTNode root = parseStatement();
        
        // Verificar que se consumieron todos los tokens
        if (currentToken != null) {
            throw new Exception("Error: Tokens inesperados después del statement");
        }
        
        return root;
    }
    
    // Statement -> Assignment
    private ASTNode parseStatement() throws Exception {
        return parseAssignment();
    }
    
    // Assignment -> ID = Expression ;
    private ASTNode parseAssignment() throws Exception {
        Token idToken = consume("ID");
        consume("ASIGNACION");
        
        ASTNode assignmentNode = new ASTNode("ASIGNACION");
        ASTNode idNode = new ASTNode("ID", idToken.getValue());
        assignmentNode.addChild(idNode);
        
        ASTNode expressionNode = parseExpression();
        assignmentNode.addChild(expressionNode);
        
        consume("PUNTO_Y_COMA");
        
        return assignmentNode;
    }
    
    // Expression -> Term ((SUMA | RESTA) Term)*
    private ASTNode parseExpression() throws Exception {
        ASTNode left = parseTerm();
        
        // Mientras haya operadores de suma o resta
        while (currentToken != null && (match("SUMA") || match("RESTA"))) {
            Token operatorToken = currentToken;  // Guardar el token completo
            advance();
            
            // Usar el VALUE del token (el carácter +, -) en lugar del tipo
            ASTNode operatorNode = new ASTNode(operatorToken.getValue());
            operatorNode.addChild(left);
            
            ASTNode right = parseTerm();
            operatorNode.addChild(right);
            
            left = operatorNode;
        }
        
        return left;
    }
    
    // Term -> Factor ((MULTI | DIVISION) Factor)*
    private ASTNode parseTerm() throws Exception {
        ASTNode left = parseFactor();
        
        // Mientras haya operadores de multiplicación o división
        while (currentToken != null && (match("MULTI") || match("DIVISION"))) {
            Token operatorToken = currentToken;  // Guardar el token completo
            advance();
            
            // Usar el VALUE del token (el carácter *, /) en lugar del tipo
            ASTNode operatorNode = new ASTNode(operatorToken.getValue());
            operatorNode.addChild(left);
            
            ASTNode right = parseFactor();
            operatorNode.addChild(right);
            
            left = operatorNode;
        }
        
        return left;
    }
    
    // Factor -> NUM | ID
    private ASTNode parseFactor() throws Exception {
        if (match("NUM")) {
            Token numToken = currentToken;
            advance();
            return new ASTNode("NUM", numToken.getValue());
        } else if (match("ID")) {
            Token idToken = currentToken;
            advance();
            return new ASTNode("ID", idToken.getValue());
        } else {
            throw new Exception("Error de sintaxis: Se esperaba NUM o ID pero se encontró " + 
                              (currentToken != null ? currentToken.getKey() : "EOF"));
        }
    }
}
