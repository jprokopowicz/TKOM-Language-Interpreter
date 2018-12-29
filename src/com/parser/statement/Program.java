package com.parser.statement;

import com.parser.parseException.DuplicationException;
import com.parser.Return;
import com.parser.variable.Variable;

import java.util.HashMap;
import java.util.Map;

public class Program {
    private Map<String, Function> functions;

    void execute() {
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
