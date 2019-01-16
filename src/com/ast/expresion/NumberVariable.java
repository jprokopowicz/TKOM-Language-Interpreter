package com.ast.expresion;

import com.executionExceptions.ArithmeticException;
import com.executionExceptions.ConflictException;
import com.executionExceptions.ExecutionException;
import com.executionExceptions.InputOutputException;

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

    private void setValue(int integer, int nominator, int denominator) {
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
    NumberVariable negate() throws ExecutionException {
        this.DenominatorCheck();
        return new NumberVariable(-this.nominator,this.denominator);
    }

    NumberVariable plus(NumberVariable component) throws ExecutionException {
        this.DenominatorCheck();
        component.DenominatorCheck();
        int commonDenominator = leastCommonMultiple(this.denominator,component.denominator);
        int newNominator = this.nominator * (commonDenominator / this.denominator) + component.nominator * (commonDenominator / component.denominator);
        return new NumberVariable(newNominator, commonDenominator);
    }

    NumberVariable minus(NumberVariable subtrahend) throws ExecutionException{
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
        if(this.denominator == 1 && divider.denominator == 1)
            return new NumberVariable(this.nominator % divider.nominator);
        else
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
        return  equal(numberVariable) || greater(numberVariable);
    }

    boolean lesser(NumberVariable numberVariable) throws ExecutionException {
        return !greaterOrEqual(numberVariable);
    }

    boolean lesserOrEqual(NumberVariable numberVariable) throws  ExecutionException {
        return !greater(numberVariable);
    }

    @Override
    public void print() {
        System.out.print(nominator + ":" + denominator);
    }

    @Override
    public void setValue(Variable value) throws ExecutionException {
        if(!(value instanceof NumberVariable))
            throw new ConflictException("NumberVariable.setValue()", "variable", "assigned value");
        NumberVariable numberVariable = (NumberVariable)value;
        this.setValue(numberVariable.nominator,numberVariable.denominator);
    }

    public static NumberVariable parseNumber(String string) throws ExecutionException {
        String [] parts = new String[3];
        int numberOfParts = 1;
        byte [] stringInBytes = string.getBytes();
        int separator = 0;

        if(string.length() == 0)
            throw new InputOutputException("Not correct number format: empty string");
        int start = stringInBytes[0] == '-' ? 1 : 0; //negative value
        try {
            for(int i = start ; i < string.length() ; ++i) {
                switch (numberOfParts) {
                    case 1:// int
                        if(!Character.isDigit(stringInBytes[i])) {
                            parts[0] = string.substring(start,i);
                            separator = i;
                            if(stringInBytes[i] == '#')
                                numberOfParts = 3;
                            else if (stringInBytes[i] == ':')
                                numberOfParts = 2;
                            else
                                throw new InputOutputException("Not correct number format: " + string);
                        } else if (i == string.length() - 1) {
                            parts[0] = string.substring(start);
                            return start == 0 ? new NumberVariable(Integer.parseInt(parts[0])) : (new NumberVariable(Integer.parseInt(parts[0]))).negate();
                        }
                        break;
                    case 2:// int ':' int
                        if(!Character.isDigit(stringInBytes[i]))
                            throw new InputOutputException("Not correct number format: " + string);
                        else if (i == string.length() - 1) {
                            parts[1] = string.substring(separator + 1);
                            return new NumberVariable(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]));
                        }
                        break;
                    case 3:// int '#' int ':' int
                        if (!Character.isDigit(stringInBytes[i])) {
                            if (stringInBytes[separator] == '#' && stringInBytes[i]==':') {
                                parts[1] = string.substring(separator+1, i);
                                separator = i;
                            } else
                                throw new InputOutputException("Not correct number format: " + string);
                        } else if (i == string.length() - 1) {
                            if (stringInBytes[separator] == ':') {
                                parts[2] = string.substring(separator + 1);
                                return start == 0 ? new NumberVariable(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]),Integer.parseInt(parts[2])) :
                                        (new NumberVariable(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]),Integer.parseInt(parts[2]))).negate();
                            } else
                                throw new InputOutputException("Not correct number format: " + string);
                        }
                        break;
                    default:
                        throw new InputOutputException("Not correct number format: " + string);
                }
            }
        } catch (NumberFormatException exc) {
            throw new InputOutputException("Not correct number format: " + string);
        }
        throw new InputOutputException("Not correct number format: " + string);
    }
}
