package com.example.springtest.form;

public class NewMessageForm {
    private Long conversationID;
    private String message;
    private String systemPrompt;
    private String username;

    public NewMessageForm() {

    }

    public void setConversationID(Long conversationID) {
        this.conversationID = conversationID;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getConversationID() {
        return conversationID;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public String getSystemPrompt() {
        return systemPrompt;
    }

    public void setSystemPrompt(String systemPrompt) {
        this.systemPrompt = systemPrompt;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
