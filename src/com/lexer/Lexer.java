package com.lexer;

import com.lexer.ByteReader.ByteReader;
import com.lexer.ByteReader.EndOfBytesException;

import java.io.IOException;

/**
 * Lexer for language interpreter
 */
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
     * Reads next token from the bytes.
     * @return Read token.
     */
    public Token readNextToken() {
        try {
            skipWhiteSigns();

            tokenPosition = new Position(reader.getPosition());

            char sign = reader.lookUpByte();
            if (Character.isAlphabetic(sign) || sign == '_') {
                defineKeywordOrIdentifier();
            } else if (Character.isDigit(sign)) {
                defineNumericLiteral();
            } else {
                defineSpecialSignOrString();
            }

        } catch (EndOfBytesException exc){
            token = new Token(Token.Type.end_of_bytes_,"",reader.getPosition());
        } catch (IOException exc) {
            token = new Token(Token.Type.invalid_, "",tokenPosition);
        }
        return token;
    }

    /**
     * Skips white signs to the next non-white sign.
     * @throws IOException ByteReader exception
     * @throws EndOfBytesException ByteReader goes to the end of Bytes
     */
    private void skipWhiteSigns() throws IOException, EndOfBytesException{
            while(Character.isWhitespace(reader.lookUpByte()))
                reader.readByte();
    }

    /**
     * After detecting a letter the method creates identifier or keyword token
     * @throws IOException ByteReader exception
     */
    private void defineKeywordOrIdentifier() throws IOException {
        buffer = new StringBuffer();
        continueToTokenEnd();
        token = new Token(Token.findKeyword(buffer.toString()),buffer.toString(),tokenPosition);
    }

    /**
     * After detecting a digit the method check if the numeric token is valid and creates its object.
     * @throws IOException ByteReader exception
     */
    private void defineNumericLiteral() throws IOException {
        buffer = new StringBuffer();
        NumberState state = NumberState.init;
        Token.Type tokenType = Token.Type.invalid_;
        try {
            while(Character.isAlphabetic(reader.lookUpByte()) || Character.isDigit(reader.lookUpByte()) || reader.lookUpByte() == '_') {
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
        } catch (EndOfBytesException exc){
            //token ended, next token is end of bytes
        }
        token = new Token(tokenType,buffer.toString(),tokenPosition);
    }

    /**
     * After detecting other sign then letter of digit the method tries to detect special sign, operator, string or comment
     * If it fails it crates invalid token
     * @throws IOException ByteReader Exception
     */
    private void defineSpecialSignOrString() throws IOException {
        buffer = new StringBuffer();
        Token.Type tokenType = Token.Type.invalid_;
        try {
            char sign = reader.readByte();
            buffer.append(sign);

            switch(sign){
                case ',':
                    tokenType = Token.Type.comma_;
                    break;
                case ';':
                    tokenType = Token.Type.semicolon_;
                    break;
                case '=':
                    tokenType = Token.Type.assign_;
                    if (reader.lookUpByte() == '=') {
                        buffer.append(reader.readByte());
                        tokenType = Token.Type.equal_;
                    }
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
                    tokenType = Token.Type.string_expression_;
                    if (!defineStringExpression())
                        tokenType = Token.Type.invalid_;
                    break;
                case '*':
                    tokenType = Token.Type.star_;
                    break;
                case '/':
                    tokenType = Token.Type.slash_;
                    if(reader.lookUpByte() == '*') {
                        buffer.append(reader.readByte());
                        tokenType = Token.Type.comment_;
                        if(!defineCommentExpression())
                            tokenType = Token.Type.invalid_;

                    }
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
                    tokenType = Token.Type.not_;
                    if(reader.lookUpByte() == '='){
                        buffer.append(reader.readByte());
                        tokenType = Token.Type.not_equal_;
                    }
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
                    tokenType = Token.Type.lesser_;
                    if(reader.lookUpByte() == '='){
                        buffer.append(reader.readByte());
                        tokenType = Token.Type.lesser_equal_;
                    }
                    break;
                case '>':
                    tokenType = Token.Type.greater_;
                    if(reader.lookUpByte() == '='){
                        buffer.append(reader.readByte());
                        tokenType = Token.Type.greater_equal_;
                    }
                    break;

                default:
                    tokenType = Token.Type.invalid_;
            }
        } catch (EndOfBytesException exc) {
            if (tokenType == Token.Type.string_expression_ || tokenType == Token.Type.comment_)
                tokenType = Token.Type.invalid_;
        }

        token = new Token(tokenType, buffer.toString(), tokenPosition);
    }

    /**
     * After detecting '"' the method tries to complete the string expression
     * @return If the expression was completed successfully
     * @throws IOException ByteReader exception
     * @throws EndOfBytesException Only in case when after '\' sing there is end of bytes
     */
    private boolean defineStringExpression() throws IOException, EndOfBytesException {
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

    /**
     * After detecting "\*" the method tries to complete the comment expression.
     * @return If the expression was completed successfully
     * @throws IOException ByteReader exception
     * @throws EndOfBytesException Only in case when after '*' there is end of file
     */
    private boolean defineCommentExpression() throws IOException, EndOfBytesException {
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

    private void continueToTokenEnd() throws IOException {
        try {
            while (Character.isAlphabetic(reader.lookUpByte()) || Character.isDigit(reader.lookUpByte()) || reader.lookUpByte() == '_')
                buffer.append(reader.readByte());
        } catch (EndOfBytesException exc) {
            //token ended, next token is end of bytes
        }
    }
}
