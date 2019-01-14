package com.parseException;

import com.Token;

public class UnknownNameException extends ParseException {
    public UnknownNameException(Token token) {
        super("Unknown name: " + token.getValue() + " line: " + token.getPosition().line + " sign: " + token.getPosition().sign);
    }
}
