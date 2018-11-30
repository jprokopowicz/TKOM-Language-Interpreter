package com.lexer;

import java.io.IOException;

public class Lexer {
    private ByteReader reader;
    private Token token;
    private Position tokenPosition;
    private StringBuffer buffer;

    private enum NumberState { init, zero, nonZero, invalid, number}

    public Lexer(ByteReader reader) {
        this.reader = reader;
        token = new Token(Token.Type.invalid_, "",  new Position());
    }

    /**
     * Token getter
     * @return last detected token.
     */
    public Token getToken(){
        return token;
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
        tokenPosition = reader.getPosition();

        try {
            char sign = reader.lookUpByte();
            if (Character.isAlphabetic(sign) || sign == '_'){
                defineKeywordOrIdentifier();
            }  else if (Character.isDigit(sign)) {
                defineNumericLiteral();
            } else {
                defineSpecialSignOrString();
            }

        } catch (IOException e) {
            token = new Token(Token.Type.invalid_, "", reader.getPosition());
        }
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
            while(!reader.endOfBytes()&&Character.isWhitespace(reader.lookUpByte())){
                reader.readByte();
            }
            if(reader.endOfBytes()){
                token = new Token(Token.Type.end_of_file_,"", reader.getPosition());
                return false;
            }
        } catch (IOException e) {
            token = new Token(Token.Type.invalid_, "", reader.getPosition());
            return false;
        }
        return true;
    }

    private void defineKeywordOrIdentifier(){
        buffer.setLength(0);
        continueToTokenEnd();
        token = new Token(Token.findKeyword(buffer.toString()),buffer.toString(),tokenPosition);
    }

    private void defineNumericLiteral() throws IOException {
        buffer.setLength(0);
        char sign = reader.lookUpByte();
        NumberState state = NumberState.init;
        while(Character.isAlphabetic(sign) || Character.isDigit(sign) || sign == '_') {
            sign = reader.lookUpByte();
            switch (state) {
                case init:
                    if (sign == 0)
                        state = NumberState.zero;
                    else
                        state = NumberState.nonZero;
                    buffer.append(reader.readByte());
                    break;
                case zero:
                    if (Character.isDigit(sign) || Character.isAlphabetic(sign) || sign == '_') {
                        state = NumberState.invalid;
                        continueToTokenEnd();
                    }
                    break;
                case nonZero:
                    if(Character.isDigit(sign)){
                        buffer.append(reader.lookUpByte());
                    } else if (Character.isAlphabetic(sign) || sign == '_') {
                        state = NumberState.invalid;
                        continueToTokenEnd();
                    }
                    break;
            }
        }
        if(state == NumberState.zero || state == NumberState.nonZero) {
            token = new Token(Token.Type.number_, buffer.toString(), tokenPosition);
        } else { // invalid
            token = new Token(Token.Type.invalid_, buffer.toString(), tokenPosition);
        }
    }

    private void defineSpecialSignOrString(){

    }

    private void defineStringExpression(StringBuffer buffer){

    }

    private void defineCommentExpression(StringBuffer buffer){

    }

    /**
     * Reads token value to the end and stores it in the buffer.
     */
    private void continueToTokenEnd(){
        try {
            if(reader.endOfBytes()){
                return;
            }
            for (char sign = reader.lookUpByte(); Character.isAlphabetic(sign) || Character.isDigit(sign) || sign == '_'; sign = reader.lookUpByte())
                buffer.append(reader.readByte());
        } catch (IOException exc) {
            System.out.println(exc.getMessage());
        }
    }
}
