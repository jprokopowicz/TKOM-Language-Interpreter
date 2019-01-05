package com.parser.expresion;

import com.parser.statement.Statement;

public class VariableCall extends Expression {
    private String variableName;
    private Statement parent;

    public VariableCall(String variableName, Statement parent) {
        this.variableName = variableName;
        this.parent = parent;
    }

    @Override
    public Variable evaluate() {
        return parent.getVariable(variableName);
    }

    @Override
    public Expression copy() {
        //todo
        return null;
    }
}
