package com;

import com.ast.expresion.NumberVariable;
import com.byteReader.StreamReader;
import com.parseException.ParseException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    private String inputCode;

    private Parser prepareParser(String testCode) {
        InputStream inputStream = new ByteArrayInputStream(testCode.getBytes());
        return new Parser(new StreamReader(inputStream));
    }

    @Test
    void parseNumber() {
        inputCode = "2#1:3";
        Parser parser = prepareParser(inputCode);
        try {
            parser.testReadNextToken();
            NumberVariable numberVariable = parser.parseNumber();
            assertEquals(7, numberVariable.getNominator());
            assertEquals(3, numberVariable.getDenominator());
        } catch (ParseException exc) {
            fail(exc.getMessage());
        }

        inputCode = "1:3";
        parser = prepareParser(inputCode);
        try {
            parser.testReadNextToken();
            NumberVariable numberVariable = parser.parseNumber();
            assertEquals(1, numberVariable.getNominator());
            assertEquals(3, numberVariable.getDenominator());
        } catch (ParseException exc) {
            fail(exc.getMessage());
        }

        inputCode = "12:0";
        parser = prepareParser(inputCode);
        try {
            parser.testReadNextToken();
            NumberVariable numberVariable = parser.parseNumber();
        } catch (ParseException exc) {
            fail(exc.getMessage());
        } catch (ArithmeticException artExc) {
            //pass
        }


        inputCode = "21:1";
        parser = prepareParser(inputCode);
        try {
            parser.testReadNextToken();
            NumberVariable numberVariable = parser.parseNumber();
            assertEquals(21, numberVariable.getNominator());
            assertEquals(1, numberVariable.getDenominator());
        } catch (ParseException exc) {
            fail(exc.getMessage());
        }

        inputCode = "8";
        parser = prepareParser(inputCode);
        try {
            parser.testReadNextToken();
            NumberVariable numberVariable = parser.parseNumber();
            assertEquals(8, numberVariable.getNominator());
            assertEquals(1, numberVariable.getDenominator());
        } catch (ParseException exc) {
            fail(exc.getMessage());
        }

        inputCode = "74:185";
        parser = prepareParser(inputCode);
        try {
            parser.testReadNextToken();
            NumberVariable numberVariable = parser.parseNumber();
            assertEquals(2, numberVariable.getNominator());
            assertEquals(5, numberVariable.getDenominator());
        } catch (ParseException exc) {
            fail(exc.getMessage());
        }
    }
}