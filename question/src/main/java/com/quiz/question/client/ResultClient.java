package com.quiz.question.client;

import com.quiz.question.dto.ScoreDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ResultClient {
    private final WebClient webClient;

    public ResultClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://127.0.0.1:8082/result/")
                .build();
    }

    public ScoreDTO getScore(String sessionKey, String username) {
        return webClient
                .get()
                .uri(sessionKey + "/" + username + "/score")
                .retrieve()
                .bodyToMono(ScoreDTO.class)
                .block();
    }
}
