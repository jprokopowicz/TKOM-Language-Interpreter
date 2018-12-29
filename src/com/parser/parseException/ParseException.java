package com.parser.parseException;

import com.lexer.Position;
import com.lexer.Token;

public class ParseException extends Exception {
    public ParseException() {
        super();
    }

    public ParseException(String msg) {
        super(msg);
    }
}
