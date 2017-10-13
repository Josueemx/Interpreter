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
public class Calculator {
    
    public String exp = ""; //Expression
    public static int pos = 0; //Current position
    public static char Char; //Char being tested
    
    public Calculator(String exp){
        this.exp = exp + " ";
        nextChar();
    }
    
    public void nextChar(){ //== getChar
        if (pos < exp.length()) 
            Char = exp.charAt(pos);
        pos++;
    }
    
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
    
    public void MatchAndConsume(char chr) {
        if (Char==chr) 
            nextChar(); 
        else{
            System.out.println("Error: unrecognizable character.");
            System.exit(0);
        }
    }
    
    public double getTerm(){
        double res = getFactor();
        while ((Char == '*') || (Char == '/') || (Char == '%') || (Char == '^')){
            switch(Char) {
            case '^':
                res = Math.pow(res, exponentiation());
                break;
            case '*':
                res = res * multiply();
                break; 
            case '/':
                res = res / divide();
                break; 
            case '%':
                res = res % modulus();
                break;
            }
            
        }
        return res; 
    }
    
    public double getFactor(){
        double res = 0; 
        if (Char == '(') {
            MatchAndConsume('(');
            res = Summation();
            MatchAndConsume(')');
        } else
            res = getNumber();
        return res;
    }
    
    public double add() {
        MatchAndConsume('+');
        return getTerm(); 
    }
    
    public double subtract() {
        MatchAndConsume('-');
        return getTerm(); 
    }
    
    public double multiply() {
        MatchAndConsume('*');
        return getFactor(); 
    }
    
    public double divide() {
        MatchAndConsume('/');
        return getFactor(); 
    }
    
    public double modulus() {
        MatchAndConsume('%');
        return getFactor(); 
    }
    
    public double exponentiation() {
        MatchAndConsume('^');
        return getFactor(); 
    }

    public double Summation() {
        double res = getTerm();
        while ((Char == '+') || (Char == '-')){
            switch(Char){
            case '+':
                res = res + add();
                break; 
            case '-':
                res = res - subtract(); 
                break;
            }
        }
        return res; 
    }
}
