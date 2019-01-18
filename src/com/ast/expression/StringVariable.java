package com.ast.expression;

import com.exceptions.executionExceptions.ConflictException;
import com.exceptions.executionExceptions.ExecutionException;

public class StringVariable extends Variable {
    private String message = "";

    public StringVariable() {
        type = Type.string_;
    }

    public StringVariable(String message) {
        type = Type.string_;
        this.message = message;
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
    public void print() {
        System.out.print(message);
    }

    @Override
    public void setValue(Variable value) throws ExecutionException {
        if(!(value instanceof StringVariable))
            throw new ConflictException("StringVariable.setValue()", "variable", "assigned value");
        StringVariable stringVariable = (StringVariable)value;
        this.message = stringVariable.message;
    }
}
