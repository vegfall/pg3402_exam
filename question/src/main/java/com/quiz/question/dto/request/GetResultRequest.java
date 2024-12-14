package com.quiz.question.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetResultRequest {
    private String username;
    private int selectedAlternative;
    private int correctAlternative;
    private String explanation;
}
