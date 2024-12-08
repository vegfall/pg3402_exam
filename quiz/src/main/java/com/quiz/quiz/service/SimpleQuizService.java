package com.quiz.quiz.service;

import com.quiz.quiz.client.QuestionClient;
import com.quiz.quiz.dto.QuestionDTO;
import org.springframework.stereotype.Service;

@Service
public class SimpleQuizService implements QuizService {
    private final QuestionClient questionClient;

    public SimpleQuizService(QuestionClient questionClient) {
        this.questionClient = questionClient;
    }

    @Override
    public QuestionDTO test() {
        return questionClient.getQuestion("1234", 2);
    }
}
