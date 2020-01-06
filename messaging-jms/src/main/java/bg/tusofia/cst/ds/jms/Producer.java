package bg.tusofia.cst.ds.jms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Message producer.
 */
public class Producer {

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false /* no transaction */, Session.AUTO_ACKNOWLEDGE);
        Queue destination = session.createQueue(Constants.QUEUE);
        MessageProducer producer = session.createProducer(destination);

        String text = args.length == 0 ? "opa opa" : args[0];
        TextMessage message = session.createTextMessage(text);

        System.out.println("Sending message to queue '" + Constants.QUEUE + "'...");
        producer.send(message);
        System.out.println("Sent message.");

        connection.close();
    }

}