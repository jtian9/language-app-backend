package com.example.springtest.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String systemPrompt;

    @ManyToOne
    @JoinColumn(name = "learner_id")
    private Learner learner;

    @OneToMany(mappedBy = "conversation")
    @JsonBackReference
    private final List<ConversationMessage> ConversationMessages = new ArrayList<>();

    public Conversation() {}

    public Conversation(String systemPrompt, Learner learner) {
        this.systemPrompt = systemPrompt;
        this.learner = learner;
    }

    public Long getId() {
        return id;
    }

    public String getSystemPrompt() {
        return systemPrompt;
    }

    public Learner getLearner() {
        return learner;
    }

    public void setSystemPrompt(String systemPrompt) {
        this.systemPrompt = systemPrompt;
    }

    public void addConversationMessage(ConversationMessage message) {
        ConversationMessages.add(message);
    }

    public List<ConversationMessage> getConversationMessages() {
        return ConversationMessages;
    }
}
