package com.ast.expression;

import com.ast.Program;
import com.ast.statement.Statement;
import com.exceptions.executionExceptions.ExecutionException;

public class BasicBoolExpression extends Expression {
    boolean not;
    Expression content;

    public BasicBoolExpression(Expression content, boolean not) {
        this.not = not;
        this.content = content;
    }

    @Override
    public Variable evaluate(Statement context, Program program) throws ExecutionException {
        BoolVariable result = (BoolVariable) content.evaluate(context, program);
        if (not)
            return result.not();
        else
            return result;
    }
}
