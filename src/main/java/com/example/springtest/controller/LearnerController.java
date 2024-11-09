package com.example.springtest.controller;

import com.example.springtest.entity.Learner;
import com.example.springtest.entity.SearchHistory;
import com.example.springtest.form.WordsInsertionForm;
import com.example.springtest.repository.LearnerRepository;
import com.example.springtest.repository.SearchHistoryRepository;
import com.example.springtest.service.LearnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/learner")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class LearnerController {

    private final LearnerRepository learnerRepository;
    private final SearchHistoryRepository searchHistoryRepository;
    private final LearnerService learnerService;

    @Autowired
    public LearnerController(LearnerRepository learnerRepository, SearchHistoryRepository searchHistoryRepository, LearnerService learnerService) {
        this.learnerRepository = learnerRepository;
        this.searchHistoryRepository = searchHistoryRepository;
        this.learnerService = learnerService;
    }

    @GetMapping("/all")
    public Iterable<Learner> findAllLearners() {
        return this.learnerRepository.findAll();
    }

    @PostMapping("/add")
    public Learner addLearner(@RequestBody Learner learner) {
        return this.learnerRepository.save(learner);
    }

    @GetMapping("/getHistory")
    public List<SearchHistory> getHistoriesByUsername(@RequestParam String username) {
        System.out.println("received get histories request for username: " + username);
        List<SearchHistory> histories = searchHistoryRepository.findByLearnerUsername(username);
        for (SearchHistory history : histories) {
            System.out.println(history.toString());
            System.out.println(history.getSearchTerm());
            System.out.println(history.getSearchResult());
        }
        return histories;
    }

    @PostMapping("/addWords")
    public void addWords(@RequestBody WordsInsertionForm wordsInsertionForm) {
        String username = wordsInsertionForm.getUsername();
        List<String> words = wordsInsertionForm.getWords();
        System.out.println("Add word request for username: " + username + " | with words: " + words);

        learnerService.addWords(username, words);
    }
}
