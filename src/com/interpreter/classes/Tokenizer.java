/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interpreter.classes;

/**
 *
 * @author Morales
 */
import java.util.List; 
import java.util.ArrayList;

public class Tokenizer {
    
    public String exp; 
    public int pos = 0; 
    public char Char;
    
    public Tokenizer(String exp) {
        this.exp = exp+" ";
        nextChar();
    }
    
    public void nextChar() {
        if (pos < exp.length()) 
            Char = exp.charAt(pos);
        pos++;
    }
    
    public boolean isOperation(char chr) {
        return (chr == '+') || (chr == '-') || (chr == '*') || (chr == '/') || (chr == '(') || (chr == ')') || (chr == '^') || (chr == '%');
    }
    
    public String getOperationType(char chr) {
        String type = "UNKNOWN"; 
        switch(chr){
            case '+':
                type = "ADD";
                break; 
            case '-':
                type = "SUBTRACT"; 
                break;
            case '*':
                type = "MULTIPLY";
                break;
            case '/':
                type = "DIVIDE";
                break; 
            case '^':
                type = "EXPONENTIATION";
                break;
            case '%':
                type = "MODULUS";
                break; 
            case '(':
                type = "LEFT_PAREN"; 
                break;
            case ')':
                type = "RIGHT_PAREN";
                break; 
        }
        return type; 
    }
    
    public List<Token> tokenize(String source) {
        List<Token> tokens = new ArrayList<Token>(); 
        String token = "";
        String state = "DEFAULT";
        for (int i = 0; i < source.length(); i++) {
            char chr = source.charAt(i); 
            switch(state){
                case "DEFAULT":
                    String operation_type = getOperationType(chr); 
                    if (isOperation(chr)){
                        tokens.add(new Token(Character.toString(chr), operation_type)); 
                    }
                    else if (Character.isDigit(chr)) {
                        token += chr;
                        state = "NUMBER";
                    }
                    break;
                case "NUMBER":
                    if (Character.isDigit(chr) || chr=='.' && Character.isDigit(source.charAt(i+1))) {//aqui puede tronar, tal vez no por el espacio
                        token += chr;
                    }
                    else{
                        tokens.add(new Token(token, "NUMBER")); 
                        token = "";
                        state = "DEFAULT";
                        i--;
                    } 
                    break;
            }
        }
        return tokens; 
    }
    
    public void Print(List<Token> tokens) {
        int number_count = 0; 
        int operation_count = 0;
        for (Token token: tokens) {
            if (token.type.equals("NUMBER")){
                System.out.println("Number....: " + token.text);
                number_count++;
            }
            else{
                System.out.println("Operator..: " + token.type);
                operation_count++;
            } 
        }
         System.out.println("Number count: "+number_count+". Operation count: " + operation_count +".");
    }
}
