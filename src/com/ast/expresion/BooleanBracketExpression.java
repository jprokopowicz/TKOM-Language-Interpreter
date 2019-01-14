package com.ast.expresion;

public class BooleanBracketExpression extends Expression {
    private BooleanExpression content;

    public BooleanBracketExpression(BooleanExpression content) {
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
