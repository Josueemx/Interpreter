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
    
    
    public boolean isParen(char chr) {
        return chr == '(' || chr == ')';
    }
    
    public TokenType getParenType(char chr) {
        TokenType type = TokenType.UNKNOWN; 
        switch(chr){
            case '(':
                type = TokenType.LEFT_PAREN;
                break; 
            case ')':
                type = TokenType.RIGHT_PAREN; 
                break;
        }
        return type; 
    }
    
    public TokenType getOperationType(char chr) {
        TokenType type = TokenType.UNKNOWN;
        switch(chr){
            case '+':
                type = TokenType.ADD;
                break; 
            case '-':
                type = TokenType.SUBTRACT; 
                break;
            case '*':
                type = TokenType.MULTIPLY;
                break;
            case '/':
                type = TokenType.DIVIDE;
                break; 
            case '^':
                type = TokenType.EXPONENTIATION;
                break;
            case '%':
                type = TokenType.MODULUS;
                break; 
            case '(':
                type = TokenType.LEFT_PAREN; 
                break;
            case ')':
                type = TokenType.RIGHT_PAREN;
                break; 
        }
        return type; 
    }
    
    public List<Token> tokenize(String source) {
        source = source + " ";//aqui se necesita?
        List<Token> tokens = new ArrayList<Token>(); 
        String token = "";
        TokenizeState state = TokenizeState.DEFAULT;
        for (int i = 0; i < source.length(); i++) {
            char chr = source.charAt(i); 
            switch(state){
                case DEFAULT:
                    TokenType operation_type = getOperationType(chr); 
                    if (isOperation(chr)){
                        tokens.add(new Token(Character.toString(chr), operation_type)); 
                    }
                    else if (isParen(chr)) {
                        TokenType parenType = getParenType(chr);
                        tokens.add(new Token(Character.toString(chr), parenType)); 
                    }
                    else if (Character.isDigit(chr)) {
                        token += chr;
                        state = TokenizeState.NUMBER;
                    }
                    break;
                case NUMBER:
                    if (Character.isDigit(chr) || chr=='.' && Character.isDigit(source.charAt(i+1))) {//aqui puede tronar, tal vez no por el espacio
                        token += chr;
                    }
                    else{
                        tokens.add(new Token(token, TokenType.NUMBER)); 
                        token = "";
                        state = TokenizeState.DEFAULT;
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
            if (token.type.equals(TokenType.NUMBER)){
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
