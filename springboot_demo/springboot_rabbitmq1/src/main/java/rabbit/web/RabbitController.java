package rabbit.web;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rabbit.config.RabbitConfig;

import java.util.UUID;

/**
 * Author: Johnny
 * Date: 2017/10/6
 * Time: 7:41
 */
@RestController
public class RabbitController implements RabbitTemplate.ConfirmCallback {
    private RabbitTemplate rabbitTemplate;

    /**
     * 配置发送消息的rabbitTemplate，因为是构造方法，所以不用注解Spring也会自动注入（应该是新版本的特性）
     */
    public RabbitController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        //设置消费回调
        this.rabbitTemplate.setConfirmCallback(this);
    }

    /**
     * 向消息队列1中发送消息
     * http://localhost:8080/send1?msg=wong
     */
    @RequestMapping("send1")
    public String send1(String msg) {
        String uuid = UUID.randomUUID().toString();
        CorrelationData correlationId = new CorrelationData(uuid);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTINGKEY1, msg,
                correlationId);
        return null;
    }

    /**
     * 向消息队列2中发送消息
     * http://localhost:8080/send2?msg=johnny
     */
    @RequestMapping("send2")
    public String send2(String msg) {
        String uuid = UUID.randomUUID().toString();
        CorrelationData correlationId = new CorrelationData(uuid);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTINGKEY2, msg,
                correlationId);
        return null;
    }

    /**
     * 消息的回调，主要是实现RabbitTemplate.ConfirmCallback接口
     * 注意，消息回调只能代表成功消息发送到RabbitMQ服务器，不能代表消息被成功处理和接受
     */
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println(" 回调id:" + correlationData);
        if (ack) {
            System.out.println("消息成功消费");
        } else {
            System.out.println("消息消费失败:" + cause + "\n重新发送");
        }
    }
}
