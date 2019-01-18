package com.ast.statement;

import com.ast.Program;
import com.ast.expression.*;
import com.exceptions.executionExceptions.ConflictException;
import com.exceptions.executionExceptions.ExecutionException;
import com.exceptions.executionExceptions.IncompleteException;

public class ValueAssignment extends Statement {
    private String targetName;
    private Expression value;

    public String getTargetName() {
        return targetName;
    }

    public Expression getValue() {
        return value;
    }

    public ValueAssignment(Statement parent, String targetName, Expression value){
        super(false);
        setParent(parent);
        this.targetName = targetName;
        this.value = value;
    }

    @Override
    public void execute(Program program) throws ExecutionException{
        Variable target = getVariable(targetName);
        if(target == null)
            throw new IncompleteException("ValueAssignment","target");
        Variable assignedValue = value.evaluate(this,program);
        if(target.getType() != assignedValue.getType())
            throw new ConflictException("ValueAssignment", "target", "assigned value");
        switch (target.getType()) {
            case number_:
                NumberVariable number = (NumberVariable)target, numValue = (NumberVariable)assignedValue;
                number.setValue(numValue.getNominator(),numValue.getDenominator());
                break;
            case bool_:
                BoolVariable bool = (BoolVariable)target, boolValue = (BoolVariable) assignedValue;
                bool.setValue(boolValue.getValue());
                break;
            case string_:
                StringVariable string = (StringVariable)target, stringValue = (StringVariable)assignedValue;
                string.setMessage(stringValue.getMessage());
            default:
                throw new IncompleteException("ValueAssignment", "target type");
        }
    }

    @Override
    public Statement copy() throws ExecutionException {
        ValueAssignment copy = new ValueAssignment(null,this.targetName,this.value);
        copy.copyInternals(this);
        return copy;
    }
}
