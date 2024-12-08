package com.quiz.quiz.service;

import com.quiz.quiz.client.QuestionClient;
import com.quiz.quiz.dto.QuestionDTO;
import com.quiz.quiz.repository.MockQuizRepository;
import org.springframework.stereotype.Service;

@Service
public class SimpleQuizService implements QuizService {
    private final MockQuizRepository quizRepository;
    private final QuestionClient questionClient;

    public SimpleQuizService(QuestionClient questionClient, MockQuizRepository quizRepository) {
        this.questionClient = questionClient;
        this.quizRepository = quizRepository;
    }

    @Override
    public QuestionDTO getCurrentQuestion(String sessionKey) {
        int currentQuestionKey = quizRepository.getSession(sessionKey).getCurrentQuestionKey();

        return questionClient.getQuestion(sessionKey, currentQuestionKey);
    }

    @Override
    public void putNextQuestion(String sessionKey) {
        int currentQuestionKey = quizRepository.getSession(sessionKey).getCurrentQuestionKey() + 1;
        quizRepository.getSession(sessionKey).setCurrentQuestionKey(currentQuestionKey);
    }
}
