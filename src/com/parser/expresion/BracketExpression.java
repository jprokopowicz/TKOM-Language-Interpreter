package com.parser.expresion;

public class BracketExpression extends Expression {
    private MathExpression content;

    public BracketExpression(MathExpression content) {
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
