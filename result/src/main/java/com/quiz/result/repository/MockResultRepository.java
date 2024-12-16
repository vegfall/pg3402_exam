package com.quiz.result.repository;

import com.quiz.result.model.Score;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Repository
public class MockResultRepository {
    Map<String, Map<String, Score>> mockResultDatabase;

    public MockResultRepository() {
        mockResultDatabase = new HashMap<>();
    }

    public void addAlternative(String username, String sessionKey, Integer chosenAlternativeKey, boolean correct) {
        Map<String, Score> mockResultTable;
        Score score;

        mockResultDatabase.putIfAbsent(username, new HashMap<>());

        mockResultTable = mockResultDatabase.get(username);

        mockResultTable.putIfAbsent(sessionKey, new Score(1L, new ArrayList<>(), 0));

        score = mockResultTable.get(sessionKey);

        score.getChosenAlternatives().add(chosenAlternativeKey);

        if (correct) {
            score.setTotalScore(score.getTotalScore() + 1);
        }
    }

    public Score getScore(String sessionKey, String username) {
        return mockResultDatabase.get(username).get(sessionKey);
    }
}