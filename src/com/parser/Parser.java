package com.parser;

import com.lexer.ByteReader.ByteReader;
import com.lexer.Lexer;
import com.lexer.Token;
import com.parser.parseException.DuplicationException;
import com.parser.parseException.ParseException;
import com.parser.parseException.UnexpectedToken;
import com.parser.statement.Statement;
import com.parser.expresion.BoolVariable;
import com.parser.expresion.NumberVariable;
import com.parser.expresion.StringVariable;
import com.parser.expresion.Variable;
import com.parser.statement.Function;
import javafx.util.Pair;
import java.util.Arrays;
import java.util.List;


public class Parser {
    private Lexer lexer;
    private Program program = null;

    Parser(ByteReader byteReader) {
        lexer = new Lexer(byteReader);
    }

    /**
     * Starts parsing code, expects function declaration
     * @return parsed program
     * @throws ParseException in case of parsing error
     */
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
            case identifier_: //value assignment or function call
//                if (program.getFunction(lexer.getToken().getValue()) != null){
//                    //function call
//                    //if after identifier there is no '(' then it it value assign
//                    break;
//                } else if (findVariable(lexer.getToken().getValue(), parent) != null) {
//                    //value assign
//                    break;
//                } else
//                    //throw new UnknownNameException(lexer.getToken());

                break;
            case if_: //
                //todo: condition problem
                break;
            case loop_: //
                //todo: same
                break;
            case read_: //
                //todo: variable read
                break;
            case write_: //
                //todo: variable write
                break;
            case return_: //
                //todo: returned variable
                break;
            default:
                throw new UnexpectedToken(lexer.getToken());
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

    void parseValueAssigmentOrFunctionCall(Statement statement) throws ParseException {

    }
    void parseMathExpression(Statement parent) throws ParseException {
        parseMultiplicationExpression(parent);
        if(lexer.readNextToken().getType() == Token.Type.plus_ || lexer.getToken().getType() == Token.Type.minus_) {
            //plus or minus value operation
            lexer.readNextToken();
            parseMultiplicationExpression(parent);
        }

    }

    void parseMultiplicationExpression(Statement parent) throws ParseException {
        parseBasicMathExpression(parent);
        if(lexer.readNextToken().getType() == Token.Type.star_ || lexer.getToken().getType() == Token.Type.slash_ || lexer.getToken().getType() == Token.Type.modulo_) {
            //multi, dev or modulo operation
            lexer.readNextToken();
            parseBasicMathExpression(parent);
        }
    }

    void parseBasicMathExpression(Statement parent) throws ParseException {
        if(lexer.getToken().getType() == Token.Type.minus_) {
            //negate value
            lexer.readNextToken();
        }

        switch (lexer.getToken().getType()) {
            case number_:
                parseNumber(parent);
                //add number to calculating value
                break;
            case identifier_:
                parseVariableOrFunctionCall(parent);
                //add variable or function call to calculating value
                break;
            case open_bracket_:
                parseBracketExpression(parent);
            default:
                throw new UnexpectedToken(lexer.getToken());
        }
    }

    void parseBracketExpression(Statement parent) throws ParseException {
        lexer.readNextToken();
        parseMathExpression(parent);
//        if(lexer.readNextToken().getType() != Token.Type.close_bracket_)
//            throw new UnexpectedToken(lexer.getToken());
        acceptTokenTypeOrThrow(Token.Type.close_bracket_);
    }

    void parseNumber(Statement parent) throws ParseException {
        int integer, nominator = 0, denominator = 1;
//        if(lexer.getToken().getType() != Token.Type.number_expression_)
//            throw new UnexpectedToken(lexer.getToken());
        acceptTokenTypeOrThrow(Token.Type.number_expression_);
        try {
            integer = Integer.parseInt(lexer.getToken().getValue());
            if (lexer.readNextToken().getType() == Token.Type.hash_) {
//                if (lexer.readNextToken().getType() != Token.Type.number_expression_)
//                    throw new UnexpectedToken(lexer.getToken());
                acceptTokenTypeOrThrow(Token.Type.number_expression_);
                nominator = Integer.parseInt(lexer.getToken().getValue());
//                if (lexer.readNextToken().getType() != Token.Type.colon_)
//                    throw new UnexpectedToken(lexer.getToken());
                acceptTokenTypeOrThrow(Token.Type.colon_);
//                if (lexer.readNextToken().getType() != Token.Type.number_expression_)
//                    throw new UnexpectedToken(lexer.getToken());
                acceptTokenTypeOrThrow(Token.Type.number_expression_);
                denominator = Integer.parseInt(lexer.getToken().getValue());
            } else if (lexer.getToken().getType() == Token.Type.colon_) {
                nominator = integer;
                integer = 0;
//                if (lexer.readNextToken().getType() != Token.Type.number_expression_)
//                    throw new UnexpectedToken(lexer.getToken());
                acceptTokenTypeOrThrow(Token.Type.number_expression_);
                denominator = Integer.parseInt(lexer.getToken().getValue());
            }
        } catch (NumberFormatException exc) {
            throw new ParseException(exc.getMessage());
        }
        NumberVariable numberVariable = new NumberVariable(integer,nominator,denominator);
        //add variable to calculation
    }

    void parseVariableOrFunctionCall(Statement parent) throws ParseException {
    }

    void parseIfExpression(Statement parent) throws ParseException {

    }

    void parseLoopExpression(Statement parent) throws ParseException {

    }
    void parseReadExpression(Statement parent) throws ParseException {

    }

    void parseWriteExpression(Statement parent) throws ParseException {

    }

    void parseReturnExpression(Statement parent) throws ParseException {


    }

    void acceptTokenTypeOrThrow(Token.Type type) throws ParseException{
        if(lexer.getToken().getType() != type)
            throw new UnexpectedToken(lexer.getToken());
    }

    void acceptTokenTypeOrThrow(List<Token.Type> types) throws ParseException{
        for (Token.Type type: types) {
            if(lexer.getToken().getType() == type)
                break;
        }
        throw new UnexpectedToken(lexer.getToken());
    }
}

