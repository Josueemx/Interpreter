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
import java.util.List; 
import java.util.LinkedList;

public class WhileNode extends Node {
    
    public Node condition; 
    public Node body;
    
    public WhileNode(){}
    
    public WhileNode(Node condition, Node body){
        this.condition = condition;
        this.body = body; 
    }
    
    public Object eval(){
        Object ret = null;
        while(((Boolean) condition.eval()).booleanValue())
            ret = body.eval();
        return ret; 
    }
}

