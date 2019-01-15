package com.ast.statement;

import com.ast.Program;
import com.executionExceptions.ExecutionException;

public class InputStatement extends Statement {
    private String targetName;
    public InputStatement(Program program, Statement parent, String targetName){
        super(program, false);
        setParent(parent);
        this.targetName = targetName;
    }

    @Override
    public void execute(){
        //todo
    }

    @Override
    public Statement copy() throws ExecutionException {
        InputStatement copy = new InputStatement(program,null,this.targetName);
        copy.copyInternals(this);
        return copy;
    }
}
