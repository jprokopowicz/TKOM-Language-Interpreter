package com.ast.expresion;

import com.ast.Program;
import com.ast.statement.Statement;

public class ComparisonExpression extends Expression {
    public enum ComparisonOperator {
        equal,
        notEqual,
        greater,
        greaterOrEqual,
        lesser,
        lesserOrEqual,
    }

    private Expression left;
    private Expression right;
    private ComparisonOperator operator;

    public ComparisonExpression(Expression left, Expression right, ComparisonOperator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public Variable evaluate(Statement context, Program program){
        //todo
        return null;
    }
}
