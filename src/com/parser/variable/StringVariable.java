package com.parser.variable;

public class StringVariable extends Variable {
    private String message;

    public StringVariable() {
        type = Type.string_;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
