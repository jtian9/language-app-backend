package com.example.springtest.controller;

import com.example.springtest.entity.Learner;
import com.example.springtest.entity.SearchHistory;
import com.example.springtest.repository.LearnerRepository;
import com.example.springtest.repository.SearchHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/learner")
@RestController
public class LearnerController {

    private final LearnerRepository learnerRepository;
    private final SearchHistoryRepository searchHistoryRepository;

    @Autowired
    public LearnerController(LearnerRepository learnerRepository, SearchHistoryRepository searchHistoryRepository) {
        this.learnerRepository = learnerRepository;
        this.searchHistoryRepository = searchHistoryRepository;
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
    public List<SearchHistory> getUsersByUsername(@RequestParam String username) {
        List<SearchHistory> histories = searchHistoryRepository.findByLearnerUsername(username);
        return histories;
    }
}
