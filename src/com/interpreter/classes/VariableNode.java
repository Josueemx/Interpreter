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
public class VariableNode extends Node {
    
    public String variable_name; 
    public Parser parser;
    
    public VariableNode() {}
    
    public VariableNode(String variable_name, Parser parser) {
        this.variable_name = variable_name;
        this.parser = parser; 
    }
    
    public Object eval() {
        Object value = parser.getVariable(variable_name);
        if (value == null) {
            Util.println("Error: undefined variable \"" + variable_name+"\"");
            System.exit(1);
        }
        return value; 
    }
}
