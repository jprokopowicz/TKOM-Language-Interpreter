package com.parser.expresion;

import com.parser.statement.Function;
import com.parser.Program;
import com.parser.statement.Statement;

import java.util.ArrayList;
import java.util.List;

public class FunctionCallExpression extends Expresion {
    private String functionName;
    private Statement parent;
    private Program program;
    private List<Expresion> arguments;

    public FunctionCallExpression(String functionName, Statement parent, Program program){
        this.functionName = functionName;
        this.parent = parent;
        this.program = program;
        this.arguments = new ArrayList<>();
    }

    public void addArgument(Expresion argument) {
        arguments.add(argument);
    }

    @Override
    Variable evaluate(){
        Function function = (Function) program.getFunction(functionName).copy();
        //TODO
        function.setParent(parent);
        function.execute();
        return function.getReturnValue();
    }
}
