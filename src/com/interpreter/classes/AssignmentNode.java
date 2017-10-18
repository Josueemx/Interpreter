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
public class AssignmentNode extends Node {
    
    public String name; 
    public Node value; 
    public Parser parser; 
    public String scope;
    
    public AssignmentNode(){}
    
    public AssignmentNode(String name, Node value, Parser parser){
        this.name = name; 
        this.value = value; 
        this.parser = parser;
    }
    
    public Object eval() {
        return parser.setVariable(name, value.eval()); 
    }
}
    

