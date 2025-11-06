package service;

import model.*;
import java.util.*;

public class Lexer {

    // Arrays con caracteres de filtro para búsqueda
    private final String numbers[] = {"0","1","2","3","4","5","6","7","8","9"};
    private final String operators[] = {"+","-","/","*"};
    private final String delimiters[] = {";", "=", "(", ")"};
    private final String identifiers[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
            "ñ", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    // Método para verificar si un carácter está en un filtro
    private boolean recorrerCaracter(String caracter, String filtro[]) {
        for (int i = 0; i < filtro.length; i++) {
            if (caracter.equals(filtro[i])) {
                return true;
            }
        }
        return false;
    }

    // Método para verificar si es un dígito
    private boolean isDigit(String caracter) {
        return recorrerCaracter(caracter, numbers);
    }

    // Método para verificar si es una letra (identificador)
    private boolean isLetter(String caracter) {
        return recorrerCaracter(caracter, identifiers);
    }

    // Método para verificar si es un operador
    private boolean isOperator(String caracter) {
        return recorrerCaracter(caracter, operators);
    }

    // Método para verificar si es un delimitador
    private boolean isDelimiter(String caracter) {
        return recorrerCaracter(caracter, delimiters);
    }

    // Método para crear token de operador específico
    private Token createOperatorToken(String operator) {
        switch (operator) {
            case "+":
                return new Token(Types.SUMA, operator);
            case "-":
                return new Token(Types.RESTA, operator);
            case "*":
                return new Token(Types.MULTI, operator);
            case "/":
                return new Token(Types.DIVISION, operator);
            default:
                return null;
        }
    }

    // Método para crear token de delimitador específico
    private Token createDelimiterToken(String delimiter) {
        switch (delimiter) {
            case ";":
                return new Token(Types.PUNTO_Y_COMA, delimiter);
            case "=":
                return new Token(Types.ASIGNACION, delimiter);
            // Puedes agregar más delimitadores como paréntesis si los necesitas
            default:
                return null;
        }
    }

    // Método principal de análisis léxico
    public ArrayList<Token> lexicalAnalysis(String codigoFuente) {
        ArrayList<Token> tokens = new ArrayList<>();
        int i = 0;
        
        try {
            while (i < codigoFuente.length()) {
                String caracter = "" + codigoFuente.charAt(i);
                
                // Ignorar espacios en blanco
                if (caracter.equals(" ") || caracter.equals("\t") || caracter.equals("\n") || caracter.equals("\r")) {
                    i++;
                    continue;
                }
                
                // Manejo de números (pueden ser de múltiples dígitos)
                if (isDigit(caracter)) {
                    StringBuilder numero = new StringBuilder();
                    while (i < codigoFuente.length() && isDigit("" + codigoFuente.charAt(i))) {
                        numero.append(codigoFuente.charAt(i));
                        i++;
                    }
                    tokens.add(new Token(Types.NUM, numero.toString()));
                    continue;
                }
                
                // Manejo de identificadores (pueden ser de múltiples letras)
                if (isLetter(caracter)) {
                    StringBuilder identificador = new StringBuilder();
                    while (i < codigoFuente.length() && 
                           (isLetter("" + codigoFuente.charAt(i)) || isDigit("" + codigoFuente.charAt(i)))) {
                        identificador.append(codigoFuente.charAt(i));
                        i++;
                    }
                    tokens.add(new Token(Types.ID, identificador.toString()));
                    continue;
                }
                
                // Manejo de operadores
                if (isOperator(caracter)) {
                    Token operatorToken = createOperatorToken(caracter);
                    if (operatorToken != null) {
                        tokens.add(operatorToken);
                    }
                    i++;
                    continue;
                }
                
                // Manejo de delimitadores
                if (isDelimiter(caracter)) {
                    Token delimiterToken = createDelimiterToken(caracter);
                    if (delimiterToken != null) {
                        tokens.add(delimiterToken);
                    }
                    i++;
                    continue;
                }
                
                // Si llegamos aquí, hay un carácter no reconocido
                throw new Exception("Carácter no reconocido: '" + caracter + "' en la posición " + i);
            }
            
        } catch (Exception e) {
            System.err.println("Error en análisis léxico: " + e.getMessage());
            return new ArrayList<>();
        }
        
        return tokens;
    }
    
    // Método auxiliar para imprimir tokens (útil para debugging)
    public void printTokens(ArrayList<Token> tokens) {
        System.out.print("[");
        for (int i = 0; i < tokens.size(); i++) {
            System.out.print(tokens.get(i));
            if (i < tokens.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}
