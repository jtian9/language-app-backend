package com.example.springtest.form;

import java.util.List;

public class OpenAIResponse {
    private List<Result> result;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return result.toString();
    }



}

