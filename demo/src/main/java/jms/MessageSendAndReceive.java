package jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class MessageSendAndReceive {
    // http://blog.itpub.net/10742815/viewspace-578457/
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");

        Connection connection = factory.createConnection();
        connection.start();

        Queue queue = new ActiveMQQueue("testQueue");

        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //create TextMessage
        Message message = session.createTextMessage("Hello JMS !");

        //
        MessageProducer producer = session.createProducer(queue);
        producer.send(message);

        System.out.println("Send Message Completed!");

        //创建一个消息消费者来接收这个消息：
        MessageConsumer consumer = session.createConsumer(queue);
        Message receiveMessage = consumer.receive();
        System.out.println(((TextMessage) receiveMessage).getText());
    }
}
