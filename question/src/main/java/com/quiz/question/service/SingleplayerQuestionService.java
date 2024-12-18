package com.quiz.question.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.question.client.ResultClient;
import com.quiz.question.dto.request.NewSessionRequest;
import com.quiz.question.entity.AlternativeEntity;
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
import com.quiz.question.util.AIPromptBuilder;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
//https://www.baeldung.com/java-completablefuture-runasync-supplyasync
public class SingleplayerQuestionService implements QuestionService {
    private final SessionRepository sessionRepository;
    private final QuestionRepository questionRepository;
    private final ResultEventHandler resultEventHandler;
    private final ResultClient resultClient;
    private final QuestionMapper questionMapper;
    private final ObjectMapper objectMapper;
    private final AIEventHandler aiEventHandler;

    public SingleplayerQuestionService(
            SessionRepository sessionRepository,
            QuestionRepository questionRepository,
            ResultEventHandler resultEventHandler,
            ResultClient resultClient,
            QuestionMapper questionMapper,
            ObjectMapper objectMapper,
            @Lazy AIEventHandler aiEventHandler
    ) {
        this.sessionRepository = sessionRepository;
        this.questionRepository = questionRepository;
        this.resultEventHandler = resultEventHandler;
        this.resultClient = resultClient;
        this.questionMapper = questionMapper;
        this.objectMapper = objectMapper;
        this.aiEventHandler = aiEventHandler;
    }

    private List<Question> getQuestionsBySessionKey(String sessionKey) {
        List<Question> questions = new ArrayList<>();
        SessionEntity session = sessionRepository.findBySessionKeyWithQuestionsAndAlternatives(sessionKey)
                .orElseThrow(() -> new RuntimeException("Session not found for sessionKey: " + sessionKey));

        for (QuestionEntity question : session.getQuestions()) {
            questions.add(questionMapper.toModel(question));
        }

        questions.sort(Comparator.comparingInt(Question::getQuestionKey));

        return questions;
    }

    private Question getQuestionByQuestionKey(String sessionKey, int questionKey) {
        QuestionEntity questionEntity = questionRepository.findBySessionKeyAndQuestionKey(sessionKey, questionKey)
                .orElseThrow(() -> new RuntimeException("Question not found for sessionKey: " + sessionKey + " and questionKey: " + questionKey));
        return questionMapper.toModel(questionEntity);
    }


    private List<String> getPreviousQuestionTexts(String sessionKey, int currentQuestionKey) {
        List<Question> previousQuestions = getQuestionsBySessionKey(sessionKey);
        List<String> previousQuestionTexts = new ArrayList<>();

        for (int i = 0; i < previousQuestions.size() && i < currentQuestionKey; i++) {
            previousQuestionTexts.add(previousQuestions.get(i).getQuestionText());
        }

        return previousQuestionTexts;
    }

    private void asyncAIQuestionGeneration(String sessionKey, int currentQuestionKey, int difficultyLevel) {
        SessionEntity session = sessionRepository.findBySessionKey(sessionKey)
                .orElseThrow(() -> new RuntimeException("Session not found for sessionKey: " + sessionKey));

        CompletableFuture.runAsync(() -> {
            try {
                AIPromptBuilder promptBuilder = new AIPromptBuilder();
                String prompt = promptBuilder.build(
                        session.getTheme(),
                        session.getNumberOfAlternatives(),
                        difficultyLevel,
                        getPreviousQuestionTexts(sessionKey, currentQuestionKey)
                );

                log.info("Sending AI request asynchronously for sessionKey: {}", sessionKey);
                aiEventHandler.sendAIRequest(sessionKey, prompt);

            } catch (Exception e) {
                log.error("Error running async to generate questions for sessionKey: {}", sessionKey, e);
            }
        });
    }

    @Transactional
    @Override
    public synchronized void saveAIQuestions(String sessionKey, String aiResponse) {
        try {
            List<Question> questions = objectMapper.readValue(aiResponse, new TypeReference<List<Question>>() {});

            SessionEntity session = sessionRepository.findBySessionKey(sessionKey)
                    .orElseThrow(() -> new RuntimeException("Session not found for sessionKey: " + sessionKey));

            int nextQuestionKey = questionRepository.findNextQuestionKeyBySessionId(session.getSessionId());

            Set<QuestionEntity> newQuestions = new HashSet<>();

            for (Question question : questions) {
                QuestionEntity newQuestion = getQuestionEntity(question, session, nextQuestionKey);
                newQuestions.add(newQuestion);
                nextQuestionKey++;
            }

            questionRepository.saveAll(newQuestions);
        } catch (Exception e) {
            log.error("Error saving AI questions: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to save AI-generated questions", e);
        }
    }

    private static QuestionEntity getQuestionEntity(Question question, SessionEntity session, int lastQuestionKey) {
        QuestionEntity newQuestion = new QuestionEntity();
        newQuestion.setSession(session);
        newQuestion.setQuestionKey(lastQuestionKey);
        newQuestion.setQuestionText(question.getQuestionText());

        Set<AlternativeEntity> newAlternatives = new HashSet<>();
        int alternativeKey = 1;

        for (Alternative alternative : question.getAlternatives()) {
            AlternativeEntity newAlternative = new AlternativeEntity();
            newAlternative.setAlternativeKey(alternativeKey++);
            newAlternative.setAlternativeText(alternative.getAlternativeText());
            newAlternative.setCorrect(alternative.isCorrect());
            newAlternative.setAlternativeExplanation(alternative.getAlternativeExplanation());
            newAlternative.setQuestion(newQuestion);

            newAlternatives.add(newAlternative);
        }

        newQuestion.setAlternatives(newAlternatives);
        return newQuestion;
    }

    @Override
    public QuestionDTO getQuestion(String sessionKey, Integer questionKey) {
        QuestionDTO currentQuestion = questionMapper.toDTO(getQuestionByQuestionKey(sessionKey, questionKey));
        int difficultyLevel = Math.min(currentQuestion.getQuestionKey() / 5, 10);

        if (checkMoreQuestions(sessionKey, (questionKey + 1))) {
            return currentQuestion;
        }

        log.info("No more questions for {}, retrieving from AI...", sessionKey);
        asyncAIQuestionGeneration(sessionKey, currentQuestion.getQuestionKey(), difficultyLevel);

        return currentQuestion;
    }

    @Override
    public ResultDTO postAnswer(String sessionKey, Integer questionKey, PostAnswerRequest answer) {
        GetResultRequest getResultRequest;
        List<Alternative> alternatives = new ArrayList<>(getQuestionByQuestionKey(sessionKey, questionKey).getAlternatives());

        //Had to implement due to database returning random sequence.
        alternatives.sort(Comparator.comparingInt(Alternative::getAlternativeKey));

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

        log.info("---\nsessionKey: {}, username: {}, question: {}", sessionKey, username, questions.getFirst().getQuestionText());

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

    @Override
    public void postSession(NewSessionRequest session) {
        AIPromptBuilder promptBuilder = new AIPromptBuilder();

        SessionEntity sessionEntity = sessionRepository.findBySessionKey(session.getSessionKey())
                .orElseGet(() -> {
                    SessionEntity newSession = new SessionEntity();
                    newSession.setSessionKey(session.getSessionKey());
                    newSession.setTheme(session.getTheme());
                    newSession.setNumberOfAlternatives(session.getNumberOfAlternatives());
                    sessionRepository.save(newSession);
                    return newSession;
                });

        String prompt = promptBuilder.build(sessionEntity.getTheme(), sessionEntity.getNumberOfAlternatives(), 1, List.of());
        aiEventHandler.sendAIRequest(sessionEntity.getSessionKey(), prompt);

        waitForQuestionsToBeReady(sessionEntity.getSessionKey(), 10);

        getQuestion(session.getSessionKey(), 1);
    }

    private void waitForQuestionsToBeReady(String sessionKey, int maxRetries) {
        int currentRetry = 0;
        while (currentRetry < maxRetries) {
            if (questionRepository.existsBySession_SessionKey(sessionKey)) {
                return;
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Interrupted while waiting for AI questions", e);
            }
            currentRetry++;
        }
        throw new RuntimeException("Unable to get find questions for: " + sessionKey + "  in " + maxRetries + " tries.");
    }
}
