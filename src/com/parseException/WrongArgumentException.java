package com.parseException;

import com.Token;

public class WrongArgumentException extends ParseException {
    public WrongArgumentException() {
        super();
    }

    public WrongArgumentException(Token token) {
        super("Wrong argument: " + token.getValue() + " line: " + token.getPosition().line + " sign: " + token.getPosition().sign);
    }

    public WrongArgumentException (int argNumber, Token token) {
        super("Expected " + argNumber + " line: " + token.getPosition().line + " sign: " + token.getPosition().sign);
    }
}
