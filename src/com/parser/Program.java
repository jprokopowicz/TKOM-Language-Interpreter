package com.parser;

import com.parser.statement.Function;

import java.util.HashMap;
import java.util.Map;

public class Program {
    private Map<String, Function> functions;

    void execute() {
    }

    public Program() {
        functions = new HashMap<>();
    }

    public void addFunction(Function newFunction) {
        functions.put(newFunction.getName(), newFunction);
    }

    public Function getFunction(String name) {
        return functions.get(name);
    }
}
