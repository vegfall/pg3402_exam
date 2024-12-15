package com.quiz.result.client;

import com.quiz.result.dto.ResultDTO;
import com.quiz.result.dto.request.GetResultRequest;
import com.quiz.result.service.SingleplayerResultService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class QuestionEventHandler {
    private final SingleplayerResultService resultService;
    private final RabbitTemplate rabbitTemplate;

    public QuestionEventHandler(SingleplayerResultService resultService, RabbitTemplate rabbitTemplate) {
        this.resultService = resultService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${amqp.exchange.name}")
    private String exchangeName;

    @Value("${amqp.queue.result.response}")
    private String resultResponseQueueName;

    @RabbitListener(queues = "${amqp.queue.result.request}")
    public void handleValidationResponse(GetResultRequest request) {
        ResultDTO result = resultService.postAnswer(request);

        rabbitTemplate.convertAndSend(exchangeName, resultResponseQueueName, result);
    }
}
