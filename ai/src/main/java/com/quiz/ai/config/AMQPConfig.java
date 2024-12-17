package com.quiz.ai.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPConfig {
    @Value("${amqp.exchange.name}")
    private String exchangeName;

    @Value("${amqp.queue.ai.request}")
    private String aiRequestQueue;

    @Value("${amqp.queue.ai.response}")
    private String aiResponseQueue;

    @Bean
    public TopicExchange aiExchange() {
        return ExchangeBuilder.topicExchange(exchangeName)
                .durable(true)
                .build();
    }

    @Bean
    public Queue aiRequestQueue() {
        return QueueBuilder.durable(aiRequestQueue).build();
    }

    @Bean
    public Queue aiResponseQueue() {
        return QueueBuilder.durable(aiResponseQueue).build();
    }

    @Bean
    public Binding aiRequestBinding(Queue aiRequestQueue, TopicExchange aiExchange) {
        return BindingBuilder.bind(aiRequestQueue).to(aiExchange).with("ai.queue.request");
    }

    @Bean
    public Binding aiResponseBinding(Queue aiResponseQueue, TopicExchange aiExchange) {
        return BindingBuilder.bind(aiResponseQueue).to(aiExchange).with("ai.queue.response");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter converter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter);
        return rabbitTemplate;
    }
}
