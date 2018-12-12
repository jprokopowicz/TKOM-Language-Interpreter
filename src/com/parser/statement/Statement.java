package com.parser.statement;
import  com.parser.Return;
import com.parser.Variable;

import java.util.Map;

abstract public class Statement {
    abstract Return execute();
    protected Map <String,Variable> variables;
}
