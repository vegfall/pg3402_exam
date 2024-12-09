package com.quiz.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SessionDTO {
    private String sessionKey;
    private String theme;
    private int numberOfAlternatives;
    //Does DTO need currentQuestionKey?
    //private int currentQuestionKey;
}
