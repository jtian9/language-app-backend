package com.example.springtest.form;

import java.util.List;
import java.util.Map;

public class OpenAIResponseParagraph implements OpenAIResponse {
    private String paragraph;

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