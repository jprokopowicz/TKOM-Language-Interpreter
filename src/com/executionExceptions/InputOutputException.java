package com.executionExceptions;

import java.io.InputStream;

public class InputOutputException extends ExecutionException {
    public InputOutputException(String msg) {
        super("IO exception: " + msg);
    }
}
