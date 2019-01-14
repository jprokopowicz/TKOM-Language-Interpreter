package com;

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

    public Position(Position position) {
        this.line = position.line;
        this.sign = position.sign;
    }

    public void print() {
        System.out.print("line: " + line + " sing: " + sign);
    }
}
