package com.ast.expresion;

import com.ast.Program;
import com.ast.statement.Statement;

import java.util.ArrayList;
import java.util.List;

public class MathExpression extends Expression {
    public enum AdditionOperator {
        add,
        subtract,
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
    public Variable evaluate(Statement context, Program program) {
        //todo
        return null;
    }
}
