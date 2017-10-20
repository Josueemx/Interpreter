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
import java.util.ArrayList;

public class FunctionCallNode extends Node {
    
    public Node name;
    public List<Parameter> actualParameters; 
    public Parser parser;
    
    public FunctionCallNode(){} 
    
    public FunctionCallNode(Node name, List<Parameter> actualParameters, Parser parser){ 
        this.name = name;
        this.actualParameters = actualParameters; 
        this.parser = parser;
    }
    
    public Object eval() {
        Function function = (Function) name.eval();
        List<BoundParameter> boundParameters = new ArrayList(); 
        if (function.getParameters() != null){
            if (actualParameters != null){
                if (actualParameters.size() < function.getParameters().size()){
                    System.out.println("Error: too few parameters in function call: "+ function.getName());
                    System.exit(0);
                }
                else if (actualParameters.size() > function.getParameters().size()) {
                    System.out.println("Error: too many parameters in function call: "+ function.getName());
                    System.exit(0);
                }
                else {
                    for (int i = 0; i < actualParameters.size(); i++) {
                        String name = function.getParameters().get(i).getName();
                        Object value = actualParameters.get(i).getValue();
                        if (value instanceof Function){
                            value = ((Function) value).eval();
                        }
                        boundParameters.add(new BoundParameter(name, value));
                    }
                }
            }
        }
        return parser.ExecuteFunction(function, boundParameters); 
    }
}

        