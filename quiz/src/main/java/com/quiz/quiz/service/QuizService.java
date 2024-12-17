package com.quiz.quiz.service;

import com.quiz.quiz.dto.QuestionDTO;
import com.quiz.quiz.dto.ResultDTO;
import com.quiz.quiz.dto.SessionDTO;
import com.quiz.quiz.dto.SessionScoreDTO;
import com.quiz.quiz.dto.conclusion.revealScoreDTO;
import com.quiz.quiz.dto.request.AIChatRequest;
import com.quiz.quiz.dto.request.CreateSessionRequest;
import com.quiz.quiz.dto.request.LoadSessionRequest;
import com.quiz.quiz.dto.request.PostAnswerRequest;
import com.quiz.quiz.model.SessionStatus;

import java.util.List;

public interface QuizService {
    SessionDTO postNewSession(CreateSessionRequest session);
    SessionDTO getSession(String sessionKey);
    QuestionDTO getCurrentQuestion(String sessionKey);
    void putNextQuestion(String sessionKey);
    ResultDTO postAnswer(String sessionKey, PostAnswerRequest answer);
    revealScoreDTO getScore(String sessionKey, String username);
    List<SessionScoreDTO> getScores(String sessionKey);
    SessionStatus getStatus(String sessionKey);
    void startSession(String sessionKey);
    SessionDTO loadPreviousSession(String sessionKey, LoadSessionRequest request);
    //AIChatRequest getAIQuestion(String sessionKey);
}
