package com.ast.expression;


import com.ast.Program;
import com.ast.statement.ValueAssignment;
import com.byteReader.StreamReader;
import com.exceptions.parseException.TypeException;
import com.exceptions.parseException.UnexpectedToken;
import com.exceptions.parseException.UnknownNameException;
import com.interpreterParts.Parser;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

class ParserExpressionTest {
    private String inputCode;
    private Parser parser;

    @Test
    void parseNumberVariableExpression() {
        inputCode = "void main() {number a; number b; a = b;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertTrue(((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content instanceof VariableCall);
            assertEquals("b",((VariableCall)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).variableName);
            assertEquals("a",(((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getTargetName()));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }
    }

    @Test
    void parseNumberVariableExpressionError() {
        inputCode = "void main() {number a; a = b;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof UnknownNameException))
                fail(exc.getMessage());
        }
    }

    @Test
    void parseNumberValueExpression() {
        inputCode = "void main() {number a; a = 1;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertFalse(((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).negate);
            assertEquals(1,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getNominator());
            assertEquals(1,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "void main() {number a; a = -1;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertTrue(((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).negate);
            assertEquals(1,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getNominator());
            assertEquals(1,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "void main() {number a; a = 1:2;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertFalse(((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).negate);
            assertEquals(1,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getNominator());
            assertEquals(2,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "void main() {number a; a = -2:4;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertTrue(((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).negate);
            assertEquals(1,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getNominator());
            assertEquals(2,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "void main() {number a; a = 1#2:3;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertFalse(((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).negate);
            assertEquals(5,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getNominator());
            assertEquals(3,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "void main() {number a; a = -1#2:3;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertTrue(((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).negate);
            assertEquals(5,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getNominator());
            assertEquals(3,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "void main() {number a; a = -12:3;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertEquals(4,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getNominator());
            assertTrue(((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).negate);
            assertEquals(1,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "void main() {number a; a = -12:3 + 2;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertTrue(((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).negate);
            assertEquals(4,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getNominator());
            assertEquals(1,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getDenominator());

            assertFalse(((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(1).basicMathExpressions.get(0).negate);
            assertEquals(2,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(1).basicMathExpressions.get(0).content).getNominator());
            assertEquals(1,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(1).basicMathExpressions.get(0).content).getDenominator());

            assertEquals(MathExpression.AdditionOperator.add,((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).additionOperators.get(0));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "void main() {number a; a = -1:3 - 2;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertTrue(((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).negate);
            assertEquals(1,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getNominator());
            assertEquals(3,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getDenominator());

            assertFalse(((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(1).basicMathExpressions.get(0).negate);
            assertEquals(2,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(1).basicMathExpressions.get(0).content).getNominator());
            assertEquals(1,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(1).basicMathExpressions.get(0).content).getDenominator());

            assertEquals(MathExpression.AdditionOperator.subtract,((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).additionOperators.get(0));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "void main() {number a; a = -1:3 - - 2;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertTrue(((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).negate);
            assertEquals(1,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getNominator());
            assertEquals(3,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getDenominator());

            assertTrue(((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(1).basicMathExpressions.get(0).negate);
            assertEquals(2,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(1).basicMathExpressions.get(0).content).getNominator());
            assertEquals(1,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(1).basicMathExpressions.get(0).content).getDenominator());

            assertEquals(MathExpression.AdditionOperator.subtract,((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).additionOperators.get(0));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "void main() {number a; a = -12:3 * 5;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertTrue(((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).negate);
            assertEquals(4,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getNominator());
            assertEquals(1,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getDenominator());

            assertFalse(((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(1).negate);
            assertEquals(5,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(1).content).getNominator());
            assertEquals(1,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(1).content).getDenominator());

            assertEquals(MultiplicationExpression.MultiplicationOperator.multiply,((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).multiplicationOperators.get(0));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "void main() {number a; a = -12:3 / 3:2;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertTrue(((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).negate);
            assertEquals(4,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getNominator());
            assertEquals(1,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getDenominator());

            assertFalse(((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(1).negate);
            assertEquals(3,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(1).content).getNominator());
            assertEquals(2,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(1).content).getDenominator());

            assertEquals(MultiplicationExpression.MultiplicationOperator.divide,((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).multiplicationOperators.get(0));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "void main() {number a; a = 5 % 3 ;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertFalse(((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).negate);
            assertEquals(5,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getNominator());
            assertEquals(1,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getDenominator());

            assertFalse(((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(1).negate);
            assertEquals(3,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(1).content).getNominator());
            assertEquals(1,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(1).content).getDenominator());

            assertEquals(MultiplicationExpression.MultiplicationOperator.modulo,((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).multiplicationOperators.get(0));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }
    }

    @Test
    void parseNumberValueExpressionError() {
        inputCode = "void main() {number a; a = 1:;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }

        inputCode = "void main() {number a; a = 1#;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }

        inputCode = "void main() {number a; a = 1.2:;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }

        inputCode = "void main() {number a; a = 1:;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }

        inputCode = "void main() {number a; a = 1:-2;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }
    }

    @Test
    void parseBracketExpression() {
        inputCode = "void main() {number a; a = a * (1:2 + 4:5 * 3);}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertTrue(((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(1).content instanceof BracketExpression);

            assertEquals(1,((NumberVariable)((BracketExpression)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(1).content)
                    .content.multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getNominator());
            assertEquals(2,((NumberVariable)((BracketExpression)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(1).content)
                    .content.multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getDenominator());
            assertEquals(MathExpression.AdditionOperator.add,((BracketExpression)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(1).content)
                    .content.additionOperators.get(0));

            assertEquals(4,((NumberVariable)((BracketExpression)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(1).content)
                    .content.multiplicationExpressions.get(1).basicMathExpressions.get(0).content).getNominator());
            assertEquals(5,((NumberVariable)((BracketExpression)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(1).content)
                    .content.multiplicationExpressions.get(1).basicMathExpressions.get(0).content).getDenominator());

            assertEquals(3,((NumberVariable)((BracketExpression)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(1).content)
                    .content.multiplicationExpressions.get(1).basicMathExpressions.get(1).content).getNominator());
            assertEquals(1,((NumberVariable)((BracketExpression)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(1).content)
                    .content.multiplicationExpressions.get(1).basicMathExpressions.get(1).content).getDenominator());

            assertEquals(MultiplicationExpression.MultiplicationOperator.multiply,((BracketExpression)((MathExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).multiplicationExpressions.get(0).basicMathExpressions.get(1).content)
                    .content.multiplicationExpressions.get(1).multiplicationOperators.get(0));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }
    }

    @Test
    void parseBracketExpressionError() {
        inputCode = "void main() {number a; a = a * (1:2 + 4:5 * 3;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }
    }

    @Test
    void parseBoolVariableExpression() {
        inputCode = "void main() {bool a; bool b; a = b;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertEquals("b", ((VariableCall)((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0))
                    .getValue()).conjunctionExpressions.get(0).basicBoolExpressions.get(0).content).variableName);
        } catch (Exception exc) {
            fail(exc.getMessage());
        }
    }

    @Test
    void parseBoolVariableExpressionError() {
        inputCode = "void main() {bool a; a = b;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof UnknownNameException))
                fail(exc.getMessage());
        }

        inputCode = "void main() {bool a; number b; a = b;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof TypeException))
                fail(exc.getMessage());
        }
    }

    @Test
    void parseBoolValueExpression() {
        inputCode = "void main() { bool a; a = true; }";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertTrue(((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue() instanceof BooleanExpression);
            assertTrue(((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(0).content instanceof BoolVariable);
            assertTrue(((BoolVariable)((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(0).content).getValue());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "void main() { bool a; a = false; }";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertTrue(((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue() instanceof BooleanExpression);
            assertTrue(((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(0).content instanceof BoolVariable);
            assertFalse(((BoolVariable)((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(0).content).getValue());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "void main() { bool a; a = true | false; }";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertTrue(((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue() instanceof BooleanExpression);
            assertTrue(((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(0).content instanceof BoolVariable);
            assertTrue(((BoolVariable)((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(0).content).getValue());
            assertTrue(((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(1).basicBoolExpressions.get(0).content instanceof BoolVariable);
            assertFalse(((BoolVariable)((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(1).basicBoolExpressions.get(0).content).getValue());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "void main() { bool a; a = true & !false ; }";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertTrue(((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue() instanceof BooleanExpression);
            assertTrue(((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(0).content instanceof BoolVariable);
            assertTrue(((BoolVariable)((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(0).content).getValue());
            assertTrue(((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(1).content instanceof BoolVariable);
            assertTrue(((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(1).not);
            assertFalse(((BoolVariable)((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(1).content).getValue());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "void main() { bool a; a = true | a; }";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertTrue(((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue() instanceof BooleanExpression);
            assertTrue(((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(0).content instanceof BoolVariable);
            assertTrue(((BoolVariable)((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(0).content).getValue());
            assertTrue(((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(1).basicBoolExpressions.get(0).content instanceof VariableCall);
            assertEquals("a",((VariableCall)((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(1).basicBoolExpressions.get(0).content).variableName);
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "void main() { bool a; a = true & a; }";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertTrue(((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue() instanceof BooleanExpression);
            assertTrue(((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(0).content instanceof BoolVariable);
            assertTrue(((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(1).content instanceof VariableCall);
            assertEquals("a",((VariableCall)((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(1).content).variableName);

        } catch (Exception exc) {
            fail(exc.getMessage());
        }
    }

    @Test
    void parseBoolValueExpressionError() {
        inputCode = "void main() { bool a = false }";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if (!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }

        inputCode = "void main() { bool a = 1 }";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if (!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }

        inputCode = "void main() { bool a = \"test\" }";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if (!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }
    }

    @Test
    void parseComparisonExpression() {
        inputCode = "void main() { bool a; a = [1 == 2] ;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertTrue(((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue() instanceof BooleanExpression);
            assertTrue(((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(0).content instanceof ComparisonExpression);
            assertEquals(ComparisonExpression.ComparisonOperator.equal,((ComparisonExpression)((BooleanExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).conjunctionExpressions.get(0).basicBoolExpressions.get(0).content).operator);
            assertTrue(((ComparisonExpression)((BooleanExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).conjunctionExpressions.get(0).basicBoolExpressions.get(0).content).left.multiplicationExpressions.get(0)
                    .basicMathExpressions.get(0).content instanceof NumberVariable);
            assertTrue(((ComparisonExpression)((BooleanExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).conjunctionExpressions.get(0).basicBoolExpressions.get(0).content).right.multiplicationExpressions.get(0)
                    .basicMathExpressions.get(0).content instanceof NumberVariable);
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "void main() { bool a; a = [1 != 2] ;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertTrue(((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue() instanceof BooleanExpression);
            assertTrue(((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(0).content instanceof ComparisonExpression);
            assertEquals(ComparisonExpression.ComparisonOperator.notEqual,((ComparisonExpression)((BooleanExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).conjunctionExpressions.get(0).basicBoolExpressions.get(0).content).operator);
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "void main() { bool a; a = [1 < 2] ;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertTrue(((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue() instanceof BooleanExpression);
            assertTrue(((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(0).content instanceof ComparisonExpression);
            assertEquals(ComparisonExpression.ComparisonOperator.lesser,((ComparisonExpression)((BooleanExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).conjunctionExpressions.get(0).basicBoolExpressions.get(0).content).operator);
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "void main() { bool a; a = [1 <= 2] ;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertTrue(((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue() instanceof BooleanExpression);
            assertTrue(((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(0).content instanceof ComparisonExpression);
            assertEquals(ComparisonExpression.ComparisonOperator.lesserOrEqual,((ComparisonExpression)((BooleanExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).conjunctionExpressions.get(0).basicBoolExpressions.get(0).content).operator);
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "void main() { bool a; a = [1 > 2] ;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertTrue(((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue() instanceof BooleanExpression);
            assertTrue(((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(0).content instanceof ComparisonExpression);
            assertEquals(ComparisonExpression.ComparisonOperator.greater,((ComparisonExpression)((BooleanExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).conjunctionExpressions.get(0).basicBoolExpressions.get(0).content).operator);
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "void main() { bool a; a = [1 >= 2] ;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertTrue(((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue() instanceof BooleanExpression);
            assertTrue(((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(0).content instanceof ComparisonExpression);
            assertEquals(ComparisonExpression.ComparisonOperator.greaterOrEqual,((ComparisonExpression)((BooleanExpression)((ValueAssignment)program.getFunction("main")
                    .innerStatements.get(0)).getValue()).conjunctionExpressions.get(0).basicBoolExpressions.get(0).content).operator);
        } catch (Exception exc) {
            fail(exc.getMessage());
        }
    }

    @Test
    void parseComparisonExpressionError() {
        inputCode = "void main() { bool a = 1 < 2 }";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if (!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }

        inputCode = "void main() { bool a = [ 1 <=> 2 ] }";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if (!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }

        inputCode = "void main() { number a = [ 1 <= 2 ] }";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if (!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }
    }

    @Test
    void parseBoolBracketExpression() {
        inputCode = "void main() { bool a; a = a & ( a | true );}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertTrue(((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(1).content instanceof BooleanBracketExpression);
            assertTrue(((BooleanBracketExpression)((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(1).content).content.conjunctionExpressions.get(0).basicBoolExpressions.get(0).content instanceof VariableCall);
            assertEquals("a",((VariableCall)((BooleanBracketExpression)((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(1).content).content.conjunctionExpressions.get(0).basicBoolExpressions.get(0).content).variableName);
            assertTrue(((BooleanBracketExpression)((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(1).content).content.conjunctionExpressions.get(1).basicBoolExpressions.get(0).content instanceof BoolVariable);
            assertTrue(((BoolVariable)((BooleanBracketExpression)((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(1).content).content.conjunctionExpressions.get(1).basicBoolExpressions.get(0).content).getValue());
        } catch (Exception exc) {
            exc.printStackTrace();
            fail(exc.getMessage());
        }
    }

    @Test
    void parseBoolBracketExpressionError() {
        inputCode = "void main() { bool a; a = a & ( a | true ;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }

        inputCode = "void main() { bool a; a = a & ( a | true ));}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }

        inputCode = "void main() { bool a; number c; a = a & ( a | c ));}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof TypeException))
                fail(exc.getMessage());
        }

        inputCode = "void main() { bool a; number c; a = a & ( 1:2 + c ));}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }

        inputCode = "void main() { bool a; number c; a = a & ( c + 1 ));}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof TypeException))
                fail(exc.getMessage());
        }
    }

    @Test
    void parseStringExpression() {
        inputCode = "void main() {string c; c = \"test\";}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertEquals("test", ((StringVariable)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue()).getMessage());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "void main() {string a;a = \"test\"; string b; b = a;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertEquals("a", ((VariableCall)((ValueAssignment)program.getFunction("main").innerStatements.get(1)).getValue()).variableName);
            assertEquals("b", (((ValueAssignment)program.getFunction("main").innerStatements.get(1)).getTargetName()));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }
    }

    @Test
    void parseStringExpressionError() {
        inputCode = "void main() {string c; c = 1;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }

        inputCode = "void main() {string c; c = d;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof UnknownNameException))
                fail(exc.getMessage());
        }

        inputCode = "void main() {string c; c = \"abc\" + \"d\";}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }

        inputCode = "void main() {string c; c = 1 + \"a\";}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }
    }

    @Test
    void parseFunctionCallExpression() {
        inputCode = "number one(){return 1;} void main() {number a; a = one();}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertTrue(((MathExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
            .multiplicationExpressions.get(0).basicMathExpressions.get(0).content instanceof FunctionCallExpression);
            assertEquals("one",((FunctionCallExpression)((MathExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .multiplicationExpressions.get(0).basicMathExpressions.get(0).content).functionName);
            assertEquals(0,((FunctionCallExpression)((MathExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .multiplicationExpressions.get(0).basicMathExpressions.get(0).content).arguments.size());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "number this(number a){return a;} void main() {number a; a = this(1);}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertTrue(((MathExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .multiplicationExpressions.get(0).basicMathExpressions.get(0).content instanceof FunctionCallExpression);
            assertEquals("this",((FunctionCallExpression)((MathExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .multiplicationExpressions.get(0).basicMathExpressions.get(0).content).functionName);
            assertEquals(1,((FunctionCallExpression)((MathExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .multiplicationExpressions.get(0).basicMathExpressions.get(0).content).arguments.size());
            assertEquals(1,((NumberVariable)((MathExpression)((FunctionCallExpression)((MathExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .multiplicationExpressions.get(0).basicMathExpressions.get(0).content).arguments.get(0)).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getNominator());
            assertEquals(1,((NumberVariable)((MathExpression)((FunctionCallExpression)((MathExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .multiplicationExpressions.get(0).basicMathExpressions.get(0).content).arguments.get(0)).multiplicationExpressions.get(0).basicMathExpressions.get(0).content).getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "number this(number a){return a;} void main() {number a; a = this(1) + 2:3;}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertTrue(((MathExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .multiplicationExpressions.get(0).basicMathExpressions.get(0).content instanceof FunctionCallExpression);
            assertEquals("this",((FunctionCallExpression)((MathExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .multiplicationExpressions.get(0).basicMathExpressions.get(0).content).functionName);
            assertEquals(2,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .multiplicationExpressions.get(1).basicMathExpressions.get(0).content).getNominator());
            assertEquals(3,((NumberVariable)((MathExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .multiplicationExpressions.get(1).basicMathExpressions.get(0).content).getDenominator());
} catch (Exception exc) {
            fail(exc.getMessage());
        }

        inputCode = "bool one(){return false;} void main() {bool a; a = one();}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            Program program = parser.parse();
            assertTrue(((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(0).content instanceof FunctionCallExpression);
            assertEquals("one",((FunctionCallExpression)((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(0).content).functionName);
            assertEquals(0,((FunctionCallExpression)((BooleanExpression)((ValueAssignment)program.getFunction("main").innerStatements.get(0)).getValue())
                    .conjunctionExpressions.get(0).basicBoolExpressions.get(0).content).arguments.size());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }
    }

    @Test
    void parseFunctionCallExpressionError() {
        inputCode = "bool one(){return true;} void main() {number a; a = one();}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof TypeException))
                fail(exc.getMessage());
        }

        inputCode = "number one(){return 1;} void main() {number a; a = one(a);}";
        parser = new Parser(new StreamReader(new ByteArrayInputStream(inputCode.getBytes())));
        try {
            parser.parse();
        } catch (Exception exc) {
            if(!(exc instanceof UnexpectedToken))
                fail(exc.getMessage());
        }
    }
}