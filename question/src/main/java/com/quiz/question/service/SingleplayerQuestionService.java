package com.quiz.question.service;

import com.quiz.question.client.ResultClient;
import com.quiz.question.event.AIEventHandler;
import com.quiz.question.event.ResultEventHandler;
import com.quiz.question.dto.QuestionDTO;
import com.quiz.question.dto.ResultDTO;
import com.quiz.question.dto.ScoreDTO;
import com.quiz.question.dto.SessionScoreDTO;
import com.quiz.question.dto.conclusion.RevealAlternativeDTO;
import com.quiz.question.dto.conclusion.RevealQuestionDTO;
import com.quiz.question.dto.conclusion.RevealScoreDTO;
import com.quiz.question.dto.request.GetResultRequest;
import com.quiz.question.dto.request.PostAnswerRequest;
import com.quiz.question.entity.QuestionEntity;
import com.quiz.question.entity.SessionEntity;
import com.quiz.question.mapper.QuestionMapper;
import com.quiz.question.model.Alternative;
import com.quiz.question.model.Question;
import com.quiz.question.repository.QuestionRepository;
import com.quiz.question.repository.SessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SingleplayerQuestionService implements QuestionService {
    private final SessionRepository sessionRepository;
    private final QuestionRepository questionRepository;
    private final ResultEventHandler resultEventHandler;
    private final ResultClient resultClient;
    private final QuestionMapper questionMapper;
    private final AIEventHandler aiEventHandler;

    public SingleplayerQuestionService(
            SessionRepository sessionRepository, QuestionRepository questionRepository, ResultEventHandler resultEventHandler, ResultClient resultClient, QuestionMapper questionMapper, @Lazy AIEventHandler aiEventHandler) {
        this.sessionRepository = sessionRepository;
        this.questionRepository = questionRepository;
        this.resultEventHandler = resultEventHandler;
        this.resultClient = resultClient;
        this.questionMapper = questionMapper;
        this.aiEventHandler = aiEventHandler;
    }

    private List<Question> getQuestionsBySessionKey(String sessionKey) {
        List<Question> questions = new ArrayList<>();
        SessionEntity session = sessionRepository.findBySessionKey(sessionKey)
                .orElseThrow(() -> new RuntimeException("Session not found for sessionKey: " + sessionKey));

        if (session.getQuestions().isEmpty()) {
            throw new RuntimeException("No questions found for sessionKey: " + sessionKey);
        }

        for (QuestionEntity question : session.getQuestions()) {
            questions.add(questionMapper.toModel(question));
        }

        return questions;
    }

    private Question getQuestionByQuestionKey(String sessionKey, int questionKey) {
        QuestionEntity questionEntity = questionRepository.findBySession_SessionKeyAndQuestionKey(sessionKey, questionKey)
                .orElseThrow(() -> new RuntimeException("Question not found for sessionKey: " + sessionKey + " and questionKey: " + questionKey));
        return questionMapper.toModel(questionEntity);
    }

    public void saveAIQuestions(String aiResponse) {
        log.info("HERE IT IS: {}", aiResponse);
    }

    @Override
    public QuestionDTO getQuestion(String sessionKey, Integer questionKey) {
        QuestionDTO currentQuestion = questionMapper.toDTO(getQuestionByQuestionKey(sessionKey, questionKey));
        boolean availableQuestions = checkMoreQuestions(sessionKey, questionKey, 1);

        log.info("SessionKey: {}, QuestionKey: {}, CurrentQuestion: {}, AvailableOptions: {}", sessionKey, questionKey, currentQuestion.getQuestionText(), availableQuestions);

        if (!availableQuestions) {
            log.info("No more questions for {}, retrieving from AI...", sessionKey);

            String prompt = "Generate 5 quiz questions with 4 alternatives each. Theme: history";
            aiEventHandler.sendAIRequest(prompt);
        }

        return currentQuestion;
    }

    @Override
    public ResultDTO postAnswer(String sessionKey, Integer questionKey, PostAnswerRequest answer) {
        GetResultRequest getResultRequest;
        List<Alternative> alternatives = getQuestionByQuestionKey(sessionKey, questionKey).getAlternatives();

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
        List<Question> questions = getQuestionsBySessionKey(sessionKey);

        List<RevealQuestionDTO> revealQuestions = new ArrayList<>();
        List<RevealAlternativeDTO> revealAlternatives;

        for (int i = 0; i < score.getChosenAlternatives().size(); i++) {
            revealAlternatives = new ArrayList<>();

            for (Alternative alternative : questions.get(i).getAlternatives()) {
                revealAlternatives.add(new RevealAlternativeDTO(
                        alternative.getAlternativeKey(),
                        alternative.getAlternativeText(),
                        alternative.isCorrect()
                ));
            }

            revealQuestions.add(new RevealQuestionDTO(
                    questions.get(i).getQuestionText(),
                    revealAlternatives,
                    score.getChosenAlternatives().get(i))
            );
        }

        return new RevealScoreDTO(revealQuestions, score.getScore());
    }

    @Override
    public List<SessionScoreDTO> getScores(String sessionKey) {
        return resultClient.getScores(sessionKey);
    }

    @Override
    public boolean checkMoreQuestions(String sessionKey, int currentQuestionKey) {
        return currentQuestionKey < getQuestionsBySessionKey(sessionKey).size() - 1;
    }

    private boolean checkMoreQuestions(String sessionKey, int currentQuestionKey, int buffer) {
        return currentQuestionKey < getQuestionsBySessionKey(sessionKey).size() - 1 - buffer;
    }
}
