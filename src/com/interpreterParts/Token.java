package com.interpreterParts;

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
        open_comparison_,
        close_comparison_,
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
        tokenNames.put(Type.number_,"number");
        tokenNames.put(Type.string_,"string");
        tokenNames.put(Type.bool_,"bool");
        tokenNames.put(Type.true_,"true");
        tokenNames.put(Type.false_,"false");
        tokenNames.put(Type.void_,"void");
        tokenNames.put(Type.return_,"return");
        tokenNames.put(Type.loop_,"loop");
        tokenNames.put(Type.if_,"if");
        tokenNames.put(Type.else_,"else");
        tokenNames.put(Type.write_,"write");
        tokenNames.put(Type.read_,"read");
        tokenNames.put(Type.semicolon_,"semicolon");
        tokenNames.put(Type.assign_,"assign");
        tokenNames.put(Type.comma_,"comma");
        tokenNames.put(Type.hash_,"hash");
        tokenNames.put(Type.colon_,"colon");
        tokenNames.put(Type.open_bracket_,"open_bracket");
        tokenNames.put(Type.close_bracket_,"close_bracket");
        tokenNames.put(Type.open_comparison_,"open_comparison");
        tokenNames.put(Type.close_comparison_,"close_comparison");
        tokenNames.put(Type.open_scope_,"open_scope");
        tokenNames.put(Type.close_scope_,"close_scope");
        tokenNames.put(Type.plus_,"plus");
        tokenNames.put(Type.minus_,"minus");
        tokenNames.put(Type.star_,"star");
        tokenNames.put(Type.slash_,"slash");
        tokenNames.put(Type.modulo_,"modulo");
        tokenNames.put(Type.or_,"or");
        tokenNames.put(Type.and_,"and");
        tokenNames.put(Type.not_,"not");
        tokenNames.put(Type.greater_,"greater");
        tokenNames.put(Type.greater_equal_,"greater_equal");
        tokenNames.put(Type.lesser_,"lesser");
        tokenNames.put(Type.lesser_equal_,"lesser_equal");
        tokenNames.put(Type.equal_,"equal");
        tokenNames.put(Type.not_equal_,"not_equal");
        tokenNames.put(Type.comment_,"comment");
        tokenNames.put(Type.identifier_,"identifier");
        tokenNames.put(Type.number_expression_,"number_expression");
        tokenNames.put(Type.string_expression_,"string_expression");
        tokenNames.put(Type.invalid_,"invalid");
        tokenNames.put(Type.end_of_bytes_,"end_of_bytes");
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
