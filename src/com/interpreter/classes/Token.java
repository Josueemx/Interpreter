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
    
    public final String text; 
    public final TokenType type;
    
    public Token(String text, TokenType type) {
        this.text = text;
        this.type = type; 
    }

    public String toString(){
        return "Text: " + text + ". Type: " + type + "."; 
    }
}
