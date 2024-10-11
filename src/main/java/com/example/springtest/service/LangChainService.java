package com.example.springtest.service;

import com.example.springtest.form.OpenAIResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LangChainService {

    ChatLanguageModel chatLanguageModel;

    @Autowired
    public LangChainService(ChatLanguageModel chatLanguageModel) {
        this.chatLanguageModel = chatLanguageModel;
    }

    String systemPrompt = """
            You are an expert at teaching languages. For every word given to you, you will generate a sentence that helps the user understand the word better. \
            Respond in a pretty JSON format as such with proper new-line characters. Follow the fields strictly: \
            {
              "result": [
                {
                  "word": "foreignWord",
                  "sentence": "sentence helping user in the foreign language"
                },{
                  "word": "foreignWord",
                  "sentence": "sentence helping user in the foreign language"
                }
              ]
            }
            
            Words:
            """;

    public OpenAIResponse generate(List<String> words) {
        String message = systemPrompt + words;
        String response = cleanString(chatLanguageModel.generate(message));
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response, OpenAIResponse.class);
        } catch (JsonProcessingException e) {
            System.out.println("error processing response from openAI as json: " + response);
        }

        return new OpenAIResponse();
    }

    /**
     * Helper to clean markdown from OpenAI.
     * @param input string to clean
     * @return cleaned string
     */
    public static String cleanString(String input) {
        int startIndex = input.indexOf('{');
        int endIndex = input.lastIndexOf('}');

        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            return input.substring(startIndex, endIndex + 1);
        }

        throw new IllegalArgumentException("invalid structure");
    }
}
