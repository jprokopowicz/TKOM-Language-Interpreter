package com.executionExceptions;

import com.ast.expresion.Variable;

public class ReturnException extends ExecutionException {
    private Variable returnValue;

    public ReturnException(Variable returnValue) {
        this.returnValue = returnValue;
    }

    public Variable getReturnValue() {
        return returnValue;
    }
}
