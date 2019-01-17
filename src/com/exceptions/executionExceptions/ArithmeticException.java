package com.exceptions.executionExceptions;

public class ArithmeticException extends ExecutionException {
    public ArithmeticException(String msg) {
        super("Arithmetic exception: " + msg);
    }
}
