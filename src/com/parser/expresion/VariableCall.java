package com.parser.expresion;

import com.parser.statement.Statement;

public class VariableCall extends Expresion {
    private String variableName;
    private Statement parent;

    VariableCall(String variableName, Statement parent) {
        this.variableName = variableName;
        this.parent = parent;
    }

    @Override
    Variable evaluate() {
        return parent.getVariable(variableName);
    }
}
