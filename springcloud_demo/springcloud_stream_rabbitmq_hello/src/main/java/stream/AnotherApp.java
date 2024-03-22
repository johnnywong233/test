package stream;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import stream.bean.Order;

import java.util.Date;

interface OrderProcessor {
    String INPUT_ORDER = "inputOrder";
    String OUTPUT_ORDER = "outputOrder";

    @Input(INPUT_ORDER)
    SubscribableChannel inputOrder();

    @Output(OUTPUT_ORDER)
    MessageChannel outputOrder();
}

interface ProductProcessor {
    String INPUT_PRODUCT_ADD = "inputProductAdd";
    String OUTPUT_PRODUCT_ADD = "outputProductAdd";

    @Input(INPUT_PRODUCT_ADD)
    SubscribableChannel inputProductAdd();

    @Output(OUTPUT_PRODUCT_ADD)
    MessageChannel outputProductAdd();
}

/**
 * Author: Johnny
 * Date: 2017/11/8
 * Time: 23:50
 */
@SpringBootApplication
@EnableBinding({Processor.class, OrderProcessor.class, ProductProcessor.class})
public class AnotherApp implements CommandLineRunner {

    @Resource
    @Qualifier("output")
    private MessageChannel output;

    @Resource
    @Qualifier("outputOrder")
    private MessageChannel outputOrder;

    @Resource
    private ProductProcessor productProcessor;

    public static void main(String[] args) {
        SpringApplication.run(AnotherApp.class);
    }

    // 监听 binding 为 Processor.INPUT 的消息
    @StreamListener(Processor.INPUT)
    public void input(Message<String> message) {
        System.out.println("receive message: " + message.getPayload());
    }

    // 监听 binding 为 OrderIntf.INPUT_ORDER 的消息
    @StreamListener(OrderProcessor.INPUT_ORDER)
    public void inputOrder(Order order) {
        System.out.println("=====order process request heard=====");
        System.out.println("order id: " + order.getOrderNum());
        System.out.println("order type: " + order.getType());
        System.out.println("order number: " + order.getNum());
        System.out.println("=====order process complete=====");
    }

    @StreamListener(ProductProcessor.INPUT_PRODUCT_ADD)
    public void inputProductAdd(Message<String> message) {
        System.out.println("newly product message received: " + message.getPayload());
    }

    @Override
    public void run(String... strings) throws Exception {
        // 字符串类型发送MQ
        System.out.println("sending message in string.");
        output.send(MessageBuilder.withPayload("hello").build());

        // 使用 定义的接口的方式来发送
        System.out.println("sending a new product.");
        productProcessor.outputProductAdd().send(MessageBuilder.withPayload("add a new product.").build());

        // 实体类型发送MQ
        System.out.println("sending message of order bean.");
        Order appleOrder = new Order();
        appleOrder.setOrderNum("0000001");
        appleOrder.setNum(10);
        appleOrder.setType("APPLE");
        appleOrder.setCreateAt(new Date());
        // 使用 注入 MessageChannel 方式发送
        outputOrder.send(MessageBuilder.withPayload(appleOrder).build());
    }

    // 定时轮询发送消息到 binding 为 Processor.OUTPUT
    @Bean
    @InboundChannelAdapter(value = Processor.OUTPUT, poller = @Poller(fixedDelay = "3000", maxMessagesPerPoll = "1"))
    public MessageSource<String> timerMessageSource() {
        return () -> MessageBuilder.withPayload("short msg -" + new Date()).build();
    }
}