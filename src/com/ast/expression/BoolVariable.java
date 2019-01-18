package com.ast.expression;

import com.exceptions.executionExceptions.ConflictException;
import com.exceptions.executionExceptions.ExecutionException;

public class BoolVariable extends Variable {
    private boolean value = false;

    public BoolVariable() {
        type = Type.bool_;
    }

    public BoolVariable(boolean value) {
        type = Type.bool_;
        this.value = value;
    }

    public BoolVariable(BoolVariable boolVariable) {
        type = Type.bool_;
        this.value = boolVariable.value;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    BoolVariable not() {
        return new BoolVariable(!this.value);
    }

    BoolVariable or(BoolVariable component) {
        return new BoolVariable(this.value || component.value);
    }

    BoolVariable and(BoolVariable component) {
        return new BoolVariable(this.value && component.value);
    }

    @Override
    public void print() {
        System.out.print(value);
    }

    @Override
    public void setValue(Variable value) throws ExecutionException {
        if (!(value instanceof BoolVariable))
            throw new ConflictException("BoolVariable.setValue()", "number variable", "assigned value");
        BoolVariable boolVariable = (BoolVariable) value;
        this.value = boolVariable.value;
    }
}
