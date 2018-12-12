package com.parser.statement;

import com.parser.Return;

class Function extends Statement{
    @Override
    Return execute() {
        return new Return();
    }
}
