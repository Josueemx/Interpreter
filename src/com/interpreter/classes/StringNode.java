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
public class StringNode extends Node {
    
    String text;
    
    public StringNode(){}
    
    public StringNode(String text){
        this.text = text; 
    }
    
    public Object eval(){
        return text; 
    }
}
