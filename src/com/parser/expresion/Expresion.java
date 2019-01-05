package com.parser.expresion;

import com.parser.statement.Statement;

abstract public class Expresion {
    public abstract Variable evaluate();
    public abstract Expresion copy();
}
