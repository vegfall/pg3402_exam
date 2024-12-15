package com.quiz.question.service;

import com.quiz.question.client.ResultClient;
import com.quiz.question.dto.QuestionDTO;
import com.quiz.question.dto.ResultDTO;
import com.quiz.question.dto.request.GetResultRequest;
import com.quiz.question.dto.request.PostAnswerRequest;
import com.quiz.question.model.Alternative;
import com.quiz.question.repository.MockQuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        GetResultRequest getResultRequest;
        List<Alternative> alternatives = questionRepository.getAlternatives(sessionKey, questionKey);

        Alternative chosenAlternative = alternatives.get(answer.getAlternativeKey() - 1);
        int correctAlternativeKey = 0;

        for (Alternative alternative : alternatives) {
            if (alternative.isCorrect()) {
                correctAlternativeKey = alternative.getAlternativeKey();
                break;
            }
        }

        getResultRequest = new GetResultRequest(
                sessionKey,
                answer.getUsername(),
                chosenAlternative.getAlternativeKey(),
                correctAlternativeKey,
                chosenAlternative.getAlternativeExplanation()
        );

        return resultClient.sendGetResultRequest(getResultRequest);
    }
}
