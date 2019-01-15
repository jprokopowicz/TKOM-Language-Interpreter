package com.ast.expresion;

import com.ast.Program;
import com.ast.statement.Statement;

public class BasicMathExpression extends Expression {
    private boolean negate;
    private Expression content;

    public BasicMathExpression(Expression content, boolean negate) {
        this.negate = negate;
        this.content = content;
    }

    @Override
    public Variable evaluate(Statement context, Program program) {
        //todo
        return null;
    }
}
