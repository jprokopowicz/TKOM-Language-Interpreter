package com.ast.statement;
import com.exceptions.executionExceptions.CopyException;
import com.exceptions.executionExceptions.ExecutionException;
import com.ast.Program;
import com.ast.expression.BoolVariable;
import com.ast.expression.NumberVariable;
import com.ast.expression.StringVariable;
import com.ast.expression.Variable;

import java.util.*;

abstract public class Statement {
    public Map <String,Variable> localVariables = null;
    public List <Statement> innerStatements = null;
    Statement parent = null;

    public Statement(boolean isScope){
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

    public abstract void execute(Program program) throws ExecutionException;

    public abstract Statement copy() throws ExecutionException;

    void copyInternals(Statement statement) throws ExecutionException {
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
