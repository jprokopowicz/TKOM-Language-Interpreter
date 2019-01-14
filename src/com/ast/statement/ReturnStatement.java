package com.ast.statement;

import com.ast.Program;
import com.ast.expresion.Expression;

public class ReturnStatement extends Statement {
    private Expression value;

    public ReturnStatement(Program program, Statement parent, Expression value){
        super(program, false);
        setParent(parent);
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
