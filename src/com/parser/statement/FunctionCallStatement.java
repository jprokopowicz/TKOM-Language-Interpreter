package com.parser.statement;

import com.parser.Program;
import com.parser.expresion.Expresion;

import java.util.ArrayList;
import java.util.List;

public class FunctionCallStatement extends Statement {
    private String functionName;
    private List<Expresion> arguments;

    public FunctionCallStatement(Program program, Statement parent, String functionName){
        super(program);
        setParent(parent);
        this.functionName = functionName;
        this.arguments = new ArrayList<>();
    }

    public void addArgument(Expresion argument) {
        arguments.add(argument);
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
