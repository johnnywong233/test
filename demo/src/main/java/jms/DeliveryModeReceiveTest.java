package jms;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class DeliveryModeReceiveTest {
    //http://blog.itpub.net/10742815/viewspace-580331/
    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");

        Connection connection = factory.createConnection();
        connection.start();

        Queue queue = new ActiveMQQueue("testQueue");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(m -> {
            try {
                System.out.println("Consumer get " + ((TextMessage) m).getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
    }

}
