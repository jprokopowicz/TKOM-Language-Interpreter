package com.exceptions.parseException;

import com.interpreterParts.Token;

public class DuplicationException extends ParseException {
    public DuplicationException(Token token) {
        super("Name duplicated: \"" + token.getValue() + "\" line: " + token.getPosition().line + " sign: " + token.getPosition().sign);
    }
}