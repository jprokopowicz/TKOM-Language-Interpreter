package com.ast.expression;

import com.ast.Program;
import com.ast.statement.Statement;
import com.exceptions.executionExceptions.ExecutionException;

public class BasicMathExpression extends Expression {
    boolean negate;
    Expression content;

    public BasicMathExpression(Expression content, boolean negate) {
        this.negate = negate;
        this.content = content;
    }

    @Override
    public Variable evaluate(Statement context, Program program) throws ExecutionException {
        NumberVariable result = (NumberVariable)content.evaluate(context,program);
        if(negate)
            return result.negate();
        else
            return result;

    }
}
