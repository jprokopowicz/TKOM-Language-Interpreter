package com.parser.statement;

import com.parser.Return;
import com.parser.Variable.Variable;

import java.util.HashMap;
import java.util.Map;

public class Function extends Statement {
    private Return.Type returnType;
    private String name;
    private Map<String,Variable> arguments;

    public Function(Return.Type returnType, String name) {
        super();
        this.name = name;
        this.returnType = returnType;

        arguments = new HashMap<>();
    }

    public Return.Type getReturnType() {
        return returnType;
    }

    public String getName() {
        return name;
    }

    public Variable getArgument(String name) {
        return arguments.get(name);
    }

    @Override
    public Return execute() {
        return new Return();
    }
}
