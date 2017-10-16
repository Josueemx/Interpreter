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
public class PrintNode extends Node{
    
    public Node expression; 
    public String type;
    
    public PrintNode(){}
    
    public PrintNode(Node expression, String type){
        this.expression = expression;
        this.type = type; 
    }
    
    public Object eval() {
        Object write_e = expression.eval();
        if (type.equals("sameline")) 
            System.out.print(write_e);
        else if (type.equals("newline")) 
            System.out.println(write_e);
        return write_e; 
    }
}
