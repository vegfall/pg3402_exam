package com.quiz.result.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Score {
    private Long scoreId;
    private final List<Integer> chosenAlternatives;

    @Setter
    private int score;
}
