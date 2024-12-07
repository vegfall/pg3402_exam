package com.quiz.question.repository;

import com.quiz.question.model.Question;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MockQuestionRepository {
    public Question getQuestion() {
        List<String> alternatives = new ArrayList<>();

        alternatives.add("Oslo");
        alternatives.add("Bergen");
        alternatives.add("Trondheim");
        alternatives.add("Hamar");

        return new Question(
                1L,
                1234,
                "What is the capital of Norway?",
                alternatives);
    }
}
