package com.quiz.quiz.service;

import com.quiz.quiz.dto.QuestionDTO;
import com.quiz.quiz.dto.ResultDTO;
import com.quiz.quiz.dto.SessionDTO;
import com.quiz.quiz.dto.conclusion.RevealScoreDTO;
import com.quiz.quiz.dto.request.CreateSessionRequest;
import com.quiz.quiz.dto.request.PostAnswerRequest;

public interface QuizService {
    SessionDTO postNewSession(CreateSessionRequest session);
    SessionDTO getSession(String sessionKey);
    QuestionDTO getCurrentQuestion(String sessionKey);
    void putNextQuestion(String sessionKey);
    String generateSessionKey();
    ResultDTO postAnswer(String sessionKey, PostAnswerRequest answer);
    RevealScoreDTO getScore(String sessionKey, String username);
}
