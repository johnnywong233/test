package jms;

import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import jakarta.jms.MessageConsumer;
import jakarta.jms.MessageProducer;
import jakarta.jms.Queue;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class JMSSelectorTest {
    //http://www.360doc.com/content/09/0712/20/18042_4241298.shtml
    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");

        Connection connection = factory.createConnection();
        connection.start();

        Queue queue = new ActiveMQQueue("testQueue");

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        MessageConsumer consumerA = session.createConsumer(queue, "receiver = 'A'");
        consumerA.setMessageListener(m -> {
            try {
                System.out.println("ConsumerA get " + ((TextMessage) m).getText());
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
        });

        MessageConsumer consumerB = session.createConsumer(queue, "receiver = 'B'");
        consumerB.setMessageListener(m -> {
            try {
                System.out.println("ConsumerB get " + ((TextMessage) m).getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });

        MessageProducer producer = session.createProducer(queue);
        for (int i = 0; i < 10; i++) {
            String receiver = (i % 3 == 0 ? "A" : "B");
            TextMessage message = session.createTextMessage("Message" + i + "，receiver:" + receiver);
            message.setStringProperty("receiver", receiver);
            producer.send(message);
        }
    }

}
