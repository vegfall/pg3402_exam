package com.quiz.result.service;

import com.quiz.result.dto.ResultDTO;
import com.quiz.result.dto.ScoreDTO;
import com.quiz.result.dto.request.GetResultRequest;

import java.util.List;

public interface ResultService {
    ResultDTO postAnswer(GetResultRequest request);
    ScoreDTO getScore(String sessionKey, String username);
    List<ScoreDTO> getScoresForSession(String sessionKey);
}
