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
        boolean addOperation = chr == '+' || chr == '-'; 
        boolean multOperation = chr == '*' || chr == '/' || chr == '^' || chr == '%';
        boolean compOperation = chr == '<' || chr == '>' || chr == '='; 
        boolean logicOperation = chr == '!' || chr == '|' || chr == '&';
        return addOperation || multOperation || compOperation || logicOperation; 
    }
    
    public boolean isParen(char chr) {
        boolean parenOperation = chr == '(' || chr == ')';
        return parenOperation;
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
    
    public TokenType getOperationType(char chr, char nextchr) {
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
            case '<':
                type = TokenType.LESS;
                if (nextchr == '=') 
                    type = TokenType.LESSEQUAL;
                break; 
            case '>':
                type = TokenType.GREATER;
                if (nextchr == '=') 
                    type = TokenType.GREATEREQUAL; 
                break;
            case '=':
                type = TokenType.ASSIGNMENT;
                if (nextchr == '=') 
                    type = TokenType.EQUAL;
                break;
            case '!':
                type = TokenType.NOT;
                if (nextchr == '=') 
                    type = TokenType.NOTEQUAL;
                break; 
            case '|':
                type = TokenType.OR; 
                break;
            case '&':
                type = TokenType.AND;
                break; 
        }
        return type; 
    }
    
    public List<Token> tokenize(String source) {
        source = source + " ";//aqui se necesita?
        List<Token> tokens = new ArrayList<Token>(); 
        Token token = null;
        String tokenstr = "";
        char firstOperator = '\0';
        TokenizeState state = TokenizeState.DEFAULT;
        
        for (int i = 0; i < source.length(); i++) {
            char chr = source.charAt(i); 
            switch(state){
                case DEFAULT:
                    if (isOperation(chr)){
                        firstOperator = chr;
                        TokenType operation_Type = getOperationType(firstOperator, '\0'); 
                        token = new Token(Character.toString(chr), operation_Type); 
                        state = TokenizeState.OPERATOR;
                    }
                    else if (isParen(chr)) {
                        TokenType paren_type = getParenType(chr);
                        tokens.add(new Token(Character.toString(chr), paren_type)); 
                    }
                    else if (Character.isDigit(chr)) {
                        tokenstr += chr;
                        state = TokenizeState.NUMBER;
                    }
                    break;
                case NUMBER:
                    if (Character.isDigit(chr) || chr=='.' && Character.isDigit(source.charAt(i+1))) {//aqui puede tronar, tal vez no por el espacio
                        tokenstr += chr;
                    }
                    else{
                        tokens.add(new Token(tokenstr, TokenType.NUMBER)); 
                        tokenstr = "";
                        state = TokenizeState.DEFAULT;
                        i--;
                    } 
                    break;
                case OPERATOR:
                    if (isOperation(chr)){
                        TokenType operation_type = getOperationType(firstOperator, chr); 
                        token = new Token(Character.toString(firstOperator) + Character.toString(chr), operation_type);
                    }
                    else{
                        tokens.add(token);
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
