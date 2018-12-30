package com.parser.expresion;

import com.parser.statement.Function;
import com.parser.statement.Program;
import com.parser.statement.Statement;

public class FunctionCall extends Expresion {
    private String functionName;
    private Statement parent;
    private Program program;

    public FunctionCall(String functionName, Statement parent, Program program){
        this.functionName = functionName;
        this.parent = parent;
        this.program = program;
    }

    @Override
    Variable evaluate(){
        Function function = (Function) program.getFunction(functionName).copy();
        function.setParent(parent);
        function.execute();
        return function.getReturnValue();
    }
}
