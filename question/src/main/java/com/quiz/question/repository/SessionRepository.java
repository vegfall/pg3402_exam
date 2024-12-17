package com.quiz.question.repository;

import com.quiz.question.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    Optional<SessionEntity> findBySessionKey(String sessionKey);

    @Query("SELECT s FROM SessionEntity s " +
            "LEFT JOIN FETCH s.questions q " +
            "LEFT JOIN FETCH q.alternatives " +
            "WHERE s.sessionKey = :sessionKey " +
            "ORDER BY q.questionKey ASC")
    Optional<SessionEntity> findBySessionKeyWithQuestionsAndAlternatives(@Param("sessionKey") String sessionKey);

}
