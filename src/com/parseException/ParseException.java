package com.parseException;

import com.Token;

public class ParseException extends Exception {
    public ParseException() {
        super();
    }

    public ParseException(String msg) {
        super(msg);
    }

    public ParseException(String msg, Token currentToken) {
        super(msg + " Current token: " + currentToken.getValue()+ " line: " + currentToken.getPosition().line + " sign: " + currentToken.getPosition().sign);
    }
}
