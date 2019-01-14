package com.ast.statement;

import com.ast.Program;
import com.ast.expresion.BooleanExpression;

public class LoopStatement extends Statement {
    private BooleanExpression condition;

    public LoopStatement(Program program, Statement parent) {
        super(program);
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
    public Statement copy() {
        //todo
        return null;
    }
}
