package com.quiz.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class QuestionDTO {
    private int questionKey;
    private String questionText;
    private List<String> alternatives;
}
