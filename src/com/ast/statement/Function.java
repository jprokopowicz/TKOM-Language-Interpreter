package com.ast.statement;

import com.ast.Program;
import com.ast.expresion.Variable;

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
            tokenToReturnTypeMap.put("number",number_);
            tokenToReturnTypeMap.put("bool",bool_);
            tokenToReturnTypeMap.put("string",string_);
            tokenToReturnTypeMap.put("void",void_);
        }
        public static Return getType(String value) {
            return tokenToReturnTypeMap.getOrDefault(value,invalid_);
        }
    }
    private Return returnType;
    private String name;
    private Variable returnValue = null;

    public List<String> argumentsNames;

    public Function(Return returnType, String name, Program program) {
        super(program);
        this.name = name;
        this.returnType = returnType;
        argumentsNames = new LinkedList<>();
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

    @Override
    public void execute() {
        //todo
    }

    @Override
    public Statement copy() throws TypeNotPresentException {
//        Function newFunction = new Function(this.returnType, this.name, this.program);
//        newFunction.copyInternals(this);
//        return newFunction;
        //todo
        return null;
    }
}
