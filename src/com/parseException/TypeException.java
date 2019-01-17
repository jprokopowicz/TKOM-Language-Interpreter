package com.parseException;

import com.Token;

public class TypeException extends ParseException {
    public TypeException(String expected, Token currentToken) {
        super("Invalid type. Expected: " + expected + " line: " + currentToken.getPosition().line + " sign: " + currentToken.getPosition().sign);
    }
}
