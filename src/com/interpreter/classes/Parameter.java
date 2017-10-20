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
public class Parameter {//aqui no extiende a nodo?

    private String name; 
    private Node value;
    
    public Parameter(Node value){
        this.value = value; 
    }
    
    public Parameter(String name, Node value){
        this.name = name; 
        this.value = value;
    }
    
    public Parameter(String name){
        this.name = name; 
    }

    public Object eval(){
        return value.eval(); 
    }
    
    public String getName(){
        return name; 
    }
    
    public Object getValue(){
        return value.eval();
    }
}
