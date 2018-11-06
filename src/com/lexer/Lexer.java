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

    /**
     * Reads next token from the stream.
     * @return Read token.
     */
    public Token readNextToken(){
        //TODO: read nets token
        if(!skipWhiteSigns()){
            return token;
        }

        Position tokenPosition = streamReader.getPosition();

        return token;
    }

    /**
     * Token getter
     * @return last detected token.
     */
    public Token getToken(){
        return token;
    }

    /**
     * Skips white signs in the input stream.
     * If it reached end of stream sets token.type to endOfFile_.
     * If there were any IO Exceptions during skipping it sets token.type to invalid_.
     * @return Are there any signs left to read.
     */
    private boolean skipWhiteSigns(){
        try{
            while(!streamReader.endOfFile()&&Character.isWhitespace(streamReader.lookUpByte())){
                streamReader.readByte();
            }
            if(streamReader.endOfFile()){
                token = new Token(Token.Type.end_of_file_,"", streamReader.getPosition());
                return false;
            }
        } catch (IOException e) {
            token = new Token(Token.Type.invalid_, "", new Position());
            return false;
        }
        return true;
    }

    private void defineKeywordOrIdentifier(){

    }

    private void defineNumericLiteral(){

    }

    private void defineSpecialSignOrString(){

    }

    private void defineStringExpression(StringBuffer buffer){

    }

    private void defineCommentExpression(StringBuffer buffer){

    }

    /**
     * Reads token value to the end and stores it in the buffer.
     * @param buffer StringBuffer for storing value.
     */
    private void continueToTokenEnd(StringBuffer buffer){
        try {
            if(streamReader.endOfFile()){
                return;
            }
            for (char sign = streamReader.lookUpByte(); Character.isAlphabetic(sign) || Character.isDigit(sign) || sign == '_'; sign = streamReader.lookUpByte())
                buffer.append(streamReader.readByte());
        } catch (IOException exc) {
            System.out.println(exc.getMessage());
        }
    }
}
