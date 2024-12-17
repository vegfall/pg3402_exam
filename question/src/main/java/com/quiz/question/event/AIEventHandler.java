package com.quiz.question.event;

import com.quiz.question.dto.request.AIChatRequest;
import com.quiz.question.dto.response.AIChatResponse;
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

    private final BlockingQueue<AIChatResponse> aiResponseQueue = new LinkedBlockingQueue<>();

    @Value("${amqp.ai.exchange.name}")
    private String exchangeName;

    @Value("${amqp.queue.ai.request}")
    private String aiRequestQueueName;

    public AIEventHandler(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public AIChatResponse sendAIRequest(String prompt) {
        AIChatRequest request = new AIChatRequest(prompt);
        AIChatResponse response = null;

        log.info("Sending AIChatRequest: {}", request);

        rabbitTemplate.convertAndSend(exchangeName, aiRequestQueueName, request);

        try {
            response = aiResponseQueue.take();
            log.info("Received AIChatResponse: {}", response);
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

        if (!offered) {
            log.warn("Failed to add AIChatResponse to the blocking queue. Queue might be full.");
        }
    }
}
