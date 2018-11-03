package com.lexer;

import javax.naming.LimitExceededException;

public class Position {
    private int line;
    private int sign;

    Position(){
        line = 0;
        sign = 0;
    }

    Position(int line, int sign) throws LimitExceededException{
        setPosition(line,sign);
    }
    public int getLine() {
        return this.line;
    }

    public void setLine(int line) throws LimitExceededException {
        if (line < 0) {
            throw new LimitExceededException("Position is positive");
        }
        this.line = line;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) throws LimitExceededException {
        if (sign < 0) {
            throw new LimitExceededException("Position is positive");
        }
        this.sign = sign;
    }

    public void setPosition(int line, int sign) throws LimitExceededException{
        setLine(line);
        setSign(sign);
    }

    void print() {
        System.out.println("line: " + line + " sing: " + sign);
    }
}
