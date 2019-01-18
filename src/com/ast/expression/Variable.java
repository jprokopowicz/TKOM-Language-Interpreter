package com.ast.expression;

import com.ast.Program;
import com.ast.statement.Statement;
import com.exceptions.executionExceptions.ExecutionException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

abstract public class Variable extends Expression {
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

    Type type = Type.invalid_;

    public static Type getType(String value) {
        return tokenToReturnTypeMap.getOrDefault(value,Type.invalid_);
    }

    public Type getType() {
        return type;
    }

    @Override
    public Variable evaluate(Statement context, Program program) {
        return this;
    }

    public abstract void setValue(Variable value) throws ExecutionException;

    public abstract void print() throws IOException;
}
