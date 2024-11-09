package com.example.springtest.form;

public class ModifyExpressionForm {
    String sentence;
    String style;

    public String getSentence() {
        return sentence;
    }

    public String getStyle() {
        return style;
    }

    @Override
    public String toString() {
        return "ModifyExpressionForm [sentence=" + sentence + ", style=" + style + "]";
    }
}
