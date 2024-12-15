package com.quiz.result.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Score {
    private Long scoreId;
    private final String session;
    private final String username;
    private final int score;
    private final int maxScore;
}
