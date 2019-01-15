package com.ast.statement;

import com.ast.Program;
import com.ast.expresion.Expression;
import com.executionExceptions.ExecutionException;

public class OutputStatement extends Statement {
    private Expression outputValue;
    public OutputStatement(Statement parent, Expression outputValue){
        super(false);
        setParent(parent);
        this.outputValue = outputValue;
    }

    @Override
    public void execute(Program program){
        //todo
    }

    @Override
    public Statement copy() throws ExecutionException {
        OutputStatement copy = new OutputStatement(null,this.outputValue);
        copy.copyInternals(this);
        return this;
    }
}
