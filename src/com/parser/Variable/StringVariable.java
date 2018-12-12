package com.parser.Variable;

public class StringVariable extends Variable {
    private String message;

    StringVariable() {
        type = Type.string_;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
