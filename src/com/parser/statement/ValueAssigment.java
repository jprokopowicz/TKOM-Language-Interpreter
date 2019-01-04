package com.parser.statement;

import com.parser.Program;
import com.parser.expresion.Expresion;
import com.parser.expresion.Variable;

public class ValueAssigment extends Statement {
    private Variable target;
    private Expresion value;

    public ValueAssigment(Program program, Statement parent, Variable target, Expresion value){
        super(program);
        setParent(parent);
        this.target = target;
        this.value = value;
    }

    @Override
    public void execute(){

    }

    @Override
    public Statement copy() {
        //todo: implement
        return null;
    }
}
