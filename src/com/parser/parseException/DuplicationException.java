package com.parser.parseException;

import com.lexer.Token;

public class DuplicationException extends ParseException {
    public DuplicationException(){
        super();
    }

    public DuplicationException(Token token) {
        super("Name duplicated: " + token.getValue() + " line: " + token.getPosition().line + " sign: " + token.getPosition().sign);
    }
}