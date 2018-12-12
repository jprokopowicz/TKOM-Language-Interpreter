package com.parser.statement;

import com.parser.Return;

public class Function extends Statement{
    private String name;

    public String getName() {
        return name;
    }

    public Function(String name) {
        super();
        this.name = name;
    }

    @Override
    public Return execute() {
        return new Return();
    }
}
