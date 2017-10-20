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
    
    public Tokenizer(){}
    
    public Tokenizer(String exp){
        this.exp = exp+" ";
        nextChar();
    }
    
    public void nextChar(){
        if (pos < exp.length()) 
            Char = exp.charAt(pos);
        pos++;
    }
    
    public boolean isOperation(char chr){
        boolean addOperation = chr == '+' || chr == '-'; 
        boolean multOperation = chr == '*' || chr == '/' || chr == '^' || chr == '%';
        boolean compOperation = chr == '<' || chr == '>' || chr == '='; 
        boolean logicOperation = chr == '!' || chr == '|' || chr == '&';
        return addOperation || multOperation || compOperation || logicOperation; 
    }
    
    public boolean isParen(char chr){
        boolean parenOperation = chr == '(' || chr == ')';
        //boolean comma = chr == ',';//aqui no estoy seguro
        return parenOperation /*|| comma*/;
    }
    
    public boolean isPunctuation(char chr) {
        boolean puncOp = chr == ',';
        return puncOp; 
    }
    
    public TokenType getPunctuationType(char firstOperator) {
        TokenType type = TokenType.UNKNOWN; 
        switch(firstOperator){
            case ',':
                type = TokenType.COMMA;
                break; 
        }
        return type; 
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
            /*case ',':
                type = TokenType.COMMA;
                break;*/
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
    
    public List<Token> tokenize(String source){
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
                    } else if (Character.isDigit(chr)){
                        tokenstr += chr;
                        state = TokenizeState.NUMBER;
                    } else if (isParen(chr)){
                        TokenType paren_type = getParenType(chr);
                        tokens.add(new Token(Character.toString(chr), paren_type)); 
                    } else if (chr == '"'){
                        state = TokenizeState.STRING;
                    } else if (chr == '#'){
                        state = TokenizeState.COMMENT;
                    } else if (Character.isLetter(chr)){
                        tokenstr += chr;
                        state = TokenizeState.KEYWORD;
                    } else if (isPunctuation(chr)){
                        TokenType puncType = getPunctuationType(chr);
                        tokens.add(new Token(Character.toString(chr), puncType)); 
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
                    if (chr=='='&&(firstOperator=='<'||firstOperator=='>'||firstOperator=='!'||firstOperator=='=')){
                        TokenType operation_type = getOperationType(firstOperator, chr); 
                        token = new Token(Character.toString(firstOperator) + Character.toString(chr), operation_type);
                        tokens.add(token);//aqui no estoy seguro
                        state = TokenizeState.DEFAULT;//aqui no estoy seguro
                    }
                    else{
                        tokens.add(token);
                        state = TokenizeState.DEFAULT;
                        i--;
                    }
                    break;
                case KEYWORD:
                    if (Character.isLetterOrDigit(chr)){
                        tokenstr += chr;
                    } 
                    else{
                        TokenType type = getStatementType(tokenstr); //aqui checar para obtener constante
                        tokens.add(new Token(tokenstr, type)); 
                        tokenstr = "";
                        state = TokenizeState.DEFAULT;
                        i--;
                    } 
                    break;
                case STRING:
                    if(chr == '"'){
                        tokens.add(new Token(tokenstr, TokenType.STRING)); 
                        tokenstr = "";
                        state = TokenizeState.DEFAULT;
                    }
                    else 
                        tokenstr += chr;
                    break;
                case COMMENT:
                    if(chr == '\n')
                        state = TokenizeState.DEFAULT;
                    break;
            }
        }
        return tokens; 
    }
    
    public TokenType getStatementType(String str) {
        TokenType type = TokenType.UNKNOWN; 
        switch(str){
            case "start":
                type = TokenType.START;//= SCRIPT
                break;
            case "end":
                type = TokenType.END;
                break;
            case "while":
                type = TokenType.WHILE;
                break;
            case "for":
                type = TokenType.FOR;
                break;
            case "if":
                type = TokenType.IF;
                break;
            case "else":
                type = TokenType.ELSE;
                break;
            case "function":
                type = TokenType.FUNCTION;
                break;
            case "print":
                type = TokenType.PRINT;
                break;
            case "println":
                type = TokenType.PRINTLN; 
                break;
            case "wait":
                type = TokenType.WAIT;
                break; 
            default:
                type = TokenType.KEYWORD;
        }
        return type;
    }
    
    public void printTokens(List<Token> tokens) {
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
