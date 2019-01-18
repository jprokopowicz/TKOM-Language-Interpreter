package com.ast.expression;

import com.ast.Program;
import com.ast.statement.Statement;
import com.exceptions.executionExceptions.ExecutionException;
import com.exceptions.executionExceptions.IncompleteException;

public class ComparisonExpression extends Expression {
    public enum ComparisonOperator {
        equal,
        notEqual,
        greater,
        greaterOrEqual,
        lesser,
        lesserOrEqual,
    }

    MathExpression left;
    MathExpression right;
    ComparisonOperator operator;

    public ComparisonExpression(MathExpression left, MathExpression right, ComparisonOperator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public Variable evaluate(Statement context, Program program) throws ExecutionException {
        NumberVariable leftNumber = (NumberVariable)left.evaluate(context,program);
        NumberVariable rightNumber = (NumberVariable)right.evaluate(context,program);
        switch (operator) {
            case equal:
                return new BoolVariable(leftNumber.equal(rightNumber));
            case notEqual:
                return new BoolVariable(leftNumber.notEqual(rightNumber));
            case greater:
                return new BoolVariable(leftNumber.greater(rightNumber));
            case greaterOrEqual:
                return new BoolVariable(leftNumber.greaterOrEqual(rightNumber));
            case lesser:
                return new BoolVariable(leftNumber.lesser(rightNumber));
            case lesserOrEqual:
                return new BoolVariable(leftNumber.lesserOrEqual(rightNumber));
            default:
                throw new IncompleteException("ComparisonExpression", "comparison operator");
        }
    }
}
