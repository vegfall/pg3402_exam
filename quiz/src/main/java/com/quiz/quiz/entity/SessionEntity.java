package com.quiz.quiz.entity;

import com.quiz.quiz.model.SessionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sessions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    private String sessionKey;
    private String theme;
    private int numberOfAlternatives;
    private String username;
    private int currentQuestionKey;
    private SessionStatus status;
}
