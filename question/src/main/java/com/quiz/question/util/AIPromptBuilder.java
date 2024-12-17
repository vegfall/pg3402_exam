package com.quiz.question.util;

import java.util.ArrayList;
import java.util.List;

public class AIPromptBuilder {
    public String build(String theme, int numberOfAlternatives, int difficultyLevel, List<String> previousQuestions) {
        List<String> previousList = new ArrayList<>(previousQuestions);
        String previousString = String.join("\n", previousList);

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
                        "questionText": "What is the capital of Norway?",
                        "alternatives": [
                            {
                                "alternativeText": "Oslo",
                                "correct": true,
                                "alternativeExplanation": "Oslo is the capital and largest city of Norway."
                            },
                            {
                                "alternativeText": "Copenhagen",
                                "correct": false,
                                "alternativeExplanation": "Copenhagen is the capital of Denmark, not Norway."
                            }
                        ]
                    },
                    {
                        "questionText": "Which element has the chemical symbol 'O'?",
                        "alternatives": [
                            {
                                "alternativeText": "Gold",
                                "correct": false,
                                "alternativeExplanation": "Gold's chemical symbol is 'Au'."
                            },
                            {
                                "alternativeText": "Oxygen",
                                "correct": true,
                                "alternativeExplanation": "Oxygen's chemical symbol is 'O'."
                            }
                        ]
                    }
                ]
                ---
                
                Do not reuse these previous questions:
                ---
                %s
                ---
                
                Please generate 5 questions. It is VERY IMPORTANT that you just send the JSON body, Do NOT send anything else.
                """;

        return String.format(
                promptText,
                theme,
                numberOfAlternatives,
                difficultyLevel,
                previousString
        );
    }
}
