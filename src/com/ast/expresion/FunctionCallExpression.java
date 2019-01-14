package com.ast.expresion;

import com.ast.Program;
import com.ast.statement.Statement;

import java.util.ArrayList;
import java.util.List;

public class FunctionCallExpression extends Expression {
    private String functionName;
    private Statement parent;
    private Program program;
    private List<Expression> arguments;

    public FunctionCallExpression(String functionName, Statement parent, Program program){
        this.functionName = functionName;
        this.parent = parent;
        this.program = program;
        this.arguments = new ArrayList<>();
    }

    public void addArgument(Expression argument) {
        arguments.add(argument);
    }

    @Override
    public Variable evaluate(){
//        Function function = (Function) program.getFunction(functionName).copy();
//        TODO
//        function.setParent(parent);
//        function.execute();
//        return function.getReturnValue();
        //todo
        return null;
    }

    @Override
    public Expression copy() {
        //todo
        return null;
    }
}
