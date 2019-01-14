package com.ast.statement;

import com.ast.Program;
import com.ast.expresion.Expression;

public class ValueAssigment extends Statement {
    private String targetName;
    private Expression value;

    public ValueAssigment(Program program, Statement parent, String targetName, Expression value){
        super(program);
        setParent(parent);
        this.targetName = targetName;
        this.value = value;
    }

    @Override
    public void execute(){
        //todo
    }

    @Override
    public Statement copy() {
        //todo
        return null;
    }
}