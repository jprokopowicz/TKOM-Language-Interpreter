package com.parser.expresion;

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
    public Variable evaluate() {
        //todo
        return null;
    }

    @Override
    public Expression copy() {
        //todo
        return null;
    }
}
