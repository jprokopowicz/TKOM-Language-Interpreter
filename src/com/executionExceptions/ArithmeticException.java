package com.executionExceptions;

public class ArithmeticException extends ExecutionException {
    public ArithmeticException(String msg) {
        super("Arithmetic exception: " + msg);
    }
}
