package com.parser;

import com.lexer.ByteReader.ByteReader;
import com.lexer.Lexer;
import com.lexer.Token;
import com.parser.expresion.*;
import com.parser.parseException.*;
import com.parser.statement.*;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.List;


public class Parser {
    private Lexer lexer;
    private Program program = null;

    Parser(ByteReader byteReader) {
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

        parseArguments(function);
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
        Token.Type tokenType = lexer.readNextToken().getType();
        switch (tokenType) {
            case number_: //variable declaration
            case bool_://fallthrough
            case string_://fallthrough
                Pair<Token, Variable> newVariable = parseVariableDeclaration();
                addVariable(newVariable, statement);
                lexer.readNextToken();
                acceptTokenTypeOrThrow(Token.Type.semicolon_);
                break;
            case identifier_:
                parseVariableOrFunctionCall(statement);
                acceptTokenTypeOrThrow(Token.Type.semicolon_);
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
        lexer.readNextToken();
        acceptTokenTypeOrThrow(Token.Type.close_scope_);
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
        parseMultiplicationExpression(statement);
        if(lexer.readNextToken().getType() == Token.Type.plus_ || lexer.getToken().getType() == Token.Type.minus_) {
            //plus or minus value operation
            lexer.readNextToken();
            parseMultiplicationExpression(statement);
        }
        return null;
    }

    MultiplicationExpression parseMultiplicationExpression(Statement statement) throws ParseException {
        parseBasicMathExpression(statement);
        if(lexer.readNextToken().getType() == Token.Type.star_ || lexer.getToken().getType() == Token.Type.slash_ || lexer.getToken().getType() == Token.Type.modulo_) {
            //multi, dev or modulo operation
            lexer.readNextToken();
            parseBasicMathExpression(statement);
        }
        return null;
    }

    BasicMathExpression parseBasicMathExpression(Statement statement) throws ParseException {
        if(lexer.getToken().getType() == Token.Type.minus_) {
            //negate value
            lexer.readNextToken();
        }

        switch (lexer.getToken().getType()) {
            case number_:
                parseNumber(statement);
                //add number to calculating value
                break;
            case identifier_:
                parseVariableOrFunctionCall(statement);
                //add variable or function call to calculating value
                break;
            case open_bracket_:
                parseBracketExpression(statement);
            default:
                throw new UnexpectedToken(lexer.getToken());
        }
        return null;
    }

    BracketExpression parseBracketExpression(Statement statement) throws ParseException {
        lexer.readNextToken();
        parseMathExpression(statement);
        lexer.readNextToken();
        acceptTokenTypeOrThrow(Token.Type.close_bracket_);
        return null;
    }

    NumberVariable parseNumber(Statement statement) throws ParseException {
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
            throw new ParseException(exc.getMessage());
        }
        return new NumberVariable(integer,nominator,denominator);
    }

    BooleanExpression parseBooleanExpression(Statement statement) throws ParseException{
        return null;
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

    void parseVariableOrFunctionCall(Statement statement) throws ParseException {
        Token nameToken = lexer.getToken();
        Statement variableOrFunctionCall;
        if(lexer.readNextToken().getType() == Token.Type.open_bracket_) {
            variableOrFunctionCall = parseFunctionCall(nameToken, statement);
        } else if (lexer.getToken().getType() == Token.Type.assign_) {
            variableOrFunctionCall = parseValueAssignment(nameToken, statement);
        } else
            throw new UnexpectedToken(lexer.getToken());
        statement.addStatement(variableOrFunctionCall);
    }

    FunctionCallStatement parseFunctionCall(Token nameToken, Statement statement) throws ParseException {
        Function function = program.getFunction(nameToken.getValue());
        if(function == null)
            throw new UnknownNameException(nameToken);
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
        return new FunctionCallStatement(program,statement,functionCallExpression);
    }

    ValueAssigment parseValueAssignment(Token nameToken, Statement statement) throws  ParseException {
        lexer.readNextToken();
        Variable target = statement.getVariable(nameToken.getValue());
        if(target == null)
            throw new UnknownNameException(nameToken);
        Expresion value;
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
        return new ValueAssigment(program,statement,target,value);
    }

    void parseIfExpression(Statement statement) throws ParseException {

    }

    void parseLoopExpression(Statement statement) throws ParseException {

    }

    void parseReadExpression(Statement statement) throws ParseException {
        lexer.readNextToken();
        acceptTokenTypeOrThrow(Token.Type.identifier_);
        Variable target = statement.getVariable(lexer.getToken().getValue());
        if(target == null)
            throw new UnknownNameException(lexer.getToken());
        InputStatement inputStatement = new InputStatement(program,statement,target);
        lexer.readNextToken();
        acceptTokenTypeOrThrow(Token.Type.semicolon_);
    }

    void parseWriteExpression(Statement statement) throws ParseException {
        Expresion outputExpression;
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
        statement.addStatement(new OutputStatement(program,statement,outputExpression));
    }

    void parseReturnExpression(Statement statement) throws ParseException {
        Statement currerntStatement = statement;
        while (!(currerntStatement instanceof Function)) {
            currerntStatement = currerntStatement.getParent();
            if(currerntStatement == null)
                throw new ParseException("Return statement without function.");
        }

        lexer.readNextToken();
        Function function = (Function)currerntStatement;
        Expresion value;
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
        statement.addStatement(new ReturnStatement(program, statement, value));
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

