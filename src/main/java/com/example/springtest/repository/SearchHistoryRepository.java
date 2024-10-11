package com.example.springtest.repository;

import com.example.springtest.entity.Learner;
import com.example.springtest.entity.SearchHistory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SearchHistoryRepository extends CrudRepository<SearchHistory, Integer> {
    List<SearchHistory> findByLearnerUsername(String username);
}
