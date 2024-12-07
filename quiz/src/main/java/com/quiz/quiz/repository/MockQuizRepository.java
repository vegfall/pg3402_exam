package com.quiz.quiz.repository;

import com.quiz.quiz.dto.QuestionDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MockQuizRepository {
    public QuestionDTO test() {
        List<String> alternatives = new ArrayList<>();

        alternatives.add("Oslo");
        alternatives.add("Bergen");
        alternatives.add("Trondheim");
        alternatives.add("Hamar");

        return new QuestionDTO(
                1234,
                "What is the capital of Norway?",
                alternatives);
    }
}
