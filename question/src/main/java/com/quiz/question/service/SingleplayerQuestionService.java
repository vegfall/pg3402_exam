package com.quiz.question.service;

import com.quiz.question.client.ResultClient;
import com.quiz.question.dto.QuestionDTO;
import com.quiz.question.dto.ResultDTO;
import com.quiz.question.dto.request.PostAnswerRequest;
import com.quiz.question.model.Alternative;
import com.quiz.question.repository.MockQuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SingleplayerQuestionService implements QuestionService {
    private final MockQuestionRepository questionRepository;
    private final ResultClient resultClient;

    public SingleplayerQuestionService(MockQuestionRepository questionRepository, ResultClient resultClient) {
        this.questionRepository = questionRepository;
        this.resultClient = resultClient;
    }

    @Override
    public QuestionDTO getQuestion(String sessionKey, Integer questionKey) {
        return questionRepository.getQuestion(sessionKey, questionKey).getDTO();
    }

    @Override
    public ResultDTO postAnswer(String sessionKey, Integer questionKey, PostAnswerRequest answer) {
        List<Alternative> alternatives = questionRepository.getAlternatives(sessionKey, questionKey);

        Alternative chosenAlternative = alternatives.get(answer.getAlternativeKey() - 1);
        Alternative correctAlternative = null;

        for (Alternative alternative : alternatives) {
            if (alternative.isCorrect()) {
                correctAlternative = alternative;
                break;
            }
        }

        if (correctAlternative == null) {
            return null;
        }

        log.info("QuestionKey: {}, CorrectKey: {}, ChosenKey: {}, Size: {}", questionKey, correctAlternative.getAlternativeKey(), chosenAlternative.getAlternativeKey(), alternatives.size());
        return new ResultDTO(correctAlternative.getAlternativeKey(), "It worked! :) --- " + chosenAlternative.getAlternativeExplanation());
    }
}
