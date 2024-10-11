package com.example.springtest.entity;

import jakarta.persistence.*;

@Entity
public class SearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
}