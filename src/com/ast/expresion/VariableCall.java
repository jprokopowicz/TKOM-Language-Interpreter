package com.ast.expresion;

import com.ast.Program;
import com.ast.statement.Statement;

public class VariableCall extends Expression {
    private String variableName;

    public VariableCall(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public Variable evaluate(Statement context, Program program) {
        //todo
        return null;
    }
}
