package com.quiz.result.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//https://www.rabbitmq.com/tutorials/tutorial-four-spring-amqp
@Configuration
public class AMQPConfig {
    @Value("${amqp.exchange.name}")
    private String exchangeName;

    @Value("${amqp.queue.result.request}")
    private String resultRequestQueueName;

    @Value("${amqp.queue.result.response}")
    private String resultResponseQueueName;

    @Bean
    public TopicExchange resultExchange() {
        return ExchangeBuilder.topicExchange(exchangeName)
            .durable(true)
            .build();
    }

    @Bean
    public Queue resultRequestQueue() {
        return QueueBuilder.durable(resultRequestQueueName).build();
    }

    @Bean
    public Queue resultResponseQueue() {
        return QueueBuilder.durable(resultResponseQueueName).build();
    }

    @Bean
    public Binding resultRequestBinding(Queue resultRequestQueue, TopicExchange resultExchange) {
        return BindingBuilder.bind(resultRequestQueue)
                .to(resultExchange)
                .with("result.queue.request");
    }

    @Bean
    public Binding resultResponseBinding(Queue resultResponseQueue, TopicExchange resultExchange) {
        return BindingBuilder.bind(resultResponseQueue)
                .to(resultExchange)
                .with("result.queue.response");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
