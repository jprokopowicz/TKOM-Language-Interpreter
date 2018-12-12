package com.parser.statement;

import com.parser.Return;

import java.util.HashMap;
import java.util.Map;

public class Program extends Statement {
    private Map<String, Function> functions;


    @Override
    Return execute() {
        return new Return();
    }

    public Program() {
        super();
        functions = new HashMap<>();
    }

    public void addFunction(Function newFunction) {
        functions.put(newFunction.getName(), newFunction);
    }

    public Function getFunction(String name) {
        return functions.get(name);
    }
}
