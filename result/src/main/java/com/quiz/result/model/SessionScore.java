package com.quiz.result.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SessionScore {
    private final String username;
    private int totalScore;
}
