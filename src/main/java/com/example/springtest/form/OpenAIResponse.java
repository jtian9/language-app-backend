package com.example.springtest.form;

public abstract class OpenAIResponse {
    private String responseType;

    public OpenAIResponse(String responseType) {
        this.responseType = responseType;
    }

    public String getResponseType() {
        return responseType;
    }

    public abstract String getResult();

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public boolean isFailure() {
        return responseType.equalsIgnoreCase("failure");
    }

    public boolean isSuccess() {
        return !responseType.equalsIgnoreCase("failure");
    }
}
