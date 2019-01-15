package com.ast.expresion;

import com.ast.Program;
import com.ast.statement.Statement;

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
    public Variable evaluate(Statement context, Program program){
        //todo
        return null;
    }
}
