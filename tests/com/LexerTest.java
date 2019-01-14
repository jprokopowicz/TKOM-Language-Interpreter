package com;

import com.byteReader.StreamReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {

    private String inputCode;

    private Lexer prepareLexer(String testCode) {
        InputStream inputStream = new ByteArrayInputStream(testCode.getBytes());
        return new Lexer(new StreamReader(inputStream));
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getTokenNoReading() {
        inputCode = "";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.getToken();
        assertEquals(Token.Type.invalid_,token.getType());
        assertEquals("",token.getValue());
        Assertions.assertEquals(new Position(1,1).sign,token.getPosition().sign);
        assertEquals(new Position(1,1).line,token.getPosition().line);
    }

    @Test
    void readNextTokenEndOfBytes_() {
        inputCode = "";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.end_of_bytes_,token.getType());
        assertEquals("",token.getValue());
        assertEquals(new Position(1,1).sign,token.getPosition().sign);
        assertEquals(new Position(1,1).line,token.getPosition().line);

        token = lexer.getToken();
        assertEquals(Token.Type.end_of_bytes_,token.getType());
        assertEquals("",token.getValue());
        assertEquals(new Position(1,1).sign,token.getPosition().sign);
        assertEquals(new Position(1,1).line,token.getPosition().line);
    }

    @Test
    void readNextTokenNumber_(){
        inputCode = "number";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.number_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.number_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenString_(){
        inputCode = "string";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.string_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.string_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenBool_(){
        inputCode = "bool";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.bool_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.bool_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenTrue_(){
        inputCode = "true";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.true_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.true_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenFalse_(){
        inputCode = "false";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.false_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.false_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenVoid_(){
        inputCode = "void";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.void_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.void_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenReturn_(){
        inputCode = "return";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.return_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.return_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenLoop_(){
        inputCode = "loop";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.loop_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.loop_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenIf_(){
        inputCode = "if";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.if_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.if_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenElse_(){
        inputCode = "else";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.else_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.else_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenWrite_(){
        inputCode = "write";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.write_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.write_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenRead_(){
        inputCode = "read";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.read_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.read_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenSemicolon(){
        inputCode = ";";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.semicolon_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.semicolon_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenAssign_(){
        inputCode = "=";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.assign_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.assign_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenComma_(){
        inputCode = ",";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.comma_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.comma_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenHash_(){
        inputCode = "#";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.hash_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.hash_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenColon_(){
        inputCode = ":";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.colon_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.colon_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenOpen_bracket_(){
        inputCode = "(";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.open_bracket_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.open_bracket_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenClose_bracket_(){
        inputCode = ")";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.close_bracket_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.close_bracket_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenOpen_comparison_(){
        inputCode = "[";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.open_comparison_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.open_comparison_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenClose_comparison_(){
        inputCode = "]";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.close_comparison_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.close_comparison_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenOpen_scope_(){
        inputCode = "{";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.open_scope_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.open_scope_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenClose_scope_(){
        inputCode = "}";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.close_scope_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.close_scope_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenPlus_(){
        inputCode = "+";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.plus_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.plus_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenMinus_(){
        inputCode = "-";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.minus_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.minus_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenStar_(){
        inputCode = "*";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.star_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.star_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenSlash_(){
        inputCode = "/";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.slash_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.slash_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenModulo_(){
        inputCode = "%";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.modulo_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.modulo_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenOr_(){
        inputCode = "|";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.or_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.or_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenAnd_(){
        inputCode = "&";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.and_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.and_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenNot_(){
        inputCode = "!";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.not_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.not_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenGreater_(){
        inputCode = ">";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.greater_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.greater_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenGreater_equal_(){
        inputCode = ">=";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.greater_equal_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.greater_equal_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenLesser_(){
        inputCode = "<";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.lesser_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.lesser_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenLesser_equal_(){
        inputCode = "<=";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.lesser_equal_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.lesser_equal_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenEqual_(){
        inputCode = "==";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.equal_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.equal_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenNot_equal_(){
        inputCode = "!=";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.not_equal_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.not_equal_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenComment_(){
        inputCode = "/*comment*/ more code";
        Lexer lexer = prepareLexer(inputCode);
        lexer.setIgnoreComment(false);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.comment_,token.getType());
        assertEquals("/*comment*/",token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.comment_,token.getType());
        assertEquals("/*comment*/",token.getValue());
    }

    @Test
    void readNextTokenNotEndedComment_(){
        inputCode = "/*comment more code";
        Lexer lexer = prepareLexer(inputCode);
        lexer.setIgnoreComment(false);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.invalid_,token.getType());
        assertEquals("/*comment more code",token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.invalid_,token.getType());
        assertEquals("/*comment more code",token.getValue());
    }

    @Test
    void readNextTokenIgnoreComment_(){
        inputCode = "/*comment*/ identifier";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.identifier_,token.getType());
        assertEquals("identifier",token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.identifier_,token.getType());
        assertEquals("identifier",token.getValue());
    }

    @Test
    void readNextTokenIgnoreCommentAtEnd_(){
        inputCode = "/*comment*/";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.end_of_bytes_,token.getType());
        assertEquals("",token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.end_of_bytes_,token.getType());
        assertEquals("",token.getValue());
    }

    @Test
    void readNextTokenSpecialCaseComment_(){
        inputCode = "/*comment*";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.invalid_,token.getType());
        assertEquals("/*comment*",token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.invalid_,token.getType());
        assertEquals("/*comment*",token.getValue());
    }

    @Test
    void readNextTokenIdentifier_(){
        inputCode = "someFunctionOrVariableName";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.identifier_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.identifier_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenIdentifier_WithDigits(){
        inputCode = "someFunctionOrVariableName123";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.identifier_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.identifier_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenNumberZero(){
        inputCode = "0";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.number_expression_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.number_expression_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenNumberWrongZero(){
        inputCode = "0123";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.invalid_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.invalid_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenCorrectNumber(){
        inputCode = "123";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.number_expression_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.number_expression_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenNumberLastletter(){
        inputCode = "123a";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.invalid_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.invalid_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenNumberSomeLetters(){
        inputCode = "123abc";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.invalid_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.invalid_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenStringCorrect(){
        inputCode = "\"Example string\" more code ";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.string_expression_,token.getType());
        assertEquals("\"Example string\"",token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.string_expression_,token.getType());
        assertEquals("\"Example string\"",token.getValue());
    }

    @Test
    void readNextTokenStringUnfinished(){
        inputCode = "\"Example string more code";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.invalid_,token.getType());
        assertEquals("\"Example string more code",token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.invalid_,token.getType());
        assertEquals("\"Example string more code",token.getValue());
    }

    @Test
    void readNextTokenStringWithEscape(){
        inputCode = "\"Example \\\"string\"";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.string_expression_,token.getType());
        assertEquals("\"Example \"string\"",token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.string_expression_,token.getType());
        assertEquals("\"Example \"string\"",token.getValue());
    }

    @Test
    void readNextTokenStringUnfinishedSpecialCase(){
        inputCode = "\"Example string\\";
        Lexer lexer = prepareLexer(inputCode);
        Token token = lexer.readNextToken();
        assertEquals(Token.Type.invalid_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.invalid_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void readNextTokenUnknownSign(){
        inputCode = "~";
        Lexer lexer = prepareLexer(inputCode);

        Token token = lexer.readNextToken();
        assertEquals(Token.Type.invalid_,token.getType());
        assertEquals(inputCode,token.getValue());

        token = lexer.getToken();
        assertEquals(Token.Type.invalid_,token.getType());
        assertEquals(inputCode,token.getValue());
    }

    @Test
    void manyTokens(){
        inputCode = "number fun(bool a,number b){\n}";
        Lexer lexer = prepareLexer(inputCode);

        List<Token> returnedTokens = new ArrayList<>();
        while (lexer.getToken().getType()!=Token.Type.end_of_bytes_){
            returnedTokens.add(lexer.readNextToken());
        }

        Token token;

        token = returnedTokens.get(0);
        assertEquals(Token.Type.number_,token.getType());
        assertEquals("number",token.getValue());
        assertEquals(1,token.getPosition().sign);
        assertEquals(1,token.getPosition().line);

        token = returnedTokens.get(1);
        assertEquals(Token.Type.identifier_,token.getType());
        assertEquals("fun",token.getValue());
        assertEquals(8,token.getPosition().sign);
        assertEquals(1,token.getPosition().line);

        token = returnedTokens.get(2);
        assertEquals(Token.Type.open_bracket_,token.getType());
        assertEquals("(",token.getValue());
        assertEquals(11,token.getPosition().sign);
        assertEquals(1,token.getPosition().line);

        token = returnedTokens.get(3);
        assertEquals(Token.Type.bool_,token.getType());
        assertEquals("bool",token.getValue());
        assertEquals(12,token.getPosition().sign);
        assertEquals(1,token.getPosition().line);

        token = returnedTokens.get(4);
        assertEquals(Token.Type.identifier_,token.getType());
        assertEquals("a",token.getValue());
        assertEquals(17,token.getPosition().sign);
        assertEquals(1,token.getPosition().line);

        token = returnedTokens.get(5);
        assertEquals(Token.Type.comma_,token.getType());
        assertEquals(",",token.getValue());
        assertEquals(18,token.getPosition().sign);
        assertEquals(1,token.getPosition().line);

        token = returnedTokens.get(6);
        assertEquals(Token.Type.number_,token.getType());
        assertEquals("number",token.getValue());
        assertEquals(19,token.getPosition().sign);
        assertEquals(1,token.getPosition().line);

        token = returnedTokens.get(7);
        assertEquals(Token.Type.identifier_,token.getType());
        assertEquals("b",token.getValue());
        assertEquals(26,token.getPosition().sign);
        assertEquals(1,token.getPosition().line);

        token = returnedTokens.get(8);
        assertEquals(Token.Type.close_bracket_,token.getType());
        assertEquals(")",token.getValue());
        assertEquals(27,token.getPosition().sign);
        assertEquals(1,token.getPosition().line);

        token = returnedTokens.get(9);
        assertEquals(Token.Type.open_scope_,token.getType());
        assertEquals("{",token.getValue());
        assertEquals(28,token.getPosition().sign);
        assertEquals(1,token.getPosition().line);

        token = returnedTokens.get(10);
        assertEquals(Token.Type.close_scope_,token.getType());
        assertEquals("}",token.getValue());
        assertEquals(1,token.getPosition().sign);
        assertEquals(2,token.getPosition().line);
    }

}