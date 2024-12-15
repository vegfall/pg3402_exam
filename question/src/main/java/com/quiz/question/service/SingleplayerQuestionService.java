package com.quiz.question.service;

import com.quiz.question.client.ResultClient;
import com.quiz.question.client.ResultEventHandler;
import com.quiz.question.dto.QuestionDTO;
import com.quiz.question.dto.ResultDTO;
import com.quiz.question.dto.ScoreDTO;
import com.quiz.question.dto.conclusion.RevealAlternativeDTO;
import com.quiz.question.dto.conclusion.RevealQuestionDTO;
import com.quiz.question.dto.conclusion.RevealScoreDTO;
import com.quiz.question.dto.request.GetResultRequest;
import com.quiz.question.dto.request.PostAnswerRequest;
import com.quiz.question.model.Alternative;
import com.quiz.question.model.Question;
import com.quiz.question.repository.MockQuestionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SingleplayerQuestionService implements QuestionService {
    private final MockQuestionRepository questionRepository;
    private final ResultEventHandler resultEventHandler;
    private final ResultClient resultClient;

    public SingleplayerQuestionService(MockQuestionRepository questionRepository, ResultEventHandler resultEventHandler, ResultClient resultClient) {
        this.questionRepository = questionRepository;
        this.resultEventHandler = resultEventHandler;
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

        return resultEventHandler.sendGetResultRequest(getResultRequest);
    }

    @Override
    public RevealScoreDTO getScore(String sessionKey, String username) {
        ScoreDTO score = resultClient.getScore(sessionKey, username);

        Question[] questions = questionRepository.getAllQuestions(sessionKey);
        List<RevealQuestionDTO> revealQuestions = new ArrayList<>();
        List<RevealAlternativeDTO> revealAlternatives;

        for (int i = 0; i < score.getChosenAlternatives().size(); i++) {
            revealAlternatives = new ArrayList<>();

            for (Alternative alternative : questions[i].getAlternatives()) {
                revealAlternatives.add(new RevealAlternativeDTO(
                        alternative.getAlternativeKey(),
                        alternative.getAlternativeText(),
                        alternative.isCorrect()
                ));
            }

            revealQuestions.add(new RevealQuestionDTO(
                    questions[i].getQuestionText(),
                    revealAlternatives,
                    score.getChosenAlternatives().get(i))
            );
        }

        return new RevealScoreDTO(revealQuestions, score.getScore());
    }

    @Override
    public boolean checkMoreQuestions(String sessionKey, int currentQuestionKey) {
        for (Question question : questionRepository.getAllQuestions(sessionKey)) {
            if (question.getQuestionKey() > currentQuestionKey + 1) {
                return true;
            }
        }

        return false;
    }
}
