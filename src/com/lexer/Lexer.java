package com.lexer;

import java.io.FileInputStream;
import java.io.IOException;

public class Lexer {
    private StreamReader streamReader;
    private Token token;

    public Lexer(FileInputStream fileInputStream) {
        this.streamReader = new StreamReader(fileInputStream);
        token = new Token(Token.Type.invalid_, "",  new Position());
    }

    public Token readNextToken(){
        //TODO: read nets token
        try{
            while(Character.isWhitespace(streamReader.lookUpByte())){
                streamReader.readByte();
                if(streamReader.endOfFile()){
                    token = new Token(Token.Type.end_of_file_,"", streamReader.getPosition());
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
