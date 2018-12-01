package com.lexer;

import java.io.IOException;

public class Lexer {
    private ByteReader reader;
    private Token token;
    private Position tokenPosition;
    private StringBuffer buffer;

    private enum NumberState { init, zero, nonZero}

    public Lexer(ByteReader reader) {
        this.reader = reader;
        token = new Token(Token.Type.invalid_, "",  new Position());
        tokenPosition = new Position();
        buffer = new StringBuffer();
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
    public Token readNextToken() {
        if (!skipWhiteSigns()) {
            return token;
        }
        tokenPosition = new Position(reader.getPosition());
        try {
            char sign = reader.lookUpByte();
            if (Character.isAlphabetic(sign) || sign == '_') {
                defineKeywordOrIdentifier();
            } else if (Character.isDigit(sign)) {
                defineNumericLiteral();
            } else {
                defineSpecialSignOrString();
            }

        } catch (EndOfBytesException exc){
            token = new Token(Token.Type.end_of_file_,"",reader.getPosition());
        } catch (IOException exc) {
            token = new Token(Token.Type.invalid_, "",tokenPosition);
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
            tokenPosition = reader.getPosition();
            while(!reader.endOfBytes() && Character.isWhitespace(reader.lookUpByte())){
                reader.readByte();
            }
        } catch (EndOfBytesException exc) {
            token = new Token(Token.Type.end_of_file_, "", reader.getPosition());
            return false;
        } catch (IOException e) {
            token = new Token(Token.Type.invalid_, "", tokenPosition);
            return false;
        }
        return true;
    }

    private void defineKeywordOrIdentifier() throws EndOfBytesException {
        buffer = new StringBuffer();
        continueToTokenEnd();
        token = new Token(Token.findKeyword(buffer.toString()),buffer.toString(),tokenPosition);
    }

    private void defineNumericLiteral() throws IOException,EndOfBytesException {
        buffer = new StringBuffer();
        NumberState state = NumberState.init;
        Token.Type tokenType = Token.Type.invalid_;
        while(!reader.endOfBytes() && (Character.isAlphabetic(reader.lookUpByte()) || Character.isDigit(reader.lookUpByte()) || reader.lookUpByte() == '_')) {
            switch (state) {
                case init:
                    if (reader.lookUpByte() == '0')
                        state = NumberState.zero;
                    else
                        state = NumberState.nonZero;
                    buffer.append(reader.readByte());
                    tokenType = Token.Type.number_expression_;
                    break;
                case zero:
                    if (Character.isDigit(reader.lookUpByte()) || Character.isAlphabetic(reader.lookUpByte()) || reader.lookUpByte() == '_') {
                        continueToTokenEnd();
                        tokenType = Token.Type.invalid_;
                    }
                    break;
                case nonZero:
                    if (Character.isDigit(reader.lookUpByte())) {
                        buffer.append(reader.readByte());
                    } else if (Character.isAlphabetic(reader.lookUpByte()) || reader.lookUpByte() == '_') {
                        continueToTokenEnd();
                        tokenType = Token.Type.invalid_;
                    }
                    break;
            }
        }
        token = new Token(tokenType,buffer.toString(),tokenPosition);
    }

    private void defineSpecialSignOrString() throws IOException,EndOfBytesException {
        buffer = new StringBuffer();
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
                if(reader.lookUpByte() == '*') {
                    buffer.append(reader.readByte());
                    if(defineCommentExpression())
                        tokenType = Token.Type.comment_;
                    else
                        tokenType = Token.Type.invalid_;

                } else
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

            default:
                tokenType = Token.Type.invalid_;
        }
        token = new Token(tokenType, buffer.toString(), tokenPosition);
    }

    private boolean defineStringExpression() throws IOException,EndOfBytesException {
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

    private boolean defineCommentExpression() throws IOException,EndOfBytesException {
        char c;
        while(!reader.endOfBytes()) {
            c = reader.readByte();
            buffer.append(c);
            if(c == '*' && reader.lookUpByte() == '/'){
                buffer.append(reader.readByte());
                return true;
            }
        }
        return false;
    }

    /**
     * Reads token value to the end and stores it in the buffer.
     */
    private void continueToTokenEnd() throws EndOfBytesException {
        try {
            while (!reader.endOfBytes() && ( Character.isAlphabetic(reader.lookUpByte()) || Character.isDigit(reader.lookUpByte()) || reader.lookUpByte() == '_'))
                buffer.append(reader.readByte());
        } catch (IOException exc) {
            System.out.println(exc.getMessage());
            exc.printStackTrace();
        }
    }
}
