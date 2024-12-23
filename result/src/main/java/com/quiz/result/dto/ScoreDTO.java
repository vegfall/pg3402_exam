package com.quiz.result.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ScoreDTO {
    private final List<Integer> chosenAlternatives;
    private final int score;
}
