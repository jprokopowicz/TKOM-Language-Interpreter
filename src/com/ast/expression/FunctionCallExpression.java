package com.ast.expression;

import com.ast.Program;
import com.ast.statement.Function;
import com.ast.statement.Statement;
import com.exceptions.executionExceptions.ConflictException;
import com.exceptions.executionExceptions.ExecutionException;
import com.exceptions.executionExceptions.IncompleteException;

import java.util.ArrayList;
import java.util.List;

public class FunctionCallExpression extends Expression {
    String functionName;
    List<Expression> arguments;

    public FunctionCallExpression(String functionName) {
        this.functionName = functionName;
        this.arguments = new ArrayList<>();
    }

    public void addArgument(Expression argument) {
        arguments.add(argument);
    }

    @Override
    public Variable evaluate(Statement context, Program program) throws ExecutionException {
        Function function = program.getFunction(functionName);
        if (function == null || !function.isDefined())
            throw new IncompleteException("FunctionCallExpression", functionName);
        function = (Function) function.copy();
        try {
            for (int i = 0; i < arguments.size(); ++i) {
                Variable argument = function.getVariable(function.argumentsNames.get(i));
                if (argument == null)
                    throw new IncompleteException("FunctionCallExpression", function.argumentsNames.get(i));
                argument.setValue(arguments.get(i).evaluate(context, program));
            }
        } catch (IndexOutOfBoundsException exc) {
            throw new ConflictException("FunctionCallExpression", "number of function arguments", "number of function call arguments");
        }
        function.execute(program);
        return function.getReturnValue();
    }
}
