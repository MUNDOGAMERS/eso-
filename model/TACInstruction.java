package model;

public class TACInstruction {
    
    private String result;      // Variable resultado (t1, t2, etc. o variable real)
    private String operator;    // Operador (+, -, *, /, =)
    private String operand1;    // Primer operando
    private String operand2;    // Segundo operando (puede ser null)
    
    // Constructor para operaciones binarias: result = operand1 op operand2
    public TACInstruction(String result, String operand1, String operator, String operand2) {
        this.result = result;
        this.operator = operator;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }
    
    // Constructor para asignaciones simples: result = operand1
    public TACInstruction(String result, String operand1) {
        this.result = result;
        this.operator = "=";
        this.operand1 = operand1;
        this.operand2 = null;
    }
    
    @Override
    public String toString() {
        if (operand2 != null) {
            // Operación binaria: t1 = a + b
            return result + " = " + operand1 + " " + operator + " " + operand2;
        } else {
            // Asignación simple: x = t1
            return result + " = " + operand1;
        }
    }
    
    // Getters
    public String getResult() {
        return result;
    }
    
    public String getOperator() {
        return operator;
    }
    
    public String getOperand1() {
        return operand1;
    }
    
    public String getOperand2() {
        return operand2;
    }
}
