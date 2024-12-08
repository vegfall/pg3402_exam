package com.quiz.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SessionDTO {
    private String sessionKey;
    private int currentQuestionKey;
}
