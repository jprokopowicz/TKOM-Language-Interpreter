package com.parser.statement;
import com.parser.expresion.Variable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

abstract public class Statement {
    private Map <String,Variable> localVariables;
    private List <Statement> innerStatements;
    private Statement parent = null;
    private Program program;

    Statement(Program program){
        this.program = program;
        localVariables = new HashMap<>();
        innerStatements = new LinkedList<>();
    }

    public void addVariable(String name, Variable variable) {
        localVariables.put(name,variable);
    }

    public Variable getVariable (String name) {
        return localVariables.get(name);
    }

    public void addStatement(Statement newStatment) {
        innerStatements.add(newStatment);
    }

    void setParent(Statement parent) {
        this.parent = parent;
    }

    public Statement getParent() {
        return  parent;
    }

    public Program getProgram() {
        return program;
    }

    abstract Variable execute();
}
