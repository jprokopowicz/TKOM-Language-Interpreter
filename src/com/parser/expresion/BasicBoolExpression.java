package com.parser.expresion;

public class BasicBoolExpression extends Expression {
    private boolean negate;
    private Expression content;

    public BasicBoolExpression(Expression content, boolean negate) {
        this.negate = negate;
        this.content = content;
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
