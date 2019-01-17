package com.exceptions.parseException;

import com.interpreterParts.Token;

public class WrongArgumentException extends ParseException {
    public WrongArgumentException(Token token) {
        super("Wrong argument: \"" + token.getValue() + "\" line: " + token.getPosition().line + " sign: " + token.getPosition().sign);
    }

    public WrongArgumentException (int argNumber, Token token) {
        super("Wrong number of arguments. Expected number: " + argNumber + " line: " + token.getPosition().line + " sign: " + token.getPosition().sign);
    }
}
