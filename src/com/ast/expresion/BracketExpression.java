package com.ast.expresion;

import com.ast.Program;
import com.ast.statement.Statement;
import com.executionExceptions.ExecutionException;

public class BracketExpression extends Expression {
    private MathExpression content;

    public BracketExpression(MathExpression content) {
        this.content = content;
    }

    @Override
    public Variable evaluate(Statement context, Program program) throws ExecutionException {
        return content.evaluate(context,program);
    }
}
