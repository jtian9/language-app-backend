package com.example.springtest.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "learners")
public class Learner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @OneToMany(mappedBy = "learner")
    @JsonBackReference
    private final List<SearchHistory> searchHistory = new ArrayList<>();

    @OneToMany(mappedBy = "learner")
    @JsonBackReference
    private final List<Conversation> conversations = new ArrayList<>();

    public Learner() {}

    public Learner(String username) {
        this.username = username;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<SearchHistory> getSearchHistory() {
        return searchHistory;
    }
    public List<Conversation> getConversations() {
        return conversations;
    }

    public void addSearchHistory(String searchTerm, String searchResult) {
        SearchHistory history = new SearchHistory(searchTerm, searchResult, this);
        this.searchHistory.add(history);
    }
}
