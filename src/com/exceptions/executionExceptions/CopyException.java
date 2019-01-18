package com.exceptions.executionExceptions;

public class CopyException extends ExecutionException {
    public CopyException(String msg) {
        super("Exception during copping statements or expressions: " + msg);
    }
}
