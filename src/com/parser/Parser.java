package com.parser;

import com.ast.Program;
import com.lexer.ByteReader.ByteReader;
import com.lexer.Lexer;
import com.lexer.Token;
import com.ast.expresion.*;
import com.parser.parseException.*;
import com.ast.statement.*;
import javafx.beans.binding.NumberExpression;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.List;


public class Parser {
    private Lexer lexer;
    private Program program = null;

    public Parser(ByteReader byteReader) {
        lexer = new Lexer(byteReader);
    }

    public Program parse() throws ParseException {
        program = new Program();
        while (lexer.readNextToken().getType() != Token.Type.end_of_bytes_) {
            Token.Type tokenType = lexer.getToken().getType();
            Function function;
            acceptTokenTypeOrThrow(Arrays.asList(Token.Type.number_, Token.Type.bool_, Token.Type.string_, Token.Type.void_));
            function = parseFunction();
            program.addFunction(function);
        }
        return program;
    }

    Function parseFunction() throws ParseException {
        Function.Return returnType = Function.Return.getType(lexer.getToken().getValue());

        acceptTokenTypeOrThrow(Token.Type.identifier_);
        String name = lexer.getToken().getValue();
        if(program.getFunction(name) != null)
            throw new DuplicationException(lexer.getToken());
        Function function = new Function(returnType, name, program);

        lexer.readNextToken();
        parseArguments(function);
        lexer.readNextToken();
        parseScope(function);
        return function;
    }

    void parseArguments(Function function) throws ParseException {
        acceptTokenTypeOrThrow(Token.Type.open_bracket_);
        if (lexer.readNextToken().getType() == Token.Type.close_bracket_)
            return;
        parseAndAddArgument(function);
        while (lexer.readNextToken().getType() == Token.Type.comma_) {
            lexer.readNextToken();
            parseAndAddArgument(function);
        }
        acceptTokenTypeOrThrow(Token.Type.close_bracket_);
    }

    void parseAndAddArgument(Function function) throws ParseException{
        Pair<Token, Variable> newVariable = parseVariableDeclaration();
        addVariable(newVariable,function);
        function.argumentsNames.add(newVariable.getKey().getValue());
    }

    void parseScope(Statement statement) throws ParseException {
        acceptTokenTypeOrThrow(Token.Type.open_scope_);
        lexer.readNextToken();
        while (lexer.getToken().getType() != Token.Type.close_scope_) {
            boolean stayOnToken = false;
            switch (lexer.getToken().getType()) {
                case number_: //variable declaration
                case bool_://fallthrough
                case string_://fallthrough
                    Pair<Token, Variable> newVariable = parseVariableDeclaration();
                    addVariable(newVariable, statement);
                    lexer.readNextToken();
                    acceptTokenTypeOrThrow(Token.Type.semicolon_);
                    break;
                case identifier_:
                    parseVariableAssignOrFunctionCall(statement);
                    acceptTokenTypeOrThrow(Token.Type.semicolon_);
                    break;
                case if_:
                    parseIfExpression(statement);
                    stayOnToken = true;
                    break;
                case loop_:
                    parseLoopExpression(statement);
                    break;
                case read_:
                    parseReadExpression(statement);
                    break;
                case write_:
                    parseWriteExpression(statement);
                    break;
                case return_:
                    parseReturnExpression(statement);
                    break;
                default:
                    throw new UnexpectedToken(lexer.getToken());
            }
            if (stayOnToken)
                stayOnToken = false;
            else
                lexer.readNextToken();
        }
    }

    void addVariable(Pair<Token,Variable> newVariable, Statement statement) throws ParseException{
        if (statement.getVariable(newVariable.getKey().getValue()) != null)
            throw new DuplicationException(newVariable.getKey());
        statement.addVariable(newVariable.getKey().getValue(),newVariable.getValue());
    }

    Pair<Token, Variable> parseVariableDeclaration() throws ParseException {
        acceptTokenTypeOrThrow(Arrays.asList(Token.Type.number_, Token.Type.bool_, Token.Type.string_));
        Variable.Type variableType = Variable.getType(lexer.getToken().getValue());
        acceptTokenTypeOrThrow(Token.Type.identifier_);

        Variable newVariable = null;
        switch (variableType) {
            case number_:
                newVariable = new NumberVariable();
                break;
            case bool_:
                newVariable = new BoolVariable();
                break;
            case string_:
                newVariable = new StringVariable();
                break;
        }
        return new Pair<>(lexer.getToken(),newVariable);
    }

    MathExpression parseMathExpression(Statement statement) throws ParseException {
        MathExpression mathExpression = new MathExpression();
        MultiplicationExpression newMultiplicationExpression;

        newMultiplicationExpression = parseMultiplicationExpression(statement);
        mathExpression.addMultiplicationExpression(newMultiplicationExpression);
        while (lexer.readNextToken().getType() == Token.Type.plus_ || lexer.getToken().getType() == Token.Type.minus_) {
            if(lexer.getToken().getType() == Token.Type.plus_)
                mathExpression.addAdditionOperator(MathExpression.AdditionOperator.add);
            else //minus
                mathExpression.addAdditionOperator(MathExpression.AdditionOperator.substract);
            lexer.readNextToken();
            newMultiplicationExpression = parseMultiplicationExpression(statement);
            mathExpression.addMultiplicationExpression(newMultiplicationExpression);
        }
        return mathExpression;
    }

    MultiplicationExpression parseMultiplicationExpression(Statement statement) throws ParseException {
        MultiplicationExpression multiplicationExpression = new MultiplicationExpression();
        BasicMathExpression newBasicMathExpression;

        newBasicMathExpression = parseBasicMathExpression(statement);
        multiplicationExpression.addBasicMathExpression(newBasicMathExpression);
        while (lexer.getToken().getType() == Token.Type.star_ || lexer.getToken().getType() == Token.Type.slash_ || lexer.getToken().getType() == Token.Type.modulo_) {
            if(lexer.getToken().getType()==Token.Type.star_)
                multiplicationExpression.addMultiplicationOperator(MultiplicationExpression.MultiplicationOperator.multiply);
            else if (lexer.getToken().getType()==Token.Type.slash_)
                multiplicationExpression.addMultiplicationOperator(MultiplicationExpression.MultiplicationOperator.divide);
            else //modulo
                multiplicationExpression.addMultiplicationOperator(MultiplicationExpression.MultiplicationOperator.modulo);

            lexer.readNextToken();
            newBasicMathExpression = parseBasicMathExpression(statement);
            multiplicationExpression.addBasicMathExpression(newBasicMathExpression);
        }
        return multiplicationExpression;
    }

    BasicMathExpression parseBasicMathExpression(Statement statement) throws ParseException {
        boolean negate = false;
        if(lexer.getToken().getType() == Token.Type.minus_) {
            negate = true;
            lexer.readNextToken();
        }

        Expression content;
        Token currentToken = lexer.getToken();
        switch (lexer.getToken().getType()) {
            case number_:
                content = parseNumber();
                lexer.readNextToken();
                break;
            case identifier_:
                content = parseVariableOrFunctionCall(statement, "number");//reade next token inside
                break;
            case open_bracket_:
                content = parseBracketExpression(statement);
                lexer.readNextToken();
                break;
            default:
                throw new UnexpectedToken(lexer.getToken());
        }
        if(!(content instanceof NumberExpression) && !(content instanceof FunctionCallExpression) && !(content instanceof VariableCall) && !(content instanceof BracketExpression))
            throw new TypeException("Expected math expression",currentToken);
        return new BasicMathExpression(content,negate);
    }

    Expression parseVariableOrFunctionCall(Statement statement, String type) throws ParseException {
        Token nameToken = lexer.getToken();
        Expression variableOrFunctionCall;
        if(lexer.readNextToken().getType() == Token.Type.open_bracket_)
            variableOrFunctionCall = parseFunctionCall(nameToken,statement,type);
        else
            variableOrFunctionCall = parseVariableCall(nameToken,statement,type);
        return variableOrFunctionCall;
    }

    VariableCall parseVariableCall(Token nameToken, Statement statement,String type) throws ParseException {
        String name = nameToken.getValue();
        if(statement.getVariable(name) == null)
            throw new UnknownNameException(nameToken);
        if(Variable.getType(type) != statement.getVariable(name).getType())
            throw new TypeException(type,lexer.getToken());
        return new VariableCall(name,statement);
    }

    BracketExpression parseBracketExpression(Statement statement) throws ParseException {
        lexer.readNextToken();
        MathExpression mathExpression = parseMathExpression(statement);
        lexer.readNextToken();
        acceptTokenTypeOrThrow(Token.Type.close_bracket_);
        return new BracketExpression(mathExpression);
    }

    NumberVariable parseNumber() throws ParseException {
        int integer, nominator = 0, denominator = 1;
        acceptTokenTypeOrThrow(Token.Type.number_expression_);
        try {
            integer = Integer.parseInt(lexer.getToken().getValue());
            if (lexer.readNextToken().getType() == Token.Type.hash_) {
                lexer.readNextToken();
                acceptTokenTypeOrThrow(Token.Type.number_expression_);
                nominator = Integer.parseInt(lexer.getToken().getValue());
                lexer.readNextToken();
                acceptTokenTypeOrThrow(Token.Type.colon_);
                lexer.readNextToken();
                acceptTokenTypeOrThrow(Token.Type.number_expression_);
                denominator = Integer.parseInt(lexer.getToken().getValue());
            } else if (lexer.getToken().getType() == Token.Type.colon_) {
                nominator = integer;
                integer = 0;
                lexer.readNextToken();
                acceptTokenTypeOrThrow(Token.Type.number_expression_);
                denominator = Integer.parseInt(lexer.getToken().getValue());
            }
        } catch (NumberFormatException exc) {
            throw new ParseException("Unexpected parse exception. Msg: " + exc.getMessage() + " ", lexer.getToken());
        }
        return new NumberVariable(integer,nominator,denominator);
    }

    BooleanExpression parseBooleanExpression(Statement statement) throws ParseException {
        BooleanExpression booleanExpression = new BooleanExpression();
        ConjunctionExpression conjunctionExpression;

        conjunctionExpression = parseConjunctionExpression(statement);
        booleanExpression.addConjunctionExpression(conjunctionExpression);
        while (lexer.readNextToken().getType() == Token.Type.or_) {
            lexer.readNextToken();
            conjunctionExpression = parseConjunctionExpression(statement);
            booleanExpression.addConjunctionExpression(conjunctionExpression);
        }
        return booleanExpression;
    }

    ConjunctionExpression parseConjunctionExpression(Statement statement) throws ParseException {
        ConjunctionExpression conjunctionExpression = new ConjunctionExpression();
        BasicBoolExpression basicBoolExpression;

        basicBoolExpression = parseBasicBoolExpression(statement);
        conjunctionExpression.addBasicBoolExpression(basicBoolExpression);
        while (lexer.readNextToken().getType() == Token.Type.and_) {
            lexer.readNextToken();
            basicBoolExpression = parseBasicBoolExpression(statement);
            conjunctionExpression.addBasicBoolExpression(basicBoolExpression);
        }
        return conjunctionExpression;
    }

    BasicBoolExpression parseBasicBoolExpression(Statement statement) throws ParseException {
        boolean negate = false;
        if (lexer.getToken().getType() == Token.Type.not_) {
            negate = true;
            lexer.readNextToken();
        }

        Expression content;
        Token currentToken = lexer.getToken();
        switch (lexer.getToken().getType()) {
            case true_:
                content = new BoolVariable(true);
                break;
            case false_:
                content = new BoolVariable(false);
                break;
            case identifier_:
                content = parseVariableOrFunctionCall(statement,"bool");
                break;
            case open_comparison_:
                content = parseComparisonExpression(statement);
                break;
            case open_bracket_:
                content = parseBooleanBracketExpression(statement);
                break;
            default:
                throw new UnexpectedToken(lexer.getToken());
        }

        if(!(content instanceof BoolVariable) &&
                !(content instanceof VariableCall) &&
                !(content instanceof FunctionCallExpression) &&
                !(content instanceof ComparisonExpression) &&
                !(content instanceof BooleanBracketExpression))
            throw new TypeException("Expected boolean expression",currentToken);
         return new BasicBoolExpression(content, negate);
    }

    BooleanBracketExpression parseBooleanBracketExpression(Statement statement) throws ParseException {
        lexer.readNextToken();
        BooleanExpression booleanExpression = parseBooleanExpression(statement);
        lexer.readNextToken();
        acceptTokenTypeOrThrow(Token.Type.close_bracket_);
        return new BooleanBracketExpression(booleanExpression);
    }

    ComparisonExpression parseComparisonExpression(Statement statement) throws ParseException {
        lexer.readNextToken();
        MathExpression first = parseMathExpression(statement);

        ComparisonExpression.ComparisonOperator operator;
        switch (lexer.getToken().getType()) {
            case equal_:
                operator = ComparisonExpression.ComparisonOperator.equal;
                break;
            case not_equal_:
                operator = ComparisonExpression.ComparisonOperator.notEqual;
                break;
            case greater_:
                operator = ComparisonExpression.ComparisonOperator.greater;
                break;
            case greater_equal_:
                operator = ComparisonExpression.ComparisonOperator.greaterOrEqual;
                break;
            case lesser_:
                operator = ComparisonExpression.ComparisonOperator.lesser;
                break;
            case lesser_equal_:
                operator = ComparisonExpression.ComparisonOperator.lesserOrEqual;
                break;
            default:
                throw new UnexpectedToken(lexer.getToken());
        }

        lexer.readNextToken();
        MathExpression second = parseMathExpression(statement);

        return new ComparisonExpression(first,second,operator);
    }

    StringVariable parseStringExpression(Statement statement) throws ParseException {
        if(lexer.getToken().getType() == Token.Type.identifier_) {
            Variable variable = statement.getVariable(lexer.getToken().getValue());
            if(!(variable instanceof StringVariable))
                throw new TypeException("string", lexer.getToken());
            return (StringVariable)variable;
        } else if (lexer.getToken().getType() == Token.Type.string_expression_) {
            String string = lexer.getToken().getValue();
            string = string.substring(1,string.length()-1);
            StringVariable stringVariable = new StringVariable();
            stringVariable.setMessage(string);
            return stringVariable;
        } else
            throw new UnexpectedToken(lexer.getToken());
    }

    Statement parseVariableAssignOrFunctionCall(Statement statement) throws ParseException {
        Token nameToken = lexer.getToken();
        Statement variableOrFunctionCall;
        if(lexer.readNextToken().getType() == Token.Type.open_bracket_) {
            variableOrFunctionCall = new FunctionCallStatement(program,statement,parseFunctionCall(nameToken, statement,""));
        } else if (lexer.getToken().getType() == Token.Type.assign_) {
            variableOrFunctionCall = parseValueAssignment(nameToken, statement);
        } else
            throw new UnexpectedToken(lexer.getToken());
        variableOrFunctionCall.setParent(statement);
        return variableOrFunctionCall;
    }

    FunctionCallExpression parseFunctionCall(Token nameToken, Statement statement, String type) throws ParseException {
        //todo: problem with not declared functions to this point
        Function function = program.getFunction(nameToken.getValue());
        if(function == null)
            throw new UnknownNameException(nameToken);
        if (type.length() > 0 && Function.Return.getType(type) != function.getReturnType())
            throw new TypeException(type,nameToken);
        FunctionCallExpression functionCallExpression = new FunctionCallExpression(nameToken.getValue(),statement,program);
        for (String name : function.argumentsNames) {
            Variable variable = function.getVariable(name);
            if(variable == null)
                throw new UnknownNameException(nameToken);
            switch (variable.getType()) {
                case number_:
                    functionCallExpression.addArgument(parseMathExpression(statement));
                    break;
                case bool_:
                    functionCallExpression.addArgument(parseBooleanExpression(statement));
                    break;
                case string_:
                    functionCallExpression.addArgument(parseStringExpression(statement));
                    break;
                case invalid_:
                default:
                    throw new ParseException("Unexpected parser error.",lexer.getToken());
            }
            lexer.readNextToken();
            if(function.argumentsNames.size() > 1 && !name.equals(function.argumentsNames.get(function.argumentsNames.size()-1))){
                acceptTokenTypeOrThrow(Token.Type.comma_);
            }
        }
        acceptTokenTypeOrThrow(Token.Type.close_bracket_);
        return functionCallExpression;
    }

    ValueAssigment parseValueAssignment(Token nameToken, Statement statement) throws  ParseException {
        lexer.readNextToken();
        Variable target = statement.getVariable(nameToken.getValue());
        if(target == null)
            throw new UnknownNameException(nameToken);
        Expression value;
        switch (target.getType()) {
            case number_:
                value = parseMathExpression(statement);
                break;
            case bool_:
                value = parseBooleanExpression(statement);
                break;
            case string_:
                value = parseStringExpression(statement);
                break;
            case invalid_://fallthrough
            default:
                throw new ParseException("Unexpected parser error.",lexer.getToken());
        }
        ValueAssigment valueAssigment = new ValueAssigment(program,statement,nameToken.getValue(),value);
        valueAssigment .setParent(statement);
        return valueAssigment;
    }

    void parseIfExpression(Statement statement) throws ParseException {
        lexer.readNextToken();
        IfStatement ifStatement = new IfStatement(program,statement);
        ifStatement.setParent(statement);
        parseConditionAndAdd(statement,ifStatement);
        lexer.readNextToken();
        parseScope(ifStatement);

        if(lexer.readNextToken().getType() == Token.Type.else_){
            IfStatement elseStatement = new IfStatement(program,statement);
            elseStatement.setParent(statement);
            elseStatement.setCondition(null);
            parseScope(elseStatement);
            ifStatement.setElseStatement(elseStatement);
            lexer.readNextToken();
        }

        statement.addStatement(ifStatement);
    }

    void parseLoopExpression(Statement statement) throws ParseException {
        lexer.readNextToken();
        LoopStatement loopStatement = new LoopStatement(program,statement);
        loopStatement.setParent(statement);
        parseConditionAndAdd(statement,loopStatement);
        lexer.readNextToken();
        parseScope(loopStatement);
    }

    void parseConditionAndAdd(Statement statement, Statement conditionStatement) throws ParseException{
        acceptTokenTypeOrThrow(Token.Type.open_bracket_);
        BooleanExpression condition = parseBooleanExpression(statement);
        lexer.readNextToken();
        acceptTokenTypeOrThrow(Token.Type.close_bracket_);
        if(conditionStatement instanceof IfStatement) {
            ((IfStatement) conditionStatement).setCondition(condition);
        } else if (conditionStatement instanceof LoopStatement) {
            ((LoopStatement) conditionStatement).setCondition(condition);
        } else
            throw new ParseException("Unexpected parser exception: statement to parse condition is not if or loop expression");
    }

    void parseReadExpression(Statement statement) throws ParseException {
        lexer.readNextToken();
        acceptTokenTypeOrThrow(Token.Type.identifier_);
        Variable target = statement.getVariable(lexer.getToken().getValue());
        if(target == null)
            throw new UnknownNameException(lexer.getToken());
        InputStatement inputStatement = new InputStatement(program,statement,target);
        inputStatement.setParent(statement);
        lexer.readNextToken();
        acceptTokenTypeOrThrow(Token.Type.semicolon_);
        statement.addStatement(inputStatement);
    }

    void parseWriteExpression(Statement statement) throws ParseException {
        Expression outputExpression;
        switch(lexer.readNextToken().getType()) {
            case number_:
                lexer.readNextToken();
                outputExpression = parseMathExpression(statement);
                break;
            case bool_:
                lexer.readNextToken();
                outputExpression = parseBooleanExpression(statement);
                break;
            case string_:
                lexer.readNextToken();
                outputExpression = parseStringExpression(statement);
                break;
            default:
                throw new UnexpectedToken(lexer.getToken());
        }
        acceptTokenTypeOrThrow(Token.Type.semicolon_);
        OutputStatement outputStatement = new OutputStatement(program,statement,outputExpression);
        outputStatement.setParent(statement);
        statement.addStatement(outputStatement);
    }

    void parseReturnExpression(Statement statement) throws ParseException {
        Statement currentStatement = statement;
        while (!(currentStatement instanceof Function)) {
            currentStatement = currentStatement.getParent();
            if(currentStatement == null)
                throw new ParseException("Return statement without function.");
        }

        lexer.readNextToken();
        Function function = (Function)currentStatement;
        Expression value;
        switch (function.getReturnType()) {
            case number_:
                value = parseMathExpression(statement);
                break;
            case bool_:
                value = parseBooleanExpression(statement);
                break;
            case string_:
                value = parseStringExpression(statement);
                break;
            case void_:
                value = null;
                break;
            case invalid_://fallthrough
            default:
                throw new ParseException("Invalid return type", lexer.getToken());
        }
        ReturnStatement returnStatement = new ReturnStatement(program, statement, value);
        returnStatement.setParent(statement);
        statement.addStatement(returnStatement);
        lexer.readNextToken();
        acceptTokenTypeOrThrow(Token.Type.semicolon_);
    }

    void acceptTokenTypeOrThrow(Token.Type type) throws ParseException{
        if(lexer.getToken().getType() != type)
            throw new UnexpectedToken(lexer.getToken());
    }

    void acceptTokenTypeOrThrow(List<Token.Type> types) throws ParseException{
        for (Token.Type type: types) {
            if(lexer.getToken().getType() == type)
                return;
        }
        throw new UnexpectedToken(lexer.getToken());
    }
}

