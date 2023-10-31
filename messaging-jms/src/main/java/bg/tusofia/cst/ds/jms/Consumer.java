package bg.tusofia.cst.ds.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Message consumer.
 */
public class Consumer {

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616"); //61616
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false /* no transaction */, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(Constants.QUEUE);
        MessageConsumer consumer = session.createConsumer(destination);

        System.out.println("Waiting for message on queue '" + Constants.QUEUE + "'...");
        Message message = consumer.receive();

        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("Received message '" + textMessage.getText() + "'");
        }
        connection.close();
    }

}