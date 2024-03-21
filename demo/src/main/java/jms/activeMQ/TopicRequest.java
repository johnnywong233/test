package jms.activeMQ;

import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.DeliveryMode;
import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import jakarta.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class TopicRequest {
	/*
	 * http://blog.csdn.net/u013256816/article/details/51161548
	 */

    private MessageProducer producer;
    private Session session;
    private Connection connection;
 
    public void init() throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://10.10.195.187:61616");
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("MessageTopic");
        producer = session.createProducer(topic);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
    }
 
    public void submit(String mess) throws Exception {
        TextMessage message = session.createTextMessage();
        message.setText(mess);
        producer.send(message);
    }
 
    public void close() {
        try {
            if (session != null) {
                session.close();
            }
            if (producer != null) {
                producer.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        catch (JMSException e) {
            e.printStackTrace();
        }
    }
 
    public static void main(String[] args) throws Exception {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.init();
        topicRequest.submit("I'm first");
        topicRequest.close();
    }

}
