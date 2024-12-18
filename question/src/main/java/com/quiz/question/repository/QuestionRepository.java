package com.quiz.question.repository;

import com.quiz.question.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    @Query("SELECT q FROM QuestionEntity q WHERE q.session.sessionKey = :sessionKey AND q.questionKey = :questionKey")
    Optional<QuestionEntity> findBySessionKeyAndQuestionKey(@Param("sessionKey") String sessionKey, @Param("questionKey") int questionKey);

    @Query("SELECT COALESCE(MAX(q.questionKey), 0) + 1 FROM QuestionEntity q WHERE q.session.sessionId = :sessionId")
    int findNextQuestionKeyBySessionId(@Param("sessionId") Long sessionId);

    boolean existsBySession_SessionKey(String sessionKey);
}
