package com.executionExceptions;

public class CopyException extends ExecutionException{
    public CopyException(String msg) {
        super("Exception during copping statements or expressions: " + msg);
    }
}
