package com.quiz.result.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Result {
    private Long resultId;
    private final int correctAlternative;
    private final String explanation;
}
