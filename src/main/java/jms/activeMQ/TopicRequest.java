package jms.activeMQ;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class TopicRequest {
	/*
	 * http://blog.csdn.net/u013256816/article/details/51161548
	 */

    //��Ϣ������
    private MessageProducer producer;
    //һ�����ͻ��߽�����Ϣ���߳�
    private Session session;
    //Connection��JMS�ͻ��˵�JMS Provider������
    private Connection connection;
 
    public void init() throws Exception {
        //ConnectionFactory���ӹ�����JMS������������
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://10.10.195.187:61616");
        //�ӹ��칤���еõ����Ӷ���
        connection = connectionFactory.createConnection();
        //����
        connection.start();
        //��ȡ���Ӳ���
        session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("MessageTopic");
        producer = session.createProducer(topic);
        //���ò��־û�
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
    }
 
    public void submit(String mess) throws Exception {
        TextMessage message = session.createTextMessage();
        message.setText(mess);
        producer.send(message);
    }
 
    public void close() {
        try {
            if(session != null)
                session.close();
            if(producer != null)
                producer.close();
            if(connection !=null )
                connection.close();
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
