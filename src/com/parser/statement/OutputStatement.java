package com.parser.statement;

import com.parser.Program;
import com.parser.expresion.Expression;

public class OutputStatement extends Statement {
    Expression outputValue;
    public OutputStatement(Program program, Statement parent, Expression outputValue){
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
