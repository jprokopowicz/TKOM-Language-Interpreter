package com.parser.expresion;

public class BracketExpression extends Expresion {
    private MathExpression content;

    BracketExpression(MathExpression content) {
        this.content = content;
    }

    @Override
    public Variable evaluate() {
        //todo
        return null;
    }

    @Override
    public Expresion copy() {
        //todo
        return null;
    }
}
