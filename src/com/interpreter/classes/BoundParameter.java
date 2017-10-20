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
public class BoundParameter {
    
    private String name; 
    private Object value;
    
    public BoundParameter(String name, Object value) {
        this.name = name;
        this.value = value; 
    }
    
    public String getName() {
        return name; 
    }
    
    public Object getValue() {
        return value; 
    }
}
