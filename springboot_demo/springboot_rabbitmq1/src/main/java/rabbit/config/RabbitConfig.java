package rabbit.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * Author: Johnny
 * Date: 2017/10/6
 * Time: 3:23
 */
@Configuration
public class RabbitConfig {
    //消息交换机的名字
    public static final String EXCHANGE = "my-mq-exchange";
    //队列key1
    public static final String ROUTING_KEY1 = "queue_one_key1";
    //队列key2
    public static final String ROUTING_KEY2 = "queue_one_key2";

    /**
     * 配置链接信息
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("127.0.0.1", 5672);
        connectionFactory.setUsername("springboot");
        connectionFactory.setPassword("password");
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherReturns(true); // 必须要设置
        return connectionFactory;
    }

    /**
     * 配置消息交换机
     * 针对消费者配置
     * FanoutExchange: 将消息分发到所有的绑定队列，无routingKey的概念
     * HeadersExchange ：通过添加属性key-value匹配
     * DirectExchange:按照routingKey分发到指定队列
     * TopicExchange:多关键字匹配
     */
    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(EXCHANGE, true, false);
    }

    /**
     * 配置消息队列1
     * 针对消费者配置
     */
    @Bean
    public Queue queue() {
        return new Queue("queue_one", true); //队列持久
    }

    /**
     * 将消息队列1与交换机绑定
     * 针对消费者配置
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(defaultExchange()).with(RabbitConfig.ROUTING_KEY1);
    }

    /**
     * 配置消息队列2
     * 针对消费者配置
     */
    @Bean
    public Queue queue1() {
        return new Queue("queue_one1", true); //队列持久
    }

    /**
     * 将消息队列2与交换机绑定
     * 针对消费者配置
     */
    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(queue1()).to(defaultExchange()).with(RabbitConfig.ROUTING_KEY2);
    }

    /**
     * 接受消息的监听，这个监听会接受消息队列1的消息
     * 针对消费者配置
     */
    @Bean
    public SimpleMessageListenerContainer messageContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
        container.setQueues(queue());
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
        container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
            byte[] body = message.getBody();
            System.out.println("收到消息 : " + new String(body));
            Objects.requireNonNull(channel).basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认消息成功消费
        });
        return container;
    }

    /**
     * 接受消息的监听，这个监听会接受消息队列1的消息
     * 针对消费者配置
     */
    @Bean
    public SimpleMessageListenerContainer messageContainer2() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
        container.setQueues(queue1());
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
        container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
            byte[] body = message.getBody();
            System.out.println("queue1 收到消息 : " + new String(body));
            Objects.requireNonNull(channel).basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认消息成功消费
        });
        return container;
    }

    @Bean
    public Queue fanoutQueue() {
        return new Queue("fanout");
    }

    @Bean
    public Queue objectQueue() {
        return new Queue("object");
    }

    @Bean
    public Queue aMessage() {
        return new Queue("fanout.1");
    }

    @Bean
    public Queue bMessage() {
        return new Queue("fanout.2");
    }

}
