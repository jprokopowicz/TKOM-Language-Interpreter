package com.ast.statement;
import com.executionExceptions.CopyException;
import com.executionExceptions.ExecutionException;
import com.ast.Program;
import com.ast.expresion.BoolVariable;
import com.ast.expresion.NumberVariable;
import com.ast.expresion.StringVariable;
import com.ast.expresion.Variable;

import java.util.*;

abstract public class Statement {
    protected Map <String,Variable> localVariables = null;
    protected List <Statement> innerStatements = null;
    protected Statement parent = null;
    protected Program program;

    public Statement(Program program, boolean isScope){
        this.program = program;
        if(isScope) {
            localVariables = new HashMap<>();
            innerStatements = new LinkedList<>();
        }
    }

    public void addVariable(String name, Variable variable) {
        if (localVariables != null)
            localVariables.put(name,variable);
    }

    public Variable getLocalVariable(String name) {
        if (localVariables != null)
            return localVariables.get(name);
        return null;
    }

    public Variable getVariable(String name) {
        if (localVariables != null && localVariables.get(name) != null)
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
        if (innerStatements != null)
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


    public abstract void execute() throws ExecutionException;

    public abstract Statement copy() throws ExecutionException;

    protected void copyInternals(Statement statement) throws ExecutionException {
        this.program = statement.program;
        if (this.localVariables != null && statement.localVariables != null) {
            for(Map.Entry<String,Variable> entry : statement.localVariables.entrySet()) {
                String key = entry.getKey();
                Variable value;
                switch (entry.getValue().getType()) {
                    case number_:
                        value = new NumberVariable();
                        break;
                    case bool_:
                        value = new BoolVariable();
                        break;
                    case string_:
                        value = new StringVariable();
                        break;
                    default:
                        throw new CopyException("Variable type undefined");
                }
                this.localVariables.put(key,value);
            }
        }
        if(this.innerStatements != null && statement.innerStatements != null) {
            for (int i = 0 ; i < statement.innerStatements.size() ; ++i){
                Statement statementCopy = statement.innerStatements.get(i).copy();
                statementCopy.setParent(this);
                this.innerStatements.add(statementCopy);
            }
        }
    }
}
