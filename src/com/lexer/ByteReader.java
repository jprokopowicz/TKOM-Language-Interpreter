package com.lexer;

import java.io.IOException;

public interface ByteReader {
    Position getPosition();
    char readByte() throws IOException,EndOfBytesException;
    char lookUpByte() throws IOException,EndOfBytesException;
    boolean endOfBytes() throws IOException;
}
