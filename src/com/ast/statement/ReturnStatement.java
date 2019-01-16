package com.ast.statement;

import com.ast.Program;
import com.ast.expresion.Expression;
import com.ast.expresion.Variable;
import com.executionExceptions.ExecutionException;
import com.executionExceptions.ReturnException;

public class ReturnStatement extends Statement {
    private Expression value;

    public ReturnStatement(Statement parent, Expression value){
        super(false);
        setParent(parent);
        this.value = value;
    }

    @Override
    public void execute(Program program) throws ExecutionException {
        Variable returnValue = value.evaluate(this,program);
        throw new ReturnException(returnValue);
    }

    @Override
    public Statement copy() throws ExecutionException {
        ReturnStatement copy = new ReturnStatement(null,this.value);
        copy.copyInternals(this);
        return copy;
    }
}
