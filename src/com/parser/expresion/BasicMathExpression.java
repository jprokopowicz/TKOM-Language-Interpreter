package com.parser.expresion;

public class BasicMathExpression extends Expresion {
    private boolean negate;
    private Expresion content;

    BasicMathExpression(Expresion content, boolean negate) {
        this.negate = negate;
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
