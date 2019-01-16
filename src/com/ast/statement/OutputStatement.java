package com.ast.statement;

import com.ast.Program;
import com.ast.expresion.*;
import com.executionExceptions.ExecutionException;
import com.executionExceptions.IncompleteException;

public class OutputStatement extends Statement {
    private Expression outputValue;
    public OutputStatement(Statement parent, Expression outputValue){
        super(false);
        setParent(parent);
        this.outputValue = outputValue;
    }

    @Override
    public void execute(Program program) throws ExecutionException {
        Variable output = outputValue.evaluate(this,program);
        String message;
        switch (output.getType()) {
            case number_:
                NumberVariable number = (NumberVariable)output;
                if(number.getDenominator() == 1)
                    message = number.getNominator() + "";
                else
                    message = number.getNominator() + "" + number.getDenominator();
                break;
            case bool_:
                BoolVariable bool = (BoolVariable)output;
                if (bool.getValue())
                    message = "true";
                else
                    message = "false";
                break;
            case string_:
                StringVariable string = (StringVariable)output;
                message = string.getMessage();
                break;
            default:
                throw new IncompleteException("OutputStatement", "output type");
        }
        //todo: writing output
    }

    @Override
    public Statement copy() throws ExecutionException {
        OutputStatement copy = new OutputStatement(null,this.outputValue);
        copy.copyInternals(this);
        return copy;
    }
}
