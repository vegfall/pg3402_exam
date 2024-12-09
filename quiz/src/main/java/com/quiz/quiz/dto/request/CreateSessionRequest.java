package com.quiz.quiz.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateSessionRequest {
    private String theme;
    private Integer numberOfAlternatives;
}
