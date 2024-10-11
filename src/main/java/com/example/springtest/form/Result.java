package com.example.springtest.form;

public class Result {
    private String word;
    private String sentence;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    @Override
    public String toString() {
        return "word=" + word + "\n" + "sentence=" + sentence;
    }
}
