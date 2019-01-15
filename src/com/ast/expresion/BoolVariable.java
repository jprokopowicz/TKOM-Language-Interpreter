package com.ast.expresion;

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
}
