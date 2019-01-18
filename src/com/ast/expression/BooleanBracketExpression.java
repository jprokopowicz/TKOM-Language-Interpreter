package com.ast.expression;

import com.ast.Program;
import com.ast.statement.Statement;
import com.exceptions.executionExceptions.ExecutionException;

public class BooleanBracketExpression extends Expression {
    BooleanExpression content;

    public BooleanBracketExpression(BooleanExpression content) {
        this.content = content;
    }

    @Override
    public Variable evaluate(Statement context, Program program) throws ExecutionException {
        return content.evaluate(context,program);
    }
}
