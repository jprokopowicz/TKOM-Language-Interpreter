package com.parser.statement;

import com.parser.Program;
import com.parser.expresion.Expression;
import com.parser.expresion.Variable;

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
