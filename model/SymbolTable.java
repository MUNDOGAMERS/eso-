package model;

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;

public class SymbolTable {
    
    private Map<String, String> symbols;
    
    public SymbolTable() {
        this.symbols = new LinkedHashMap<>();
    }
    
    public void declare(String variableName, String type) throws Exception {
        if (symbols.containsKey(variableName)) {
            throw new Exception("Error semantico: La variable '" + variableName + 
                              "' ya fue declarada anteriormente.");
        }
        symbols.put(variableName, type);
    }
    
    public boolean isDeclared(String variableName) {
        return symbols.containsKey(variableName);
    }
    
    public String getType(String variableName) throws Exception {
        if (!isDeclared(variableName)) {
            throw new Exception("Error semantico: La variable '" + variableName + 
                              "' no existe en la tabla de simbolos.");
        }
        return symbols.get(variableName);
    }
    
    public void printTable() {
        System.out.println("\n+====================================+");
        System.out.println("|       TABLA DE SIMBOLOS            |");
        System.out.println("+================+====================+");
        System.out.println("|    Variable    |       Tipo         |");
        System.out.println("+================+====================+");
        
        if (symbols.isEmpty()) {
            System.out.println("|    (vacia)     |                    |");
        } else {
            for (Map.Entry<String, String> entry : symbols.entrySet()) {
                System.out.printf("| %-14s | %-18s |%n", 
                                entry.getKey(), entry.getValue());
            }
        }
        
        System.out.println("+================+====================+");
    }
    
    public Map<String, String> getSymbols() {
        return new HashMap<>(symbols);
    }
    
    public int size() {
        return symbols.size();
    }
}
