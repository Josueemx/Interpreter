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

public class NegOperationNode extends Node {
    
    public Node node; 
    
    public NegOperationNode(){}
    
    public NegOperationNode(Node node){
        this.node = node; 
    }

    public double toNumber(Node node){
        Object res = node.eval();
        return ((Double) res).doubleValue(); 
    }
    
    public Object eval() {
        Object result = new Double(-toNumber(node));
        return result; 
    }
}
