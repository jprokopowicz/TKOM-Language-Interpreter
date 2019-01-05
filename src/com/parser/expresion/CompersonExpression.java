package com.parser.expresion;

public class CompersonExpression extends Expression {
    public enum CompersonOperator {
        equal,
        notEqual,
        greater,
        greaterOrEqual,
        lesser,
        lesserOrEqual,
    }

    private Expression left;
    private Expression right;
    private CompersonOperator operator;

    public CompersonExpression(Expression left, Expression right, CompersonOperator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
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
