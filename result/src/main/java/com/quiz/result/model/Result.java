package com.quiz.result.model;

import com.quiz.result.dto.ResultDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Result {
    private int resultId;
    private final int correctAlternative;
    private final String explanation;

    public ResultDTO getDTO() {
        return new ResultDTO(correctAlternative, explanation);
    }
}
