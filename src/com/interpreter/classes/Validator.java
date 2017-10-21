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
public class Validator {
    
    public static void validate(Node node, String message){
        if(node == null){
            System.out.println(message);
            System.exit(0);
        }
    }
    
}
