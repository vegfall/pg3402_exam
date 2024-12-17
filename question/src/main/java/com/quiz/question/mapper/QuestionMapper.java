package com.quiz.question.mapper;

import com.quiz.question.dto.AlternativeDTO;
import com.quiz.question.dto.QuestionDTO;
import com.quiz.question.entity.AlternativeEntity;
import com.quiz.question.entity.QuestionEntity;
import com.quiz.question.model.Alternative;
import com.quiz.question.model.Question;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class QuestionMapper {
    public QuestionDTO toDTO(Question model) {
        List<AlternativeDTO> alternatives = new ArrayList<>();

        for (Alternative alternative : model.getAlternatives()) {
            alternatives.add(toDTO(alternative));
        }

        return new QuestionDTO(
                model.getQuestionKey(),
                model.getQuestionText(),
                alternatives
        );
    }

    public Question toModel(QuestionEntity entity) {
        Set<Alternative> alternatives = new HashSet<>();

        for (AlternativeEntity alternative : entity.getAlternatives()) {
            alternatives.add(toModel(alternative));
        }

        return new Question(
                entity.getQuestionId(),
                entity.getQuestionKey(),
                entity.getQuestionText(),
                alternatives
        );
    }

    public QuestionEntity toEntity(Question model) {
        Set<AlternativeEntity> alternatives = new HashSet<>();
        AlternativeEntity entity;

        for (Alternative alternative : model.getAlternatives()) {
            entity = toEntity(alternative);
            entity.setQuestion(new QuestionEntity(model.getQuestionId(), 0, null, null, null));
            alternatives.add(entity);
        }

        return new QuestionEntity(
                model.getQuestionId(),
                model.getQuestionKey(),
                model.getQuestionText(),
                alternatives,
                null
        );
    }

    public AlternativeDTO toDTO(Alternative model) {
        return new AlternativeDTO(
                model.getAlternativeKey(),
                model.getAlternativeText()
        );
    }

    public Alternative toModel(AlternativeEntity entity) {
        return new Alternative(
                entity.getAlternativeId(),
                entity.getAlternativeKey(),
                entity.getAlternativeText(),
                entity.isCorrect(),
                entity.getAlternativeExplanation()
        );
    }

    public AlternativeEntity toEntity(Alternative model) {
        return new AlternativeEntity(
                model.getAlternativeId(),
                model.getAlternativeKey(),
                model.getAlternativeText(),
                model.isCorrect(),
                model.getAlternativeExplanation(),
                null
        );
    }
}
