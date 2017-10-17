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
public class Util {
    
    public static void print(Object obj) {
        System.out.print(obj);
    }
    
    public static void println(Object obj) {
        System.out.println(obj);
    }
    
    public static void println() {
        System.out.println();
    }
    
    public static void printTokens(List<Token> tokens) {
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

