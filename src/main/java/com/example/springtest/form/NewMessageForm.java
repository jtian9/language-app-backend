package com.example.springtest.form;

public class NewMessageForm {
    private Long conversationID;
    private String message;

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
}
