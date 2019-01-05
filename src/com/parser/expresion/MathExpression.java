package com.parser.expresion;

import java.util.ArrayList;
import java.util.List;

public class MathExpression extends Expression {
    public enum AdditionOperator {
        add,
        substract,
    }

    private List<MultiplicationExpression> multiplicationExpressions;
    private List<AdditionOperator> additionOparators;

    public MathExpression() {
        multiplicationExpressions = new ArrayList<>();
        additionOparators = new ArrayList<>();
    }

    public void addMultiplicationExpression(MultiplicationExpression multiplicationExpression) {
        multiplicationExpressions.add(multiplicationExpression);
    }

    public void addAdditionOperator(AdditionOperator operator) {
        additionOparators.add(operator);
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
