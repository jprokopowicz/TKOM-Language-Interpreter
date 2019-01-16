package com.ast.expresion;

import com.ast.Program;
import com.ast.statement.Statement;
import com.executionExceptions.ExecutionException;
import com.executionExceptions.IncompleteException;

public class VariableCall extends Expression {
    private String variableName;

    public VariableCall(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public Variable evaluate(Statement context, Program program) throws ExecutionException {
        Variable variable = context.getVariable(variableName);
        if (variable == null)
            throw new IncompleteException("Variable call", "variable");
        return variable;
    }
}
