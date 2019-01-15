package com.executionExceptions;

public class CopyException extends ExecutionException{
    public CopyException() {
        super();
    }

    public CopyException(String msg) {
        super("Exceprion during copping statements or expressions: " + msg);
    }
}
