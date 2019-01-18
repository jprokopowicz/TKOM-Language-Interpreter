package com.ast.statement;

import com.ast.Program;
import com.ast.expression.Variable;
import com.exceptions.executionExceptions.ExecutionException;
import com.exceptions.executionExceptions.ReturnException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Function extends Statement {
    public enum Return {
        number_,
        bool_,
        string_,
        void_,
        invalid_;

        private static Map<String, Return> tokenToReturnTypeMap;

        static {
            tokenToReturnTypeMap = new HashMap<>();
            tokenToReturnTypeMap.put("number", number_);
            tokenToReturnTypeMap.put("bool", bool_);
            tokenToReturnTypeMap.put("string", string_);
            tokenToReturnTypeMap.put("void", void_);
        }

        public static Return getType(String value) {
            return tokenToReturnTypeMap.getOrDefault(value, invalid_);
        }
    }

    private Return returnType;
    private String name;
    private Variable returnValue = null;
    private boolean defined;
    public List<String> argumentsNames;

    public Function(Return returnType, String name) {
        super(true);
        this.returnType = returnType;
        this.name = name;
        argumentsNames = new LinkedList<>();
        defined = false;
    }

    public Return getReturnType() {
        return returnType;
    }

    public String getName() {
        return name;
    }

    public Variable getReturnValue() {
        return returnValue;
    }

    public boolean isDefined() {
        return defined;
    }

    public void setDefined(boolean defined) {
        this.defined = defined;
    }

    @Override
    public void execute(Program program) throws ExecutionException {
        try {
            for (Statement instruction : innerStatements)
                instruction.execute(program);
        } catch (ReturnException exc) {
            returnValue = exc.getReturnValue();
        }
    }

    @Override
    public Statement copy() throws ExecutionException {
        Function copy = new Function(this.returnType, this.name);
        copy.copyInternals(this);
        copy.setParent(null);
        copy.defined = this.defined;
        copy.argumentsNames = this.argumentsNames;
        return copy;
    }
}
