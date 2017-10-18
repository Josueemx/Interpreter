/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interpreter.classes;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Morales
 */
public class Parser {
    
    
    public static int pos = 0; //Current position
    public List<Token> tokens; //all tokens
    public Map symbol_table = new HashMap();
    
    public Parser() {
        symbol_table.put("pi", 3.141592653589793);
        symbol_table.put("e", 2.718281828459045);
    }
    
    public Parser(List<Token> tokens){
        this.tokens = tokens;
        symbol_table.put("pi", 3.141592653589793);
        symbol_table.put("e", 2.718281828459045);
    }
    
    public List<Token> getTokens(){
        return tokens; 
    }
    
    public Token getToken(int offset){
        if (pos + offset >= tokens.size()){
            return new Token("", TokenType.EOF); 
        }
        return tokens.get(pos + offset);
    }
    
    public Token currentToken(){
        return getToken(0); 
    }
    
    public Token nextToken(){
        return getToken(1); 
    }
    
    public void consumeToken(int offset){//= EatToken
        pos = pos + offset;
    }
    
    public Token MatchAndConsume(TokenType type){
        Token token = currentToken();
        if (!currentToken().type.equals(type)){
            System.out.println("Error: got " + token.type +", but " + type.name() +" expected.");
            System.exit(0);
        }
        consumeToken(1);
        return token; 
    }
    
    /*public Node getSignedFactor() {aqui se quedo pendiente
        Node node = getFactor();
        while(currentToken().type == TokenType.SUBTRACT){
            MatchAndConsume(TokenType.SUBTRACT); 
            node = new NegOperationNode(node);
        }
        return node; 
    }*/
    
    public Node getSignedFactor() {
        if(currentToken().type == TokenType.SUBTRACT){
            MatchAndConsume(TokenType.SUBTRACT); 
            Node node = new NegOperationNode(getFactor()); 
            return node;
        }
        return getFactor(); 
    }
    
    public Node getExponentiation(){
        Node node = getSignedFactor();
        while(currentToken().type==TokenType.EXPONENTIATION){
            node = new BinOperationNode(TokenType.EXPONENTIATION, node, exponentiation());           
        }
        return node;
    }
    
    public Node getTerm(){
        //Node node = getSignedFactor();
        Node node = getExponentiation();
        while (isMultiplicationOperation(currentToken().type)){
            switch(currentToken().type) {
                /*case EXPONENTIATION:
                    node = new BinOperationNode(TokenType.EXPONENTIATION, node, exponentiation());
                    break;*/
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
    
    public Node getFactor(){//aqui creo que es donde se sacan las variables
        Node res = null;
        if(currentToken().type.equals(TokenType.LEFT_PAREN)){
            MatchAndConsume(TokenType.LEFT_PAREN);
            res = Expression();
            MatchAndConsume(TokenType.RIGHT_PAREN);
        } else if(isNumber()){
            Token token = MatchAndConsume(TokenType.NUMBER);
            res = new NumberNode(new Double(token.text).doubleValue());
        } else if (isString()){
            Token token = MatchAndConsume(TokenType.STRING);
            res = new StringNode(new String(token.text)); 
        } else if (isKeyWord()){
            res = ConsumeVariable();
        } else{
            System.out.println("Error: NUMBER or LEFT_PAREN expected or some shit: "+currentToken().type.name()); //aqui ir agregando conforme se agregan tipos de datos
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
        //return getFactor(); 
        return getExponentiation(); 
    }
    
    public Node divide() {
        MatchAndConsume(TokenType.DIVIDE);
        //return getFactor(); 
        return getExponentiation(); 
    }
    
    public Node modulus() {
        MatchAndConsume(TokenType.MODULUS);
        //return getFactor(); 
        return getExponentiation(); 
    }
    
    public Node exponentiation(){
        MatchAndConsume(TokenType.EXPONENTIATION);
        return getSignedFactor(); //aqui checar que sea o no getFactor
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
            switch(currentToken().type){
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
        return type == TokenType.MULTIPLY || type == TokenType.DIVIDE || type == TokenType.MODULUS ;//|| type == TokenType.EXPONENTIATION 
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
    
    public boolean isNumber(){
        return currentToken().type == TokenType.NUMBER; 
    }
    
    public boolean isString() {
        return currentToken().type == TokenType.STRING; 
    }
    
    public boolean isKeyWord() {
        return currentToken().type == TokenType.KEYWORD; 
    }
    
    public boolean isAssignment() {
        TokenType type = currentToken().type; 
        return type == TokenType.KEYWORD && nextToken().type == TokenType.ASSIGNMENT;
    }
    
    public boolean isWhile() {
        return currentToken().type == TokenType.WHILE; 
    }
    
    public Node ConsumeVariable(){//= Variable
        Token token = MatchAndConsume(TokenType.KEYWORD); 
        Node node = new VariableNode(token.text, this);
        return node; 
    }
    
    public Object setVariable(String name, Object value) {
        symbol_table.put(name, value);
        return value; 
    }
    
    public Object getVariable(String name){
        Object value = (Object) symbol_table.get(name); 
        if (value != null) 
            return value;
        return null; 
    }
    
    public Node Assignment() {
        Node node = null;
        String name = MatchAndConsume(TokenType.KEYWORD).text; 
        MatchAndConsume(TokenType.ASSIGNMENT);
        Node value = Expression();
        node = new AssignmentNode(name, value, this);
        return node; 
    }
    
    public Node While() {
        MatchAndConsume(TokenType.WHILE);
        Node condition = Expression();
        Node body = getBlock();
        return new WhileNode(condition, body); 
    }

    public Node Statement(){
        Node node = null;
        TokenType type = currentToken().type;
        if(isAssignment()){
            node = Assignment();
        } else if (isWhile()){
            node = While();
        } else if (type == TokenType.PRINT){
            MatchAndConsume(TokenType.PRINT);
            node = new PrintNode(Expression(), "sameline"); 
        } else if (type == TokenType.PRINTLN){
            MatchAndConsume(TokenType.PRINTLN);
            node = new PrintNode(Expression(), "newline"); 
        } else if (type == TokenType.WAIT){
            MatchAndConsume(TokenType.WAIT);
            node = new WaitNode(Expression()); 
        } else {
            Util.println("Error: unknown language construct: "+ currentToken().text);
            System.exit(0);
        }
        return node; 
    }
    
    public BlockNode getBlock(){
        List<Node> statements = new LinkedList<Node>(); 
        while(currentToken().type != TokenType.END){
            statements.add(Statement());
        }
        MatchAndConsume(TokenType.END);
        return new BlockNode(statements);
    }
}
