package com.lexer;

import java.io.IOException;

public interface ByteReader {
    Position getPosition();
    char readByte() throws IOException;
    char lookUpByte() throws IOException;
    boolean endOfBytes() throws IOException;
}
