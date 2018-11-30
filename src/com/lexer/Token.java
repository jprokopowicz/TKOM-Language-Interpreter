package com.lexer;

import java.util.HashMap;
import java.util.Map;

public class Token {

    private Type type;
    private String value;
    private Position position;

    private static Map<String, Type> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("number", Type.number_);
        keywords.put("string", Type.string_);
        keywords.put("bool", Type.bool_);
        keywords.put("true", Type.true_);
        keywords.put("false", Type.false_);
        keywords.put("void", Type.void_);
        keywords.put("main", Type.main_);
        keywords.put("return", Type.return_);
        keywords.put("loop", Type.loop_);
        keywords.put("if", Type.if_);
        keywords.put("else", Type.else_);
        keywords.put("write", Type.write_);
        keywords.put("read", Type.read_);
    }

    public enum Type {
        number_,
        string_,
        bool_,
        true_,
        false_,
        void_,
        main_,
        return_,
        loop_,
        if_,
        else_,
        write_,
        read_,
        semicolon_,
        assign_,
        comma_,
        hash_,
        colon_,
        open_bracket_,
        close_bracket_,
        open_scope_,
        close_scope_,
        plus_,
        minus_,
        star_,
        slash_,
        modulo_,
        or_,
        and_,
        not_,
        greater_,
        greater_equal_,
        lesser_,
        lesser_equal_,
        equal_,
        not_equal_,
        comment_,
        identifier_,
        number_expression_,
        string_expression_,
        invalid_,
        end_of_file_,
    }



    public Token(Type type, String value, Position position) {
        this.type = type;
        this.value = value;
        this.position = position;
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public Position getPosition() {
        return position;
    }

    public static Type findKeyword(String value) {
        if (keywords.containsKey(value))
            return keywords.get(value);
        return Type.identifier_;
    }

}
