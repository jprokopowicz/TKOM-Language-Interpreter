package com.parser;

import com.lexer.ByteReader.ByteReader;
import com.lexer.Lexer;
import com.lexer.Token;
import com.parser.Variable.Variable;
import com.parser.statement.Program;
import com.parser.statement.Function;

public class Parser {
    private Lexer lexer;

    Parser(ByteReader byteReader) {
        lexer = new Lexer(byteReader);
    }

    Program parse() throws ParseException {
        Program program = new Program();
        while (lexer.readNextToken().getType() != Token.Type.end_of_bytes_) {
            Token.Type tokenType = lexer.getToken().getType();
            Function function;
            if (tokenType == Token.Type.number_ || tokenType == Token.Type.bool_ || tokenType == Token.Type.string_ || tokenType == Token.Type.void_) {
                function = parseFunction();
                program.addFunction(function);
            } else
                throw new ParseException(lexer.getToken());
        }
        return program;
    }

    private Function parseFunction() throws ParseException {
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
        while (lexer.readNextToken().getType() != Token.Type.close_bracket_) {
            //todo parse arguments
        }
    }

    private Variable parseVariableDeklaration() {
        //todo parse variable declaration
        return new Variable();
    }
}

