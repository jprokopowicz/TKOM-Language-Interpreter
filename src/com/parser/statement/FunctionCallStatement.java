package com.parser.statement;

import com.parser.Program;
import com.parser.expresion.Expresion;
import com.parser.expresion.FunctionCallExpression;

import java.util.ArrayList;
import java.util.List;

public class FunctionCallStatement extends Statement {
    private FunctionCallExpression functionCallExpression;

    public FunctionCallStatement(Program program, Statement parent,FunctionCallExpression functionCallExpression) {
        super(program);
        setParent(parent);
        this.functionCallExpression = functionCallExpression;
    }

    @Override
    public void execute(){

    }

    @Override
    public Statement copy() {
        //todo: fix copy function call expression
        FunctionCallStatement statement = new FunctionCallStatement(this.program,this.parent,this.functionCallExpression);
        statement.copyInternals(this);
        return statement;
    }
}
