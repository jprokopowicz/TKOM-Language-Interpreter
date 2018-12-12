package com.parser.Variable;

import com.lexer.Token;

import java.util.HashMap;
import java.util.Map;

public class Variable {
    //todo:is type needed when can be replaced with 'instanceOf'
    enum Type {
        number_,
        bool_,
        string_,
        invalid_,
    }

    private static Map<String,Type> tokenToReturnTypeMap;
    static {
        tokenToReturnTypeMap = new HashMap<>();
        tokenToReturnTypeMap.put("number",Type.number_);
        tokenToReturnTypeMap.put("bool",Type.bool_);
        tokenToReturnTypeMap.put("string",Type.string_);
    }
    public static Type getType(String value) {
        return tokenToReturnTypeMap.getOrDefault(value,Type.invalid_);
    }

    Type type = Type.invalid_;

}
