package service;
import model.*;
import java.util.*;

public class Lexer {    

    final String numbers[] = {"0","1","2","3","4","5","6","7","8","9"};
    final String operators [] = {"+","-","/","*"};
    final String delimiters [] = {";","(",")"};
    final String identifiers [] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", 
                                   "ñ", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    public boolean filterChar(String caracter){

        boolean x = true;
        int i = 0;

        while(x != false){

            if(caracter.equalsIgnoreCase(numbers[i])){

                x = false;
                return true;
            }

            i++;
        }

        i=0;
        x=true;

        while(x != false){

            if(caracter.equalsIgnoreCase(identifiers[i])){

                x = false;
                return true;
            }

            i++;
        }

        i=0;
        x=true;

        while(x != false){

            if(caracter.equalsIgnoreCase(operators[i])){

                x = false;
                return true;
            }

            i++;
        }

        i=0;
        x=true;

        while(x != false){

            if(caracter.equalsIgnoreCase(delimiters[i])){

                x = false;
                return true;
            }

            i++;
        }
        
        return false;
    }


    public ArrayList<Token> lexicalAnalysis(String codigoFuente){

        try {
            for(int i = 0; i <= codigoFuente.length(); i++){

            }


        } catch (Exception e) {

        }


        return new ArrayList(); 
    }
}
