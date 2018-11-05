import com.lexer.Position;
import com.lexer.StreamReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class StreamReaderTest {

    private final String examples [] = {
            "main{\nvar fraction a = 1:2;\n}",
            "function bool isPositive(var fraction number){}",
            "if(a == b)\n{\na = c;\n}",
            "",
    };

//    @BeforeEach
//    void setUp() {
//    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getPosition() {
        InputStream inputStream = new ByteArrayInputStream(examples[0].getBytes());
        StreamReader streamReader = new StreamReader(inputStream);
        Position position;
        try{
            for(int i = 0 ; i < 20 ; ++i){
                streamReader.get();
            }
            position = streamReader.getPosition();
        } catch (IOException exc){
            position = new Position(0,0);
        }
        assertEquals(2,position.line);
        assertEquals(14,position.sign);
    }

    @Test
    void get() {
        InputStream inputStream = new ByteArrayInputStream(examples[0].getBytes());
        StreamReader streamReader = new StreamReader(inputStream);
        char sign;
        try{
            for(int i = 0 ; i < 3 ; ++i){
                streamReader.get();
            }
            sign = streamReader.get();
        } catch (IOException exc){
            sign = '\0';
        }
        assertEquals('i',sign);
    }

    @Test
    void peek() {
    }

    @Test
    void eof() {
    }
}