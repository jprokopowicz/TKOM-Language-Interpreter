package com.parser;

import com.lexer.ByteReader.ByteReader;
import com.lexer.Lexer;
import com.lexer.Token;
import com.parser.statement.Program;
import com.parser.statement.Statement;
import com.parser.statement.Function;

public class Parser {
    private Lexer lexer;

    Parser(ByteReader byteReader) {
        lexer = new Lexer(byteReader);
    }

    Program parse() throws ParseException {
        Program program = new Program();
        Token token;
        while (lexer.readNextToken().getType() != Token.Type.end_of_bytes_) {
            token = lexer.getToken();
            Token.Type tokenType = token.getType();
            Statement statement;
            if(tokenType == Token.Type.number_ || tokenType == Token.Type.bool_ || tokenType == Token.Type.string_ || tokenType == Token.Type.void_){
                statement = parseFunctionOrVariableDeclaration();
                if (statement instanceof Function) {
                    program.addFunction((Function)statement);
                } else {
                    program.addStatement(statement);
                }
            } else if(tokenType == Token.Type.identifier_) {
                statement = parseValueAssigment();
                program.addStatement(statement);
            } else
                //TODO: other place for creating this msg
                throw new ParseException("Illegal token: " + token.getValue() + " line: " + token.getPosition().line + " sign: " + token.getPosition().sign);
        }
        return program;
    }

    private Statement parseFunctionOrVariableDeclaration() throws ParseException{
        //TODO
        return new Function("");
    }

    private Statement parseValueAssigment() {

        //TODO
        return new Function("");
    }

}
