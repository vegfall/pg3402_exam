package com.quiz.quiz.service;

import com.quiz.quiz.client.QuestionClient;
import com.quiz.quiz.dto.QuestionDTO;
import com.quiz.quiz.dto.ResultDTO;
import com.quiz.quiz.dto.SessionDTO;
import com.quiz.quiz.dto.SessionScoreDTO;
import com.quiz.quiz.dto.conclusion.revealScoreDTO;
import com.quiz.quiz.dto.request.CreateSessionRequest;
import com.quiz.quiz.dto.request.LoadSessionRequest;
import com.quiz.quiz.dto.request.NewSessionRequest;
import com.quiz.quiz.dto.request.PostAnswerRequest;
import com.quiz.quiz.entity.SessionEntity;
import com.quiz.quiz.mapper.QuizMapper;
import com.quiz.quiz.model.Session;
import com.quiz.quiz.model.SessionStatus;
import com.quiz.quiz.repository.SessionRepository;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;

import java.util.List;

@Service
public class SingleplayerQuizService implements QuizService {
    private final QuestionClient questionClient;
    private final SessionRepository sessionRepository;
    private final QuizMapper quizMapper;

    public SingleplayerQuizService(QuestionClient questionClient, SessionRepository sessionRepository, QuizMapper quizMapper) {
        this.questionClient = questionClient;
        this.sessionRepository = sessionRepository;
        this.quizMapper = quizMapper;
    }

    @Override
    public SessionDTO postNewSession(CreateSessionRequest sessionRequest) {
        String sessionKey = generateSessionKey(6);
        SessionEntity sessionEntity = new SessionEntity();
        SessionEntity savedSessionEntity;
        NewSessionRequest newSessionRequest;

        sessionEntity.setSessionKey(sessionKey);
        sessionEntity.setTheme(sessionRequest.getTheme());
        sessionEntity.setNumberOfAlternatives(sessionRequest.getNumberOfAlternatives());
        sessionEntity.setUsername(sessionRequest.getUsername());
        sessionEntity.setCurrentQuestionKey(1);
        sessionEntity.setStatus(SessionStatus.ONGOING);

        savedSessionEntity = sessionRepository.save(sessionEntity);

        newSessionRequest = new NewSessionRequest(
                savedSessionEntity.getSessionKey(),
                savedSessionEntity.getTheme(),
                savedSessionEntity.getNumberOfAlternatives()
        );

        questionClient.postSession(newSessionRequest);

        return quizMapper.toDTO(quizMapper.toModel(savedSessionEntity));
    }

    @Override
    public SessionDTO getSession(String sessionKey) {
        return quizMapper.toDTO(getSessionByKey(sessionKey));
    }

    @Override
    public QuestionDTO getCurrentQuestion(String sessionKey) {
        Session session = getSessionByKey(sessionKey);
        int currentQuestionKey = session.getCurrentQuestionKey();

        return questionClient.getQuestion(sessionKey, currentQuestionKey);
    }

    @Override
    public void putNextQuestion(String sessionKey) {
        SessionEntity sessionEntity = getSessionEntityByKey(sessionKey);

        sessionEntity.setCurrentQuestionKey(sessionEntity.getCurrentQuestionKey() + 1);

        sessionRepository.save(sessionEntity);
    }

    @Override
    public ResultDTO postAnswer(String sessionKey, PostAnswerRequest answer) {
        Session session = getSessionByKey(sessionKey);
        int currentQuestionKey = session.getCurrentQuestionKey();

        return questionClient.postAnswer(sessionKey, currentQuestionKey, answer);
    }

    @Override
    public revealScoreDTO getScore(String sessionKey, String username) {
        setStatus(sessionKey, SessionStatus.COMPLETED);

        return questionClient.getScore(sessionKey, username);
    }

    @Override
    public List<SessionScoreDTO> getScores(String sessionKey) {
        return questionClient.getScores(sessionKey);
    }

    @Override
    public SessionStatus getStatus(String sessionKey) {
        SessionEntity sessionEntity = getSessionEntityByKey(sessionKey);
        SessionStatus status = sessionEntity.getStatus();

        boolean moreQuestions = questionClient.checkMoreQuestions(sessionKey, sessionEntity.getCurrentQuestionKey());

        if (!moreQuestions) {
            status = SessionStatus.COMPLETED;
            setStatus(sessionKey, status);
        }

        return status;
    }

    @Override
    public void startSession(String sessionKey) {
        setStatus(sessionKey, SessionStatus.ONGOING);
    }

    @Override
    public SessionDTO loadPreviousSession(String sessionKey, LoadSessionRequest request) {
        SessionEntity sessionEntity = getSessionEntityByKey(sessionKey);

        sessionEntity.setUsername(request.getUsername());
        sessionEntity.setCurrentQuestionKey(0);
        sessionEntity.setStatus(SessionStatus.ONGOING);

        sessionRepository.save(sessionEntity);

        return quizMapper.toDTO(quizMapper.toModel(sessionEntity));
    }

    private SessionEntity getSessionEntityByKey(String sessionKey) {
        return sessionRepository.findBySessionKey(sessionKey)
                .orElseThrow(() -> new RuntimeException("Session not found"));
    }

    private Session getSessionByKey(String sessionKey) {
        return quizMapper.toModel(getSessionEntityByKey(sessionKey));
    }

    private String generateSessionKey(int length) {
        String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sessionKey = new StringBuilder(10);

        for (int i = 0; i < length; i++) {
            sessionKey.append(allowedCharacters.charAt(random.nextInt(allowedCharacters.length())));
        }

        return sessionKey.toString();
    }

    private void setStatus(String sessionKey, SessionStatus status) {
        SessionEntity session = getSessionEntityByKey(sessionKey);

        session.setStatus(status);

        sessionRepository.save(session);
    }
}
