package com.ast.expresion;

import com.executionExceptions.ArithmeticException;
import com.executionExceptions.ExecutionException;

public class NumberVariable extends Variable {
    private int nominator = 0;
    private int denominator = 1;

    public NumberVariable() {
        this.type = Type.number_;
        setValue(0,1);
    }

    public NumberVariable(int nominator) {
        this.type = Type.number_;
        setValue(nominator);
    }

    public NumberVariable(int nominator, int denominator) {
        this.type = Type.number_;
        setValue(nominator,denominator);
    }

    public NumberVariable(int integer, int nominator, int denominator) {
        this.type = Type.number_;
        setValue(integer,nominator,denominator);
    }

    public NumberVariable(NumberVariable numberVariable) {
        this.type = Type.number_;
        setValue(numberVariable.nominator, numberVariable.denominator);
    }

    public int getNominator() {
        return nominator;
    }

    public int getDenominator() {
        return denominator;
    }

    public void setValue(int integer){
        setValue(integer,1);
    }

    public void setValue(int nominator, int denominator) {
        this.nominator = nominator;
        if(nominator == 0)
            this.denominator = 1;
        this.denominator = denominator;
        if(this.denominator < 0) {
            this.nominator *=-1;
            this.denominator*=-1;
        }
        shortenFraction();
    }

    public void setValue(int integer, int nominator, int denominator) {
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
        if(a == 0 || b == 0)
            return 0;
        while (a != b) {
            if(a < b)
                b -= a;
            else
                a -= b;
        }
        return a;
    }

    private int leastCommonMultiple(int a, int b) {
        if(a == 0 || b == 0)
            return 0;
        int greatestCommonDivider = greatestCommonDivisor(a, b);
        return a * (b / greatestCommonDivider);
    }

    private void DenominatorCheck() throws ExecutionException {
        if (denominator == 0)
            throw new ArithmeticException("Devision by 0");
        if (denominator < 0)
            throw new ArithmeticException("Negative denominator");
    }
    //Operators
    public NumberVariable negate() throws ExecutionException {
        this.DenominatorCheck();
        return new NumberVariable(-this.nominator,this.denominator);
    }

    public NumberVariable plus(NumberVariable component) throws ExecutionException {
        this.DenominatorCheck();
        component.DenominatorCheck();
        int commonDenominator = leastCommonMultiple(this.denominator,component.denominator);
        int newNominator = this.nominator * (commonDenominator / this.denominator) + component.nominator * (commonDenominator / component.denominator);
        return new NumberVariable(newNominator, commonDenominator);
    }

    public NumberVariable minus(NumberVariable subtrahend) throws ExecutionException{
        return this.plus(subtrahend.negate());
    }

    public NumberVariable multiply(NumberVariable factor) throws ExecutionException {
        this.DenominatorCheck();
        factor.DenominatorCheck();
        return new NumberVariable(this.nominator * factor.nominator, this.denominator * factor.denominator);
    }

    public NumberVariable dev(NumberVariable divider) throws ExecutionException {
        this.DenominatorCheck();
        divider.DenominatorCheck();
        if (divider.nominator == 0)
            throw new ArithmeticException("Division by 0");
        return new NumberVariable(this.nominator * divider.denominator, this.denominator * divider.nominator);
    }

    public NumberVariable mod(NumberVariable divider) throws ExecutionException {
        this.DenominatorCheck();
        divider.DenominatorCheck();
        if(this.denominator == 1 && divider.denominator == 1)
            return new NumberVariable(this.nominator % divider.nominator);
        else
            return new NumberVariable(divider);
    }

    public boolean equal(NumberVariable numberVariable) throws ExecutionException {
        this.DenominatorCheck();
        numberVariable.DenominatorCheck();
        return this.nominator == numberVariable.nominator && this.denominator == numberVariable.denominator;
    }

    public boolean notEqual(NumberVariable numberVariable) throws ExecutionException {
        return !this.equal(numberVariable);
    }

    public boolean greater(NumberVariable numberVariable) throws ExecutionException {
        this.DenominatorCheck();
        numberVariable.DenominatorCheck();
        int commonDenominator = leastCommonMultiple(this.denominator, numberVariable.denominator);
        return this.nominator * (commonDenominator / denominator) > numberVariable.nominator * (commonDenominator / numberVariable.denominator);
    }

    public boolean greaterOrEqual(NumberVariable numberVariable) throws ExecutionException {
        return  equal(numberVariable) || greater(numberVariable);
    }

    public boolean lesser(NumberVariable numberVariable) throws ExecutionException {
        return !greaterOrEqual(numberVariable);
    }

    public boolean lesserOrEqual(NumberVariable numberVariable) throws  ExecutionException {
        return !greater(numberVariable);
    }

    @Override
    public void print() {
        System.out.print(nominator + ":" + denominator);
    }
}
