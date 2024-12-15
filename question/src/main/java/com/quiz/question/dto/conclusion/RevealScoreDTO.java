package com.quiz.question.dto.conclusion;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class RevealScoreDTO {
    private final List<RevealQuestionDTO> questions;
    private final int score;
}
