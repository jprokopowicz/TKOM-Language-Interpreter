package com.lexer;

import java.io.IOException;

public class Lexer {
    private ByteReader reader;
    private Token token;
    private Position tokenPosition;
    private StringBuffer buffer;

    private enum NumberState { init, zero, nonZero, invalid }

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
        NumberState state = NumberState.init;
        while(Character.isAlphabetic(reader.lookUpByte()) || Character.isDigit(reader.lookUpByte()) || reader.lookUpByte() == '_') {
            switch (state) {
                case init:
                    if (reader.lookUpByte() == 0)
                        state = NumberState.zero;
                    else
                        state = NumberState.nonZero;
                    buffer.append(reader.readByte());
                    break;
                case zero:
                    if (Character.isDigit(reader.lookUpByte()) || Character.isAlphabetic(reader.lookUpByte()) || reader.lookUpByte() == '_') {
                        state = NumberState.invalid;
                        continueToTokenEnd();
                    }
                    break;
                case nonZero:
                    if (Character.isDigit(reader.lookUpByte())) {
                        buffer.append(reader.lookUpByte());
                    } else if (Character.isAlphabetic(reader.lookUpByte()) || reader.lookUpByte() == '_') {
                        state = NumberState.invalid;
                        continueToTokenEnd();
                    }
                    break;
            }
        }
        if(state == NumberState.zero || state == NumberState.nonZero)
            token = new Token(Token.Type.number_expression_, buffer.toString(), tokenPosition);
        else // invalid
            token = new Token(Token.Type.invalid_, buffer.toString(), tokenPosition);

    }

    private void defineSpecialSignOrString() throws IOException{
        buffer.setLength(0);
        char sign = reader.readByte();
        buffer.append(sign);
        Token.Type tokenType;
        switch(sign){
            case ',':
                tokenType = Token.Type.comma_;
                break;
            case ';':
                tokenType = Token.Type.semicolon_;
                break;
            case '=':
                if(reader.lookUpByte() == '='){
                    buffer.append(reader.readByte());
                    tokenType = Token.Type.equal_;
                }else
                    tokenType = Token.Type.assign_;
                break;
            case ':':
                tokenType = Token.Type.colon_;
                break;
            case '#':
                tokenType = Token.Type.hash_;
                break;
            case '+':
                tokenType = Token.Type.plus_;
                break;
            case '-':
                tokenType = Token.Type.minus_;
                break;
            case '"':
                if(defineStringExpression())
                    tokenType = Token.Type.string_expression_;
                else
                    tokenType = Token.Type.invalid_;
                break;
            case '*':
                tokenType = Token.Type.star_;
                break;
            case '/':
                tokenType = Token.Type.slash_;
                break;
            case '%':
                tokenType = Token.Type.modulo_;
                break;
            case '|':
                tokenType = Token.Type.or_;
                break;
            case '&':
                tokenType = Token.Type.and_;
                break;
            case '!':
                if(reader.lookUpByte() == '='){
                    buffer.append(reader.readByte());
                    tokenType = Token.Type.not_equal_;
                }else
                    tokenType = Token.Type.not_;
                break;
            case '(':
                tokenType = Token.Type.open_bracket_;
                break;
            case ')':
                tokenType = Token.Type.close_bracket_;
                break;
            case '{':
                tokenType = Token.Type.open_scope_;
                break;
            case '}':
                tokenType = Token.Type.close_scope_;
                break;
            case '<':
                if(reader.lookUpByte() == '='){
                    buffer.append(reader.readByte());
                    tokenType = Token.Type.lesser_equal_;
                }
                else
                    tokenType = Token.Type.lesser_;
                break;
            case '>':
                if(reader.lookUpByte() == '='){
                    buffer.append(reader.readByte());
                    tokenType = Token.Type.greater_equal_;
                }
                else
                    tokenType = Token.Type.greater_;
                break;
            case '\\':
                if(reader.lookUpByte() == '*') {
                    buffer.append(reader.readByte());
                    if(defineCommentExpression())
                        tokenType = Token.Type.comment_;
                    else
                        tokenType = Token.Type.invalid_;

                } else
                    tokenType = Token.Type.invalid_;
                break;
            default:
                tokenType = Token.Type.invalid_;
        }
        token = new Token(tokenType, buffer.toString(), tokenPosition);
    }

    private boolean defineStringExpression() throws IOException{
        char c;
        while(!reader.endOfBytes()) {
            c = reader.readByte();
            buffer.append(c);
            if(c == '\\' && reader.lookUpByte() == '"')
                buffer.append(reader.readByte());
            else if(c == '"')
                return true;
        }
        return false;
    }

    private boolean defineCommentExpression() throws IOException{
        char c;
        while(!reader.endOfBytes()) {
            c = reader.readByte();
            buffer.append(c);
            if(c == '*' && reader.lookUpByte() == '\\'){
                buffer.append(reader.readByte());
                return true;
            }
        }
        return false;
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
