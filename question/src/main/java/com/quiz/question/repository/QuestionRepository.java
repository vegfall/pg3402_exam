package com.quiz.question.repository;

import com.quiz.question.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    Optional<QuestionEntity> findBySession_SessionKeyAndQuestionKey(String sessionKey, int questionKey);
}
