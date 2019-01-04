package com.parser.statement;

import com.parser.Program;
import com.parser.expresion.Expresion;

public class OutputStatement extends Statement {
    Expresion outputValue;
    public OutputStatement(Program program, Statement parent, Expresion outputValue){
        super(program);
        setParent(parent);
        this.outputValue = outputValue;
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
