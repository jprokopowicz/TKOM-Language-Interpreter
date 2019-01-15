package com.ast.statement;

import com.ast.Program;
import com.ast.expresion.Expression;
import com.executionExceptions.ExecutionException;

public class ValueAssignment extends Statement {
    private String targetName;
    private Expression value;

    public ValueAssignment(Statement parent, String targetName, Expression value){
        super(false);
        setParent(parent);
        this.targetName = targetName;
        this.value = value;
    }

    @Override
    public void execute(Program program){
        //todo
    }

    @Override
    public Statement copy() throws ExecutionException {
        ValueAssignment copy = new ValueAssignment(null,this.targetName,this.value);
        copy.copyInternals(this);
        return copy;
    }
}
