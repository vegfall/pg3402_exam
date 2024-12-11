package com.quiz.quiz.service;

import com.quiz.quiz.client.QuestionClient;
import com.quiz.quiz.dto.QuestionDTO;
import com.quiz.quiz.dto.SessionDTO;
import com.quiz.quiz.dto.request.CreateSessionRequest;
import com.quiz.quiz.model.Session;
import com.quiz.quiz.repository.MockQuizRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SimpleQuizService implements QuizService {
    private final MockQuizRepository quizRepository;
    private final QuestionClient questionClient;

    public SimpleQuizService(QuestionClient questionClient, MockQuizRepository quizRepository) {
        this.questionClient = questionClient;
        this.quizRepository = quizRepository;
    }

    @Override
    public SessionDTO postNewSession(CreateSessionRequest sessionRequest) {
        String sessionKey = generateSessionKey();

        Session createdSession = new Session(
                sessionKey, sessionRequest.getTheme(), sessionRequest.getNumberOfAlternatives()
        );

        return quizRepository.insertSession(createdSession).getDTO();
    }

    @Override
    public QuestionDTO getCurrentQuestion(String sessionKey) {
        int currentQuestionKey = quizRepository.getSession(sessionKey).getCurrentQuestionKey();

        return questionClient.getQuestion(sessionKey, currentQuestionKey);
    }

    @Override
    public void putNextQuestion(String sessionKey) {
        int currentQuestionKey = quizRepository.getSession(sessionKey).getCurrentQuestionKey() + 1;
        quizRepository.getSession(sessionKey).setCurrentQuestionKey(currentQuestionKey);
    }

    @Override
    public String generateSessionKey() {
        return "1234";
    }
}
