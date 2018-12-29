package com.parser.statement;

import com.parser.Return;
import com.parser.variable.Variable;

import java.util.HashMap;
import java.util.Map;

public class Function extends Statement {
    private Return returnType;
    private String name;

    public Function(Return returnType, String name, Program program) {
        super(program);
        this.name = name;
        this.returnType = returnType;
    }

    public Return getReturnType() {
        return returnType;
    }

    public String getName() {
        return name;
    }

    @Override
    public Variable execute() {
        return new Variable();
    }
}
