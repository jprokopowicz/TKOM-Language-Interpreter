package com.interpreterParts;

import com.ast.Program;
import com.ast.expresion.NumberVariable;
import com.ast.expresion.Variable;
import com.ast.statement.Function;
import com.byteReader.StreamReader;
import com.exceptions.parseException.ParseException;
import com.exceptions.parseException.TypeException;
import com.exceptions.parseException.UnexpectedToken;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    private String inputCode;
    private Parser parser;

    private Parser prepareParser(String testCode) {
        InputStream inputStream = new ByteArrayInputStream(testCode.getBytes());
        Parser parser =  new Parser(new StreamReader(inputStream));
        parser.testReadNextToken();
        return parser;
    }

    @Test
    void parseNumber() {
        inputCode = "2#1:3";
        parser = prepareParser(inputCode);
        try {
            NumberVariable numberVariable = parser.parseNumber();
            assertEquals(7, numberVariable.getNominator());
            assertEquals(3, numberVariable.getDenominator());
        } catch (ParseException exc) {
            fail(exc.getMessage());
        }

        inputCode = "1:3";
        parser = prepareParser(inputCode);
        try {
            NumberVariable numberVariable = parser.parseNumber();
            assertEquals(1, numberVariable.getNominator());
            assertEquals(3, numberVariable.getDenominator());
        } catch (ParseException exc) {
            fail(exc.getMessage());
        }

        inputCode = "12:0";
        parser = prepareParser(inputCode);
        try {
            NumberVariable numberVariable = parser.parseNumber();
        } catch (ParseException exc) {
            fail(exc.getMessage());
        } catch (ArithmeticException artExc) {
            //pass
        }


        inputCode = "21:1";
        parser = prepareParser(inputCode);
        try {
            NumberVariable numberVariable = parser.parseNumber();
            assertEquals(21, numberVariable.getNominator());
            assertEquals(1, numberVariable.getDenominator());
        } catch (ParseException exc) {
            fail(exc.getMessage());
        }

        inputCode = "8";
        parser = prepareParser(inputCode);
        try {
            NumberVariable numberVariable = parser.parseNumber();
            assertEquals(8, numberVariable.getNominator());
            assertEquals(1, numberVariable.getDenominator());
        } catch (ParseException exc) {
            fail(exc.getMessage());
        }

        inputCode = "74:185";
        parser = prepareParser(inputCode);
        try {
            NumberVariable numberVariable = parser.parseNumber();
            assertEquals(2, numberVariable.getNominator());
            assertEquals(5, numberVariable.getDenominator());
        } catch (ParseException exc) {
            fail(exc.getMessage());
        }
    }

    @Test
    void parseBool() {
        inputCode = "true";
        parser = prepareParser(inputCode);
        assertTrue(parser.parseBool().getValue());

        inputCode = "false";
        parser = prepareParser(inputCode);
        assertFalse(parser.parseBool().getValue());

    }

    @Test
    void parseString() {
        inputCode = "\"string\"";
        parser = prepareParser(inputCode);
        assertEquals("string", parser.parseString().getMessage());
    }

    @Test
    void acceptTokenTypeOrThrow() {
        inputCode = "\"string\"";
        parser = prepareParser(inputCode);
        try {
            parser.acceptTokenTypeOrThrow(Token.Type.string_expression_);
        } catch (ParseException exc) {
            fail(exc.getMessage());
        }
        inputCode = "\"string\"";
        parser = prepareParser(inputCode);
        try {
            parser.acceptTokenTypeOrThrow(Token.Type.false_);
        } catch (ParseException exc) {
            //pass
        }
    }

    @Test
    void acceptTokenTypeOrThrowList() {
        inputCode = "(";
        parser = prepareParser(inputCode);
        try {
            parser.acceptTokenTypeOrThrow(Arrays.asList(Token.Type.open_bracket_, Token.Type.open_scope_, Token.Type.semicolon_));
        } catch (ParseException exc) {
            fail(exc.getMessage());
        }
        inputCode = "{";
        parser = prepareParser(inputCode);
        try {
            parser.acceptTokenTypeOrThrow(Arrays.asList(Token.Type.open_bracket_, Token.Type.open_scope_, Token.Type.semicolon_));
        } catch (ParseException exc) {
            fail(exc.getMessage());
        }
        inputCode = ";";
        parser = prepareParser(inputCode);
        try {
            parser.acceptTokenTypeOrThrow(Arrays.asList(Token.Type.open_bracket_, Token.Type.open_scope_, Token.Type.semicolon_));
        } catch (ParseException exc) {
            fail(exc.getMessage());
        }

        inputCode = "[";
        parser = prepareParser(inputCode);
        try {
            parser.acceptTokenTypeOrThrow(Arrays.asList(Token.Type.open_bracket_, Token.Type.open_scope_, Token.Type.semicolon_));
        } catch (ParseException exc) {
            //pass
        }
    }

    @Test
    void acceptTokenTypeOrThrowListAndReadToken() {
        inputCode = "\"string\"";
        parser = prepareParser(inputCode);
        try {
            parser.acceptTokenTypeOrThrowAndReadToken(Token.Type.string_expression_);
        } catch (ParseException exc) {
            fail(exc.getMessage());
        }
        inputCode = "\"string\"";
        parser = prepareParser(inputCode);
        try {
            parser.acceptTokenTypeOrThrowAndReadToken(Token.Type.false_);
        } catch (ParseException exc) {
            //pass
        }
    }

    @Test
    void testReadNextToken() {
        inputCode = "aaaaa";
        parser = prepareParser(inputCode);
        assertEquals(Token.Type.identifier_,parser.testReadNextToken().getType());
    }

    @Test
    void testParseFunctionHeader() {
        inputCode = "void functionName();";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        Program program;
        try {
            program = parser.parse();
            assertNull(program.getFunction("notFunctionName"));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "void functionName();";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            program = parser.parse();
            assertFalse(program.getFunction("functionName").isDefined());
            assertEquals(0, program.getFunction("functionName").argumentsNames.size());
            assertEquals(Function.Return.void_, program.getFunction("functionName").getReturnType() );
            assertEquals(0, program.getFunction("functionName").innerStatements.size());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "number functionName(number a); ";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            program = parser.parse();
            assertFalse(program.getFunction("functionName").isDefined());
            assertEquals("a", program.getFunction("functionName").argumentsNames.get(0));
            assertEquals(Variable.Type.number_, program.getFunction("functionName").getVariable(program.getFunction("functionName").argumentsNames.get(0)).getType());
            assertEquals(Function.Return.number_, program.getFunction("functionName").getReturnType() );
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "bool functionName(bool b); ";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            program = parser.parse();
            assertFalse(program.getFunction("functionName").isDefined());
            assertEquals("b", program.getFunction("functionName").argumentsNames.get(0));
            assertEquals(Variable.Type.bool_, program.getFunction("functionName").getVariable(program.getFunction("functionName").argumentsNames.get(0)).getType());
            assertEquals(Function.Return.bool_, program.getFunction("functionName").getReturnType() );
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "string functionName(string c); ";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            program = parser.parse();
            assertFalse(program.getFunction("functionName").isDefined());
            assertEquals("c", program.getFunction("functionName").argumentsNames.get(0));
            assertEquals(Variable.Type.string_, program.getFunction("functionName").getVariable(program.getFunction("functionName").argumentsNames.get(0)).getType());
            assertEquals(Function.Return.string_, program.getFunction("functionName").getReturnType() );
        } catch (Exception exc) {
            fail(exc.getMessage());
        }
    }

    @Test
    void testParseFunctionHeaderError() {
        inputCode = "void functionName(;";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }

        inputCode = "functionName();";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }

        inputCode = "number functionName(a); ";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }

        inputCode = "bool functionName(bool); ";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }

        inputCode = "string functionName(string c)";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }
    }

    @Test
    void testParseFunction() {
        inputCode = "void main() {}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        Program program;
        try {
            program = parser.parse();
            assertTrue(program.getFunction("main").isDefined());
            assertEquals(Function.Return.void_, program.getFunction("main").getReturnType() );
            assertEquals(0, program.getFunction("main").argumentsNames.size());
            assertEquals(0, program.getFunction("main").innerStatements.size());
            assertEquals(0, program.getFunction("main").localVariables.size());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "void main(string string_, bool bool_, number number_) {}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            program = parser.parse();
            assertTrue(program.getFunction("main").isDefined());
            assertEquals(Function.Return.void_, program.getFunction("main").getReturnType() );
            assertEquals(3, program.getFunction("main").argumentsNames.size());
            assertEquals(0, program.getFunction("main").innerStatements.size());
            assertEquals(3, program.getFunction("main").localVariables.size());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }
    }

    @Test
    void testParseFunctionError() {
        inputCode = "void main() {";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }

        inputCode = "number main(string string_, bool bool_, number number_);" +
                "void main(string string_, bool bool_, number number_) {}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof TypeException))
                fail(exc.getMessage());
        }
    }

    @Test
    void testParseVariableDeclaration() {
        inputCode = "void main() {string word; number integer; bool isOk;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        Program program;

        try {
            program = parser.parse();
            assertTrue(program.getFunction("main").isDefined());
            assertEquals(Function.Return.void_, program.getFunction("main").getReturnType() );
            assertEquals(0, program.getFunction("main").argumentsNames.size());
            assertEquals(0, program.getFunction("main").innerStatements.size());
            assertEquals(3, program.getFunction("main").localVariables.size());
            assertEquals(Variable.Type.string_,program.getFunction("main").localVariables.get("word").getType());
            assertEquals(Variable.Type.number_,program.getFunction("main").localVariables.get("integer").getType());
            assertEquals(Variable.Type.bool_,program.getFunction("main").localVariables.get("isOk").getType());
            assertNull(program.getFunction("main").localVariables.get("notVariable"));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }
    }

    @Test
    void testParseVariableDeclarationError() {
        inputCode = "void main() {number a}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }

        inputCode = "void main() {number a = 1;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }

        inputCode = "void main() {int a;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }
    }
}