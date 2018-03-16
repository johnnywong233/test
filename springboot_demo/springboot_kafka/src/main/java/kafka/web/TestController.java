package kafka.web;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Johnny on 2018/3/16.
 */
@RequestMapping
@RestController
@EnableAutoConfiguration
public class TestController {
    public static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private KafkaTemplate<String, String> template;

    @RequestMapping("/send1")
    @ResponseBody
    String send(String topic, String key, String data) {
        template.send(topic, key, data);
        return "success";
    }

    @KafkaListener(id = "t1", topics = "t1")
    public void listenT1(ConsumerRecord<?, ?> cr) throws Exception {
        logger.info("{} - {} : {}", cr.topic(), cr.key(), cr.value());
    }

    @KafkaListener(id = "t2", topics = "t2")
    public void listenT2(ConsumerRecord<?, ?> cr) throws Exception {
        logger.info("{} - {} : {}", cr.topic(), cr.key(), cr.value());
    }
}
