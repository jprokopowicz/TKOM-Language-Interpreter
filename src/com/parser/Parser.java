package com.parser;

import com.lexer.ByteReader.ByteReader;
import com.lexer.Lexer;

public class Parser {
    private Lexer lexer;

    Parser (ByteReader byteReader){
        lexer = new Lexer(byteReader);
    }


}
