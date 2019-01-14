package com.ast.statement;

import com.ast.Program;
import com.ast.expresion.BooleanExpression;

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
    public Statement copy() {
//        IfStatement newIfStatement = new IfStatement(this.program, this.parent);
//        newIfStatement.copyInternals(this);
//        newIfStatement.setElseStatement((IfStatement)elseStatement.copy());
//        //todo: implement
        //todo
        return null;
    }
}
