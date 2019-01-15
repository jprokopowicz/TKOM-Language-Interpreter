package com.ast.statement;

import com.ast.Program;
import com.ast.expresion.BooleanExpression;
import com.executionExceptions.ExecutionException;

public class LoopStatement extends Statement {
    private BooleanExpression condition;

    public LoopStatement(Statement parent) {
        super(true);
        setParent(parent);
    }

    public void setCondition(BooleanExpression condition) {
        this.condition = condition;
    }

    @Override
    public void execute(Program program) {
        //todo
    }

    @Override
    public Statement copy() throws ExecutionException {
        LoopStatement copy = new LoopStatement(null);
        copy.copyInternals(this);
        copy.setCondition(this.condition);
        return copy;
    }
}
