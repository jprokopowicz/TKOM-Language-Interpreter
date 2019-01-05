package com.parser.statement;

import com.parser.Program;
import com.parser.expresion.Expression;
import com.parser.expresion.Variable;

public class ValueAssigment extends Statement {
    private Variable target;
    private Expression value;

    public ValueAssigment(Program program, Statement parent, Variable target, Expression value){
        super(program);
        setParent(parent);
        this.target = target;
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
