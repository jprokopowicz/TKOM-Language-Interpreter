package com.parser.variable;

public class BoolVariable extends Variable {
    private boolean value;

    public BoolVariable() {
        type = Type.bool_;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}