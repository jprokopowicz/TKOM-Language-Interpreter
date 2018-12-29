package com.parser;

import com.lexer.ByteReader.ByteReader;
import com.lexer.Lexer;
import com.lexer.Token;
import com.parser.parseException.DuplicationException;
import com.parser.parseException.ParseException;
import com.parser.parseException.UnexpectedToken;
import com.parser.parseException.UnknownNameException;
import com.parser.statement.Statement;
import com.parser.variable.BoolVariable;
import com.parser.variable.NumberVariable;
import com.parser.variable.StringVariable;
import com.parser.variable.Variable;
import com.parser.statement.Program;
import com.parser.statement.Function;
import javafx.util.Pair;

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
    Program parse() throws ParseException {
        program = new Program();
        while (lexer.readNextToken().getType() != Token.Type.end_of_bytes_) {
            Token.Type tokenType = lexer.getToken().getType();
            Function function;
            if (tokenType != Token.Type.number_ && tokenType != Token.Type.bool_ && tokenType != Token.Type.string_ && tokenType != Token.Type.void_)
                throw new UnexpectedToken(lexer.getToken());
            function = parseFunction();
            program.addFunction(function);
        }
        return program;
    }

    private Function parseFunction() throws ParseException {
        Return returnType = Return.getType(lexer.getToken().getValue());

        if(lexer.readNextToken().getType() != Token.Type.identifier_)
            throw new UnexpectedToken(lexer.getToken());
        String name = lexer.getToken().getValue();

        if(program.getFunction(name) != null)
            throw new DuplicationException(lexer.getToken());
        Function function = new Function(returnType, name, program);

        parseArguments(function);
        parseScope(function);

        return function;
    }

    private void parseArguments(Function function) throws ParseException {
        if(lexer.readNextToken().getType() != Token.Type.open_bracket_)
            throw new UnexpectedToken(lexer.getToken());
        if (lexer.readNextToken().getType() == Token.Type.close_bracket_)
            return;

        parseAndAddArgument(function);

        while (lexer.readNextToken().getType() == Token.Type.comma_) {
            lexer.readNextToken();
            parseAndAddArgument(function);
        }

        if (lexer.getToken().getType() != Token.Type.close_bracket_)
            throw new UnexpectedToken(lexer.getToken());
    }

    private void parseAndAddArgument(Function function) throws ParseException{
        Pair<Token, Variable> newVariable = parseVariableDeclaration();
        if(function.getVariable(newVariable.getKey().getValue()) != null)
            throw new DuplicationException(newVariable.getKey());
        function.addVariable(newVariable.getKey().getValue(), newVariable.getValue());
    }

    private void parseScope(Statement parent) throws ParseException {
        if (lexer.readNextToken().getType() != Token.Type.open_scope_)
            throw new UnexpectedToken(lexer.getToken());

        Token.Type tokenType = lexer.readNextToken().getType();
        switch (tokenType) {
            case number_: //variable declaration
                //fallthrough
            case bool_:
                //fallthrough
            case string_:
                Pair<Token, Variable> newVariable = parseVariableDeclaration();
                addVariable(newVariable, parent);
                break;
            case identifier_: //value assignment or function call
                if (program.getFunction(lexer.getToken().getValue()) != null){
                    ;//function call
                } else if (findVariable(lexer.getToken().getValue(), parent) != null) {
                    ;//value assign
                } else
                    throw new UnknownNameException(lexer.getToken());
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

    private Variable findVariable(String name, Statement parent) {
        Statement currentStatement = parent;
        Variable variable = null;
        while (currentStatement != null) {
            variable = currentStatement.getVariable(name);
            if (variable == null)
                currentStatement = currentStatement.getParent();
            else
                break;
        }
        return variable;
    }

    private void addVariable(Pair<Token,Variable> newVariable, Statement statement) throws ParseException{
        if (statement.getVariable(newVariable.getKey().getValue()) != null)
            throw new DuplicationException(newVariable.getKey());
        statement.addVariable(newVariable.getKey().getValue(),newVariable.getValue());
    }

    private Pair<Token, Variable> parseVariableDeclaration() throws ParseException {
        Token.Type tokenType = lexer.getToken().getType();
        if(tokenType != Token.Type.number_ && tokenType != Token.Type.bool_ && tokenType != Token.Type.string_)
            throw new UnexpectedToken(lexer.getToken());

        Variable.Type variableType = Variable.getType(lexer.getToken().getValue());

        tokenType = lexer.readNextToken().getType();
        if (tokenType != Token.Type.identifier_)
            throw new UnexpectedToken(lexer.getToken());

        Variable newVariable = new Variable();

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

    void parseIfExpression(Statement parent){

    }


}

