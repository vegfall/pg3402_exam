package com.quiz.ai.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AIChatRequest {
    private final String prompt;
}