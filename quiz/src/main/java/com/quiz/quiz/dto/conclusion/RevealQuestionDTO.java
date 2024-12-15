package com.quiz.quiz.dto.conclusion;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class RevealQuestionDTO {
    private final String questionText;
    private final List<RevealAlternativeDTO> alternatives;
    private final int chosenAlternativeKey;
}
