package com.quiz.question.service;

import com.quiz.question.client.ResultClient;
import com.quiz.question.dto.QuestionDTO;
import com.quiz.question.dto.ResultDTO;
import com.quiz.question.dto.request.PostAnswerRequest;
import com.quiz.question.model.Alternative;
import com.quiz.question.repository.MockQuestionRepository;
import org.springframework.stereotype.Service;

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
        Alternative correctAlternative = questionRepository.getCorrectAlternative(sessionKey, questionKey);

        return new ResultDTO(correctAlternative.getAlternativeKey(), "It worked! :) --- " + correctAlternative.getAlternativeExplanation());
    }
}
