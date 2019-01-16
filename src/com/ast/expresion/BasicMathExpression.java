package com.ast.expresion;

import com.ast.Program;
import com.ast.statement.Statement;
import com.executionExceptions.ExecutionException;

public class BasicMathExpression extends Expression {
    private boolean negate;
    private Expression content;

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
