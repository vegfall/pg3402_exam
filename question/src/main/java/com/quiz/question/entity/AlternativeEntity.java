package com.quiz.question.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alternatives")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alternativeId;

    private int alternativeKey;
    private String alternativeText;
    private boolean correct;
    private String alternativeExplanation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;
}
