package com.ast.statement;

import com.ast.Program;
import com.ast.expresion.Variable;
import com.executionExceptions.ExecutionException;
import com.executionExceptions.IncompleteException;

public class InputStatement extends Statement {
    private String targetName;
    public InputStatement(Statement parent, String targetName){
        super(false);
        setParent(parent);
        this.targetName = targetName;
    }

    @Override
    public void execute(Program program) throws ExecutionException{
        Variable target = getVariable(targetName);
        if (target == null)
            throw new IncompleteException("InputStatement", "targetVariable");
        //todo: reading input
    }

    @Override
    public Statement copy() throws ExecutionException {
        InputStatement copy = new InputStatement(null,this.targetName);
        copy.copyInternals(this);
        return copy;
    }
}
