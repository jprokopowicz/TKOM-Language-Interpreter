package com.ast.statement;

import com.ast.Program;
import com.ast.expression.*;
import com.exceptions.executionExceptions.ExecutionException;
import com.exceptions.executionExceptions.InputOutputException;

import java.io.IOException;

public class OutputStatement extends Statement {
    private Expression outputValue;

    public OutputStatement(Statement parent, Expression outputValue) {
        super(false);
        setParent(parent);
        this.outputValue = outputValue;
    }

    @Override
    public void execute(Program program) throws ExecutionException {
        Variable output = outputValue.evaluate(this, program);
        try {
            output.print();
        } catch (IOException exc) {
            throw new InputOutputException("Output");
        }

    }

    @Override
    public Statement copy() throws ExecutionException {
        OutputStatement copy = new OutputStatement(null, this.outputValue);
        copy.copyInternals(this);
        return copy;
    }
}
