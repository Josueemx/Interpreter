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
public class ActionNode extends Node {
    public String name; 
    public Node execute;
    public Parser parser;
    
    public ActionNode() {}
    
    public ActionNode(String name, Node excecute, Parser parser){
        this.name = name;
        this.execute = excecute;
        this.parser = parser;
    }
    
    public Object eval(){
        return parser.setVariable(name, execute.eval()); 
    }
}
