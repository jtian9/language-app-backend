package com.example.springtest.controller;

import com.example.springtest.entity.Learner;
import com.example.springtest.repository.LearnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LearnerController {

    private final LearnerRepository learnerRepository;

    @Autowired
    public LearnerController(LearnerRepository learnerRepository) {
        this.learnerRepository = learnerRepository;
    }

    @GetMapping("/learners")
    public Iterable<Learner> findAllLearners() {
        return this.learnerRepository.findAll();
    }

    @PostMapping("/learners")
    public Learner addLearner(@RequestBody Learner learner) {
        return this.learnerRepository.save(learner);
    }
}
