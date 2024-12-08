package com.quiz.quiz.model;

import com.quiz.quiz.dto.SessionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Session {
    private final String sessionKey;
    private int currentQuestionKey;

    public SessionDTO getDTO() {
        return new SessionDTO(sessionKey, currentQuestionKey);
    }
}
