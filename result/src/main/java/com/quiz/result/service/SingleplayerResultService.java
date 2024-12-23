package com.quiz.result.service;

import com.quiz.result.dto.ResultDTO;
import com.quiz.result.dto.ScoreDTO;
import com.quiz.result.dto.SessionScoreDTO;
import com.quiz.result.dto.request.GetResultRequest;
import com.quiz.result.entity.ScoreEntity;
import com.quiz.result.entity.UserEntity;
import com.quiz.result.mapper.ResultMapper;
import com.quiz.result.repository.ScoreRepository;
import com.quiz.result.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SingleplayerResultService implements ResultService {
    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;
    private final ResultMapper resultMapper;

    public SingleplayerResultService(ScoreRepository scoreRepository, UserRepository userRepository, ResultMapper resultMapper) {
        this.scoreRepository = scoreRepository;
        this.userRepository = userRepository;
        this.resultMapper = resultMapper;
    }

    private UserEntity getOrCreateUser(String username) {
        return userRepository.findByUsername(username)
                .orElseGet(() -> {
                   UserEntity newUser = new UserEntity(null, username);

                   return userRepository.save(newUser);
                });
    }

    @Override
    public ResultDTO postAnswer(GetResultRequest request) {
        ResultDTO result = new ResultDTO(request.getCorrectAlternativeKey(), request.getExplanation());
        UserEntity user = getOrCreateUser(request.getUsername());
        String alternatives;
        ScoreEntity scoreEntity = scoreRepository.findByUserAndSessionKey(user, request.getSessionKey())
                .orElseGet(() -> {
                    ScoreEntity newScore = new ScoreEntity();
                    newScore.setUser(user);
                    newScore.setSessionKey(request.getSessionKey());
                    newScore.setTotalScore(0);
                    newScore.setChosenAlternatives("");
                    return newScore;
                });

        if (request.getCorrectAlternativeKey() == request.getSelectedAlternativeKey()) {
            scoreEntity.setTotalScore(scoreEntity.getTotalScore() + 1);
        }

        alternatives = (scoreEntity.getChosenAlternatives() != null ? scoreEntity.getChosenAlternatives() : "") + request.getSelectedAlternativeKey();

        scoreEntity.setChosenAlternatives(alternatives);
        scoreRepository.save(scoreEntity);

        return result;
    }

    @Override
    public ScoreDTO getScore(String sessionKey, String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User " + username + " not found."));

        ScoreEntity scoreEntity = scoreRepository.findByUserAndSessionKey(user, sessionKey)
                .orElseThrow(() -> new RuntimeException("Score not found for sessionKey: " + sessionKey + " and username: " + username));

        return resultMapper.toDTO(resultMapper.toModel(scoreEntity));
    }

    @Override
    public List<SessionScoreDTO> getScoresForSession(String sessionKey) {
        List<ScoreEntity> scoreEntities = scoreRepository.findBySessionKey(sessionKey);
        List<SessionScoreDTO> sessionScoreDTOs = new ArrayList<>();

        for (ScoreEntity score : scoreEntities) {
            sessionScoreDTOs.add(resultMapper.toSessionScoreDTO(score));
        }

        return sessionScoreDTOs;
    }
}
