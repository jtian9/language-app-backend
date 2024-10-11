package com.example.springtest.form;

import java.util.ArrayList;
import java.util.List;

public class OpenAIResponseSentence extends OpenAIResponse {
    private List<SentenceResult> result;

    public OpenAIResponseSentence() {
        super("sentence");
    }

    public List<SentenceResult> getResult() {
        return result;
    }

    public void setResult(List<SentenceResult> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return result.toString();
    }

}

