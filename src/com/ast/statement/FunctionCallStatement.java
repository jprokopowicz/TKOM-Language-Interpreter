package com.ast.statement;

import com.ast.Program;
import com.ast.expresion.FunctionCallExpression;
import com.executionExceptions.ExecutionException;

public class FunctionCallStatement extends Statement {
    private FunctionCallExpression functionCallExpression;

    public FunctionCallStatement(Program program, Statement parent,FunctionCallExpression functionCallExpression) {
        super(program,false);
        setParent(parent);
        this.functionCallExpression = functionCallExpression;
    }

    @Override
    public void execute(){
        //todo
    }

    @Override
    public Statement copy() throws ExecutionException {
        FunctionCallStatement copy = new FunctionCallStatement(program,null,(FunctionCallExpression)this.functionCallExpression.copy());
        copy.copyInternals(this);
        return copy;
    }
}
