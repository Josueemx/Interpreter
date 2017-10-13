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
public class Token {
    
    public String text; 
    public String type;
    
    public Token(String text, String type){
        this.text = text;
        this.type = type;
    }
    
    public String toString(){
        return "Text: " + text + ". Type: " + type + "."; 
    }
}
