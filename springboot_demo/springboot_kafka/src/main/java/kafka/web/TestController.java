package kafka.web;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Johnny on 2018/3/16.
 */
@Slf4j
@RequestMapping
@RestController
@EnableAutoConfiguration
public class TestController {
    @Resource
    private KafkaTemplate<String, String> template;

    @RequestMapping("/send1")
    String send(String topic, String key, String data) {
        template.send(topic, key, data);
        return "success";
    }

    @KafkaListener(id = "t1", topics = "t1")
    public void listenT1(ConsumerRecord<?, ?> cr) {
        log.info("{} - {} : {}", cr.topic(), cr.key(), cr.value());
    }

    @KafkaListener(id = "t2", topics = "t2")
    public void listenT2(ConsumerRecord<?, ?> cr) {
        log.info("{} - {} : {}", cr.topic(), cr.key(), cr.value());
    }
}
