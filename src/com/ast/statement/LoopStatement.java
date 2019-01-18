package com.ast.statement;

import com.ast.Program;
import com.ast.expression.BoolVariable;
import com.ast.expression.BooleanExpression;
import com.exceptions.executionExceptions.ExecutionException;
import com.exceptions.executionExceptions.IncompleteException;

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
    public void execute(Program program) throws ExecutionException {
        if (condition == null)
            throw new IncompleteException("LoopStatement", "condition");
        while (((BoolVariable)condition.evaluate(parent,program)).getValue()) {
            for (Statement instruction : innerStatements)
                instruction.execute(program);
        }
    }

    @Override
    public Statement copy() throws ExecutionException {
        LoopStatement copy = new LoopStatement(null);
        copy.copyInternals(this);
        copy.setCondition(this.condition);
        return copy;
    }
}
