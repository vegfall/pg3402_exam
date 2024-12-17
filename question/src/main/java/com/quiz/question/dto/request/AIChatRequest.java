package com.quiz.question.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AIChatRequest {
    private final String sessionKey;
    private final String prompt;
}