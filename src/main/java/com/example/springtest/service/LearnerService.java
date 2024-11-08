package com.example.springtest.service;

import com.example.springtest.entity.Learner;
import com.example.springtest.entity.SearchHistory;
import com.example.springtest.repository.LearnerRepository;
import com.example.springtest.repository.SearchHistoryRepository;
import jakarta.transaction.Transactional;
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
        // @TODO can throw exception if null maybe
        return learnerRepository.findByUsername(username);
    }

    @Transactional
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

    @Transactional
    public Learner getOrAddLearner(String username) {
        Learner learner = learnerRepository.findByUsername(username);
        if (learner == null) {
            learner = new Learner(username);
            learnerRepository.save(learner);
        }
        return learner;
    }

    @Transactional
    public void addHistoryForce(String username, String searchTerm, String searchResult) {
        Learner learner = getOrAddLearner(username);
        SearchHistory searchHistory = new SearchHistory(searchTerm, searchResult, learner);
        searchHistoryRepository.save(searchHistory);
    }

    @Transactional
    public int getCorrectAnswers(String username) {
        Learner learner = getOrAddLearner(username);
        return learner.getCorrectAnswers();
    }

    @Transactional
    public int getTotalAnswers(String username) {
        Learner learner = getOrAddLearner(username);
        return learner.getTotalAnswers();
    }

    @Transactional
    public void addCorrectAnswer(String username) {
        Learner learner = getOrAddLearner(username);
        learner.addCorrectAnswer();
    }
    @Transactional
    public void addWrongAnswer(String username) {
        Learner learner = getOrAddLearner(username);
        learner.addWrongAnswer();
    }
}
