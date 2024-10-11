package com.example.springtest.form;

public abstract class OpenAIResponse {
    private String responseType;

    public OpenAIResponse(String responseType) {
        this.responseType = responseType;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }
}
