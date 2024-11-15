package com.example.springtest.controller;

import com.example.springtest.form.QuizResponse;
import com.example.springtest.form.UsernameForm;
import com.example.springtest.service.LangChainService;
import com.example.springtest.service.LearnerService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/quiz")
@CrossOrigin(origins = "http://localhost:3000")
public class QuizController {

    private final LangChainService langChainService;
    private final LearnerService learnerService;

    public QuizController(LangChainService langChainService, LearnerService learnerService) {
        this.langChainService = langChainService;
        this.learnerService = learnerService;
    }

    @RequestMapping(value = "/getQuiz", method = RequestMethod.POST)
    public QuizResponse generateQuiz(@RequestBody UsernameForm usernameForm) throws Exception {
        String username = usernameForm.getUsername();
        String quizWord = learnerService.getRandomWord(username);

        if (quizWord == null) {
            QuizResponse quizResponse = new QuizResponse();
            quizResponse.setFailure();
            return quizResponse;
        }

        int correctAnswers = learnerService.getCorrectAnswers(username);
        int totalAnswers = learnerService.getTotalAnswers(username);

        QuizResponse response = langChainService.generateQuiz(quizWord);

        response.setCorrectAnswers(correctAnswers);
        response.setTotalAnswers(totalAnswers);

        System.out.println("Received generated quiz: " + response);
        response.setCorrectAnswer(quizWord);
        return response;
    }

    @RequestMapping(value = "/correct", method = RequestMethod.POST)
    public void quizCorrect(@RequestBody UsernameForm usernameForm) throws Exception {
        String username = usernameForm.getUsername();
        learnerService.addCorrectAnswer(username);
        System.out.println("Received quizCorrect for: " + username);

    }

    @RequestMapping(value = "/wrong", method = RequestMethod.POST)
    public void quizWrong(@RequestBody UsernameForm usernameForm) throws Exception {
        String username = usernameForm.getUsername();
        learnerService.addWrongAnswer(username);
        System.out.println("Received quizWrong for: " + username);

    }
}
