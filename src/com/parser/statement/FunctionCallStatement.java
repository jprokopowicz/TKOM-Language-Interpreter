package com.parser.statement;

import com.parser.Program;

public class FunctionCallStatement extends Statement {
    private String functionName;

    FunctionCallStatement(Program program, Statement parent, String functionName){
        super(program);
        setParent(parent);
        this.functionName = functionName;
    }

    @Override
    public void execute(){

    }

    @Override
    public Statement copy() {
        FunctionCallStatement statement = new FunctionCallStatement(this.program,this.parent,this.functionName);
        statement.copyInternals(this);
        return statement;
    }
}
