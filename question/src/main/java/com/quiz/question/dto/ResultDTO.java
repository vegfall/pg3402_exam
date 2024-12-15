package com.quiz.question.dto;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
public class ResultDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String flowId;
    private final int correctAlternative;
    private final String explanation;

    public ResultDTO(int correctAlternative, String explanation) {
        this.flowId = UUID.randomUUID().toString();
        this.correctAlternative = correctAlternative;
        this.explanation = explanation;
    }
}