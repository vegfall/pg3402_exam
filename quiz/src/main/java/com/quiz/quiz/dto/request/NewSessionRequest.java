package com.quiz.quiz.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NewSessionRequest {
    private final String sessionKey;
    private final String theme;
    private final int numberOfAlternatives;
}
