package com.parser.statement;
import com.parser.expresion.Variable;

import java.util.*;

abstract public class Statement {
    protected Map <String,Variable> localVariables;
    protected List <Statement> innerStatements;
    protected Statement parent = null;
    protected Program program;

    public Statement(Program program){
        this.program = program;
        localVariables = new HashMap<>();
        innerStatements = new LinkedList<>();
    }

    public void addVariable(String name, Variable variable) {
        localVariables.put(name,variable);
    }

    public Variable getVariable(String name) {
        Statement currentStatement = parent;
        Variable variable = null;
        while (currentStatement != null) {
            variable = currentStatement.getVariable(name);
            if (variable == null)
                currentStatement = currentStatement.getParent();
            else
                break;
        }
        return variable;
    }

    public void addStatement(Statement newStatment) {
        innerStatements.add(newStatment);
    }

    public void setParent(Statement parent) {
        this.parent = parent;
    }

    public Statement getParent() {
        return  parent;
    }

    public Program getProgram() {
        return program;
    }


    public abstract void execute();

    public abstract Statement copy();

    void copyInternals(Statement statement) {
        this.program = statement.program;
        this.parent = statement.parent;
        for (Map.Entry localVariable : statement.localVariables.entrySet())
            this.localVariables.put((String) localVariable.getKey(), new Variable((Variable) localVariable.getValue()));
        for (Statement innerStatement : innerStatements)
            this.innerStatements.add(innerStatement.copy());
    }
}
