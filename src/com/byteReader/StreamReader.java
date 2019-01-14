package com.byteReader;

import com.Position;

import java.io.IOException;
import java.io.InputStream;

public class StreamReader implements ByteReader{
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

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public char readByte() throws IOException,EndOfBytesException {
        if (endOfBytes())
            throw new EndOfBytesException();
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

    @Override
    public char lookUpByte() throws IOException,EndOfBytesException {
        if (endOfBytes())
            throw new EndOfBytesException();
        if (!isByteStored) {
            storedByte = (char) inputStream.read();
            isByteStored = true;
        }
        return storedByte;
    }

    @Override
    public boolean endOfBytes() throws IOException{
        return 0 == inputStream.available() + (isByteStored ? 1 : 0);
    }
}