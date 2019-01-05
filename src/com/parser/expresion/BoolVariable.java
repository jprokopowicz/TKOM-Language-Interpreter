package com.parser.expresion;

public class BoolVariable extends Variable {
    private boolean value;

    public BoolVariable() {
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
    public Expression copy() {
        //todo
        return null;
    }
}
