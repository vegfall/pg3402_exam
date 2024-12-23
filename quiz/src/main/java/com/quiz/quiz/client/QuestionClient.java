package com.quiz.quiz.client;

//https://www.kapresoft.com/java/2023/10/16/spring-uricomponentsbuilder-best-practices.html
//https://stackoverflow.com/questions/23674046/get-list-of-json-objects-with-spring-resttemplate
//https://www.javacodegeeks.com/2018/03/doing-stuff-with-spring-webflux.html

import com.quiz.quiz.dto.QuestionDTO;
import com.quiz.quiz.dto.ResultDTO;
import com.quiz.quiz.dto.SessionScoreDTO;
import com.quiz.quiz.dto.conclusion.revealScoreDTO;
import com.quiz.quiz.dto.request.NewSessionRequest;
import com.quiz.quiz.dto.request.PostAnswerRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class QuestionClient {
    private final WebClient webClient;

    public QuestionClient(WebClient.Builder webClientBuilder, @Value("${gateway.api.key}") String apiKey) {
        this.webClient = webClientBuilder
                .baseUrl("http://question/question/")
                .defaultHeader("gateway-auth", apiKey)
                .build();
    }

    public QuestionDTO getQuestion(String sessionKey, Integer questionKey) {
        return webClient
                .get()
                .uri(sessionKey + "/" + questionKey)
                .retrieve()
                .bodyToMono(QuestionDTO.class)
                .block();
    }

    public ResultDTO postAnswer(String sessionKey, Integer questionKey, PostAnswerRequest answer) {
        return webClient
                .post()
                .uri(sessionKey + "/" + questionKey + "/post-answer")
                .bodyValue(answer)
                .retrieve()
                .bodyToMono(ResultDTO.class)
                .block();
    }

    public revealScoreDTO getScore(String sessionKey, String username) {
        return webClient
                .get()
                .uri(sessionKey + "/" + username + "/score")
                .retrieve()
                .bodyToMono(revealScoreDTO.class)
                .block();
    }

    public Boolean checkMoreQuestions(String sessionKey, int currentQuestionKey) {
        return Boolean.TRUE.equals(webClient
                .get()
                .uri(sessionKey + "/" + currentQuestionKey + "/check-more")
                .retrieve()
                .bodyToMono(boolean.class)
                .block());
    }

    public List<SessionScoreDTO> getScores(String sessionKey) {
        return webClient
                .get()
                .uri(sessionKey + "/scores")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<SessionScoreDTO>>() {})
                .block();
    }

    public Void postSession(NewSessionRequest request) {
        return webClient
                .post()
                .uri("post-session")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
