package rabbit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;

/**
 * Author: Johnny
 * Date: 2017/10/6
 * Time: 7:40
 */
//@Configuration
//no-use
public class RabbitFanoutConfig {
    private static final String MESSAGE = "topic.message";
    private static final String MESSAGES = "topic.messages";

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    Binding bindingExchangeA(Queue message, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(message).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(Queue message, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(message).to(fanoutExchange);
    }

    @Bean
    public Queue queueMessage() {
        return new Queue(RabbitFanoutConfig.MESSAGE);
    }

    @Bean
    public Queue queueMessages() {
        return new Queue(RabbitFanoutConfig.MESSAGES);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("topicExchange");
    }

    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with(MESSAGE);
    }

    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }

}