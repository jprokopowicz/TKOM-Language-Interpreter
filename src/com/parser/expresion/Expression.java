package com.parser.expresion;

abstract public class Expression {
    public abstract Variable evaluate();
    public abstract Expression copy();
}
