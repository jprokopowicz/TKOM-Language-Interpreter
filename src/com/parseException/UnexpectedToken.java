package com.parseException;

import com.Token;

public class UnexpectedToken extends ParseException {
    public UnexpectedToken(Token token) {
        super("Invalid token: \"" + token.getValue() + "\" type: \"" + Token.tokenNames.get(token.getType()) + "\" line: " + token.getPosition().line + " sign: " + token.getPosition().sign);
    }

    public UnexpectedToken(Token token, Token.Type expectedType) {
        super("Invalid token: \"" + token.getValue() + "\" actual type: \"" + Token.tokenNames.get(token.getType()) + "\" expected type: \"" +
                Token.tokenNames.get(expectedType) + "\" line: " + token.getPosition().line + " sign: " + token.getPosition().sign);
    }
}
