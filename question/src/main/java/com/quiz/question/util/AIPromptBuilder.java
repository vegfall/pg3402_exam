package com.quiz.question.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class AIPromptBuilder {
    private final String theme;
    private final int numberOfAlternatives;
    private final int difficultyLevel;
    private final int numberOfQuestions;
    private List<String> previousQuestions;

    public String build() {
        String previousString = "";

        List<String> previousList = new ArrayList<>(previousQuestions);

        previousString = String.join("\n", previousList);

        String promptText =
                """
                I need help to create questions for a quiz about "%s", and each question will have %d alternatives.
                Each question will increase in difficulty, ranging from 1 - 10, where 1 is common knowledge while 10 is expert difficulty.
                You are currently on difficulty level %d.
                It is VERY IMPORTANT that the questions follow this JSON structure, although you still use the correct number of alternatives,
                and the position of the correct alternative is random:
                ---
                [
                    {
                        "questionText": "What is the capital of France?",
                        "alternatives": [
                            {
                                "alternativeText": "Paris",
                                "correct": true,
                                "alternativeExplanation": "Paris is the capital and largest city of France."
                            },
                            {
                                "alternativeText": "Rome",
                                "correct": false,
                                "alternativeExplanation": "Rome is the capital of Italy, not France."
                            }
                        ]
                    }
                ]
                ---
                
                Do not reuse these previous questions:
                ---
                %s
                ---
                Please generate %d question(s). It is VERY IMPORTANT that you just send the JSON body, Do NOT send anything else.
                """;

        return String.format(
                promptText,
                theme,
                numberOfAlternatives,
                difficultyLevel,
                previousString,
                numberOfQuestions
        );
    }
}
