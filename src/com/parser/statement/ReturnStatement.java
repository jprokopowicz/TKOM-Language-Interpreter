package com.parser.statement;

import com.parser.Program;
import com.parser.expresion.Expresion;
import com.parser.expresion.Variable;

public class ReturnStatement extends Statement {
    private Expresion value;

    public ReturnStatement(Program program, Statement parent, Expresion value){
        super(program);
        setParent(parent);
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
