package com.example.springtest.form;

import java.util.List;

public class SentenceGenerationForm {
    private List<String> words;

    public SentenceGenerationForm() {
    }

    public SentenceGenerationForm(List<String> words) {
        this.words = words;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    @Override
    public String toString() {
        return "SentenceGenerationForm [words=" + words + "]";
    }
}
