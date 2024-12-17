package com.quiz.ai.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AIChatResponse {
    private final String prompt;
    private final String response;
}
