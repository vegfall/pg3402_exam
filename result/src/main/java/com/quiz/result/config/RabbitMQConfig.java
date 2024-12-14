package com.quiz.result.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//https://www.rabbitmq.com/tutorials/tutorial-four-spring-amqp
@Configuration
public class RabbitMQConfig {
    @Value("${amqp.exchange.name}")
    private String exchangeName;

    @Value("${amqp.queue.validation.response}")
    private String validationResponseQueueName;

    @Value("${amqp.queue.quiz.finalization}")
    private String quizFinalizationQueueName;

    @Bean
    public TopicExchange resultExchange() {
        return ExchangeBuilder.topicExchange(exchangeName)
            .durable(true)
            .build();
    }

    @Bean
    public Queue validationResponseQueue() {
        return QueueBuilder.durable(validationResponseQueueName).build();
    }

    @Bean
    public Queue quizFinalizationQueue() {
        return QueueBuilder.durable(quizFinalizationQueueName).build();
    }

    @Bean
    public Binding validationResponseBinding(Queue validationResponseQueue, TopicExchange resultExchange) {
        return BindingBuilder.bind(validationResponseQueue)
                .to(resultExchange)
                .with("result.question.validation.response");
    }

    @Bean
    public Binding quizFinalizationBinding(Queue quizFinalizationQueue, TopicExchange resultExchange) {
        return BindingBuilder.bind(quizFinalizationQueue)
                .to(resultExchange)
                .with("result.quiz.finalization");
    }
}
