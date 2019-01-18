package com.byteReader;

import com.interpreterParts.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class StreamReaderTest {

    private final String example =
            "1234\n" +
                    "abcd\n" +
                    "5678\n" +
                    "efgh";

    private StreamReader streamReader;

    private final InputStream inputStream = new ByteArrayInputStream(example.getBytes());

    @BeforeEach
    void setUp() {
        streamReader = new StreamReader(inputStream);
    }

    @AfterEach
    void tearDown() {
        try {
            inputStream.reset();
        } catch (IOException exc) {
            System.out.println(exc.getMessage());
        }
    }

    @Test
    void getPositionBeginning() {

        Position position = streamReader.getPosition();
        assertEquals(1, position.line);
        assertEquals(1, position.sign);
    }

    @Test
    void getPositionInFirstLine() {
        Position position = new Position();
        try {
            for (int i = 0; i < 3; ++i) {
                streamReader.readByte();
            }
            position = streamReader.getPosition();
        } catch (IOException | EndOfBytesException exc) {
            fail("IO/endOfFile exception");
        }
        assertEquals(1, position.line);
        assertEquals(4, position.sign);
    }

    @Test
    void getPosition() {
        Position position = new Position();
        try {
            for (int i = 0; i < 6; ++i) {
                streamReader.readByte();
            }
            position = streamReader.getPosition();
        } catch (IOException | EndOfBytesException exc) {
            fail("IO/endOfFile exception");
        }
        assertEquals(2, position.line);
        assertEquals(2, position.sign);
    }

    @Test
    void readByteBeginning() {
        char sign = '\0';
        try {
            sign = streamReader.readByte();
        } catch (IOException | EndOfBytesException exc) {
            fail("IO/endOfFile exception");
        }
        assertEquals('1', sign);
    }

    @Test
    void readByte() {
        char sign = '\0';
        try {
            for (int i = 0; i < 10; ++i) {
                streamReader.readByte();
            }
            sign = streamReader.readByte();
        } catch (IOException | EndOfBytesException exc) {
            fail("IO/endOfFile exception");
        }
        assertEquals('5', sign);
    }

    @Test
    void readBytesWhiteSign() {
        char sign = '\0';
        try {
            for (int i = 0; i < 4; ++i) {
                streamReader.readByte();
            }
            sign = streamReader.readByte();
        } catch (IOException | EndOfBytesException exc) {
            fail("IO/endOfFile exception");
        }
        assertEquals('\n', sign);
    }

    @Test
    void readBytesAfterLookUp() {
        char sign = '\0';
        try {
            for (int i = 0; i < 3; ++i) {
                streamReader.lookUpByte();
            }
            sign = streamReader.readByte();
        } catch (IOException | EndOfBytesException exc) {
            fail("IO/endOfFile exception");
        }
        assertEquals('1', sign);
    }

    @Test
    void lookUpByteBegin() {
        char sign = '\0';
        try {
            sign = streamReader.lookUpByte();
        } catch (IOException | EndOfBytesException exc) {
            fail("IO/endOfFile exception");
        }
        assertEquals('1', sign);
    }

    @Test
    void lookUpByte() {
        char sign = '\0';
        char[] readBytes = new char[3];
        try {
            for (int i = 0; i < 3; ++i) {
                readBytes[i] = streamReader.readByte();
            }
            sign = streamReader.lookUpByte();
        } catch (IOException | EndOfBytesException exc) {
            fail("IO/endOfFile exception");
        }
        assertEquals('1', readBytes[0]);
        assertEquals('2', readBytes[1]);
        assertEquals('3', readBytes[2]);
        assertEquals('4', sign);
    }

    @Test
    void lookUpByteWhiteSign() {
        try {
            for (int i = 0; i < 4; ++i) {
                streamReader.readByte();
            }

        } catch (IOException | EndOfBytesException exc) {
            fail("IO/endOfFile exception");
        }
    }

    @Test
    void endOfFileTrue() {
        try {
            for (int i = 0; i < 19; ++i) {
                streamReader.readByte();
            }
            assertTrue(streamReader.endOfBytes());
        } catch (IOException | EndOfBytesException exc) {
            fail("IO/endOfFile exception");
        }
    }

    @Test
    void endOfFileWithLookUp() {
        try {
            for (int i = 0; i < 18; ++i) {
                streamReader.readByte();
            }
            boolean[] isEnd = new boolean[3];
            isEnd[0] = streamReader.endOfBytes();
            streamReader.lookUpByte();
            isEnd[1] = streamReader.endOfBytes();
            streamReader.readByte();
            isEnd[2] = streamReader.endOfBytes();
            assertFalse(isEnd[0]);
            assertFalse(isEnd[1]);
            assertTrue(isEnd[2]);
        } catch (IOException | EndOfBytesException exc) {
            fail("IO/endOfFile exception");
        }
    }

    @Test
    void readByteEndOfStream() {
        try {
            for (int i = 0; i < 19; ++i) {
                streamReader.readByte();
            }
        } catch (IOException | EndOfBytesException exc) {
            fail("Reader should be able to read 19 bytes");
        }

        try {
            streamReader.readByte();
            fail("No endOfFile exception");
        } catch (IOException exc) {
            fail("No endOfFile exception");
        } catch (EndOfBytesException exc) {
            //pass
        }
    }

    @Test
    void lookUpEndOfFile() {
        try {
            for (int i = 0; i < 19; ++i) {
                streamReader.readByte();
            }
        } catch (IOException | EndOfBytesException exc) {
            fail("Reader should be able to read 19 bytes");
        }

        try {
            streamReader.lookUpByte();
            fail("No endOfFile exception");
        } catch (IOException exc) {
            fail("No endOfFile exception");
        } catch (EndOfBytesException exc) {
            //pass
        }
    }

}