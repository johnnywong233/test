package jms.activeMQ;

import java.io.Serializable;
import java.util.HashMap;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class RequestSubmit {
    /*
     * http://blog.csdn.net/u013256816/article/details/51161548
     */
    private MessageProducer producer;
    private Session session;

    public void init() throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://0.0.0.0:8161");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
        Destination destinatin = session.createQueue("RequestQueue");
        producer = session.createProducer(destinatin);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
    }

    public void submit(HashMap<Serializable, Serializable> requestParam) throws Exception {
        ObjectMessage message = session.createObjectMessage(requestParam);
        producer.send(message);
        session.commit();
    }

    public static void main(String[] args) throws Exception {
        RequestSubmit submit = new RequestSubmit();
        submit.init();
        HashMap<Serializable, Serializable> requestParam = new HashMap<>();
        requestParam.put("johnny", "wong");
        submit.submit(requestParam);
    }


}
