package com.quiz.quiz.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AIChatRequest {
    private final String prompt;
}