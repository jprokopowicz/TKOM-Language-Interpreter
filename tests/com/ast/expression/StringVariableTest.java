package com.ast.expression;

import com.exceptions.executionExceptions.ConflictException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringVariableTest {

    @Test
    void setValue() {
        StringVariable stringVariable;
        try {
            stringVariable = new StringVariable("test");
            StringVariable stringVariable1 = new StringVariable("test not");
            assertNotEquals(stringVariable.getMessage(), stringVariable1.getMessage());
            stringVariable.setValue(stringVariable1);
            assertEquals(stringVariable.getMessage(), stringVariable1.getMessage());
        } catch (Exception exc) {
            fail(exc.getMessage());
        }

        try {
            stringVariable = new StringVariable();
            NumberVariable numberVariable = new NumberVariable();
            stringVariable.setValue(numberVariable);
            fail("Expected exception");
        } catch (Exception exc) {
            if (!(exc instanceof ConflictException))
                fail(exc.getMessage());
        }
    }
}