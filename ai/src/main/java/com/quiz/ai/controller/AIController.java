package com.quiz.ai.controller;

import com.quiz.ai.dto.request.AIChatRequest;
import com.quiz.ai.dto.request.ChatGPTRequest;
import com.quiz.ai.dto.response.AIChatResponse;
import com.quiz.ai.dto.response.ChatGPTResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/ai")
public class AIController {
    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.model}")
    private String model;

    private final RestTemplate restTemplate;

    public AIController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/openai/chat")
    public ResponseEntity<AIChatResponse> openAIChat(@RequestBody AIChatRequest prompt) {
        AIChatResponse responseMessage;
        ChatGPTRequest request = new ChatGPTRequest(model, prompt.getPrompt());
        HttpEntity<ChatGPTRequest> entity = new HttpEntity<>(request);

        ResponseEntity<ChatGPTResponse> response = restTemplate.exchange(
                apiUrl, HttpMethod.POST, entity, ChatGPTResponse.class
        );

        if (response.getBody() != null && !response.getBody().getChoices().isEmpty()) {
            responseMessage = new AIChatResponse(prompt.getPrompt(), response.getBody().getChoices().getFirst().getMessage().getContent());

            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}