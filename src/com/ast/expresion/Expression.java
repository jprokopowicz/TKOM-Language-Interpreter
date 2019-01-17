package com.ast.expresion;

import com.ast.Program;
import com.ast.statement.Statement;
import com.exceptions.executionExceptions.ExecutionException;

abstract public class Expression {
    public abstract Variable evaluate(Statement context, Program program) throws ExecutionException;
}
