package com.quiz.question.model;

import com.quiz.question.dto.AlternativeDTO;
import com.quiz.question.dto.QuestionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class Question {
    private Long questionId;
    private int questionKey;
    private String questionText;
    private List<Alternative> alternatives;
}
