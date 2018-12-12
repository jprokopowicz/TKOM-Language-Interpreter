package com.parser;

import com.lexer.Position;
import com.lexer.Token;

public class ParseException extends Exception {
    ParseException() {
        super();
    }
    ParseException(Token token) {
        super("Invalid token: " + token.getValue()+ " line: " + token.getPosition().line + " sign: " + token.getPosition().sign);
    }
}
