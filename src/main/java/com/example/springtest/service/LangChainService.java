package com.example.springtest.service;

import com.example.springtest.form.*;
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

    private final String singleWordSystemPrompt = """
            You are an expert at teaching languages. You will be given a single word or phrase, you will generate a
            sentence that helps the user understand how to use this word or phrase better. \
            Respond in a pretty JSON format as such with proper new-line characters. Follow the fields strictly: \
            {
              "result": "Sentence helping user understand word/phrase"
            }
            
            Word:
            """;

    private final String multiWordSystemPrompt = """
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

    private final String quizPrompt = """
            Please generate a quiz using the given foreign word. The quiz and all questions, answers, and explanations must be entirely in the language of the target word. Use the word below to create a multiple-choice question where it is the correct answer.
            
            Word: %s
            
            First consider the language this word is in. Then, utilize this language for the rest of the response.
            
            The quiz will be in the style of a fill in the blank sentence, where the user has to choose the most apt word. Do not provide any instructions, only return the questions and the answers.
            The other answers have to be incorrect, there cannot be multiple correct answers.
            
            Return the output strictly in the following JSON format:
            
            {
              "question": "Your question text here in the target language",
              "answers": ["Answer 1 in the target language", "Answer 2", "Answer 3", "Correct Answer"]
            }
            """;

    private String getQuizPrompt(String word) {
        return String.format(quizPrompt, word);
    }

    private final String paragraphSystemPrompt = """
            You are an expert at teaching languages. You will use all the words given to you and construct a paragraph utilizing these words. \
            You can make the paragraph as long as possible, as the priority is to use the words in an apt manner that helps the learner understand its usage. \
            It is imperative you use every single word provided without modifying or deleting any of them. \
            Respond in a pretty JSON format as such with proper new-line characters. Follow the fields strictly: \
            {
              "result": "Paragraph using all the words provided in the foreign language."
            }
            
            Words:
            """;

    private final String i1SystemPrompt = """
            You are an expert at teaching languages. You will be given a sentence that the learner has good understanding of. \
            Your goal is to first understand the level of the learner's capabilities based on the sentence given. \
            You then have to introduce one word to the sentence. This word has to be harder than the learner's level. \
            Respond in a pretty JSON format as such with proper new-line characters. Follow the fields strictly: \
            {
              "sentence": "sentence helping user in the foreign language"
            }
            
            Sentence:
            """;

    private final String expressionPrompt = """
            Given the following sentence:
            
            %s
            
            Consider what language this sentence is in, and remember this as the target language.
            
            Please edit it so that it will be in the following style:
            
            %s
            
            Make sure to return the sentence in the target language. You are allowed to modify the sentence greatly
            as long as you convey the original intended meaning. 
            
            Respond strictly in a pretty JSON format as such with proper new-line characters. Follow the fields strictly:
            {
              "result": "Modified sentence."
            }
            """;

    private String getExpressionPrompt(String sentence, String modify) {
        return String.format(expressionPrompt, sentence, modify);
    }

    public QuizResponse generateQuiz(String word) {
        String message = getQuizPrompt(word);
        String response = cleanString(chatLanguageModel.generate(message));
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response, QuizResponse.class);
        } catch (JsonProcessingException e) {
            System.out.println("error processing response from openAI sentence as json: " + response);
        }

        return new QuizResponse();
    }


    public OpenAIResponse generateSingle(String word) {
        String message = singleWordSystemPrompt + word;
        String response = cleanString(chatLanguageModel.generate(message));
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            OpenAIResponseSentence r = objectMapper.readValue(response, OpenAIResponseSentence.class);
            r.addWord(word);
            return r;
        } catch (JsonProcessingException e) {
            System.out.println("error processing response from openAI sentence as json: " + response);
        }

        return new OpenAIResponseFailure();
    }

    public OpenAIResponse modifyExpression(String sentence, String style) {
        String message = getExpressionPrompt(sentence, style);
        String response = cleanString(chatLanguageModel.generate(message));
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            OpenAIResponseSentence r = objectMapper.readValue(response, OpenAIResponseSentence.class);
            return r;
        } catch (JsonProcessingException e) {
            System.out.println("error processing response from openAI sentence as json: " + response);
        }

        return new OpenAIResponseFailure();
    }

    public OpenAIResponse generateParagraph(List<String> words) {
        String message = paragraphSystemPrompt + words;
        String response = cleanString(chatLanguageModel.generate(message));
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            OpenAIResponseParagraph r = objectMapper.readValue(response, OpenAIResponseParagraph.class);
            r.setWords(words);
            return r;
        } catch (JsonProcessingException e) {
            System.out.println("error processing response from openAI paragraph as json: " + response);
            System.out.println(e.getMessage());
        }

        return new OpenAIResponseFailure();
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
