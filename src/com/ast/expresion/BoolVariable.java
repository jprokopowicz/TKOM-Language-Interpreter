package com.ast.expresion;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

public class BoolVariable extends Variable {
    private boolean value = false;

    public BoolVariable() {
        type = Type.bool_;
    }

    public BoolVariable(boolean value) {
        this.value = value;
        type = Type.bool_;
    }

    public BoolVariable(BoolVariable boolVariable) {
        this.value = boolVariable.value;
        type = Type.bool_;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public void print() {
        System.out.print(value);
    }
}
