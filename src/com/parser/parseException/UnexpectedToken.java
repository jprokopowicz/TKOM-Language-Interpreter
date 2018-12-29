package com.parser.parseException;

import com.lexer.Token;

public class UnexpectedToken extends ParseException {
    public UnexpectedToken() {
        super();
    }

    public UnexpectedToken(String msg) {
        super(msg);
    }

    public UnexpectedToken(Token token) {
        super("Invalid token: " + token.getValue()+ " line: " + token.getPosition().line + " sign: " + token.getPosition().sign);
    }
}
