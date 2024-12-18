package com.quiz.quiz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Session {
    private Long sessionId;
    private final String sessionKey;
    private final String theme;
    private final int numberOfAlternatives;
    private final String username;
    private int currentQuestionKey;
    private SessionStatus status;
}
