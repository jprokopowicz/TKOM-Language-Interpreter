package com.lexer;

public class Position {
    public int line;
    public int sign;

    public Position(){
        line = 1;
        sign = 1;
    }

    public Position(int line, int sign){
        this.line = line;
        this.sign = sign;
    }

    public void print() {
        System.out.println("line: " + line + " sing: " + sign);
    }
}
