package com.parser.statement;

import com.parser.Program;
import com.parser.expresion.Expression;

public class OutputStatement extends Statement {
    private Expression outputValue;
    public OutputStatement(Program program, Statement parent, Expression outputValue){
        super(program);
        setParent(parent);
        this.outputValue = outputValue;
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
