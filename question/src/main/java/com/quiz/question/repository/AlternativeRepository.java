package com.quiz.question.repository;

import com.quiz.question.entity.AlternativeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlternativeRepository extends JpaRepository<AlternativeEntity, Long> {}
