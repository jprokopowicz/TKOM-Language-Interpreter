package com.ast.expresion;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

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
    //Operators
    public BoolVariable not() {
        return new BoolVariable(!this.value);
    }

    public BoolVariable or(BoolVariable component) {
        return new BoolVariable(this.value || component.value);
    }

    public BoolVariable and(BoolVariable component) {
        return new BoolVariable(this.value && component.value);
    }

    public boolean equal(BoolVariable boolVariable) {
        return this.value == boolVariable.value;
    }

    public boolean notEqual(BoolVariable boolVariable) {
        return !equal(boolVariable);
    }

    @Override
    public void print() {
        System.out.print(value);
    }
}
