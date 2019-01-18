package com;

import com.ast.Program;
import com.ast.expression.Variable;
import com.byteReader.StreamReader;
import com.exceptions.executionExceptions.ExecutionException;
import com.interpreterParts.Parser;
import com.exceptions.parseException.ParseException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Interpreter {
    public static void main(String[] args) {
        if(args.length == 1){
            try {
                FileInputStream inputStream = new FileInputStream(args[0]);
                Parser parser = new Parser(new StreamReader(inputStream));
                Program program = parser.parse();
                Variable result = program.execute();
                if (result != null) {
                    System.out.print("Program message: ");
                    result.print();
                }
            } catch (FileNotFoundException exc){
                System.out.println("No such file or invalid argument: " + exc.getMessage());
            } catch (ParseException exc) {
                System.out.println("Parser exception: " + exc.getMessage());
            }  catch (ExecutionException exc) {
                System.out.println("Execution exception: " + exc.getMessage());
            } catch (Exception exc){
                System.out.println("Other exception: "+ exc.getMessage());
                exc.printStackTrace();
            }
        } else if(args.length == 0){
            System.out.println("No given file");
        } else {
            System.out.println("Too many arguments");
        }
    }
}
