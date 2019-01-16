package com.executionExceptions;

public class IncompleteException extends ExecutionException {
    public IncompleteException(String name, String field) {
        super("Missing parameter in class: \"" + name + "\" what: \"" + field + "\"");
    }
}
