package com.ast.statement;

import com.ast.Program;
import com.ast.expression.FunctionCallExpression;
import com.exceptions.executionExceptions.ExecutionException;

public class FunctionCallStatement extends Statement {
    private FunctionCallExpression functionCallExpression;

    public FunctionCallStatement(Statement parent, FunctionCallExpression functionCallExpression) {
        super(false);
        setParent(parent);
        this.functionCallExpression = functionCallExpression;
    }

    @Override
    public void execute(Program program) throws ExecutionException {
        functionCallExpression.evaluate(this, program);
    }

    @Override
    public Statement copy() throws ExecutionException {
        FunctionCallStatement copy = new FunctionCallStatement(null, this.functionCallExpression);
        copy.copyInternals(this);
        return copy;
    }
}
