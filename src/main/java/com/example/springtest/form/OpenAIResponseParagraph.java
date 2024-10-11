package com.example.springtest.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OpenAIResponseParagraph extends OpenAIResponse {
    private String result;
    private List<String> words;

    public OpenAIResponseParagraph() {
        super("paragraph");
        this.words = new ArrayList<>();
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public void addWord(String word) {
        words.add(word);
    }

    public void setParagraph(String paragraph) {
        this.result = paragraph;
    }

    public String getParagraph() {
        return result;
    }

    @Override
    public String toString() {
        return result;
    }
}