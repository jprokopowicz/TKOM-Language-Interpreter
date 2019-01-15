package com.ast.statement;

import com.ast.Program;
import com.ast.expresion.Expression;
import com.executionExceptions.ExecutionException;

public class ReturnStatement extends Statement {
    private Expression value;

    public ReturnStatement(Statement parent, Expression value){
        super(false);
        setParent(parent);
        this.value = value;
    }

    @Override
    public void execute(Program program){
        //todo
    }

    @Override
    public Statement copy() throws ExecutionException {
        ReturnStatement copy = new ReturnStatement(null,this.value);
        copy.copyInternals(this);
        return copy;
    }
}
