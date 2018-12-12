package com.parser.Variable;

public class NumerVariable extends Variable {
    private int nominator;
    private int denominator;

    public NumerVariable() {
        this.nominator = 0;
        this.denominator = 1;
        this.type = Type.number_;
    }

    public int getNominator() {
        return nominator;
    }

    public int getDenominator() {
        return denominator;
    }

    public void negate() {
        nominator *=-1;
    }

    public void setValue(int integer){
        nominator = integer;
        denominator = 1;
    }

    public void setValue(int nominator, int denominator) throws ArithmeticException{
        if(denominator == 0)
            throw new ArithmeticException("denominator is zero");
        this.nominator = nominator;
        if(nominator == 0){
            this.denominator = 1;
            return;
        }
        this.denominator = denominator;
        shortenFraction();
    }

    public void setValue(int integer, int nominator, int denominator) throws ArithmeticException{
        setValue(nominator + integer * denominator,denominator);
    }

    private void shortenFraction() {
        if(nominator == 1 || denominator ==1)
            return;
        int divisor = greatestCommonDivisor(nominator,denominator);
        nominator /= divisor;
        denominator /= divisor;
    }

    private int greatestCommonDivisor(int a, int b) {
        while (a != b) {
            if(a < b)
                b -= a;
            else
                a -= b;
        }
        return a;
    }
}
