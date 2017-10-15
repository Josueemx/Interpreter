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
public class BooleanNode extends Node {
    
    Boolean value;
    
    public BooleanNode(){}
    
    public BooleanNode(Boolean value){
        this.value = value; 
    }
    
    public Object eval(){
        return value; 
    }
    
    public String toString(){
        return value+""; 
    }
}
