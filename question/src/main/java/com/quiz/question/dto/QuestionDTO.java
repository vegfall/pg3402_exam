package com.quiz.question.dto;

import com.quiz.question.model.Alternative;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class QuestionDTO {
    private int questionKey;
    private String questionText;
    private List<AlternativeDTO> alternatives;
}