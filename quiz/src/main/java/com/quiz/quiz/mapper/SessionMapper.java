package com.quiz.quiz.mapper;

import com.quiz.quiz.dto.SessionDTO;
import com.quiz.quiz.entity.SessionEntity;
import com.quiz.quiz.model.Session;

public class SessionMapper {
    public SessionDTO toDTO(Session model) {
        return new SessionDTO(
                model.getSessionKey(), model.getTheme(), model.getNumberOfAlternatives(), model.getUsername()
        );
    }

    public SessionEntity toEntity(Session model) {
        return new SessionEntity(
                model.getSessionId(), model.getSessionKey(), model.getTheme(), model.getNumberOfAlternatives(),
                model.getUsername(), model.getCurrentQuestionKey()
        );
    }

    public Session toModel(SessionEntity entity) {
        return new Session(
                entity.getSessionId(), entity.getSessionKey(), entity.getTheme(), entity.getNumberOfAlternatives(),
                entity.getUsername(), entity.getCurrentQuestionKey()
        );
    }
}
