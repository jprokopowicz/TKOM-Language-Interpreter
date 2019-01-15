package com.ast.statement;

import com.ast.Program;
import com.ast.expresion.BooleanExpression;
import com.executionExceptions.ExecutionException;

public class IfStatement extends Statement {
    private BooleanExpression condition = null;
    private IfStatement elseStatement = null;

    public IfStatement(Statement parent){
        super(true);
        setParent(parent);
    }

    public void setCondition(BooleanExpression condition) {
        this.condition = condition;
    }

    public void setElseStatement(IfStatement elseStatement) {
        this.elseStatement = elseStatement;
    }

    @Override
    public void setParent(Statement parent) {
        this.parent = parent;
        if(elseStatement != null)
            elseStatement.parent = parent;
    }

    @Override
    public void execute(Program program){
        //todo
    }

    @Override
    public Statement copy() throws ExecutionException {
        IfStatement copy = new IfStatement(null);
        copy.copyInternals(this);
        if (this.condition != null)
            copy.setCondition(this.condition);
        if (this.elseStatement != null) {
            IfStatement copyElse = new IfStatement(null);
            copyElse.copyInternals(elseStatement);
            copy.setElseStatement(copyElse);
        }
        return copy;
    }
}
