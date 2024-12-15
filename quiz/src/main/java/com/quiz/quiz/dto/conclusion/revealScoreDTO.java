package com.quiz.quiz.dto.conclusion;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class revealScoreDTO {
    private final List<RevealQuestionDTO> questions;
    private final int score;
}
