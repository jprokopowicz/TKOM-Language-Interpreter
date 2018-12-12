package com.parser.statement;

import com.parser.Return;

import java.util.Map;

public class Program extends Statement{
    @Override
    Return execute() {
        return new Return();
    }

    Map<String, Function> functions;
}
