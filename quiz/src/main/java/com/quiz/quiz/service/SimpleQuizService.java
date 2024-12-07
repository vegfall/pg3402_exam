package com.quiz.quiz.service;

import com.quiz.quiz.dto.QuestionDTO;
import com.quiz.quiz.repository.MockQuizRepository;
import org.springframework.stereotype.Service;

@Service
public class SimpleQuizService implements QuizService {
    private final MockQuizRepository quizRepository;

    public SimpleQuizService(MockQuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Override
    public QuestionDTO test() {
        return quizRepository.test();
    }
}
