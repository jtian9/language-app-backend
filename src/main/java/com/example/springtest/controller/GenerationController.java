package com.example.springtest.controller;

import com.example.springtest.form.OpenAIResponse;
import com.example.springtest.form.SentenceGenerationForm;
import com.example.springtest.service.LangChainService;
import com.example.springtest.service.LearnerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


}