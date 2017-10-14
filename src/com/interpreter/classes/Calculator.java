/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interpreter.classes;

import java.util.List;

/**
 *
 * @author Morales
 */
public class Calculator {
    
    
    public static int pos = 0; //Current position
    public List<Token> tokens;
    
    public Calculator(List<Token> tokens){
        this.tokens = tokens;
    }
    
    public Token getToken(int offset) {
        if (pos + offset >= tokens.size()) {
            return new Token("", TokenType.EOF); 
        }
        return tokens.get(pos + offset);
    }
    
    public Token currentToken() {
        return getToken(0); 
    }
    
    public Token nextToken() {
        return getToken(1); 
    }
    
    public void consumeToken(int offset) {//= EatToken
        pos = pos + offset;
    }
    
    public Token MatchAndConsume(TokenType type) {
        Token token = currentToken();
        if (!currentToken().type.equals(type)) {
            System.out.println("Error: got " + token.type +", but " + type +" expected.");
            System.exit(0);
        }
        consumeToken(1);
        return token; 
    }
    
    /* tener en cuenta esto
    public double getNumber(){
        double number = 0;
        String temp_number = "";
        if (!Character.isDigit(Char)){//aqui puede ser variable
            System.out.println("Error: number expected."); 
            System.exit(0);
        }
        while (Character.isDigit(Char)){
            temp_number = temp_number + Char;
            nextChar(); 
        }
        if(Char=='.' && Character.isDigit(exp.charAt(pos))){//aqui checar
            temp_number = temp_number + Char;
            nextChar();
            if('.' == Char){//aqui este y siguiente if, factorizar? talvez esto no sea necesario (1)
                System.out.println("Error: number expected."); 
                System.exit(0);
            }
            while (Character.isDigit(Char)) {
                temp_number = temp_number + Char;
                nextChar(); 
            }
            if('.' == Char){//aqui este y siguiente if, factorizar? talvez esto no sea necesario (2)
                System.out.println("Error: number expected."); 
                System.exit(0);
            }
        }
        number = new Double(temp_number).doubleValue();
        return number;
    }
    */
    public double getTerm(){
        double res = getFactor();
        while (currentToken().type.equals(TokenType.MULTIPLY) || currentToken().type.equals(TokenType.DIVIDE) || currentToken().type.equals(TokenType.EXPONENTIATION) || currentToken().type.equals(TokenType.MODULUS)){
            switch(currentToken().type) {
            case EXPONENTIATION:
                res = Math.pow(res, exponentiation());
                break;
            case MULTIPLY:
                res = res * multiply();
                break;
            case DIVIDE:
                res = res / divide(); 
                break; 
            case MODULUS:
                res = res % modulus();
                break;
            }
            
        }
        return res; 
    }
    
    public double getFactor(){
        double res = 0; 
        if (currentToken().type.equals(TokenType.LEFT_PAREN)) {
            MatchAndConsume(TokenType.LEFT_PAREN);
            res = Summation();
            MatchAndConsume(TokenType.RIGHT_PAREN);
        } else if (currentToken().type.equals(TokenType.NUMBER)) {
            res = new Double(currentToken().text).doubleValue();
            MatchAndConsume(TokenType.NUMBER);
        }
        return res;
    }
    
    public double add() {
        MatchAndConsume(TokenType.ADD);
        return getTerm(); 
    }
    
    public double subtract() {
        MatchAndConsume(TokenType.SUBTRACT);
        return getTerm(); 
    }
    
    public double multiply() {
        MatchAndConsume(TokenType.MULTIPLY);
        return getFactor(); 
    }
    
    public double divide() {
        MatchAndConsume(TokenType.DIVIDE);
        return getFactor(); 
    }
    
    public double modulus() {
        MatchAndConsume(TokenType.MODULUS);
        return getFactor(); 
    }
    
    public double exponentiation() {
        MatchAndConsume(TokenType.EXPONENTIATION);
        return getFactor(); 
    }

    public double Summation() {
        double res = getTerm();
        while (currentToken().type.equals(TokenType.ADD) || currentToken().type.equals(TokenType.SUBTRACT)){
            switch(currentToken().type){
            case ADD:
                res = res + add();
                break; 
            case SUBTRACT:
                res = res - subtract(); 
                break;
            }
        }
        return res; 
    }
}
