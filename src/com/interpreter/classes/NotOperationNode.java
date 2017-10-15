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
public class NotOperationNode extends Node {
    
    public Node node; 
    
    public NotOperationNode(){}
    
    public NotOperationNode(Node node){
        this.node = node; 
    }
    
    public boolean toBool(Node node){
        Object res = node.eval();
        return ((Boolean) res).booleanValue(); 
    }
    
    public Object eval() {
        Object result = new Boolean(!toBool(node));
        return result; 
    }
}
