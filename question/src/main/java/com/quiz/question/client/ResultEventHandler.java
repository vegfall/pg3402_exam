package com.quiz.question.client;

import com.quiz.question.dto.ResultDTO;
import com.quiz.question.dto.request.GetResultRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

//https://www.baeldung.com/java-blocking-queue
@Slf4j
@Service
public class ResultEventHandler {
    private final RabbitTemplate rabbitTemplate;

    public ResultEventHandler(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${amqp.exchange.name}")
    private String exchangeName;

    @Value("${amqp.queue.result.request}")
    private String resultRequestQueueName;

    private final BlockingQueue<ResultDTO> responseQueue = new LinkedBlockingQueue<>();

    public ResultDTO sendGetResultRequest(GetResultRequest request) {
        log.info("Sending GetResultRequest: {}", request);

        rabbitTemplate.convertAndSend(exchangeName, resultRequestQueueName, request);

        try {
            ResultDTO response = responseQueue.take();
            log.info("Received ResultDTO: {}", response);
            return response;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Failed to retrieve ResultDTO from response queue", e);
            return null;
        }
    }

    @RabbitListener(queues = "${amqp.queue.result.response}")
    public void handleResultResponse(ResultDTO result) {
        log.info("Received ResultDTO from response queue: {}", result);
        boolean offered = responseQueue.offer(result);

        if (!offered) {
            log.warn("Failed to add ResultDTO to the blocking queue. Queue might be full.");
        }
    }
}