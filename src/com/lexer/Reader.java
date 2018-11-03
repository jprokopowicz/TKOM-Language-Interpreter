package com.lexer;

import java.io.FileInputStream;
import java.io.IOException;

public class Reader {
    private FileInputStream fileInputStream;
    private int readBytes;
    private Position position;

    public Reader(FileInputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
        readBytes = 0;
        position = new Position();
    }

    public char get() throws IOException{
        char sign = (char)fileInputStream.read();
        readBytes++;
        position.sign++;
        if(sign=='\n'){
            position.sign = 1;
            position.line++;
        }
        return sign;
    }

    public char peek() throws IOException {
        char sign = (char)fileInputStream.read();
        fileInputStream.reset();
        if(fileInputStream.available() < fileInputStream.skip(readBytes))
            throw new IOException();
        return sign;
    }

    public boolean eof() throws IOException{
        return fileInputStream.available() == 0;
    }
}
