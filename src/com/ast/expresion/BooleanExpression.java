package com.ast.expresion;

import com.ast.Program;
import com.ast.statement.Statement;
import com.exceptions.executionExceptions.ExecutionException;

import java.util.ArrayList;
import java.util.List;

public class BooleanExpression extends Expression {
    private List<ConjunctionExpression> conjunctionExpressions;

    public BooleanExpression() {
        conjunctionExpressions = new ArrayList<>();
    }

    public void addConjunctionExpression(ConjunctionExpression conjunctionExpression) {
        conjunctionExpressions.add(conjunctionExpression);
    }

    @Override
    public Variable evaluate(Statement context, Program program) throws ExecutionException {
        BoolVariable result = (BoolVariable)conjunctionExpressions.get(0).evaluate(context,program);
        for(int i = 1 ; i < conjunctionExpressions.size() ; ++i)
            result = result.or((BoolVariable)conjunctionExpressions.get(i).evaluate(context,program));
        return result;
    }
}
