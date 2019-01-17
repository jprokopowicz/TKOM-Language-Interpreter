package com;

import com.interpreterParts.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    private final ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
    private final PrintStream standardOutput = System.out;
    private final Position position = new Position(3,4);

    @BeforeEach
    void setUp(){
        System.setOut(new PrintStream(testOutput));
    }

    @AfterEach
    void tearDown(){
        System.setOut(standardOutput);
        testOutput.reset();
    }

    @Test
    void print() {
        position.print();
        assertEquals("line: 3 sing: 4",testOutput.toString());
    }

    @Test
    void printFail() {
        position.print();
        assertNotEquals("not correct autput",testOutput.toString());
    }
}