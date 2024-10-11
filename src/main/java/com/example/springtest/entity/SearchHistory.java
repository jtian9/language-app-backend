package com.example.springtest.entity;

import jakarta.persistence.*;

@Entity
public class SearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String searchTerm;
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
}