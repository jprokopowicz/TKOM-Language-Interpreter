package com.ast;

import com.ast.expression.Variable;
import com.ast.statement.Function;
import com.exceptions.executionExceptions.ExecutionException;
import com.exceptions.executionExceptions.IncompleteException;

import java.util.HashMap;
import java.util.Map;

public class Program {
    private Map<String, Function> functions;

    public Program() {
        functions = new HashMap<>();
    }

    public void addFunction(Function newFunction) {
        functions.put(newFunction.getName(), newFunction);
    }

    public Function getFunction(String name) {
        return functions.get(name);
    }

    public Variable execute() throws ExecutionException {
        String mainName = "main";
        Function main = getFunction(mainName);
        if (main == null || !main.isDefined())
            throw new IncompleteException("Main program", mainName + "function");
        main = (Function) main.copy();
        main.execute(this);
        return main.getReturnValue();
    }
}
