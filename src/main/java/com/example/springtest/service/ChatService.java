package com.example.springtest.service;

import com.example.springtest.ApiKeys;
import com.example.springtest.entity.Conversation;
import com.example.springtest.entity.ConversationMessage;
import com.example.springtest.form.ConversationResponse;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static dev.langchain4j.data.message.SystemMessage.*;
import static dev.langchain4j.data.message.UserMessage.*;
import static dev.langchain4j.data.message.AiMessage.*;
import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O;
import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;

@Service
public class ChatService {

    ChatLanguageModel model;
    ConversationService conversationService;

    @Autowired
    public ChatService(ConversationService conversationService) {
         model = OpenAiChatModel.builder()
                .temperature(0.1)
                .apiKey(ApiKeys.OPENAI_API_KEY)
                .modelName(GPT_4_O)
                .build();
         this.conversationService = conversationService;
    }
    private final int MAX_TOKENS = 300;

    public ConversationResponse handleMessage(Long id, String message, String systemPrompt, String username) {
        ConversationResponse chatResponse = new ConversationResponse();

        SystemMessage systemMessage = systemMessage(systemPrompt);
        UserMessage userMessage = userMessage(message);

        if (id < 1) {
            // new conversation
            ChatMemory chatMemory = TokenWindowChatMemory.withMaxTokens(MAX_TOKENS, new OpenAiTokenizer());

            chatMemory.add(systemMessage);
            chatMemory.add(userMessage);

            AiMessage response = model.generate(chatMemory.messages()).content();

            // use conversation service to create new conversation and return id
            Long conversationID = conversationService.newConversation(message, response.text(), systemPrompt, username);

            chatResponse.setConversationID(conversationID);
            chatResponse.setAiResponse(response.text());
        } else {
            ChatMemory chatMemory = getChatMemory(id);

            chatMemory.add(userMessage);

            AiMessage response = model.generate(chatMemory.messages()).content();

            conversationService.updateConversation(id, message, response.text());

            chatResponse.setConversationID(id);
            chatResponse.setAiResponse(response.text());
        }

        return chatResponse;
    }

    private ChatMemory getChatMemory(Long conversationId) {
        ChatMemory chatMemory = TokenWindowChatMemory.withMaxTokens(MAX_TOKENS, new OpenAiTokenizer());

        if (conversationId > 0) {
            Conversation conversation = conversationService.getConversationByID(conversationId);
            if (conversation == null) {
                throw new IllegalArgumentException("conversation does not exist for id: " + conversationId);
            }

            // add system prompt
            chatMemory.add(systemMessage(conversation.getSystemPrompt()));

            // populate chatMemory
            for (ConversationMessage conversationMessage : conversation.getConversationMessages()) {
                if (conversationMessage.getType().equals("USER")) {
                    chatMemory.add(userMessage(conversationMessage.getMessage()));
                } else if (conversationMessage.getType().equals("AI")) {
                    chatMemory.add(aiMessage(conversationMessage.getMessage()));
                }
            }
        } else {
            throw new IllegalArgumentException("invalid id: " + conversationId);
        }

        return chatMemory;
    }
}
