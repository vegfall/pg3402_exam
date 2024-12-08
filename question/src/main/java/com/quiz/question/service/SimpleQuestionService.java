package com.quiz.question.service;

import com.quiz.question.dto.QuestionDTO;
import com.quiz.question.repository.MockQuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class SimpleQuestionService implements QuestionService {
    private final MockQuestionRepository questionRepository;

    public SimpleQuestionService(MockQuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public QuestionDTO getQuestion(String sessionKey, Integer questionKey) {
        return questionRepository.getQuestion(sessionKey, questionKey).getDTO();
    }
}
