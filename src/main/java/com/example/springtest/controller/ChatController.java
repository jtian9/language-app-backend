package com.example.springtest.controller;

import com.example.springtest.form.OpenAIResponse;
import com.example.springtest.form.OpenAIResponseSentence;
import com.example.springtest.form.SentenceGenerationForm;
import com.example.springtest.service.LangChainService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ChatController {

    private final LangChainService langChainService;

    public ChatController(LangChainService langChainService) {
        this.langChainService = langChainService;
    }

    @RequestMapping(value = "/generateSentence", method = RequestMethod.POST)
    public OpenAIResponse generateSentence(@RequestBody SentenceGenerationForm sentenceGenerationForm) throws Exception {
        System.out.println("Received sentence generation request: " + sentenceGenerationForm);
        if (sentenceGenerationForm.getWords().size() == 1) {
            return langChainService.generate(sentenceGenerationForm.getWords());
        } else if (sentenceGenerationForm.getWords().size() > 1) {
            return langChainService.generateParagraph(sentenceGenerationForm.getWords());
        } else {
            throw new IllegalArgumentException("Invalid sentence generation request");
        }
    }
}