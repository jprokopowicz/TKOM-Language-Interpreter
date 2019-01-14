package com.ast.expresion;

public class BasicMathExpression extends Expression {
    private boolean negate;
    private Expression content;

    public BasicMathExpression(Expression content, boolean negate) {
        this.negate = negate;
        this.content = content;
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
