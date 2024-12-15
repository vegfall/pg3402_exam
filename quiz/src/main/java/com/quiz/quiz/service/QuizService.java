package com.quiz.quiz.service;

import com.quiz.quiz.dto.questionDTO;
import com.quiz.quiz.dto.resultDTO;
import com.quiz.quiz.dto.SessionDTO;
import com.quiz.quiz.dto.conclusion.revealScoreDTO;
import com.quiz.quiz.dto.request.CreateSessionRequest;
import com.quiz.quiz.dto.request.PostAnswerRequest;
import com.quiz.quiz.model.SessionStatus;

public interface QuizService {
    SessionDTO postNewSession(CreateSessionRequest session);
    SessionDTO getSession(String sessionKey);
    questionDTO getCurrentQuestion(String sessionKey);
    void putNextQuestion(String sessionKey);
    String generateSessionKey();
    resultDTO postAnswer(String sessionKey, PostAnswerRequest answer);
    revealScoreDTO getScore(String sessionKey, String username);
    SessionStatus getStatus(String sessionKey);

}
