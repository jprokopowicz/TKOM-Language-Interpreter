package com.ast.statement;

import com.ast.Program;
import com.executionExceptions.ExecutionException;

public class InputStatement extends Statement {
    private String targetName;
    public InputStatement(Statement parent, String targetName){
        super(false);
        setParent(parent);
        this.targetName = targetName;
    }

    @Override
    public void execute(Program program){
        //todo
    }

    @Override
    public Statement copy() throws ExecutionException {
        InputStatement copy = new InputStatement(null,this.targetName);
        copy.copyInternals(this);
        return copy;
    }
}
