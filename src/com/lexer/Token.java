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
        end_of_bytes_,
    }

    public static Map<Type, String> tokenNames;
    static {
        tokenNames = new HashMap<>();
        tokenNames.put(Type.number_,"number_");
        tokenNames.put(Type.string_,"string_");
        tokenNames.put(Type.bool_,"bool_");
        tokenNames.put(Type.true_,"true_");
        tokenNames.put(Type.false_,"false_");
        tokenNames.put(Type.void_,"void_");
        tokenNames.put(Type.return_,"return_");
        tokenNames.put(Type.loop_,"loop_");
        tokenNames.put(Type.if_,"if_");
        tokenNames.put(Type.else_,"else_");
        tokenNames.put(Type.write_,"write_");
        tokenNames.put(Type.read_,"read_");
        tokenNames.put(Type.semicolon_,"semicolon_");
        tokenNames.put(Type.assign_,"assign_");
        tokenNames.put(Type.comma_,"comma_");
        tokenNames.put(Type.hash_,"hash_");
        tokenNames.put(Type.colon_,"colon_");
        tokenNames.put(Type.open_bracket_,"open_bracket_");
        tokenNames.put(Type.close_bracket_,"close_bracket_");
        tokenNames.put(Type.open_scope_,"open_scope_");
        tokenNames.put(Type.close_scope_,"close_scope_");
        tokenNames.put(Type.plus_,"plus_");
        tokenNames.put(Type.minus_,"minus_");
        tokenNames.put(Type.star_,"star_");
        tokenNames.put(Type.slash_,"slash_");
        tokenNames.put(Type.modulo_,"modulo_");
        tokenNames.put(Type.or_,"or_");
        tokenNames.put(Type.and_,"and_");
        tokenNames.put(Type.not_,"not_");
        tokenNames.put(Type.greater_,"greater_");
        tokenNames.put(Type.greater_equal_,"greater_equal_");
        tokenNames.put(Type.lesser_,"lesser_");
        tokenNames.put(Type.lesser_equal_,"lesser_equal_");
        tokenNames.put(Type.equal_,"equal_");
        tokenNames.put(Type.not_equal_,"not_equal_");
        tokenNames.put(Type.comment_,"comment_");
        tokenNames.put(Type.identifier_,"identifier_");
        tokenNames.put(Type.number_expression_,"number_expression_");
        tokenNames.put(Type.string_expression_,"string_expression_");
        tokenNames.put(Type.invalid_,"invalid_");
        tokenNames.put(Type.end_of_bytes_,"end_of_bytes_");
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
