package jms;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;


public class TopicTest {
    //http://blog.itpub.net/10742815/viewspace-580272/
    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");

        Connection connection = factory.createConnection();
        connection.start();

        //创建一个Topic,唯一不同的地方
        Topic topic = new ActiveMQTopic("testTopic");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //注册消费者1
        MessageConsumer consumer1 = session.createConsumer(topic);
        consumer1.setMessageListener(m -> {
            try {
                System.out.println("Consumer1 get " + ((TextMessage) m).getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });

        //注册消费者2
        MessageConsumer consumer2 = session.createConsumer(topic);
        consumer2.setMessageListener(m -> {
            try {
                System.out.println("Consumer2 get " + ((TextMessage) m).getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });

        //创建一个生产者，然后发送多个消息。
        MessageProducer producer = session.createProducer(topic);
        for (int i = 0; i < 10; i++) {
            producer.send(session.createTextMessage("Message:" + i));
        }
    }

}
