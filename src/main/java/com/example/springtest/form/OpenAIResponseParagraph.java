package com.example.springtest.form;

import java.util.List;
import java.util.Map;

public class OpenAIResponseParagraph extends OpenAIResponse {
    private String paragraph;

    public OpenAIResponseParagraph() {
        super("paragraph");
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }

    public String getParagraph() {
        return paragraph;
    }

    @Override
    public String toString() {
        return paragraph;
    }
}