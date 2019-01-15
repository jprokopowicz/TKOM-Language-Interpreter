package com.ast.expresion;

import com.ast.Program;
import com.ast.statement.Statement;

abstract public class Expression {
    public abstract Variable evaluate(Statement context, Program program);
}
