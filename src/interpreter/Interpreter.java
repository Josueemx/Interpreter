/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

import com.interpreter.classes.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Morales
 */
public class Interpreter {
    
    public static void main(String[] args) {
        try {
            boolean debug = false;
        
            if(args.length < 1){ 
                Util.println("Usage: Demo <script>"); 
                return; 
            }

            if(args.length > 1){ 
                if (args[1].equals("debug")) 
                    debug = true; 
            }        

            Interpreter interpreter = new Interpreter();
            String source_code = interpreter.ReadFile(args[0]);
            interpreter.interpret(source_code, debug);
        } catch (Exception e) {
            System.out.println("Error: there was an error parsing an expression.");
            System.exit(0);
        }
    }
    
    public void interpret(String source, boolean debug){
        Tokenizer tokenizer = new Tokenizer(source);
        Parser parser = new Parser(tokenizer.tokenize(source));
        if(debug) 
            dumpTokens(parser);
        parser.MatchAndConsume(TokenType.START);
        Node script = parser.getBlock();
        script.eval();
    }

    public void dumpTokens(Parser parser){
        for (Token token: parser.getTokens())
            Util.println("Type: " + token.type + " Text: " + token.text+" ");
        Util.println();    
    }
        
    private String ReadFile(String path)
    {
        FileInputStream stream = null;
        InputStreamReader input = null;
        try{
            stream = new FileInputStream(path);                       
            input = new InputStreamReader(stream, Charset.defaultCharset());
                
            Reader reader = new BufferedReader(input);
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[8192];
            int read;
            while ((read = reader.read(buffer, 0, buffer.length)) > 0){
                builder.append(buffer, 0, read);
            }
            builder.append(" ");
            return builder.toString();
        }
        catch(FileNotFoundException e){
            Util.println("Error: file not found.");
            System.exit(0);
        }
        catch(IOException e){
            Util.println("Error: an error occurred while reading the script. "+e.getMessage());
            System.exit(0);
        }
        catch(Exception e){
            Util.println("Error: the file could not be read. "+e.getMessage());
            System.exit(0);
        }        
        finally{
            try{
                input.close();
                stream.close();
            }
            catch (Exception e){             
                Util.println("Error: the file could not be closed.");
                System.exit(0);
            }            
        }     
        return null;
    }    
    
}
