package com.ast.expression;

import com.exceptions.executionExceptions.ConflictException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoolVariableTest {

    @Test
    void not() {
        try {
            BoolVariable boolVariable = new BoolVariable();
            boolVariable.setValue(true);
            BoolVariable boolVariable1 = boolVariable.not();
            assertTrue(boolVariable.getValue());
            assertFalse(boolVariable1.getValue());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            BoolVariable boolVariable = new BoolVariable();
            boolVariable.setValue(false);
            BoolVariable boolVariable1 = boolVariable.not();
            assertFalse(boolVariable.getValue());
            assertTrue(boolVariable1.getValue());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }
    }

    @Test
    void or() {
        try {
            BoolVariable boolVariable = new BoolVariable(false);
            BoolVariable boolVariable1 = new BoolVariable(false);
            assertFalse(boolVariable.or(boolVariable1).getValue());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            BoolVariable boolVariable = new BoolVariable(true);
            BoolVariable boolVariable1 = new BoolVariable(false);
            assertTrue(boolVariable.or(boolVariable1).getValue());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            BoolVariable boolVariable = new BoolVariable(false);
            BoolVariable boolVariable1 = new BoolVariable(true);
            assertTrue(boolVariable.or(boolVariable1).getValue());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            BoolVariable boolVariable = new BoolVariable(true);
            BoolVariable boolVariable1 = new BoolVariable(true);
            assertTrue(boolVariable.or(boolVariable1).getValue());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }
    }

    @Test
    void and() {
        try {
            BoolVariable boolVariable = new BoolVariable(false);
            BoolVariable boolVariable1 = new BoolVariable(false);
            assertFalse(boolVariable.and(boolVariable1).getValue());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            BoolVariable boolVariable = new BoolVariable(true);
            BoolVariable boolVariable1 = new BoolVariable(false);
            assertFalse(boolVariable.and(boolVariable1).getValue());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            BoolVariable boolVariable = new BoolVariable(false);
            BoolVariable boolVariable1 = new BoolVariable(true);
            assertFalse(boolVariable.and(boolVariable1).getValue());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            BoolVariable boolVariable = new BoolVariable(true);
            BoolVariable boolVariable1 = new BoolVariable(true);
            assertTrue(boolVariable.and(boolVariable1).getValue());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }
    }

    @Test
    void setValue() {
        BoolVariable boolVariable;
        try {
            boolVariable = new BoolVariable(true);
            BoolVariable boolVariable1 = new BoolVariable(false);
            assertNotEquals(boolVariable.getValue(), boolVariable1.getValue());
            boolVariable.setValue(boolVariable1);
            assertEquals(boolVariable.getValue(), boolVariable1.getValue());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            boolVariable = new BoolVariable();
            NumberVariable numberVariable = new NumberVariable();
            boolVariable.setValue(numberVariable);
            fail("Expected exception");
        } catch (Exception exc) {
            if (!(exc instanceof ConflictException))
                fail(exc.getMessage());
        }
    }
}