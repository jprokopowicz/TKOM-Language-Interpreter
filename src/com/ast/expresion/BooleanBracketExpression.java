package com.ast.expresion;

import com.ast.Program;
import com.ast.statement.Statement;

public class BooleanBracketExpression extends Expression {
    private BooleanExpression content;

    public BooleanBracketExpression(BooleanExpression content) {
        this.content = content;
    }

    @Override
    public Variable evaluate(Statement context, Program program) {
        //todo
        return null;
    }
}
