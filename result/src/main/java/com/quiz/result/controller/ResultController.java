package com.quiz.result.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/result")
@RequiredArgsConstructor
public class ResultController {
    private final RabbitTemplate rabbitTemplate;

    @Value("${amqp.exchange.name}")
    private String exchangeName;

    @GetMapping("/test-rabbit")
    public ResponseEntity<String> testRabbitMQ() {
        String testMessage = "Test message from ResultService";
        rabbitTemplate.convertAndSend(exchangeName, "result.quiz.finalization", testMessage);
        log.info("Sent test message: {}", testMessage);
        return ResponseEntity.ok("Message sent to RabbitMQ");
    }
}
