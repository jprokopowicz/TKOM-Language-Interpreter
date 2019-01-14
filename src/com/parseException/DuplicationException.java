package com.parseException;

import com.Token;

public class DuplicationException extends ParseException {
    public DuplicationException(){
        super();
    }

    public DuplicationException(Token token) {
        super("Name duplicated: " + token.getValue() + " line: " + token.getPosition().line + " sign: " + token.getPosition().sign);
    }
}