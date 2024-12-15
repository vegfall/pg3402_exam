package com.quiz.result.service;

import com.quiz.result.dto.ResultDTO;
import com.quiz.result.dto.request.GetResultRequest;

public interface ResultService {
    ResultDTO postAnswer(GetResultRequest request);
}
