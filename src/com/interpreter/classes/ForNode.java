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
public class ForNode extends Node {
    
    public Node variable; 
    public Node condition; 
    public Node action;
    public Node body;
    
    public ForNode() {}
    
    public ForNode(Node variable, Node condition, Node action, Node body){
        this.variable = variable;
        this.condition = condition;
        this.action = action;
        this.body = body;
    }
    
    public Object eval(){
        Object res = null;
        variable.eval();
        while(((Boolean) condition.eval()).booleanValue()){
            res = body.eval();
            action.eval();
        }
        return res; 
    }
}
