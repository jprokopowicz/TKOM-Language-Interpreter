package com.ast.expression;

import com.ast.Program;
import com.ast.statement.Statement;
import com.exceptions.executionExceptions.ExecutionException;

import java.util.ArrayList;
import java.util.List;

public class ConjunctionExpression extends Expression {
    List<BasicBoolExpression> basicBoolExpressions;

    public ConjunctionExpression() {
        basicBoolExpressions = new ArrayList<>();
    }

    public void addBasicBoolExpression(BasicBoolExpression basicBoolExpression) {
        basicBoolExpressions.add(basicBoolExpression);
    }

    @Override
    public Variable evaluate(Statement context, Program program) throws ExecutionException {
        BoolVariable result = (BoolVariable) basicBoolExpressions.get(0).evaluate(context, program);
        for (int i = 1; i < basicBoolExpressions.size(); ++i)
            result = result.and((BoolVariable) basicBoolExpressions.get(i).evaluate(context, program));
        return result;
    }
}
