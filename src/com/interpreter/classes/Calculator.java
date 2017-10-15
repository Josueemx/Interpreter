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
    public List<Token> tokens; //all tokens
    
    public Calculator(List<Token> tokens){
        this.tokens = tokens;
    }
    
    public Token getToken(int offset) {
        if (pos + offset >= tokens.size()){
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
            System.out.println("Error: got " + token.type +", but " + type.name() +" expected.");
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
    
    public Node getSignedFactor() {
        if (currentToken().type == TokenType.SUBTRACT){
            MatchAndConsume(TokenType.SUBTRACT); 
            Node node = new NegOperationNode(getFactor()); 
            return node;
        }
        return getFactor(); 
    }
    
    public Node getTerm(){
        Node node = getSignedFactor();
        while (isMultiplicationOperation(currentToken().type)){
            switch(currentToken().type) {
                case EXPONENTIATION:
                    node = new BinOperationNode(TokenType.EXPONENTIATION, node, exponentiation());
                    break;
                case MULTIPLY:
                    node = new BinOperationNode(TokenType.MULTIPLY, node, multiply());
                    break;
                case DIVIDE:
                    node = new BinOperationNode(TokenType.DIVIDE, node, divide());
                    break; 
                case MODULUS:
                    node = new BinOperationNode(TokenType.MODULUS, node, modulus());
                    break;
            }
            
        }
        return node; 
    }
    
    public Node getFactor(){
        Node res = null;
        if(currentToken().type.equals(TokenType.LEFT_PAREN)){
            MatchAndConsume(TokenType.LEFT_PAREN);
            res = Expression();
            MatchAndConsume(TokenType.RIGHT_PAREN);
        } else if(isNumber()){
            Token token = MatchAndConsume(TokenType.NUMBER);
            res = new NumberNode(new Double(token.text).doubleValue());
        } else{
            System.out.println("Error: NUMBER or LEFT_PAREN expected or some shit: "+currentToken().type.name()); //aqui checar
            System.exit(0);
        }
        return res;
    }
    
    public Node add() {
        MatchAndConsume(TokenType.ADD);
        return getTerm(); 
    }
    
    public Node subtract() {
        MatchAndConsume(TokenType.SUBTRACT);
        return getTerm(); 
    }
    
    public Node multiply() {
        MatchAndConsume(TokenType.MULTIPLY);
        return getFactor(); 
    }
    
    public Node divide() {
        MatchAndConsume(TokenType.DIVIDE);
        return getFactor(); 
    }
    
    public Node modulus() {
        MatchAndConsume(TokenType.MODULUS);
        return getFactor(); 
    }
    
    public Node exponentiation(){
        MatchAndConsume(TokenType.EXPONENTIATION);
        return getFactor(); 
    }

    public Node Summation(){
        Node node = getTerm();
        while(isAddOperation(currentToken().type)){
            switch(currentToken().type){
            case ADD:
                node = new BinOperationNode(TokenType.ADD, node, add());
                break; 
            case SUBTRACT:
                node = new BinOperationNode(TokenType.SUBTRACT, node, subtract());
                break;
            }
        }
        return node; 
    }
    
    public Node less(Node node){
        MatchAndConsume(TokenType.LESS);
        return new BinOperationNode(TokenType.LESS, node, Summation()); 
    }
    
    public Node lessEqual(Node node){
       MatchAndConsume(TokenType.LESSEQUAL);
        return new BinOperationNode(TokenType.LESSEQUAL, node, Summation()); 
    }

    public Node equal(Node node){
        MatchAndConsume(TokenType.EQUAL);
        return new BinOperationNode(TokenType.EQUAL, node, Summation()); 
    }
    
    public Node notEqual(Node node){
        MatchAndConsume(TokenType.NOTEQUAL);
        return new BinOperationNode(TokenType.NOTEQUAL, node, Summation()); 
    }
    
    public Node greater(Node node){
        MatchAndConsume(TokenType.GREATER);
        return new BinOperationNode(TokenType.GREATER, node, Summation()); 
    }

    public Node greaterEqual(Node node) {
        MatchAndConsume(TokenType.GREATEREQUAL);
        return new BinOperationNode(TokenType.GREATEREQUAL, node, Summation()); 
    }
    
    public Node relation() {
        Node node = Summation(); 
        if(isRelationOperation(currentToken().type)){
            switch(currentToken().type) {
                case LESS:
                    node = less(node);
                    break;
                case LESSEQUAL:
                    node = lessEqual(node); 
                    break;
                case EQUAL:
                    node = equal(node);
                    break;
                case NOTEQUAL:
                    node = notEqual(node);
                    break;
                case GREATER:
                    node = greater(node); 
                    break;
                case GREATEREQUAL:
                    node = greaterEqual(node);
                    break; 
            }
        }
        return node; 
    }
    
    public Node BoolFactor() {
        return relation(); 
    }
    
    public Node notBoolFactor() {
        if (currentToken().type == TokenType.NOT) {
            MatchAndConsume(TokenType.NOT); 
            Node node = BoolFactor(); 
            return new NotOperationNode(node);
        }
        return BoolFactor(); 
    }
    
    public Node BoolTerm() {
        Node node = notBoolFactor();
        while (currentToken().type.equals(TokenType.AND)){
                MatchAndConsume(TokenType.AND);
                node = new BinOperationNode(TokenType.AND, node, BoolFactor());
            }
        return node; 
    }
    
    public Node BoolSummation() {
        Node node = BoolTerm();
        while (isLogicalOperation(currentToken().type)) {
            switch(currentToken().type) {
                case OR:
                    MatchAndConsume(TokenType.OR); 
                    node = new BinOperationNode(TokenType.OR, node, BoolTerm());
                break; 
            }
        }
        return node; 
    }
    
    public Node Expression() {
        return BoolSummation(); 
    }
    
    public boolean isMultiplicationOperation(TokenType type){
        return type == TokenType.MULTIPLY || type == TokenType.DIVIDE || type == TokenType.MODULUS || type == TokenType.EXPONENTIATION; 
    }
    
    public boolean isAddOperation(TokenType type){
        return type == TokenType.ADD || type == TokenType.SUBTRACT; 
    }
    
    public boolean isMultiDigitOperation(TokenType type){
        return type == TokenType.LESSEQUAL || type == TokenType.GREATEREQUAL; 
    }
    
    public boolean isRelationOperation(TokenType type){
        boolean less_greater_operation = type == TokenType.LESS || type == TokenType.GREATER; 
        boolean equal_operation = type == TokenType.EQUAL || type == TokenType.NOTEQUAL; 
        return less_greater_operation || equal_operation || isMultiDigitOperation(type);
    }
    
    public boolean isLogicalOperation(TokenType type){
        return type == TokenType.OR || type == TokenType.AND; 
    }
    
    public boolean isNumber() {
        return currentToken().type == TokenType.NUMBER; 
    }

}
