package kafka.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * Created by Johnny on 2018/3/16.
 * new Listener()生成一个bean用来处理从kafka读取的数据。Listener简单的实现demo如下：只是简单的读取并打印key和message值,
 * KafkaListener中topics属性用于指定kafka topic名称，topic名称由消息生产者指定，也就是由kafkaTemplate在发送消息时指定。
 */
@Slf4j
public class Listener {
    @KafkaListener(topics = {"test"})
    public void listen(ConsumerRecord<?, ?> record) {
        log.info("kafka的key: " + record.key());
        log.info("kafka的value: " + record.value().toString());
    }
}
