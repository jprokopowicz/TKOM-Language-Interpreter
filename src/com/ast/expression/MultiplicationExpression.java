package com.ast.expression;

import com.ast.Program;
import com.ast.statement.Statement;
import com.exceptions.executionExceptions.ExecutionException;

import java.util.ArrayList;
import java.util.List;

public class MultiplicationExpression extends Expression {
    public enum MultiplicationOperator {
        multiply,
        divide,
        modulo,
    }

    List<BasicMathExpression> basicMathExpressions;
    List<MultiplicationOperator> multiplicationOperators;

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
    public Variable evaluate(Statement context, Program program) throws ExecutionException {
        NumberVariable result = (NumberVariable) basicMathExpressions.get(0).evaluate(context,program);
        for(int i = 1 ; i < basicMathExpressions.size() ; ++i) {
            if(multiplicationOperators.get(i-1) == MultiplicationOperator.multiply)
                result = result.multiply((NumberVariable)basicMathExpressions.get(i).evaluate(context,program));
            else if (multiplicationOperators.get(i-1) == MultiplicationOperator.divide)
                result = result.dev((NumberVariable)basicMathExpressions.get(i).evaluate(context,program));
            else //modulo
                result = result.mod((NumberVariable)basicMathExpressions.get(i).evaluate(context,program));
        }
        return result;
    }
}
