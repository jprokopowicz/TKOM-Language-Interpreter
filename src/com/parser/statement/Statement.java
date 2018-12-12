package com.parser.statement;
import  com.parser.Return;
import com.parser.variable.Variable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

abstract public class Statement {
    private Map <String,Variable> localVariables;
    private List <Statement> innerStatements;
    private Statement parent = null;

    Statement(){
        localVariables = new HashMap<>();
        innerStatements = new LinkedList<>();
    }

    public void addStatement(Statement newStatment) {
        innerStatements.add(newStatment);
    }

    void setParent(Statement parent) {
        this.parent = parent;
    }

    Statement getParent() {
        return  parent;
    }

    abstract Return execute();
}
