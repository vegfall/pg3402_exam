package com.quiz.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResultDTO {
    private final int correctAlternative;
    private final String explanation;
}
