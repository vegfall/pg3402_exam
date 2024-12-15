package com.quiz.result.service;

import com.quiz.result.dto.ResultDTO;
import com.quiz.result.dto.request.GetResultRequest;
import org.springframework.stereotype.Service;

@Service
public class SingleplayerResultService implements ResultService {
    @Override
    public ResultDTO postAnswer(GetResultRequest request) {
        return new ResultDTO(request.getCorrectAlternativeKey(), request.getExplanation());
    }
}
