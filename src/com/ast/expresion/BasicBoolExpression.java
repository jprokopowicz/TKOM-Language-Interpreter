package com.ast.expresion;

import com.ast.Program;
import com.ast.statement.Statement;
import com.executionExceptions.ExecutionException;

public class BasicBoolExpression extends Expression {
    private boolean not;
    private Expression content;

    public BasicBoolExpression(Expression content, boolean not) {
        this.not = not;
        this.content = content;
    }

    @Override
    public Variable evaluate(Statement context, Program program) throws ExecutionException {
        BoolVariable result = (BoolVariable)content.evaluate(context,program);
        if(not)
            return result.not();
        else
            return result;
    }
}
