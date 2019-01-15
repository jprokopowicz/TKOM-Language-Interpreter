package com.ast.expresion;

import com.ast.Program;
import com.ast.statement.Statement;

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
    public Variable evaluate(Statement context, Program program) {
        //todo
        return null;
    }
}
