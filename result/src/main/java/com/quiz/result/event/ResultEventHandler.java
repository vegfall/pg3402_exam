package com.quiz.result.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ResultEventHandler {
    @RabbitListener(queues = "${amqp.queue.quiz.finalization}")
    public void handleTestMessage(String message) {
        try {
            log.info("Processing message: {}", message);

            Thread.sleep(5000);

            log.info("Finished processing message: {}", message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Message processing was interrupted", e);
        }
    }
}
