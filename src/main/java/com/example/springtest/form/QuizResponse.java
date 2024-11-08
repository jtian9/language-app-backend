package com.example.springtest.form;

import java.util.List;

public class QuizResponse {

    private String question;
    private List<String> answers;
    private String correctAnswer;

    private int correctAnswers;
    private int totalAnswers;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public void addAnswer(String answer) {
        answers.add(answer);
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
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

    @Override
    public String toString() {
        return "QuizResponse [question=" + question + ", answers=" + answers + ", correctAnswers=" + correctAnswers + ", totalAnswers=" + totalAnswers + "]";
    }

}
