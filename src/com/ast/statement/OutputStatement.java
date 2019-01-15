package com.ast.statement;

import com.ast.Program;
import com.ast.expresion.Expression;
import com.executionExceptions.ExecutionException;

public class OutputStatement extends Statement {
    private Expression outputValue;
    public OutputStatement(Program program, Statement parent, Expression outputValue){
        super(program, false);
        setParent(parent);
        this.outputValue = outputValue;
    }

    @Override
    public void execute(){
        //todo
    }

    @Override
    public Statement copy() throws ExecutionException {
        OutputStatement copy = new OutputStatement(this.program,null,this.outputValue.copy());
        copy.copyInternals(this);
        return this;
    }
}
