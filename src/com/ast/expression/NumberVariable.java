package com.ast.expression;

import com.byteReader.StreamReader;
import com.exceptions.executionExceptions.ArithmeticException;
import com.exceptions.executionExceptions.ConflictException;
import com.exceptions.executionExceptions.ExecutionException;
import com.exceptions.executionExceptions.InputOutputException;
import com.exceptions.parseException.ParseException;
import com.interpreterParts.Parser;
import com.interpreterParts.Token;

import java.io.ByteArrayInputStream;

public class NumberVariable extends Variable {

    private int nominator = 0;
    private int denominator = 1;

    private boolean printInteger = true;

    public NumberVariable() {
        this.type = Type.number_;
        setValue(0, 1);
    }

    public NumberVariable(int nominator) {
        this.type = Type.number_;
        setValue(nominator);
    }

    public NumberVariable(int nominator, int denominator) {
        this.type = Type.number_;
        setValue(nominator, denominator);
    }

    public NumberVariable(int integer, int nominator, int denominator) {
        this.type = Type.number_;
        setValue(integer, nominator, denominator);
    }

    public NumberVariable(NumberVariable numberVariable) {
        this.type = Type.number_;
        printInteger = numberVariable.printInteger;
        setValue(numberVariable.nominator, numberVariable.denominator);
    }

    public int getNominator() {
        return nominator;
    }

    public int getDenominator() {
        return denominator;
    }

    public void setValue(int integer) {
        setValue(integer, 1);
    }

    public void setValue(int nominator, int denominator) {
        this.nominator = nominator;
        if (nominator == 0)
            this.denominator = 1;
        this.denominator = denominator;
        if (this.denominator < 0) {
            this.nominator *= -1;
            this.denominator *= -1;
        }
        shortenFraction();
    }

    public void setValue(int integer, int nominator, int denominator) {
        setValue(nominator + integer * denominator, denominator);
    }

    public boolean isPrintInteger() {
        return printInteger;
    }

    public void setPrintInteger(boolean printInteger) {
        this.printInteger = printInteger;
    }

    private void shortenFraction() {
        if (nominator == 1 || denominator == 1)
            return;
        if (nominator == 0 && denominator != 0)
            denominator = 1;
        int divisor = greatestCommonDivisor(nominator, denominator);
        if(divisor == 0)
            return;
        nominator /= divisor;
        denominator /= divisor;
    }

    private int greatestCommonDivisor(int a, int b) {
        if(a < 0)
            a *=-1;
        if(b < 0)
            b *=-1;
        if (a == 0 || b == 0)
            return 0;
        while (a != b) {
            if (a < b)
                b -= a;
            else
                a -= b;
        }
        return a;
    }

    private int leastCommonMultiple(int a, int b) {
        if (a == 0 || b == 0)
            return 0;
        int greatestCommonDivider = greatestCommonDivisor(a, b);
        return a * (b / greatestCommonDivider);
    }

    private void DenominatorCheck() throws ExecutionException {
        if (denominator == 0)
            throw new ArithmeticException("Denominator equals 0");
        if (denominator < 0)
            throw new ArithmeticException("Negative denominator");
    }

    NumberVariable negate() throws ExecutionException {
        this.DenominatorCheck();
        return new NumberVariable(-this.nominator, this.denominator);
    }

    NumberVariable plus(NumberVariable component) throws ExecutionException {
        this.DenominatorCheck();
        component.DenominatorCheck();
        int commonDenominator = leastCommonMultiple(this.denominator, component.denominator);
        int newNominator = this.nominator * (commonDenominator / this.denominator) + component.nominator * (commonDenominator / component.denominator);
        return new NumberVariable(newNominator, commonDenominator);
    }

    NumberVariable minus(NumberVariable subtrahend) throws ExecutionException {
        return this.plus(subtrahend.negate());
    }

    NumberVariable multiply(NumberVariable factor) throws ExecutionException {
        this.DenominatorCheck();
        factor.DenominatorCheck();
        return new NumberVariable(this.nominator * factor.nominator, this.denominator * factor.denominator);
    }

    NumberVariable dev(NumberVariable divider) throws ExecutionException {
        this.DenominatorCheck();
        divider.DenominatorCheck();
        if (divider.nominator == 0)
            throw new ArithmeticException("Division by 0");
        return new NumberVariable(this.nominator * divider.denominator, this.denominator * divider.nominator);
    }

    NumberVariable mod(NumberVariable divider) throws ExecutionException {
        this.DenominatorCheck();
        divider.DenominatorCheck();
        if (this.denominator == 1 && divider.denominator == 1) {
            try {
                return new NumberVariable(this.nominator % divider.nominator);
            } catch (java.lang.ArithmeticException exc) {
                throw new ArithmeticException("Division by 0");
            }
        } else
            return new NumberVariable(divider);
    }

    boolean equal(NumberVariable numberVariable) throws ExecutionException {
        this.DenominatorCheck();
        numberVariable.DenominatorCheck();
        return this.nominator == numberVariable.nominator && this.denominator == numberVariable.denominator;
    }

    boolean notEqual(NumberVariable numberVariable) throws ExecutionException {
        return !this.equal(numberVariable);
    }

    boolean greater(NumberVariable numberVariable) throws ExecutionException {
        this.DenominatorCheck();
        numberVariable.DenominatorCheck();
        int commonDenominator = leastCommonMultiple(this.denominator, numberVariable.denominator);
        return this.nominator * (commonDenominator / denominator) > numberVariable.nominator * (commonDenominator / numberVariable.denominator);
    }

    boolean greaterOrEqual(NumberVariable numberVariable) throws ExecutionException {
        return equal(numberVariable) || greater(numberVariable);
    }

    boolean lesser(NumberVariable numberVariable) throws ExecutionException {
        return !greaterOrEqual(numberVariable);
    }

    boolean lesserOrEqual(NumberVariable numberVariable) throws ExecutionException {
        return !greater(numberVariable);
    }

    @Override
    public void print() {
        if(denominator == 1)
            System.out.print(nominator);
        else if (printInteger && (denominator <  nominator || (nominator < 0 && denominator <  -nominator) ) && denominator != 0)
            System.out.print(nominator / denominator + "#" + (nominator >= 0 ? nominator % denominator  : -nominator % denominator) + ":" + denominator);
        else
            System.out.print(nominator + ":" + denominator);
    }

    @Override
    public void setValue(Variable value) throws ExecutionException {
        if (!(value instanceof NumberVariable))
            throw new ConflictException("NumberVariable.setValue()", "variable", "assigned value");
        NumberVariable numberVariable = (NumberVariable) value;
        this.setValue(numberVariable.nominator, numberVariable.denominator);
    }

    public static NumberVariable parseNumber(String string) throws ExecutionException {
        String stringCopy;
        boolean negate = false;
        if(string.getBytes()[0] == '-') {
            negate = true;
            stringCopy = string.substring(1);
        } else
            stringCopy = string;
        try {
            Parser parser = new Parser(new StreamReader(new ByteArrayInputStream(stringCopy.getBytes())));
            parser.readNextToken();
            NumberVariable result = parser.parseNumber();
            if(parser.readNextToken().getType() != Token.Type.end_of_bytes_)
                throw new ParseException("Not correct number format: " + string);
            if(negate)
                return result.negate();
            else
                return result;
        } catch (ParseException exc) {
            throw new InputOutputException("Not correct number format: " + string);
        }
    }
}
