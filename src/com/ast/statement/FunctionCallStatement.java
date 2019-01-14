package com.ast.statement;

import com.ast.Program;
import com.ast.expresion.FunctionCallExpression;

public class FunctionCallStatement extends Statement {
    private FunctionCallExpression functionCallExpression;

    public FunctionCallStatement(Program program, Statement parent,FunctionCallExpression functionCallExpression) {
        super(program);
        setParent(parent);
        this.functionCallExpression = functionCallExpression;
    }

    @Override
    public void execute(){
        //todo
    }

    @Override
    public Statement copy() {
//        //todo: fix copy function call expression
//        FunctionCallStatement statement = new FunctionCallStatement(this.program,this.parent,this.functionCallExpression);
//        statement.copyInternals(this);
//        return statement;
        //todo
        return null;
    }
}
