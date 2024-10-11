package com.example.springtest.form;

public class ConversationResponse {
    private Long conversationID;
    private String aiResponse;

    public ConversationResponse() {}

    public ConversationResponse(Long conversationID, String aiResponse) {
        this.conversationID = conversationID;
        this.aiResponse = aiResponse;
    }

    public Long getConversationID() {
        return conversationID;
    }

    public void setConversationID(Long conversationID) {
        this.conversationID = conversationID;
    }

    public String getAiResponse() {
        return aiResponse;
    }

    public void setAiResponse(String aiResponse) {
        this.aiResponse = aiResponse;
    }
}
