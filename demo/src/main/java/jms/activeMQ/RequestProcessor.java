package jms.activeMQ;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RequestProcessor {
    /*
     * http://blog.csdn.net/u013256816/article/details/51161548
     */

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://localhost:8161");//
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("RequestQueue");
        MessageConsumer consumer = session.createConsumer(destination);

        RequestProcessor processor = new RequestProcessor();

        while (true) {
            ObjectMessage message = (ObjectMessage) consumer.receive(1000);
            if (null != message) {
                System.out.println(message);
                HashMap<Serializable, Serializable> requestParam = (HashMap<Serializable, Serializable>) message.getObject();
                processor.requestHandler(requestParam);
            } else {
                break;
            }
        }
    }

    public void requestHandler(HashMap<Serializable, Serializable> requestParam) {
        System.out.println("requestHandler....." + requestParam.toString());
        for (Map.Entry<Serializable, Serializable> entry : requestParam.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

}
