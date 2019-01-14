package com.ast.expresion;

import com.ast.statement.Statement;

public class VariableCall extends Expression {
    private String variableName;
    private Statement parent;

    public VariableCall(String variableName, Statement parent) {
        this.variableName = variableName;
        this.parent = parent;
    }

    @Override
    public Variable evaluate() {
//        return parent.getVariable(variableName);
        //todo
        return null;
    }

    @Override
    public Expression copy() {
        //todo
        return null;
    }
}
