package com.quiz.ai.service;

import com.quiz.ai.dto.request.AIChatRequest;
import com.quiz.ai.dto.response.AIChatResponse;

public interface AIService {
    AIChatResponse getResponse(AIChatRequest prompt);
}
