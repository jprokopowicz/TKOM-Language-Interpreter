package com.parser.statement;

import com.parser.Program;
import com.parser.expresion.BooleanExpression;

public class IfStatement extends Statement {
    BooleanExpression condition = null;
    IfStatement elseStatement = null;
    public IfStatement(Program program, Statement parent){
        super(program);
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

    }

    @Override
    public Statement copy() {
        IfStatement newIfStatement = new IfStatement(this.program, this.parent);
        newIfStatement.copyInternals(this);
        newIfStatement.setElseStatement((IfStatement)elseStatement.copy());
//        //todo: implement
        return null;
    }
}
