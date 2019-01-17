package com.ast.statement;

import com.ast.Program;
import com.ast.expresion.Expression;
import com.ast.expresion.Variable;
import com.exceptions.executionExceptions.ConflictException;
import com.exceptions.executionExceptions.ExecutionException;
import com.exceptions.executionExceptions.ReturnException;

public class ReturnStatement extends Statement {
    private Expression value;

    public ReturnStatement(Statement parent, Expression value){
        super(false);
        setParent(parent);
        this.value = value;
    }

    @Override
    public void execute(Program program) throws ExecutionException {
        Statement currentStatement = parent;
        while(currentStatement != null) {
            if (currentStatement instanceof Function && ((Function)currentStatement).getReturnType() == Function.Return.void_)
                throw new ReturnException(null);
            else
                currentStatement = currentStatement.parent;
        }
        if(value == null)
            throw new ConflictException("Return", "return type", "no return");
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
