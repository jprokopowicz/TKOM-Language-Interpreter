package com.executionExceptions;

public class CopyException extends ExecutionException{
    public CopyException() {
        super();
    }

    public CopyException(String msg) {
        super("Exception during copping statements or expressions: " + msg);
    }
}
