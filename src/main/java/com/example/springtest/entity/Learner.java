package com.example.springtest.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
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

    @ElementCollection
    private final List<String> words = new ArrayList<>();

    private int correctAnswers;

    private int totalAnswers;

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

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getTotalAnswers() {
        return totalAnswers;
    }

    public List<String> getWords() {
        return words;
    }

    public String getWordAt(final int index) {
        return words.get(index);
    }

    public void addWord(String word) {
        words.add(word);
    }

    public void addWords(Collection<? extends String> words) {
        this.words.addAll(words);
    }

    public void addCorrectAnswer() {
        System.out.println("Adding correct answer: " + correctAnswers);
        this.correctAnswers = correctAnswers + 1;
        this.totalAnswers = totalAnswers + 1;
        System.out.println("Correct answers incremented: " + correctAnswers);
    }

    public void addWrongAnswer() {
        this.totalAnswers = totalAnswers + 1;
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
