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
public class IfNode extends Node {
    
    public Node condition; 
    public Node then; 
    public Node _else;
    
    public IfNode() {}
    
    public IfNode(Node condition, Node thenPart, Node _else) {
        this.condition = condition; this.then = thenPart; this._else = _else;
    }
    
    public Object eval() {
        Object ret = null;
        if ((condition != null) && (then != null))
            if (((Boolean) condition.eval()).booleanValue())
                ret = then.eval();
        if ((condition != null) && (_else != null))
            if (!((Boolean) condition.eval()).booleanValue())
                ret = _else.eval();
        return ret;
    }
}

