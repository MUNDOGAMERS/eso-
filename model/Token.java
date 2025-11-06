package model;

public class Token {
    //atributos 
    String key;
    String value;

    //constructor clase recordar 

    public Token(Types tipo , String value){
        this.key=tipo.toString(); 
        this.value=value;
    }

    //metodos

    public String getKey(){
        return this.key;
    }

    public String getValue(){
        return this.value;
    }

    // polimorfismo toString

    @Override
    public String toString(){
        return "('" + this.key + "', '" + this.value + "')";
    }

}
