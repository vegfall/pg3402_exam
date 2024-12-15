package com.quiz.question.repository;

import com.quiz.question.model.Alternative;
import com.quiz.question.model.Question;
import org.springframework.stereotype.Repository;

import java.util.*;

import static org.springframework.util.CollectionUtils.toArray;

@Repository
public class MockQuestionRepository {
    Map<String, Map<Integer, Question>> mockQuestionDatabase;
    Map<Integer, Question> mockQuestionTable;



    public MockQuestionRepository() {
        mockQuestionDatabase = new HashMap<>();
        mockQuestionTable = new HashMap<>();

        createMockQuestionDatabase();

        mockQuestionDatabase.put("1234", mockQuestionTable);
    }

    private void createMockQuestionDatabase() {
        mockQuestionTable.put(0, new Question(
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

        mockQuestionTable.put(1, new Question(
                1L,
                2,
                "Who was the first emperor of Rome?",
                Arrays.asList(
                    new Alternative(5L, 1,"Cato", false, "No"),
                    new Alternative(6L, 2,"Caesar", false, "No"),
                    new Alternative(7L, 3,"Augustus", true, "Yes")
                )
        ));

        mockQuestionTable.put(2, new Question(
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
        return mockQuestionDatabase.get(sessionKey).get(questionKey);
    }

    public Question[] getAllQuestions(String sessionKey) {
        return mockQuestionDatabase.get(sessionKey).values().toArray(new Question[0]);
    }

    public List<Alternative> getAlternatives(String sessionKey, Integer questionKey) {
        Question currentQuestion = getQuestion(sessionKey, questionKey);

        return currentQuestion.getAlternatives();
    }
}
