package com.quiz.result.dto.request;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
public class GetResultRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String flowId;
    private final String sessionKey;
    private final String username;
    private final int selectedAlternativeKey;
    private final int correctAlternativeKey;
    private final String explanation;

    public GetResultRequest(String sessionKey, String username, int selectedAlternativeKey, int correctAlternativeKey, String explanation) {
        this.flowId = UUID.randomUUID().toString();
        this.sessionKey = sessionKey;
        this.username = username;
        this.selectedAlternativeKey = selectedAlternativeKey;
        this.correctAlternativeKey = correctAlternativeKey;
        this.explanation = explanation;
    }
}

