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
                System.out.println("Program parsed successfully. Press any key to execute");
                System.in.read();
                Variable result = program.execute();
                System.out.println("Program executed");
                if (result != null) {
                    System.out.print("message: ");
                    result.print();
                    System.out.print("\n");
                }
            } catch (FileNotFoundException exc){
                System.out.println("No such file or invalid argument: " + exc.getMessage());
            } catch (ParseException exc) {
                System.out.println("Paser exception: " + exc.getMessage());
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
