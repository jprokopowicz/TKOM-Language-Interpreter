package com.parser.expresion;

import java.util.HashMap;
import java.util.Map;

abstract public class Variable extends Expresion {
    //todo:is type needed when can be replaced with 'instanceOf'
    public enum Type {
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

    public Type getType() {
        return type;
    }

    @Override
    Variable evaluate() {
        return this;
    }
}
