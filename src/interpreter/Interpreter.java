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

    public int TokenPos = 0;
    
    public static void main(String[] args) {
        String expression = "(600+3+2)*5";
        Tokenizer tokenizer = new Tokenizer(expression);
        List<Token> tokens = tokenizer.tokenize(expression);
        Calculator cal = new Calculator(tokens);
        System.out.println(cal.Summation());
        tokenizer.Print(tokens);

    }
    
}
