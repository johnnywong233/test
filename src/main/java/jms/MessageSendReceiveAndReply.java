package jms;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class MessageSendReceiveAndReply {
    /*
     * http://blog.itpub.net/10742815/viewspace-580334/
     */
    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");

        Connection connection = factory.createConnection();
        connection.start();

        //消息发送到这个Queue
        Queue queue = new ActiveMQQueue("testQueue");

        //消息回复到这个Queue
        Queue replyQueue = new ActiveMQQueue("replyQueue");

        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //创建一个消息，并设置它的JMSReplyTo为replyQueue。
        Message message = session.createTextMessage("Andy");
        message.setJMSReplyTo(replyQueue);

        MessageProducer producer = session.createProducer(queue);
        producer.send(message);

        //消息的接收者
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(m -> {
            try {
                //创建一个新的MessageProducer来发送一个回复消息。
                MessageProducer producer1 = session.createProducer(m.getJMSReplyTo());
                producer1.send(session.createTextMessage("Hello " + ((TextMessage) m).getText()));
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
        });

        //这个接收者用来接收回复的消息
        MessageConsumer consumer2 = session.createConsumer(replyQueue);
        consumer2.setMessageListener(m -> {
            try {
                System.out.println(((TextMessage) m).getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
    }

}
