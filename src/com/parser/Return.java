package com.parser;

import java.util.HashMap;
import java.util.Map;

public enum Return {
    number_,
    bool_,
    string_,
    void_,
    invalid_;

    private static Map<String, Return> tokenToReturnTypeMap;
    static {
        tokenToReturnTypeMap = new HashMap<>();
        tokenToReturnTypeMap.put("number",number_);
        tokenToReturnTypeMap.put("bool",bool_);
        tokenToReturnTypeMap.put("string",string_);
        tokenToReturnTypeMap.put("void",void_);
    }
    public static Return getType(String value) {
        return tokenToReturnTypeMap.getOrDefault(value,invalid_);
    }
}