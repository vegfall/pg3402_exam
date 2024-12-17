package com.quiz.question.repository;

import com.quiz.question.entity.AlternativeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlternativeRepository extends JpaRepository<AlternativeEntity, Long> {
    @Query("SELECT MAX(a.alternativeKey) FROM AlternativeEntity a WHERE a.question.id = :questionId")
    Integer findMaxAlternativeKeyByQuestionId(@Param("questionId") Long questionId);
}
