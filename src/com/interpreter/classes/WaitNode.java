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
public class WaitNode extends Node {
    
    public Node interval; 
    
    public WaitNode() {}
    
    public WaitNode(Node interval) {
        this.interval = interval; 
    }
    
    public Object eval() {
        Double waitAmount = (Double) interval.eval(); 
        try{
            Thread.sleep((int)waitAmount.doubleValue());
        } 
        catch (Exception e) {
            System.out.println("Error: in wait operation: " + e);
            System.exit(0);
        }
        return waitAmount; 
    }
}
