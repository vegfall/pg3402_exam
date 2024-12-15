package com.quiz.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class resultDTO {
    private final int correctAlternative;
    private final String explanation;
}
