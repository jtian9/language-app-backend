package com.example.springtest.form;

public class QuizRecord {
    private int correctAnswers;
    private int totalAnswers;

    public QuizRecord(int correctAnswers, int totalAnswers) {
        this.correctAnswers = correctAnswers;
        this.totalAnswers = totalAnswers;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getTotalAnswers() {
        return totalAnswers;
    }

    public void setTotalAnswers(int totalAnswers) {
        this.totalAnswers = totalAnswers;
    }
}
