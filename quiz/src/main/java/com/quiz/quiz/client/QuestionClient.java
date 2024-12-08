package com.quiz.quiz.client;

//https://www.kapresoft.com/java/2023/10/16/spring-uricomponentsbuilder-best-practices.html
//https://stackoverflow.com/questions/23674046/get-list-of-json-objects-with-spring-resttemplate
//https://www.javacodegeeks.com/2018/03/doing-stuff-with-spring-webflux.html

import com.quiz.quiz.dto.QuestionDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class QuestionClient {
    private final WebClient webClient;

    public QuestionClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://127.0.0.1:8081/question")
                .build();
    }

    public QuestionDTO getQuestion(String sessionKey, Integer questionId) {
        return webClient
                .get()
                .uri("/" + sessionKey + "/" + questionId)
                .retrieve()
                .bodyToMono(QuestionDTO.class)
                .block();
    }
}
