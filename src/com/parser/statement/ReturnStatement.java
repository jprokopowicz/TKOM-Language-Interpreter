package com.parser.statement;

import com.parser.Program;
import com.parser.expresion.Expression;

public class ReturnStatement extends Statement {
    private Expression value;

    public ReturnStatement(Program program, Statement parent, Expression value){
        super(program);
        setParent(parent);
        this.value = value;
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
