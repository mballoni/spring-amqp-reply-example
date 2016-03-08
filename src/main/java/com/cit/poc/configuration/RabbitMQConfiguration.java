package com.cit.poc.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cit.poc.SkuTransactionListener;

@Configuration
@EnableAutoConfiguration
public class RabbitMQConfiguration {

    @Autowired
    private ConnectionFactory rabbitConnectionFactory;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("localhost:5672");
        connectionFactory.setVirtualHost("dojo_vhost");
        connectionFactory.setPassword("dojo");
        connectionFactory.setUsername("dojo");
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate fixedReplyQRabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(this.rabbitConnectionFactory);
        template.setExchange(ex().getName());
        template.setReplyAddress("my.reply.queue");
        return template;
    }

    @Bean
    public SimpleMessageListenerContainer replyListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(this.rabbitConnectionFactory);
        container.setQueues(replyQueue());
        container.setMessageListener(fixedReplyQRabbitTemplate());
        return container;
    }

    @Bean
    public SimpleMessageListenerContainer serviceListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(this.rabbitConnectionFactory);
        container.setQueues(requestQueue());
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setMessageListener(new MessageListenerAdapter(new SkuTransactionListener()));
        return container;
    }

    @Bean
    public DirectExchange ex() {
        return new DirectExchange("ex");
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(requestQueue()).to(ex()).with("test");
    }

    @Bean
    public Queue requestQueue() {
        return new Queue("my.request.queue", false);
    }

    @Bean
    public Queue replyQueue() {
        return new Queue("my.reply.queue", false);
    }
}
