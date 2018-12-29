package com.parser;

import com.lexer.ByteReader.ByteReader;
import com.lexer.Lexer;
import com.lexer.Token;
import com.parser.parseException.DuplicationException;
import com.parser.parseException.ParseException;
import com.parser.parseException.UnexpectedToken;
import com.parser.variable.BoolVariable;
import com.parser.variable.NumberVariable;
import com.parser.variable.StringVariable;
import com.parser.variable.Variable;
import com.parser.statement.Program;
import com.parser.statement.Function;
import javafx.util.Pair;

public class Parser {
    private Lexer lexer;

    Parser(ByteReader byteReader) {
        lexer = new Lexer(byteReader);
    }

    /**
     * Starts parsing code, expects function declaration
     * @return parsed program
     * @throws ParseException in case of parsing error
     */
    Program parse() throws ParseException {
        Program program = new Program();
        while (lexer.readNextToken().getType() != Token.Type.end_of_bytes_) {
            Token.Type tokenType = lexer.getToken().getType();
            Function function;
            if (tokenType != Token.Type.number_ && tokenType != Token.Type.bool_ && tokenType != Token.Type.string_ && tokenType != Token.Type.void_)
                throw new UnexpectedToken(lexer.getToken());
            function = parseFunction(program);
            program.addFunction(function);
        }
        return program;
    }

    private Function parseFunction(Program program) throws ParseException {
        Return returnType = Return.getType(lexer.getToken().getValue());
        if (returnType == Return.invalid_)
            throw new UnexpectedToken(lexer.getToken());

        if(lexer.readNextToken().getType() != Token.Type.identifier_)
            throw new UnexpectedToken(lexer.getToken());
        String name = lexer.getToken().getValue();
        if(program.getFunction(name) != null)
            throw new DuplicationException(lexer.getToken());
        Function function = new Function(returnType, name);

        if(lexer.readNextToken().getType() != Token.Type.open_bracket_)
            throw new UnexpectedToken(lexer.getToken());
        parseArguments(function);

        //todo statement parse

        return function;
    }

    private void parseArguments(Function function) throws ParseException {
        do {
            Pair<Token,Variable> newVariable = parseVariableDeclaration();
            if(function.getArgument(newVariable.getKey().getValue()) != null)
                throw new DuplicationException(newVariable.getKey());
            function.addArgument(newVariable.getKey().getValue(), newVariable.getValue());
        } while (lexer.readNextToken().getType() == Token.Type.comma_);

        if (lexer.getToken().getType() != Token.Type.close_bracket_)
            throw new UnexpectedToken(lexer.getToken());
    }

    private Pair<Token, Variable> parseVariableDeclaration() throws ParseException {
        Token.Type tokenType = lexer.readNextToken().getType();
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
}

