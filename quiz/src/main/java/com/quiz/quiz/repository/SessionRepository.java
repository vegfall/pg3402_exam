package com.quiz.quiz.repository;

import com.quiz.quiz.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    Optional<SessionEntity> findBySessionKey(String sessionKey);
}
