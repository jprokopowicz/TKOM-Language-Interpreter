package com.ast.statement;
import com.ast.Program;
import com.ast.expresion.Variable;

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

    public Variable getLocalVariable(String name) {
        return localVariables.get(name);
    }

    public Variable getVariable(String name) {
        if(localVariables.get(name) != null)
            return localVariables.get(name);

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
        //todo: make sure that objects are copied and not just references
//        this.program = statement.program;
//        this.parent = statement.parent;
//        for (Map.Entry localVariable : statement.localVariables.entrySet()) {
//            Variable variable;
//            if(localVariable instanceof NumberVariable)
//                variable = new NumberVariable((NumberVariable) localVariable);
//            else if (localVariable instanceof BoolVariable)
//                variable = new BoolVariable((BoolVariable) localVariable);
//            else //StringVariable
//                variable = new StringVariable((StringVariable) localVariable);
//            this.localVariables.put((String) localVariable.getKey(),variable);
//        }
//        for (Statement innerStatement : innerStatements)
//            this.innerStatements.add(innerStatement.copy());
    }
}
