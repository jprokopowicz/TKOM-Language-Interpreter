package com.exceptions.executionExceptions;

public class InputOutputException extends ExecutionException {
    public InputOutputException(String msg) {
        super("IO exception: " + msg);
    }
}
