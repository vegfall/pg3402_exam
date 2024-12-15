package com.quiz.result.service;

import com.quiz.result.dto.ResultDTO;
import com.quiz.result.dto.ScoreDTO;
import com.quiz.result.dto.request.GetResultRequest;
import com.quiz.result.repository.MockResultRepository;
import org.springframework.stereotype.Service;

@Service
public class SingleplayerResultService implements ResultService {
    private final MockResultRepository resultRepository;

    public SingleplayerResultService(MockResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @Override
    public ResultDTO postAnswer(GetResultRequest request) {
        boolean correct = request.getCorrectAlternativeKey() == request.getSelectedAlternativeKey();

        resultRepository.addAlternative(
                request.getUsername(),
                request.getSessionKey(),
                request.getSelectedAlternativeKey(),
                correct
        );

        return new ResultDTO(request.getCorrectAlternativeKey(), request.getExplanation());
    }

    @Override
    public ScoreDTO getScore(String sessionKey, String username) {
        return resultRepository.getScore(sessionKey, username).getDTO();
    }
}
