package com.example.springtest.controller;

import com.example.springtest.form.OpenAIResponse;
import com.example.springtest.form.SentenceGenerationForm;
import com.example.springtest.service.LangChainService;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final LangChainService langChainService;

    public ChatController(LangChainService langChainService) {
        this.langChainService = langChainService;
    }

    @RequestMapping(value = "/jsonTest", method = RequestMethod.POST)
    public void process(@RequestBody SentenceGenerationForm payload)
            throws Exception {
        System.out.println(payload);
    }

    @RequestMapping(value = "/jsonTestReal", method = RequestMethod.POST)
    public OpenAIResponse generateSentence(@RequestBody SentenceGenerationForm sentenceGenerationForm) throws Exception {
        return langChainService.generate(sentenceGenerationForm.getWords());
    }
}