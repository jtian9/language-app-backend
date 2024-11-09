package com.example.springtest.controller;

import com.example.springtest.form.*;
import com.example.springtest.service.LangChainService;
import com.example.springtest.service.LearnerService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class GenerationController {

    private final LangChainService langChainService;
    private final LearnerService learnerService;

    public GenerationController(LangChainService langChainService, LearnerService learnerService) {
        this.langChainService = langChainService;
        this.learnerService = learnerService;
    }

    @RequestMapping(value = "/generateSentence", method = RequestMethod.POST)
    public OpenAIResponse generateSentence(@RequestBody SentenceGenerationForm sentenceGenerationForm) throws Exception {
        String username = sentenceGenerationForm.getUsername();
        List<String> words = sentenceGenerationForm.getWords();

        System.out.println("Received sentence generation request: " + sentenceGenerationForm + " from user: " + username);

        OpenAIResponse response;

        if (sentenceGenerationForm.getWords().size() == 1) {
            response = langChainService.generateSingle(sentenceGenerationForm.getWords().get(0));
        } else if (sentenceGenerationForm.getWords().size() > 1) {
            response = langChainService.generateParagraph(sentenceGenerationForm.getWords());
        } else {
            throw new IllegalArgumentException("Invalid sentence generation request");
        }

        if (response.isSuccess()) {
            learnerService.addHistoryForce(username, String.join(", ", words), response.getResult());
        }
        return response;
    }

    @RequestMapping(value = "/expression", method = RequestMethod.POST)
    public OpenAIResponse modifyExpression(@RequestBody ModifyExpressionForm modifyExpressionForm) throws Exception {
        OpenAIResponse response;
        System.out.println("Received modifyExpression request: " + modifyExpressionForm);
        String sentence = modifyExpressionForm.getSentence();
        String style = modifyExpressionForm.getStyle();

        response = langChainService.modifyExpression(sentence, style);
        System.out.println("modifyExpression response: " + response);
        return response;
    }

    @RequestMapping(value = "/compare", method = RequestMethod.POST)
    public OpenAIResponse compareNuance(@RequestBody CompareForm compareForm) throws Exception {
        OpenAIResponse response;
        System.out.println("Received compareNuance request: " + compareForm);
        String word1 = compareForm.getWord1();
        String word2 = compareForm.getWord2();

        response = langChainService.compareNuance(word1, word2);
        System.out.println("compareNuance response: " + response);
        return response;
    }
}