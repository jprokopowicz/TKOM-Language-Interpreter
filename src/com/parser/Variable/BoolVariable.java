package com.parser.Variable;

public class BoolVariable extends Variable {
    private boolean value;

    BoolVariable() {
        type = Type.bool_;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
