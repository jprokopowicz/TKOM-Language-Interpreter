package com.parser.statement;

import com.parser.Program;

public class ReturnStatement extends Statement {
    ReturnStatement(Program program, Statement parent){
        super(program);
        setParent(parent);
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
