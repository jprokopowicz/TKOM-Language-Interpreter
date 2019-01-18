package com.ast.statement;

import com.ast.Program;
import com.ast.expression.BoolVariable;
import com.ast.expression.BooleanExpression;
import com.exceptions.executionExceptions.ExecutionException;
import com.exceptions.executionExceptions.IncompleteException;

public class IfStatement extends Statement {
    private BooleanExpression condition = null;
    private IfStatement elseStatement = null;

    public IfStatement(Statement parent){
        super(true);
        this.parent = parent;
    }

    public void setCondition(BooleanExpression condition) {
        this.condition = condition;
    }

    public void setElseStatement(IfStatement elseStatement) {
        this.elseStatement = elseStatement;
        elseStatement.parent = this.parent;
    }

    public IfStatement getElse() {
        return elseStatement;
    }

    @Override
    public void setParent(Statement parent) {
        this.parent = parent;
        if(elseStatement != null)
            elseStatement.parent = parent;
    }

    @Override
    public void execute(Program program) throws ExecutionException {
        if (condition != null) {
            if (((BoolVariable) condition.evaluate(parent, program)).getValue()) {
                for (Statement instruction : innerStatements)
                    instruction.execute(program);
            } else if (elseStatement != null)
                elseStatement.execute(program);
        } else if (elseStatement == null) {
            for (Statement instruction : innerStatements)
                instruction.execute(program);
        } else
            throw new IncompleteException("IfStatement", "condition");
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
