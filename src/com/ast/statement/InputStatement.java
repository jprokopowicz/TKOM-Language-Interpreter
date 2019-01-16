package com.ast.statement;

import com.ast.Program;
import com.ast.expresion.BoolVariable;
import com.ast.expresion.NumberVariable;
import com.ast.expresion.StringVariable;
import com.ast.expresion.Variable;
import com.executionExceptions.ExecutionException;
import com.executionExceptions.IncompleteException;
import com.executionExceptions.InputOutputException;
import com.sun.xml.internal.ws.commons.xmlutil.Converter;

import java.util.Scanner;

public class InputStatement extends Statement {
    private String targetName;
    public InputStatement(Statement parent, String targetName){
        super(false);
        setParent(parent);
        this.targetName = targetName;
    }

    @Override
    public void execute(Program program) throws ExecutionException{
        Variable target = getVariable(targetName);
        if (target == null)
            throw new IncompleteException("InputStatement", "targetVariable");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        switch (target.getType()) {
            case number_:
                target.setValue(new NumberVariable(NumberVariable.parseNumber(input)));
                break;
            case bool_:
                switch (input) {
                    case "true":
                        target.setValue(new BoolVariable(true));
                        break;
                    case "false":
                        target.setValue(new BoolVariable((false)));
                        break;
                    default:
                        throw new InputOutputException("Input is not a boolean value");
                }
                break;
            case string_:
                target.setValue( new StringVariable(input));
                break;
            default:
        }
    }

    @Override
    public Statement copy() throws ExecutionException {
        InputStatement copy = new InputStatement(null,this.targetName);
        copy.copyInternals(this);
        return copy;
    }
}
