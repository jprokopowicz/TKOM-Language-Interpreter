package com;

import com.ast.Program;
import com.ast.expresion.Variable;
import com.byteReader.StreamReader;
import com.executionExceptions.ExecutionException;
import com.parseException.ParseException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ProgramMain {
    public static void main(String[] args) {
        if(args.length == 1){
            try {
                FileInputStream inputStream = new FileInputStream(args[0]);
                Parser parser = new Parser(new StreamReader(inputStream));
                Program program = parser.parse();
                System.out.println("Program parsed successfully. Press enter to execute.");
                System.in.read();
                Variable result = program.execute();
                if (result != null) {
                    System.out.print("\n\nProgram message: ");
                    result.print();
                }
            } catch (FileNotFoundException exc){
                System.out.println("No such file or invalid argument: " + exc.getMessage());
            } catch (ParseException exc) {
                System.out.println("Parser exception: " + exc.getMessage());
                exc.printStackTrace();
            }  catch (ExecutionException exc) {
                System.out.println("Execution exception: " + exc.getMessage());
                exc.printStackTrace();
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
