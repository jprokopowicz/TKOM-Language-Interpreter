package com.parser;

import java.util.HashMap;
import java.util.Map;

public class Return {
    public enum Type {
        number_,
        bool_,
        string_,
        void_,
        invalid_,
    }

    private static Map<String, Type> tokenToReturnTypeMap;
    static {
        tokenToReturnTypeMap = new HashMap<>();
        tokenToReturnTypeMap.put("number",Type.number_);
        tokenToReturnTypeMap.put("bool",Type.bool_);
        tokenToReturnTypeMap.put("string",Type.string_);
        tokenToReturnTypeMap.put("void",Type.void_);
    }
    public static Type getType(String value) {
        return tokenToReturnTypeMap.getOrDefault(value,Type.invalid_);
    }
}
