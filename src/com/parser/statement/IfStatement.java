package com.parser.statement;

import com.parser.Program;
import com.parser.expresion.BooleanExpression;

public class IfStatement extends Statement {
    BooleanExpression condition;
    public IfStatement(Program program, Statement parent){
        super(program);
        setParent(parent);
    }

    public void setCondition(BooleanExpression condition) {
        this.condition = condition;
    }

    @Override
    public void execute(){

    }

    @Override
    public Statement copy() {
        //todo: implement
        return null;
    }
}
