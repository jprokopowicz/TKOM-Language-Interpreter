package com.parser.statement;

import com.parser.Return;
import com.parser.parseException.DuplicationException;
import com.parser.variable.Variable;

import java.util.HashMap;
import java.util.Map;

public class Function extends Statement {
    private Return returnType;
    private String name;
    private Map<String,Variable> arguments;

    public Function(Return returnType, String name) {
        super();
        this.name = name;
        this.returnType = returnType;

        arguments = new HashMap<>();
    }

    public Return getReturnType() {
        return returnType;
    }

    public String getName() {
        return name;
    }

    public Variable getArgument(String name) {
        return arguments.get(name);
    }

    public void addArgument(String name, Variable argument) {
        arguments.put(name,argument);
    }

    @Override
    public Variable execute() {
        return new Variable();
    }
}
