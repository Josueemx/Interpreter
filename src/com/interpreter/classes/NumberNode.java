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

public class NumberNode extends Node {
    
    Double value;
    
    public NumberNode(){}
    
    public NumberNode(Double value){
            this.value = value;
        }
    
    public Object eval(){
        return value; 
    }
    
    public String toString(){
        return value+""; 
    }
}
