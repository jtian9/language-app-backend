package com.example.springtest.controller;

import com.example.springtest.ApiKeys;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageType;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dev.langchain4j.data.message.UserMessage.userMessage;
import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;

/**
 * Controller class for chat functionality.
 */
@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "http://localhost:3000")
public class ChatController {

    @RequestMapping(value = "/test")
    public String test() {
        ChatMemory chatMemory = TokenWindowChatMemory.withMaxTokens(300, new OpenAiTokenizer());

        ChatLanguageModel model = OpenAiChatModel.builder()
                .temperature(0.2)
                .apiKey(ApiKeys.OPENAI_API_KEY)
                .modelName(GPT_4_O_MINI)
                .build();

        /* from langchain4j examples */
        chatMemory.add(userMessage("Hello, my name is Klaus"));
        AiMessage answer = model.generate(chatMemory.messages()).content();
        System.out.println(answer.text()); // Hello Klaus! How can I assist you today?
        chatMemory.add(answer);

        chatMemory.add(userMessage("What is my name?"));
        AiMessage answerWithName = model.generate(chatMemory.messages()).content();
        System.out.println(answerWithName.text()); // Your name is Klaus.
        chatMemory.add(answerWithName);

        StringBuilder sb = new StringBuilder();

        for (ChatMessage message : chatMemory.messages()) {
            if (message.type().equals(ChatMessageType.USER)){
                UserMessage userMessage = (UserMessage) message;

                sb.append("USER: ");
                sb.append(userMessage.singleText());
            } else if (message.type().equals(ChatMessageType.AI)) {
                AiMessage aiMessage = (AiMessage) message;
                sb.append("AI: ");
                sb.append(aiMessage.text());
            }
            sb.append("<br />\n");
        }

        return sb.toString();
    }
}
