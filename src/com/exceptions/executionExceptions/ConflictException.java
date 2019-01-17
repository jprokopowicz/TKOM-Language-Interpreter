package com.exceptions.executionExceptions;

public class ConflictException extends ExecutionException {
    public ConflictException (String where, String what, String withWhat) {
        super("Conflict in: \""+ where + "\"mismatch: \"" + what + "\" with \""+ withWhat + "\"");
    }
}
