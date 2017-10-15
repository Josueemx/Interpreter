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
public class BinOperationNode extends Node {
    
    public TokenType operation; 
    public Node left; 
    public Node right;
    
    public BinOperationNode(){}
    
    public BinOperationNode(TokenType operation, Node left, Node right){
        this.operation = operation; 
        this.left = left; 
        this.right = right;
    }
    
    public double toNumber(Node node) {
        Object res = node.eval();
        return ((Double) res).doubleValue(); 
    }
    
    public boolean toBool(Node node) {
        Object res = node.eval();
        return ((Boolean) res).booleanValue(); 
    }
    
    public Object toObject(Node node) {
        return node.eval(); 
    }
    
    public Object eval() {
        Object res = null; 
        switch(operation){
            case ADD:
                res = new Double(toNumber(left) + toNumber(right));
                break;
            case SUBTRACT:
                res = new Double(toNumber(left) - toNumber(right)); 
                break;
            case MULTIPLY:
                res = new Double(toNumber(left) * toNumber(right));
                break;
            case EXPONENTIATION:
                res = (double) Math.pow(toNumber(left), toNumber(right));
                break;
            case MODULUS:
                res = new Double(toNumber(left) % toNumber(right));
                break;
            case DIVIDE:
                if (toNumber(right) == 0) {
                    System.out.println("Error: division by zero.");
                    System.exit(0);
                }
                res = new Double(toNumber(left) / toNumber(right)); 
                break;
            case LESS:
                res = new Boolean(toNumber(left) < toNumber(right));
                break;
            case GREATER:
                res = new Boolean(toNumber(left) > toNumber(right)); 
                break;
            case EQUAL:
                res = new Boolean(toObject(left).equals(toObject(right)));
                break;
            case NOTEQUAL:
                res = new Boolean(!toObject(left).equals(toObject(right))); 
                break;
            case LESSEQUAL:
                res = new Boolean(toNumber(left) <= toNumber(right));
                break;
            case GREATEREQUAL:
                res = new Boolean(toNumber(left) >= toNumber(right)); 
                break;
            case OR:
                res = new Boolean(toBool(left) || toBool(right));
                break; 
            case AND:
                res = new Boolean(toBool(left) && toBool(right)); 
                break;
        }
        return res; 
    }

}