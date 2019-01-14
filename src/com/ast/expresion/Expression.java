package com.ast.expresion;

abstract public class Expression {
    public abstract Variable evaluate();
    public abstract Expression copy();
}
