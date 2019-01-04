package com.parser.expresion;

import com.parser.statement.Function;
import com.parser.Program;
import com.parser.statement.Statement;

public class FunctionCallExpression extends Expresion {
    private String functionName;
    private Statement parent;
    private Program program;

    public FunctionCallExpression(String functionName, Statement parent, Program program){
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
