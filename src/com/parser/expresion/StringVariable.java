package com.parser.expresion;

public class StringVariable extends Variable {
    private String message;

    public StringVariable() {
        type = Type.string_;
    }

    public StringVariable(StringVariable stringVariable) {
        this.message = stringVariable.message;
        type = Type.string_;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Expresion copy() {
        //todo
        return null;
    }
}
