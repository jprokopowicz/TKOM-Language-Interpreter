package com.ast.expression;

import com.exceptions.executionExceptions.ArithmeticException;
import com.exceptions.executionExceptions.ConflictException;
import com.exceptions.executionExceptions.ExecutionException;
import com.exceptions.executionExceptions.InputOutputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberVariableTest {

    @Test
    void setValue() {
        NumberVariable numberVariable;

        numberVariable = new NumberVariable();
        numberVariable.setValue(0);
        assertEquals(0, numberVariable.getNominator());
        assertEquals(1, numberVariable.getDenominator());

        numberVariable = new NumberVariable();
        numberVariable.setValue(1);
        assertEquals(1, numberVariable.getNominator());
        assertEquals(1, numberVariable.getDenominator());

        numberVariable = new NumberVariable();
        numberVariable.setValue(1, 2);
        assertEquals(1, numberVariable.getNominator());
        assertEquals(2, numberVariable.getDenominator());

        numberVariable = new NumberVariable();
        numberVariable.setValue(1, -2);
        assertEquals(-1, numberVariable.getNominator());
        assertEquals(2, numberVariable.getDenominator());

        numberVariable = new NumberVariable();
        numberVariable.setValue(5, 3);
        assertEquals(5, numberVariable.getNominator());
        assertEquals(3, numberVariable.getDenominator());

        numberVariable = new NumberVariable();
        numberVariable.setValue(24, 3);
        assertEquals(8, numberVariable.getNominator());
        assertEquals(1, numberVariable.getDenominator());

        numberVariable = new NumberVariable();
        numberVariable.setValue(0, 3);
        assertEquals(0, numberVariable.getNominator());
        assertEquals(1, numberVariable.getDenominator());

        numberVariable = new NumberVariable();
        numberVariable.setValue(1, 2, 3);
        assertEquals(5, numberVariable.getNominator());
        assertEquals(3, numberVariable.getDenominator());

        numberVariable = new NumberVariable();
        numberVariable.setValue(1, 5, 3);
        assertEquals(8, numberVariable.getNominator());
        assertEquals(3, numberVariable.getDenominator());

        try {
            numberVariable = new NumberVariable(1, 2);
            NumberVariable numberVariable1 = new NumberVariable(43, 21);
            assertFalse(numberVariable.equal(numberVariable1));
            numberVariable.setValue(numberVariable1);
            assertTrue(numberVariable.equal(numberVariable1));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            numberVariable = new NumberVariable();
            BoolVariable boolVariable = new BoolVariable();
            numberVariable.setValue(boolVariable);
            fail("Expected exception");
        } catch (Exception exc) {
            if (!(exc instanceof ConflictException))
                fail(exc.getMessage());
        }
    }

    @Test
    void negate() {
        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(0, 1);
            NumberVariable negatedNumberVariable = numberVariable.negate();
            assertEquals(numberVariable.getNominator(), negatedNumberVariable.getNominator());
            assertEquals(negatedNumberVariable.getDenominator(), negatedNumberVariable.getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(1, 1);
            NumberVariable negatedNumberVariable = numberVariable.negate();
            assertEquals(-numberVariable.getNominator(), negatedNumberVariable.getNominator());
            assertEquals(negatedNumberVariable.getDenominator(), negatedNumberVariable.getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable negatedNumberVariable = numberVariable.negate();
            assertEquals(-3, negatedNumberVariable.getNominator());
            assertEquals(1, negatedNumberVariable.getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(0, 0);
            numberVariable.negate();
            fail("Exception expected");
        } catch (Exception exc) {
            if (!(exc instanceof ArithmeticException))
                fail(exc.getMessage());
        }
    }

    @Test
    void plus() {
        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(4, 1);
            assertEquals(7, numberVariable.plus(numberVariable1).getNominator());
            assertEquals(1, numberVariable.plus(numberVariable1).getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(3, 4);
            assertEquals(15, numberVariable.plus(numberVariable1).getNominator());
            assertEquals(4, numberVariable.plus(numberVariable1).getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(0, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(3, 4);
            assertEquals(3, numberVariable.plus(numberVariable1).getNominator());
            assertEquals(4, numberVariable.plus(numberVariable1).getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }
    }

    @Test
    void minus() {
        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(4, 1);
            assertEquals(-1, numberVariable.minus(numberVariable1).getNominator());
            assertEquals(1, numberVariable.minus(numberVariable1).getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(3, 4);
            assertEquals(9, numberVariable.minus(numberVariable1).getNominator());
            assertEquals(4, numberVariable.minus(numberVariable1).getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(0, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(3, 4);
            assertEquals(-3, numberVariable.minus(numberVariable1).getNominator());
            assertEquals(4, numberVariable.minus(numberVariable1).getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }
    }

    @Test
    void multiply() {
        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(4, 1);
            assertEquals(12, numberVariable.multiply(numberVariable1).getNominator());
            assertEquals(1, numberVariable.multiply(numberVariable1).getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(3, 4);
            assertEquals(9, numberVariable.multiply(numberVariable1).getNominator());
            assertEquals(4, numberVariable.multiply(numberVariable1).getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(0, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(3, 4);
            assertEquals(0, numberVariable.multiply(numberVariable1).getNominator());
            assertEquals(1, numberVariable.multiply(numberVariable1).getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }
    }

    @Test
    void dev() {
        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(4, 1);
            assertEquals(3, numberVariable.dev(numberVariable1).getNominator());
            assertEquals(4, numberVariable.dev(numberVariable1).getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(3, 4);
            assertEquals(4, numberVariable.dev(numberVariable1).getNominator());
            assertEquals(1, numberVariable.dev(numberVariable1).getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 4);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(0, 1);
            numberVariable.dev(numberVariable1);
            fail("Exception expected");
        } catch (Exception exc) {
            if (!(exc instanceof ArithmeticException))
                fail(exc.getMessage());
        }
    }

    @Test
    void mod() {
        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(4, 1);
            assertEquals(3, numberVariable.mod(numberVariable1).getNominator());
            assertEquals(1, numberVariable.mod(numberVariable1).getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(2, 1);
            assertEquals(1, numberVariable.mod(numberVariable1).getNominator());
            assertEquals(1, numberVariable.mod(numberVariable1).getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(3, 4);
            assertEquals(3, numberVariable.mod(numberVariable1).getNominator());
            assertEquals(4, numberVariable.mod(numberVariable1).getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(2, 3);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(3, 1);
            assertEquals(3, numberVariable.mod(numberVariable1).getNominator());
            assertEquals(1, numberVariable.mod(numberVariable1).getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }
    }

    @Test
    void equal() {
        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            assertTrue(numberVariable.equal(numberVariable));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(4, 3);
            assertFalse(numberVariable.equal(numberVariable1));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 2);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(3, 2);
            assertTrue(numberVariable.equal(numberVariable1));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(4, 0);
            numberVariable.equal(numberVariable1);
            fail("Exception expected");
        } catch (Exception exc) {
            if (!(exc instanceof ArithmeticException))
                fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 4);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(3, 2);
            NumberVariable numberVariable2 = numberVariable.negate();
            numberVariable2.setValue(4, 2);
            NumberVariable numberVariable3 = numberVariable1.dev(numberVariable2);
            assertTrue(numberVariable.equal(numberVariable3));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }
    }

    @Test
    void notEqual() {
        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            assertFalse(numberVariable.notEqual(numberVariable));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(4, 3);
            assertTrue(numberVariable.notEqual(numberVariable1));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 2);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(3, 2);
            assertFalse(numberVariable.notEqual(numberVariable1));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(4, 0);
            numberVariable.notEqual(numberVariable1);
            fail("Exception expected");
        } catch (Exception exc) {
            if (!(exc instanceof ArithmeticException))
                fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 4);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(3, 2);
            NumberVariable numberVariable2 = numberVariable.negate();
            numberVariable2.setValue(4, 2);
            NumberVariable numberVariable3 = numberVariable1.dev(numberVariable2);
            assertFalse(numberVariable.notEqual(numberVariable3));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }
    }

    @Test
    void greater() {
        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(3, 1);
            assertFalse(numberVariable.greater(numberVariable1));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(4, 3);
            assertTrue(numberVariable.greater(numberVariable1));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(0, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(4, 3);
            assertFalse(numberVariable.greater(numberVariable1));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(0, 1);
            assertTrue(numberVariable.greater(numberVariable1));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(4, 0);
            numberVariable.notEqual(numberVariable1);
            fail("ExpectedException");
        } catch (Exception exc) {
            if (!(exc instanceof ArithmeticException))
                fail(exc.getMessage());
        }
    }

    @Test
    void greaterOrEqual() {
        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(3, 1);
            assertTrue(numberVariable.greaterOrEqual(numberVariable1));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(4, 3);
            assertTrue(numberVariable.greaterOrEqual(numberVariable1));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(0, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(4, 3);
            assertFalse(numberVariable.greaterOrEqual(numberVariable1));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(0, 1);
            assertTrue(numberVariable.greaterOrEqual(numberVariable1));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(4, 0);
            numberVariable.greaterOrEqual(numberVariable1);
            fail("ExpectedException");
        } catch (Exception exc) {
            if (!(exc instanceof ArithmeticException))
                fail(exc.getMessage());
        }
    }

    @Test
    void lesser() {
        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(3, 1);
            assertFalse(numberVariable.lesser(numberVariable1));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(4, 3);
            assertFalse(numberVariable.lesser(numberVariable1));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(0, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(4, 3);
            assertTrue(numberVariable.lesser(numberVariable1));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(0, 1);
            assertFalse(numberVariable.lesser(numberVariable1));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(4, 0);
            numberVariable.lesser(numberVariable1);
            fail("ExpectedException");
        } catch (Exception exc) {
            if (!(exc instanceof ArithmeticException))
                fail(exc.getMessage());
        }
    }

    @Test
    void lesserOrEqual() {
        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(3, 1);
            assertTrue(numberVariable.lesserOrEqual(numberVariable1));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(4, 3);
            assertFalse(numberVariable.lesserOrEqual(numberVariable1));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(0, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(4, 3);
            assertTrue(numberVariable.lesserOrEqual(numberVariable1));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(0, 1);
            assertFalse(numberVariable.lesserOrEqual(numberVariable1));
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            NumberVariable numberVariable = new NumberVariable();
            numberVariable.setValue(3, 1);
            NumberVariable numberVariable1 = numberVariable.negate();
            numberVariable1.setValue(4, 0);
            numberVariable.lesserOrEqual(numberVariable1);
            fail("ExpectedException");
        } catch (Exception exc) {
            if (!(exc instanceof ArithmeticException))
                fail(exc.getMessage());
        }
    }

    @Test
    void parseNumber() {
        String input;
        try {
            input = "0";
            NumberVariable numberVariable = NumberVariable.parseNumber(input);
            assertEquals(0, numberVariable.getNominator());
            assertEquals(1, numberVariable.getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            input = "1";
            NumberVariable numberVariable = NumberVariable.parseNumber(input);
            assertEquals(1, numberVariable.getNominator());
            assertEquals(1, numberVariable.getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            input = "2:3";
            NumberVariable numberVariable = NumberVariable.parseNumber(input);
            assertEquals(2, numberVariable.getNominator());
            assertEquals(3, numberVariable.getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            input = "3:1";
            NumberVariable numberVariable = NumberVariable.parseNumber(input);
            assertEquals(3, numberVariable.getNominator());
            assertEquals(1, numberVariable.getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            input = "4:2";
            NumberVariable numberVariable = NumberVariable.parseNumber(input);
            assertEquals(2, numberVariable.getNominator());
            assertEquals(1, numberVariable.getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            input = "360:12";
            NumberVariable numberVariable = NumberVariable.parseNumber(input);
            assertEquals(30, numberVariable.getNominator());
            assertEquals(1, numberVariable.getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            input = "1#2:3";
            NumberVariable numberVariable = NumberVariable.parseNumber(input);
            assertEquals(5, numberVariable.getNominator());
            assertEquals(3, numberVariable.getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            input = "2#3:2";
            NumberVariable numberVariable = NumberVariable.parseNumber(input);
            assertEquals(7, numberVariable.getNominator());
            assertEquals(2, numberVariable.getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            input = "0#1:2";
            NumberVariable numberVariable = NumberVariable.parseNumber(input);
            assertEquals(1, numberVariable.getNominator());
            assertEquals(2, numberVariable.getDenominator());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            input = "1#2#3";
            NumberVariable.parseNumber(input);
            fail("Exception expected");
        } catch (Exception exc) {
            if (!(exc instanceof InputOutputException))
                fail(exc.getMessage());
        }

        try {
            input = "2#3";
            NumberVariable.parseNumber(input);
            fail("Exception expected");
        } catch (Exception exc) {
            if (!(exc instanceof InputOutputException))
                fail(exc.getMessage());
        }

        try {
            input = "0:2:3";
            NumberVariable.parseNumber(input);
            fail("Exception expected");
        } catch (Exception exc) {
            if (!(exc instanceof InputOutputException))
                fail(exc.getMessage());
        }

        try {
            input = "1.25";
            NumberVariable.parseNumber(input);
            fail("Exception expected");
        } catch (Exception exc) {
            if (!(exc instanceof InputOutputException))
                fail(exc.getMessage());
        }

        try {
            input = "2#3:f";
            NumberVariable.parseNumber(input);
            fail("Exception expected");
        } catch (Exception exc) {
            if (!(exc instanceof InputOutputException))
                fail(exc.getMessage());
        }
    }
}