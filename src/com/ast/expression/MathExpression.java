package com.ast.expression;

import com.ast.Program;
import com.ast.statement.Statement;
import com.exceptions.executionExceptions.ExecutionException;

import java.util.ArrayList;
import java.util.List;

public class MathExpression extends Expression {
    public enum AdditionOperator {
        add,
        subtract,
    }

    List<MultiplicationExpression> multiplicationExpressions;
    List<AdditionOperator> additionOperators;

    public MathExpression() {
        multiplicationExpressions = new ArrayList<>();
        additionOperators = new ArrayList<>();
    }

    public void addMultiplicationExpression(MultiplicationExpression multiplicationExpression) {
        multiplicationExpressions.add(multiplicationExpression);
    }

    public void addAdditionOperator(AdditionOperator operator) {
        additionOperators.add(operator);
    }

    @Override
    public Variable evaluate(Statement context, Program program) throws ExecutionException {
        NumberVariable result = (NumberVariable)multiplicationExpressions.get(0).evaluate(context,program);
        for(int i = 1 ; i < multiplicationExpressions.size() ; ++i) {
            if(additionOperators.get(i-1) == AdditionOperator.add)
                result = result.plus((NumberVariable)multiplicationExpressions.get(i).evaluate(context,program));
            else // subtract
                result = result.minus((NumberVariable)multiplicationExpressions.get(i).evaluate(context,program));
        }
        return result;
    }
}
