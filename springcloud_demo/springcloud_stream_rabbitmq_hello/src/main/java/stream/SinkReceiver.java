package stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * Author: Johnny
 * Date: 2017/11/8
 * Time: 23:28
 */
@EnableBinding(Sink.class)
public class SinkReceiver {
    private static final Logger logger = LoggerFactory.getLogger(StreamHelloApplication.class);
    @StreamListener(Sink.INPUT)
    public void receive(Object playLoad) {
        logger.info("Received:"+playLoad);
    }
}
