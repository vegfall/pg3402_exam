package com.quiz.question.service;

import com.quiz.question.dto.QuestionDTO;
import com.quiz.question.dto.ResultDTO;
import com.quiz.question.dto.SessionScoreDTO;
import com.quiz.question.dto.conclusion.RevealScoreDTO;
import com.quiz.question.dto.request.NewSessionRequest;
import com.quiz.question.dto.request.PostAnswerRequest;

import java.util.List;

public interface QuestionService {
    QuestionDTO getQuestion(String sessionKey, Integer questionKey);
    ResultDTO postAnswer(String sessionKey, Integer questionKey, PostAnswerRequest answer);
    RevealScoreDTO getScore(String sessionKey, String username);
    List<SessionScoreDTO> getScores(String sessionKey);
    boolean checkMoreQuestions(String sessionKey, int currentQuestionKey);
    void postSession(NewSessionRequest session);
    void saveAIQuestions(String aiResponse);
}
