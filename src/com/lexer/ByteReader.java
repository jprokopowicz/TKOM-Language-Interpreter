package com.lexer;

import java.io.IOException;

/**
 * Interface for readers in lexer
 */
public interface ByteReader {
    Position getPosition();

    /**
     * Reads the next byte
     * @return Read byte
     * @throws IOException Any error connected to the source
     * @throws EndOfBytesException Trying to read end of bytes
     */
    char readByte() throws IOException,EndOfBytesException;

    /**
     * Checks the next read byte without moving the reader
     * @return Checked byte
     * @throws IOException Any error connected to the source
     * @throws EndOfBytesException Trying to check end of bytes
     */
    char lookUpByte() throws IOException,EndOfBytesException;

    /**
     * Checks the end of bytes
     * @return If thereader is at the en of bytes
     * @throws IOException Any error connected to the source
     */
    boolean endOfBytes() throws IOException;
}
