package com.quiz.quiz.entity;

import com.quiz.quiz.model.SessionStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class SessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id", nullable = false, updatable = false)
    private Long sessionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "ENUM('CREATED', 'ONGOING', 'COMPLETED') DEFAULT 'CREATED'")
    private SessionStatus status = SessionStatus.CREATED;

    @Column(name = "session_key", nullable = false, unique = true)
    private String sessionKey;

    @Column(name = "theme", nullable = false)
    private String theme;

    @Column(name = "number_of_alternatives", nullable = false)
    private int numberOfAlternatives;

    @Column(name = "username")
    private String username;

    @Column(name = "current_question_key", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int currentQuestionKey = 0;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
