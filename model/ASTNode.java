package model;

import java.util.ArrayList;
import java.util.List;

public class ASTNode {
    private String type;           // Tipo de nodo: ASIGNACION, SUMA, ID, NUM, etc.
    private String value;          // Valor del nodo (para ID y NUM)
    private List<ASTNode> children; // Hijos del nodo

    // Constructor para nodos con hijos (operadores, asignaciones)
    public ASTNode(String type) {
        this.type = type;
        this.value = null;
        this.children = new ArrayList<>();
    }

    // Constructor para nodos hoja (ID, NUM)
    public ASTNode(String type, String value) {
        this.type = type;
        this.value = value;
        this.children = new ArrayList<>();
    }

    // Agregar un hijo al nodo
    public void addChild(ASTNode child) {
        this.children.add(child);
    }

    // Getters
    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    // Método para imprimir el árbol de forma jerárquica
    public void printTree(String prefix, boolean isLast) {
        // Imprimir el nodo actual
        System.out.print(prefix);
        
        if (isLast) {
            System.out.print("└── ");
        } else {
            System.out.print("├── ");
        }
        
        // Imprimir tipo y valor
        if (value != null) {
            System.out.println(type + ": " + value);
        } else {
            System.out.println(type);
        }
        
        // Imprimir hijos
        for (int i = 0; i < children.size(); i++) {
            String newPrefix = prefix;
            
            if (isLast) {
                newPrefix += "    ";
            } else {
                newPrefix += "│   ";
            }
            
            children.get(i).printTree(newPrefix, i == children.size() - 1);
        }
    }

    @Override
    public String toString() {
        if (value != null) {
            return type + ": " + value;
        }
        return type;
    }
}
