package com.ast.expresion;

import java.util.ArrayList;
import java.util.List;

public class ConjunctionExpression extends Expression {
    private List<BasicBoolExpression> basicBoolExpressions;

    public ConjunctionExpression() {
        basicBoolExpressions = new ArrayList<>();
    }

    public void addBasicBoolExpression(BasicBoolExpression basicBoolExpression) {
        basicBoolExpressions.add(basicBoolExpression);
    }

    @Override
    public Variable evaluate(){
        //todo
        return null;
    }

    @Override
    public Expression copy() {
        //todo
        return null;
    }
}
