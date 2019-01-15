package com.ast.expresion;

import com.ast.Program;
import com.ast.statement.Statement;

import java.util.ArrayList;
import java.util.List;

public class FunctionCallExpression extends Expression {
    private String functionName;
    private List<Expression> arguments;

    public FunctionCallExpression(String functionName){
        this.functionName = functionName;
        this.arguments = new ArrayList<>();
    }

    public void addArgument(Expression argument) {
        arguments.add(argument);
    }

    @Override
    public Variable evaluate(Statement context, Program program){
        //todo
        return null;
    }
}
