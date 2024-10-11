package com.example.springtest.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "learners")
public class Learner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String username;

    @OneToMany(mappedBy = "learner")
    private final List<SearchHistory> searchHistory = new ArrayList<>();

    public Learner() {}

    public Learner(String username) {
        this.username = username;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<SearchHistory> getSearchHistory() {
        return searchHistory;
    }

    public void addSearchHistory(String searchTerm, String searchResult) {
        SearchHistory history = new SearchHistory(searchTerm, searchResult, this);
        this.searchHistory.add(history);
    }
}
