package tests;

import com.lexer.Position;
import com.lexer.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

    Token token = new Token(Token.Type.true_,"true",new Position(3,5));

    @Test
    void getType() {
        assertEquals(Token.Type.true_,token.getType());
    }

    @Test
    void getTypeFail(){
        assertNotEquals(Token.Type.false_,token.getType());
    }

    @Test
    void getValue() {
        assertEquals("true",token.getValue());
    }

    @Test
    void getValueFail() {
        assertNotEquals("loop",token.getValue());
    }

    @Test
    void getPosition() {
        assertEquals(3,token.getPosition().line);
        assertEquals(5,token.getPosition().sign);
    }

    @Test
    void getPositionFailLine() {
        assertNotEquals(2,token.getPosition().line);
        assertEquals(5,token.getPosition().sign);
    }

    @Test
    void getPositionFailSign() {
        assertEquals(3,token.getPosition().line);
        assertNotEquals(4,token.getPosition().sign);
    }

    @Test
    void findKeyword() {
        assertEquals(Token.Type.main_,Token.findKeyword("main"));
    }

    @Test
    void findKeywordFail() {
        assertEquals(Token.Type.identifier_,Token.findKeyword("randomName"));
    }
}