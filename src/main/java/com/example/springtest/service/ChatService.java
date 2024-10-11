package com.example.springtest.service;

import com.example.springtest.ApiKeys;
import com.example.springtest.entity.Conversation;
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
import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;

@Service
public class ChatService {

    ChatLanguageModel model;
    ConversationService conversationService;

    @Autowired
    public ChatService(ConversationService conversationService) {
         model = OpenAiChatModel.builder()
                .temperature(0.2)
                .apiKey(ApiKeys.OPENAI_API_KEY)
                .modelName(GPT_4_O_MINI)
                .build();
         this.conversationService = conversationService;
    }
    private final int MAX_TOKENS = 300;
    public void handleMessage(Long id, String message, String systemPrompt, String username) {
        if (id < 0) {
            // new conversation
            ChatMemory chatMemory = TokenWindowChatMemory.withMaxTokens(MAX_TOKENS, new OpenAiTokenizer());
            SystemMessage systemMessage = systemMessage(systemPrompt);
            UserMessage userMessage = userMessage(message);

            chatMemory.add(systemMessage);
            chatMemory.add(userMessage);

            AiMessage response = model.generate(chatMemory.messages()).content();

            // use conversation service to create new conversation and return id
            Long conversationID = conversationService.newConversation(message, response.text(), systemPrompt, username);


        }
    }
}
