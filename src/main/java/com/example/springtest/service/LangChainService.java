package com.example.springtest.service;

import com.example.springtest.form.OpenAIResponse;
import com.example.springtest.form.OpenAIResponseParagraph;
import com.example.springtest.form.OpenAIResponseSentence;
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

    String singleWordSystemPrompt = """
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

    String paragraphSystemPrompt = """
            You are an expert at teaching languages. You will use all the words given to you and construct a paragraph utilizing these words. \
            You can make the paragraph as long as possible, as the priority is to use the words in an apt manner that helps the learner understand its usage. \
            It is imperative you use every single word provided without modifying or deleting any of them. \
            Respond in a pretty JSON format as such with proper new-line characters. Follow the fields strictly: \
            {
              "paragraph": "Paragraph using all the words provided in the foreign language."
            }
            
            Words:
            """;

    String i1SystemPrompt = """
            You are an expert at teaching languages. You will be given a sentence that the learner has good understanding of. \
            Your goal is to first understand the level of the learner's capabilities based on the sentence given. \
            You then have to introduce one word to the sentence. This word has to be harder than the learner's level. \
            Respond in a pretty JSON format as such with proper new-line characters. Follow the fields strictly: \
            {
              "sentence": "sentence helping user in the foreign language"
            }
            
            Sentence:
            """;


    public OpenAIResponse generate(List<String> words) {
        String message = singleWordSystemPrompt + words;
        String response = cleanString(chatLanguageModel.generate(message));
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response, OpenAIResponseSentence.class);
        } catch (JsonProcessingException e) {
            System.out.println("error processing response from openAI sentence as json: " + response);
        }

        return new OpenAIResponseSentence(); // @TODO change to actual error response
    }

    public OpenAIResponse generateParagraph(List<String> words) {
        String message = paragraphSystemPrompt + words;
        String response = cleanString(chatLanguageModel.generate(message));
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response, OpenAIResponseParagraph.class);
        } catch (JsonProcessingException e) {
            System.out.println("error processing response from openAI paragraph as json: " + response);
        }

        return new OpenAIResponseParagraph(); // @TODO change to actual error response
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
