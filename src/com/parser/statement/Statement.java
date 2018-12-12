package com.parser.statement;
import  com.parser.Return;
import com.parser.Variable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

abstract public class Statement {
    protected Map <String,Variable> variables;
    protected List <Statement> innerStatements;
    Statement(){
        variables = new HashMap<>();
        innerStatements = new LinkedList<>();
    }

    public void addStatement(Statement newStatment) {
        innerStatements.add(newStatment);
    }

    abstract Return execute();
}
