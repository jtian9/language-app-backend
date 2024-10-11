package com.example.springtest.controller;

import com.example.springtest.entity.Learner;
import com.example.springtest.entity.SearchHistory;
import com.example.springtest.form.OpenAIResponse;
import com.example.springtest.form.OpenAIResponseSentence;
import com.example.springtest.form.SentenceGenerationForm;
import com.example.springtest.repository.LearnerRepository;
import com.example.springtest.repository.SearchHistoryRepository;
import com.example.springtest.service.LangChainService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ChatController {

    private final LangChainService langChainService;
    private final LearnerRepository learnerRepository;
    private final SearchHistoryRepository searchHistoryRepository;

    public ChatController(LangChainService langChainService, LearnerRepository learnerRepository, SearchHistoryRepository searchHistoryRepository) {
        this.langChainService = langChainService;
        this.learnerRepository = learnerRepository;
        this.searchHistoryRepository = searchHistoryRepository;
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
            Learner learner = learnerRepository.findByUsername(username);
            if (learner == null) {
                learner = new Learner(username);
                learnerRepository.save(learner);
            }

            SearchHistory search = new SearchHistory(String.join(", ", words), response.getResult(), learner);
            searchHistoryRepository.save(search);
        }
        return response;
    }
}