package com.quiz.question.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResultClient {

    private final RabbitTemplate rabbitTemplate;

    @Value("${amqp.exchange.name}")
    private String exchangeName;

    public void sendValidationMessage(String sessionKey, Long questionId, int alternativeKey) {
        String routingKey = "result.question.validation.response";

        String message = String.format(
                "{\"sessionKey\":\"%s\",\"questionId\":%d,\"alternativeKey\":%d}",
                sessionKey, questionId, alternativeKey
        );

        log.info("Sending validation message: {}", message);

        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);

        log.info("Validation message sent to RabbitMQ: {}", routingKey);
    }
}