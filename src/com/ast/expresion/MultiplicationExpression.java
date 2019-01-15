package com.ast.expresion;

import com.ast.Program;
import com.ast.statement.Statement;

import java.util.ArrayList;
import java.util.List;

public class MultiplicationExpression extends Expression {
    public enum MultiplicationOperator {
        multiply,
        divide,
        modulo,
    }

    private List<BasicMathExpression> basicMathExpressions;
    private List<MultiplicationOperator> multiplicationOperators;

    public MultiplicationExpression() {
        basicMathExpressions = new ArrayList<>();
        multiplicationOperators = new ArrayList<>();
    }

    public void addBasicMathExpression(BasicMathExpression basicMathExpression) {
        basicMathExpressions.add(basicMathExpression);
    }

    public void addMultiplicationOperator(MultiplicationOperator operator) {
        multiplicationOperators.add(operator);
    }

    @Override
    public Variable evaluate(Statement context, Program program){
        //todo
        return  null;
    }
}
