package com.executionExceptions;

public class NoDeclarationException extends ExecutionException {
    public NoDeclarationException(){
        super();
    }

    public NoDeclarationException(String missingFunctionName) {
        super("Missing function declaration: " + missingFunctionName);
    }
}
