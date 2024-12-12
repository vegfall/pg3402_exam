package com.quiz.question.repository;

import com.quiz.question.model.Alternative;
import com.quiz.question.model.Question;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MockQuestionRepository {
    Dictionary<Integer, Question> mockQuestionDatabase;

    public MockQuestionRepository() {
        mockQuestionDatabase = new Hashtable<>();

        createMockQuestionDatabase();
    }

    private void createMockQuestionDatabase() {
        mockQuestionDatabase.put(0, new Question(
                0L,
                1,
                "What is the capital of Norway?",
                Arrays.asList(
                    new Alternative(1L, 1,"Oslo", true, "Yes"),
                    new Alternative(2L, 2,"Stockholm", false, "No"),
                    new Alternative(3L, 3,"Copenhagen", false, "No"),
                    new Alternative(4L, 4,"Reykjavik", false, "No")
                )
        ));

        mockQuestionDatabase.put(1, new Question(
                1L,
                2,
                "Who was the first emperor of Rome?",
                Arrays.asList(
                    new Alternative(5L, 1,"Cato", false, "No"),
                    new Alternative(6L, 2,"Caesar", false, "No"),
                    new Alternative(7L, 3,"Augustus", true, "Yes")
                )
        ));

        mockQuestionDatabase.put(2, new Question(
                2L,
                3,
                "Does this project follow microservice architecture?",
                Arrays.asList(
                    new Alternative(8L, 1,"Yes", true, "Yes"),
                    new Alternative(9L, 2,"No", false, "No")
                )
        ));
    }

    public Question getQuestion(String sessionKey, Integer questionKey) {
        return Objects.equals(sessionKey, "1234") ? mockQuestionDatabase.get(questionKey) : null;
    }
}
