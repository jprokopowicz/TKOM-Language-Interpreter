package com.ast.statement;

import com.ast.Program;
import com.ast.expresion.BooleanExpression;
import com.executionExceptions.ExecutionException;

public class LoopStatement extends Statement {
    private BooleanExpression condition;

    public LoopStatement(Program program, Statement parent) {
        super(program, true);
        setParent(parent);
    }

    public void setCondition(BooleanExpression condition) {
        this.condition = condition;
    }

    @Override
    public void execute() {
        //todo
    }

    @Override
    public Statement copy() throws ExecutionException {
        LoopStatement copy = new LoopStatement(program,null);
        copy.copyInternals(this);
        copy.setCondition((BooleanExpression)this.condition.copy());
        return copy;
    }
}
