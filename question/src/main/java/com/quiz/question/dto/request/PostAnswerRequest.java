package com.quiz.question.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostAnswerRequest {
    private String username;
    private int alternativeKey;
}
