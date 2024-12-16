package com.quiz.question.model;

import com.quiz.question.dto.AlternativeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Alternative {
    private Long alternativeId;
    private int alternativeKey;
    private String alternativeText;
    private boolean correct;
    private String alternativeExplanation;
}
