package com.parser.parseException;

import com.lexer.Token;

public class UnknownNameException extends ParseException {
    public UnknownNameException(Token token) {
        super("Unknown name: " + token.getValue() + " line: " + token.getPosition().line + " sign: " + token.getPosition().sign);
    }
}
