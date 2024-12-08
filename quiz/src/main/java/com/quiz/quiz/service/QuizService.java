package com.quiz.quiz.service;

import com.quiz.quiz.dto.QuestionDTO;

public interface QuizService {
    QuestionDTO getCurrentQuestion(String sessionKey);
    void putNextQuestion(String sessionKey);
}
