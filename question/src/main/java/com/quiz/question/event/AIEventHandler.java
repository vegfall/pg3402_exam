package com.quiz.question.event;

import com.quiz.question.dto.request.AIChatRequest;
import com.quiz.question.dto.response.AIChatResponse;
import com.quiz.question.service.SingleplayerQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Service
public class AIEventHandler {
    private final RabbitTemplate rabbitTemplate;
    private final SingleplayerQuestionService questionService;

    private final BlockingQueue<AIChatResponse> aiResponseQueue = new LinkedBlockingQueue<>();

    @Value("${amqp.ai.exchange.name}")
    private String exchangeName;

    @Value("${amqp.queue.ai.request}")
    private String aiRequestQueueName;

    public AIEventHandler(RabbitTemplate rabbitTemplate, SingleplayerQuestionService questionService) {
        this.rabbitTemplate = rabbitTemplate;
        this.questionService = questionService;
    }

    public AIChatResponse sendAIRequest(String sessionKey, String prompt) {
        AIChatRequest request = new AIChatRequest(sessionKey, prompt);
        AIChatResponse response = null;

        rabbitTemplate.convertAndSend(exchangeName, aiRequestQueueName, request);

        try {
            response = aiResponseQueue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Failed to retrieve AIChatResponse from response queue", e);
        }

        return response;
    }

    @RabbitListener(queues = "${amqp.queue.ai.response}")
    public void handleAIResponse(AIChatResponse response) {
        log.info("Received AIChatResponse from AI response queue: {}", response);
        boolean offered = aiResponseQueue.offer(response);

        questionService.saveAIQuestions(response.getSessionKey(), response.getResponse());

        if (!offered) {
            log.warn("Failed to add AIChatResponse to the blocking queue. Queue might be full.");
        }
    }
}
