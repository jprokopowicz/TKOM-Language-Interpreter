package com;

import com.ast.Program;
import com.byteReader.ByteReader;
import com.ast.expresion.*;
import com.parseException.*;
import com.ast.statement.*;
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
        lexer.readNextToken();
        acceptTokenTypeOrThrow(Token.Type.identifier_);
        String name = lexer.getToken().getValue();

        Function function = program.getFunction(name);
        if(function != null) {
            if(function.isDefined())
                throw new DuplicationException(lexer.getToken());
        } else {
            function = new Function(returnType, name, program);
            function.setDefined(true);
        }

        lexer.readNextToken();
        parseArguments(function);
        acceptTokenTypeOrThrow(Arrays.asList(Token.Type.open_scope_, Token.Type.semicolon_));
        if(lexer.getToken().getType() == Token.Type.open_scope_) {
            parseScope(function);
            function.setDefined(true);
        } else //semicolon
            function.setDefined(false);
        return function;
    }

    void parseArguments(Function function) throws ParseException {
        acceptTokenTypeOrThrowAndReadToken(Token.Type.open_bracket_);
        if (lexer.getToken().getType() == Token.Type.close_bracket_)
            return;
        if(!function.isDefined() && function.argumentsNames.size() == 0)
            throw new WrongArgumentException(0, lexer.getToken());

        Token argumentStartToken = lexer.getToken();
        parseAndAddArgument(function, 0);
        int argNumber;
        for (argNumber = 1 ; lexer.readNextToken().getType() == Token.Type.comma_ ; ++argNumber) {
            lexer.readNextToken();
            parseAndAddArgument(function, argNumber);
        }
        if (argNumber != function.argumentsNames.size())
            throw new WrongArgumentException(argNumber, argumentStartToken);
        acceptTokenTypeOrThrowAndReadToken(Token.Type.close_bracket_);
    }

    void parseAndAddArgument(Function function, int argNumber) throws ParseException {
        Pair<Token, Variable> newVariable = parseVariable();
        if (!function.isDefined()) {
            if (!function.argumentsNames.get(argNumber).equals(newVariable.getKey().getValue()) ||
                    function.getVariable(function.argumentsNames.get(argNumber)) == null)
                throw new WrongArgumentException(newVariable.getKey());
            Variable.Type variableType = function.getVariable(function.argumentsNames.get(argNumber)).getType();
            if (variableType != newVariable.getValue().getType()) {
                String variableTypeName;
                switch (variableType) {
                    case number_:
                        variableTypeName = "number";
                        break;
                    case bool_:
                        variableTypeName = "bool";
                        break;
                    case string_:
                        variableTypeName = "string";
                        break;
                    default:
                            throw new ParseException("Unexpected parse exception: invalid variable type", lexer.getToken());
                }
                throw new TypeException(variableTypeName, newVariable.getKey());
            }
        } else
            addArgument(newVariable,function);
    }

    void addArgument(Pair<Token,Variable> newVariable, Function function) throws ParseException {
        addVariable(newVariable,function);
        function.argumentsNames.add(newVariable.getKey().getValue());
    }

    void parseScope(Statement statement) throws ParseException {
        acceptTokenTypeOrThrowAndReadToken(Token.Type.open_scope_);
        while (lexer.getToken().getType() != Token.Type.close_scope_) {
            switch (lexer.getToken().getType()) {
                case number_: //variable declaration
                case bool_://fallthrough
                case string_://fallthrough
                    parseVariableDeclaration(statement);
                    break;
                case identifier_:
                    parseVariableAssignOrFunctionCall(statement);
                    break;
                case if_:
                    parseIfExpression(statement);
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
        }
    }

    void parseVariableDeclaration(Statement statement) throws ParseException {
        Pair<Token, Variable> newVariable = parseVariable();
        addVariable(newVariable, statement);
        lexer.readNextToken();
        acceptTokenTypeOrThrowAndReadToken(Token.Type.semicolon_);
    }

    void addVariable(Pair<Token,Variable> newVariable, Statement statement) throws ParseException{
        if (statement.getLocalVariable(newVariable.getKey().getValue()) != null)
            throw new DuplicationException(newVariable.getKey());
        statement.addVariable(newVariable.getKey().getValue(),newVariable.getValue());
    }

    Pair<Token, Variable> parseVariable() throws ParseException {
        acceptTokenTypeOrThrow(Arrays.asList(Token.Type.number_, Token.Type.bool_, Token.Type.string_));
        Variable.Type variableType = Variable.getType(lexer.getToken().getValue());
        lexer.readNextToken();
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
        while (lexer.getToken().getType() == Token.Type.plus_ || lexer.getToken().getType() == Token.Type.minus_) {
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
            case number_expression_:
                content = parseNumber();
                break;
            case identifier_:
                content = parseVariableOrFunctionCall(statement, "number");//reade next token inside
                break;
            case open_bracket_:
                content = parseBracketExpression(statement);
                break;
            default:
                throw new UnexpectedToken(lexer.getToken());
        }
        if(!(content instanceof NumberVariable) && !(content instanceof FunctionCallExpression) && !(content instanceof VariableCall) && !(content instanceof BracketExpression))
            throw new TypeException("Expected math expression",currentToken);
        return new BasicMathExpression(content,negate);
    }

    Expression parseVariableOrFunctionCall(Statement statement, String type) throws ParseException {
        Token nameToken = lexer.getToken();
        Expression variableOrFunctionCall;
        if(lexer.readNextToken().getType() == Token.Type.open_bracket_)
            variableOrFunctionCall = parseFunctionCall(nameToken, statement, type);
        else
            variableOrFunctionCall = parseVariableCall(nameToken,statement,type);
        return variableOrFunctionCall;
    }

    VariableCall parseVariableCall(Token nameToken, Statement statement,String type) throws ParseException {
        String name = nameToken.getValue();
        if(statement.getVariable(name) == null)
            throw new UnknownNameException(nameToken);
        if(Variable.getType(type) != statement.getVariable(name).getType())
            throw new TypeException(type,nameToken);
        return new VariableCall(name,statement);
    }

    BracketExpression parseBracketExpression(Statement statement) throws ParseException {
        lexer.readNextToken();
        MathExpression mathExpression = parseMathExpression(statement);
        lexer.readNextToken();
        acceptTokenTypeOrThrowAndReadToken(Token.Type.close_bracket_);
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
                acceptTokenTypeOrThrowAndReadToken(Token.Type.colon_);
                acceptTokenTypeOrThrow(Token.Type.number_expression_);
                denominator = Integer.parseInt(lexer.getToken().getValue());
                lexer.readNextToken();
            } else if (lexer.getToken().getType() == Token.Type.colon_) {
                nominator = integer;
                integer = 0;
                lexer.readNextToken();
                acceptTokenTypeOrThrow(Token.Type.number_expression_);
                denominator = Integer.parseInt(lexer.getToken().getValue());
                lexer.readNextToken();
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
        while (lexer.getToken().getType() == Token.Type.or_) {
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
        while (lexer.getToken().getType() == Token.Type.and_) {
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
            case false_:
                content = parseBool();
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

    BoolVariable parseBool() {
        BoolVariable boolVariable;
        if(lexer.getToken().getType() == Token.Type.true_)
            boolVariable = new BoolVariable(true);
        else
            boolVariable = new BoolVariable(false);
        lexer.readNextToken();
        return boolVariable;
    }

    BooleanBracketExpression parseBooleanBracketExpression(Statement statement) throws ParseException {
        lexer.readNextToken();
        BooleanExpression booleanExpression = parseBooleanExpression(statement);
        lexer.readNextToken();
        acceptTokenTypeOrThrowAndReadToken(Token.Type.close_bracket_);
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
        acceptTokenTypeOrThrowAndReadToken(Token.Type.close_comparison_);
        return new ComparisonExpression(first,second,operator);
    }

    StringVariable parseStringExpression(Statement statement) throws ParseException {
        if(lexer.getToken().getType() == Token.Type.identifier_) {
            if (statement.getVariable(lexer.getToken().getValue()) == null)
                throw new UnknownNameException(lexer.getToken());
            Variable variable = statement.getVariable(lexer.getToken().getValue());
            if(!(variable instanceof StringVariable))
                throw new TypeException("string", lexer.getToken());
            lexer.readNextToken();
            return (StringVariable)variable;
        } else if (lexer.getToken().getType() == Token.Type.string_expression_) {
            return parseString();
        } else
            throw new UnexpectedToken(lexer.getToken());
    }

    StringVariable parseString() {
        String string = lexer.getToken().getValue();
        string = string.substring(1,string.length()-1);
        StringVariable stringVariable = new StringVariable();
        stringVariable.setMessage(string);
        lexer.readNextToken();
        return stringVariable;
    }

    void parseVariableAssignOrFunctionCall(Statement statement) throws ParseException {
        Token nameToken = lexer.getToken();
        Statement variableOrFunctionCall;
        if(lexer.readNextToken().getType() == Token.Type.open_bracket_) {
            variableOrFunctionCall = new FunctionCallStatement(program,statement,parseFunctionCall(nameToken, statement,""));
        } else if (lexer.getToken().getType() == Token.Type.assign_) {
            variableOrFunctionCall = parseValueAssignment(nameToken, statement);
        } else
            throw new UnexpectedToken(lexer.getToken());
        variableOrFunctionCall.setParent(statement);
        acceptTokenTypeOrThrowAndReadToken(Token.Type.semicolon_);
        statement.addStatement(variableOrFunctionCall);
    }

    FunctionCallExpression parseFunctionCall(Token nameToken, Statement statement, String type) throws ParseException {
        Function function = program.getFunction(nameToken.getValue());
        if(function == null)
            throw new UnknownNameException(nameToken);
        if (type.length() > 0 && Function.Return.getType(type) != function.getReturnType())
            throw new TypeException(type,nameToken);
        FunctionCallExpression functionCallExpression = new FunctionCallExpression(nameToken.getValue(),statement,program);
        lexer.readNextToken();
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
            if(function.argumentsNames.size() > 1 && !name.equals(function.argumentsNames.get(function.argumentsNames.size()-1))){
                acceptTokenTypeOrThrowAndReadToken(Token.Type.comma_);
            }
        }
        acceptTokenTypeOrThrowAndReadToken(Token.Type.close_bracket_);
        return functionCallExpression;
    }

    ValueAssignment parseValueAssignment(Token nameToken, Statement statement) throws  ParseException {
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
        ValueAssignment valueAssignment = new ValueAssignment(program,statement,nameToken.getValue(),value);
        valueAssignment.setParent(statement);
        return valueAssignment;
    }

    void parseIfExpression(Statement statement) throws ParseException {
        lexer.readNextToken();
        IfStatement ifStatement = new IfStatement(program,statement);
        ifStatement.setParent(statement);
        parseAndAddCondition(statement,ifStatement);
        parseScope(ifStatement);

        if(lexer.readNextToken().getType() == Token.Type.else_){
            IfStatement elseStatement = new IfStatement(program,statement);
            elseStatement.setParent(statement);
            elseStatement.setCondition(null);
            lexer.readNextToken();
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
        parseAndAddCondition(statement,loopStatement);
        parseScope(loopStatement);
        statement.addStatement(loopStatement);
        lexer.readNextToken();
    }

    void parseAndAddCondition(Statement statement, Statement conditionStatement) throws ParseException{
        acceptTokenTypeOrThrowAndReadToken(Token.Type.open_bracket_);
        BooleanExpression condition = parseBooleanExpression(statement);
        acceptTokenTypeOrThrowAndReadToken(Token.Type.close_bracket_);
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
        acceptTokenTypeOrThrowAndReadToken(Token.Type.semicolon_);
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
        acceptTokenTypeOrThrowAndReadToken(Token.Type.semicolon_);
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
        acceptTokenTypeOrThrowAndReadToken(Token.Type.semicolon_);
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

    void acceptTokenTypeOrThrowAndReadToken(Token.Type type) throws ParseException{
        acceptTokenTypeOrThrow(type);
        lexer.readNextToken();
    }

    void acceptTokenTypeOrThrowAndReadToken(List<Token.Type> types) throws ParseException{
        acceptTokenTypeOrThrow(types);
        lexer.readNextToken();
    }

    Token testReadNextToken(){
        Token token = lexer.getToken();
        lexer.readNextToken();
        return token;
    }
}

