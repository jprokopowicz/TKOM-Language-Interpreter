package com.interpreter;

public class Token {
    public enum Type {
        var_,
        frantion_,
        string_,
        bool_,
        true_,
        false_,
        void_,
        main_,
        function_,
        return_,
        loop_,
        if_,
        else_,
        write_,
        read,
        comma_,
        semicon_,
        assign_,
        open_bracket_,
        clese_bracket,
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
        invalide_,
        end_of_file
    }

    private Type type = Type.invalide_;
    private String value = "";
    private Position position;


}
