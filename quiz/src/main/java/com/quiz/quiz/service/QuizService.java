package com.quiz.quiz.service;

import com.quiz.quiz.dto.QuestionDTO;
import com.quiz.quiz.dto.SessionDTO;
import com.quiz.quiz.dto.request.CreateSessionRequest;

public interface QuizService {
    SessionDTO postNewSession(CreateSessionRequest session);
    SessionDTO getSession(String sessionKey);
    QuestionDTO getCurrentQuestion(String sessionKey);
    void putNextQuestion(String sessionKey);
    String generateSessionKey();
}
