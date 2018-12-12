package com.parser.statement;

import com.parser.DuplicationException;
import com.parser.Return;

import java.util.HashMap;
import java.util.Map;

public class Program {
    private Map<String, Function> functions;

    Return execute() {
        return new Return();
    }

    public Program() {
        super();
        functions = new HashMap<>();
    }

    public void addFunction(Function newFunction) throws DuplicationException {
        if (functions.containsKey(newFunction.getName()))
            throw new DuplicationException();
        functions.put(newFunction.getName(), newFunction);
    }

    public Function getFunction(String name) {
        return functions.get(name);
    }
}
