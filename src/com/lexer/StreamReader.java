package com.lexer;

import java.io.IOException;
import java.io.InputStream;

public class StreamReader {
    private InputStream inputStream;
    private int readBytes;
    private Position position;

    public StreamReader(InputStream inputStream) {
        this.inputStream = inputStream;
        readBytes = 0;
        position = new Position();
    }

    public Position getPosition() {
        return position;
    }

    public char get() throws IOException{
        char sign = (char)inputStream.read();
        readBytes++;
        position.sign++;
        if(sign=='\n'){
            position.sign = 1;
            position.line++;
        }
        return sign;
    }

    public char peek() throws IOException {
        char sign = (char)inputStream.read();
        inputStream.reset();
        if(inputStream.available() < inputStream.skip(readBytes))
            throw new IOException();
        return sign;
    }

    public boolean eof() throws IOException{
        return inputStream.available() == 0;
    }

}