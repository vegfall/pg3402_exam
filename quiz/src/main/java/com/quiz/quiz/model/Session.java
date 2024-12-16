package com.quiz.quiz.model;

import com.quiz.quiz.dto.SessionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Session {
    private Long sessionId;
    private SessionStatus status;
    private final String sessionKey;
    private final String theme;
    private final int numberOfAlternatives;
    private final String username;
    private int currentQuestionKey;
    //CreatedAt

    public SessionDTO getDTO() {
        return new SessionDTO(sessionKey, theme, numberOfAlternatives, username);
    }
}
