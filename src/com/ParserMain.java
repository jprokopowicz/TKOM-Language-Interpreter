package com;

import com.byteReader.StreamReader;
import com.parseException.ParseException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ParserMain {
    public static void main(String[] args) {
        if(args.length == 1){
            try {
                FileInputStream inputStream = new FileInputStream(args[0]);
                Parser parser = new Parser(new StreamReader(inputStream));
                parser.parse();
                System.out.println("end");
            } catch (FileNotFoundException exc){
                System.out.println("No such file or invalid argument: " + exc.getMessage());
            } catch (ParseException exc) {
                System.out.println(exc.getMessage());
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
