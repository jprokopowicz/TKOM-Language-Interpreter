package com.lexer;

import java.io.FileInputStream;
import java.io.IOException;

public class Lexer {
    private Reader reader;
    private Token token;

    public Lexer(FileInputStream fileInputStream) {
        this.reader = new Reader(fileInputStream);
        token = new Token(Token.Type.invalid_, "",  new Position());
    }

    public Token readNextToken(){
        //TODO: read nets token
        try{
            while(Character.isWhitespace(reader.peek())){
                reader.get();
                if(reader.eof()){
                    token = new Token(Token.Type.end_of_file_,"",reader.getPosition());
                    return token;
                }
            }


        } catch (IOException e) {
            token = new Token(Token.Type.invalid_, "",  new Position());
        }
        return token;
    }

    public Token getToken(){
        return token;
    }
}
