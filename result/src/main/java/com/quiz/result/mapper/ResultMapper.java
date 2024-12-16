package com.quiz.result.mapper;

import com.quiz.result.dto.ScoreDTO;
import com.quiz.result.dto.SessionScoreDTO;
import com.quiz.result.entity.ScoreEntity;
import com.quiz.result.model.Score;
import com.quiz.result.model.SessionScore;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ResultMapper {
    public ScoreDTO toDTO(Score model) {
        return new ScoreDTO(model.getChosenAlternatives(), model.getTotalScore());
    }

    public ScoreEntity toEntity(Score model) {
        ScoreEntity entity = new ScoreEntity();
        StringBuilder chosenAlternatives = new StringBuilder();

        entity.setScoreId(model.getScoreId());
        entity.setSessionKey(model.getSessionKey());
        entity.setTotalScore(model.getTotalScore());

        for (Integer integer : model.getChosenAlternatives()) {
            chosenAlternatives.append(integer);
        }

        entity.setChosenAlternatives(chosenAlternatives.toString());

        return entity;
    }

    public Score toModel(ScoreEntity entity) {
        List<Integer> chosenAlternatives = new ArrayList<>();
        for (char alternative : entity.getChosenAlternatives().toCharArray()) {
            chosenAlternatives.add(Character.getNumericValue(alternative));
        }

        return new Score(
                entity.getScoreId(),
                entity.getSessionKey(),
                chosenAlternatives,
                entity.getTotalScore()
        );
    }

    public SessionScoreDTO toSessionScoreDTO(ScoreEntity entity) {
        return new SessionScoreDTO(entity.getUser().getUsername(), entity.getTotalScore());
    }
}
