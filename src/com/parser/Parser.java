package com.parser;

import com.lexer.ByteReader.ByteReader;
import com.lexer.Lexer;
import com.lexer.Token;
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

    Program parse() throws ParseException, DuplicationException {
        Program program = new Program();
        while (lexer.readNextToken().getType() != Token.Type.end_of_bytes_) {
            Token.Type tokenType = lexer.getToken().getType();
            Function function;
            if (tokenType != Token.Type.number_ && tokenType != Token.Type.bool_ && tokenType != Token.Type.string_ && tokenType != Token.Type.void_)
                throw new ParseException(lexer.getToken());
            function = parseFunction(program);
            program.addFunction(function);
        }
        return program;
    }

    private Function parseFunction(Program program) throws ParseException {
        Return.Type returnType = Return.getType(lexer.getToken().getValue());
        if (returnType == Return.Type.invalid_)
            throw new ParseException(lexer.getToken());
        String name;
        Function function;

        if(lexer.readNextToken().getType() != Token.Type.identifier_)
            throw new ParseException(lexer.getToken());
        name = lexer.getToken().getValue();
        function = new Function(returnType, name);

        if(lexer.readNextToken().getType() == Token.Type.open_bracket_)
            parseArguments(function);
        else
            throw new ParseException(lexer.getToken());

        //todo statement parse

        return function;
    }

    private void parseArguments(Function function) throws ParseException {
        do {
            Pair<String,Variable> newVariable = parseVariableDeclaration();
            try {
                function.addArgument(newVariable.getKey(), newVariable.getValue());
            } catch (DuplicationException exc) {
                throw new ParseException(lexer.getToken());
            }
        } while (lexer.readNextToken().getType() == Token.Type.comma_);

        if (lexer.getToken().getType() != Token.Type.close_bracket_)
            throw new ParseException(lexer.getToken());
    }

    private Pair<String, Variable> parseVariableDeclaration() throws ParseException {
        Token.Type tokenType = lexer.readNextToken().getType();
        if(tokenType != Token.Type.number_ && tokenType != Token.Type.bool_ && tokenType != Token.Type.string_)
            throw new ParseException(lexer.getToken());

        Variable.Type variableType = Variable.getType(lexer.getToken().getValue());

        tokenType = lexer.readNextToken().getType();
        if (tokenType != Token.Type.identifier_)
            throw new ParseException(lexer.getToken());

        String variableName = lexer.getToken().getValue();

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

        return new Pair<>(variableName,newVariable);
    }
}

