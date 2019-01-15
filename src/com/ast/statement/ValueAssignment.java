package com.ast.statement;

import com.ast.Program;
import com.ast.expresion.Expression;
import com.executionExceptions.ExecutionException;

public class ValueAssignment extends Statement {
    private String targetName;
    private Expression value;

    public ValueAssignment(Program program, Statement parent, String targetName, Expression value){
        super(program, false);
        setParent(parent);
        this.targetName = targetName;
        this.value = value;
    }

    @Override
    public void execute(){
        //todo
    }

    @Override
    public Statement copy() throws ExecutionException {
        ValueAssignment copy = new ValueAssignment(this.program,null,this.targetName,this.value.copy());
        copy.copyInternals(this);
        return copy;
    }
}
