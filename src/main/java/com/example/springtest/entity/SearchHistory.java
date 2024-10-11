package com.example.springtest.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class SearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String searchTerm;

    @Column(columnDefinition = "TEXT")
    private String searchResult;

    @ManyToOne
    @JoinColumn(name = "learner_id")
    private Learner learner;

    public SearchHistory() {}

    public SearchHistory(String searchTerm, String searchResult, Learner learner) {
        this.searchTerm = searchTerm;
        this.searchResult = searchResult;
        this.learner = learner;
    }

    public Long getId() {
        return id;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public String getSearchResult() {
        return searchResult;
    }

    public Learner getLearner() {
        return learner;
    }
}