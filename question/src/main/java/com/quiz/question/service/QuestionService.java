package com.quiz.question.service;

import com.quiz.question.dto.QuestionDTO;
import com.quiz.question.dto.ResultDTO;
import com.quiz.question.dto.request.PostAnswerRequest;

public interface QuestionService {
    QuestionDTO getQuestion(String sessionKey, Integer questionKey);
    ResultDTO postAnswer(String sessionKey, Integer questionKey, PostAnswerRequest answer);
}
