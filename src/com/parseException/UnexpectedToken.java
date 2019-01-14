package com.parseException;

import com.Token;

public class UnexpectedToken extends ParseException {
    public UnexpectedToken() {
        super();
    }

    public UnexpectedToken(String msg) {
        super(msg);
    }

    public UnexpectedToken(Token token) {
        super("Invalid token: " + token.getValue() + " line: " + token.getPosition().line + " sign: " + token.getPosition().sign);
    }
}
