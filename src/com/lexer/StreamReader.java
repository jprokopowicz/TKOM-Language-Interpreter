package com.lexer;

import java.io.IOException;
import java.io.InputStream;

public class StreamReader {
    private InputStream inputStream;
    private char storedByte;
    private boolean isByteStored;
    private Position position;

    public StreamReader(InputStream inputStream) {
        this.inputStream = inputStream;
        storedByte = '\0';
        isByteStored = false;
        position = new Position();
    }

    public Position getPosition() {
        return position;
    }

    public char readByte() throws IOException{
        if (endOfFile())
            throw new IOException();
        char sign;
        if(isByteStored){
            sign =  storedByte;
            isByteStored = false;
        } else {
            sign = (char) inputStream.read();
        }

        if(sign=='\n'){
            position.sign = 1;
            position.line++;
        } else {
            position.sign++;
        }

        return sign;
    }

    public char lookUpByte() throws IOException {
        if (endOfFile())
            throw new IOException();
        if (!isByteStored) {
            storedByte = (char) inputStream.read();
            isByteStored = true;
        }
        return storedByte;
    }

    public boolean endOfFile() throws IOException{
        return 0 == inputStream.available() + (isByteStored ? 1 : 0);
    }
}