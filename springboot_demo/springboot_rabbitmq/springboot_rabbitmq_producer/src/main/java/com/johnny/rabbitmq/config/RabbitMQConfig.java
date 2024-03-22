package com.johnny.rabbitmq.config;

import com.rabbitmq.client.ConnectionFactory;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Objects;

@Configuration
@ComponentScan(basePackages = {"com.johnny.rabbitmq"})
@PropertySource(value = {"classpath:application.properties"})
public class RabbitMQConfig {

    @Resource
    private Environment env;

    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(env.getProperty("mq.host"));
        connectionFactory.setPort(Integer.parseInt(Objects.requireNonNull(env.getProperty("mq.port"))));
        connectionFactory.setVirtualHost(env.getProperty("mq.vhost"));
        connectionFactory.setUsername(env.getProperty("mq.username"));
        connectionFactory.setPassword(env.getProperty("mq.password"));
        return connectionFactory;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        return new CachingConnectionFactory(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory());
        rabbitTemplate.setChannelTransacted(true);
        return rabbitTemplate;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(cachingConnectionFactory());
    }

    @Bean
    Queue queue() {
        String name = env.getProperty("mq.queue");
        assert name != null;
        // 是否持久化
        boolean durable = !StringUtils.isNotBlank(env.getProperty("mq.queue.durable")) || Boolean.parseBoolean(env.getProperty("mq.queue.durable"));
        // 仅创建者可以使用的私有队列，断开后自动删除
        boolean exclusive = StringUtils.isNotBlank(env.getProperty("mq.queue.exclusive")) && Boolean.parseBoolean(env.getProperty("mq.queue.exclusive"));
        // 当所有消费客户端连接断开后，是否自动删除队列
        boolean autoDelete = StringUtils.isNotBlank(env.getProperty("mq.queue.autoDelete")) && Boolean.parseBoolean(env.getProperty("mq.queue.autoDelete"));
        return new Queue(name, durable, exclusive, autoDelete);
    }

    @Bean
    TopicExchange exchange() {
        String name = env.getProperty("mq.exchange");
        // 是否持久化
        boolean durable = !StringUtils.isNotBlank(env.getProperty("mq.exchange.durable")) || Boolean.parseBoolean(env.getProperty("mq.exchange.durable"));
        // 当所有消费客户端连接断开后，是否自动删除队列
        boolean autoDelete = StringUtils.isNotBlank(env.getProperty("mq.exchange.autoDelete")) && Boolean.parseBoolean(env.getProperty("mq.exchange.autoDelete"));
        return new TopicExchange(name, durable, autoDelete);
    }

    @Bean
    Binding binding() {
        String routeKey = env.getProperty("mq.routekey");
        return BindingBuilder.bind(queue()).to(exchange()).with(routeKey);
    }
}
