package com.example.springtest.form;

public class OpenAIResponseFailure extends OpenAIResponse {

    public OpenAIResponseFailure() {
        super("failure");
    }

    @Override
    public String getResult() {
        return "failure";
    }
}
