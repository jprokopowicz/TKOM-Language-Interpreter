package com.parser.statement;

import com.parser.Program;
import com.parser.expresion.Variable;

public class InputStatement extends Statement {
    private Variable target;
    public InputStatement(Program program, Statement parent, Variable target){
        super(program);
        setParent(parent);
        this.target = target;
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
