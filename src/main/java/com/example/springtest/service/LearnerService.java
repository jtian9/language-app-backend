package com.example.springtest.service;

import com.example.springtest.entity.Learner;
import com.example.springtest.repository.LearnerRepository;
import com.example.springtest.repository.SearchHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LearnerService {
    LearnerRepository learnerRepository;
    SearchHistoryRepository searchHistoryRepository;

    @Autowired
    public LearnerService(LearnerRepository learnerRepository, SearchHistoryRepository searchHistoryRepository) {
        this.learnerRepository = learnerRepository;
        this.searchHistoryRepository = searchHistoryRepository;
    }

    public Learner getLearner(String username) {
        return learnerRepository.findByUsername(username);
    }

    public boolean addLearner(String username) {
        Learner learner = learnerRepository.findByUsername(username);

        if (learner == null) {
            learner = new Learner(username);
            learnerRepository.save(learner);
            return true;
        } else {
            return false;
        }
    }
}
