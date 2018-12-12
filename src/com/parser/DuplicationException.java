package com.parser;

public class DuplicationException extends Exception {
    public DuplicationException(){
        super();
    }

    public DuplicationException(String msg) {
        super("Name duplicated: " + msg);
    }
}
