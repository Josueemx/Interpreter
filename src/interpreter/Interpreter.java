/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

import com.interpreter.classes.*;
import java.util.List;

/**
 *
 * @author Morales
 */
public class Interpreter {
    
    public static void main(String[] args) {
        String expression = "((5+1)*100-2+3)";
        Tokenizer tokenizer = new Tokenizer(expression);
        List<Token> tokens = tokenizer.tokenize(expression);
        Calculator cal = new Calculator(tokens);
        Node res = cal.Expression();
        System.out.println(res.eval());
        tokenizer.Print(tokens);
    }
    
}
