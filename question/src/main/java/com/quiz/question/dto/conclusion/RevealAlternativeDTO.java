package com.quiz.question.dto.conclusion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RevealAlternativeDTO {
    private final int alternativeKey;
    private final String alternativeText;
    private final boolean correct;
}
