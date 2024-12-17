package com.quiz.question.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "alternatives", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"question_id", "alternativeKey"})
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlternativeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alternativeId;

    @Column(nullable = false)
    private int alternativeKey;

    private String alternativeText;
    private boolean correct;
    private String alternativeExplanation;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    @JsonIgnore
    private QuestionEntity question;
}
