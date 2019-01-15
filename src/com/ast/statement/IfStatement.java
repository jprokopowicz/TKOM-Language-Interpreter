package com.ast.statement;

import com.ast.Program;
import com.ast.expresion.BooleanExpression;
import com.executionExceptions.ExecutionException;

public class IfStatement extends Statement {
    private BooleanExpression condition = null;
    private IfStatement elseStatement = null;

    public IfStatement(Program program, Statement parent){
        super(program,true);
        setParent(parent);
    }

    public void setCondition(BooleanExpression condition) {
        this.condition = condition;
    }

    public void setElseStatement(IfStatement elseStatement) {
        this.elseStatement = elseStatement;
    }

    @Override
    public void execute(){
        //todo
    }

    @Override
    public Statement copy() throws ExecutionException {
        IfStatement copy = new IfStatement(program,parent);
        copy.copyInternals(this);
        if (this.condition != null)
            copy.setCondition((BooleanExpression)this.condition.copy());
        if (this.elseStatement != null) {
            IfStatement copyElse = new IfStatement(program,parent);
            copyElse.copyInternals(elseStatement);
            copy.setElseStatement(copyElse);
        }
        return copy;
    }
}
